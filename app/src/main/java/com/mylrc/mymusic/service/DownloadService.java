package com.mylrc.mymusic.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.activity.MainActivity;
import com.mylrc.mymusic.activity.SongListActivity;
import com.mylrc.mymusic.database.SongDatabaseHelper;
import com.mylrc.mymusic.network.ImageDownloadUtils;
import com.mylrc.mymusic.network.LyricDownloadUtils;
import com.mylrc.mymusic.network.OkHttpClientManager;
import com.mylrc.mymusic.tool.MusicUrlHelper;
import com.mylrc.mymusic.utils.FileUtils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import okhttp3.Request;
import okhttp3.Response;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.json.JSONException;

public class DownloadService extends Service {

  private static final String CHANNEL_DOWNLOAD = "channel_2";
  private static final String CHANNEL_FAILED = "channel_3";
  private static final String CHANNEL_COMPLETE = "channel_4";

  private static final int NOTIFICATION_FOREGROUND = 22222;
  private static final int NOTIFICATION_COMPLETE = 66666;
  private final Handler uiHandler = new UIHandler(this, Looper.getMainLooper());
  private List<Map<String, String>> downloadTaskList;
  private SharedPreferences preferences;
  private MusicUrlHelper musicUrlHelper;
  private String currentFileName;
  private NotificationManager notificationManager;
  private boolean lrcDownloadSuccess;
  private int totalCount = 0;
  private int successCount = 0;
  private int failCount = 0;
  private int remainingCount = 0;
  private int failNotificationId = 4;

  private void downloadMusic(String musicUrl, String savePath, String fileExtension,
      String musicId, String fileName, String title,
      String singer, String album) throws IOException {

    String safeFileName = fileName
        .replace(":", "：")
        .replace("?", "？")
        .replace("|", "｜")
        .replace("*", "＊")
        .replace("\\", "＼")
        .replace("/", "／")
        .replace("\"", "＂")
        .replace("<", "〈")
        .replace(">", "〉");

    if (safeFileName.getBytes().length > 249) {
      safeFileName = singer.getBytes().length < 249 ? singer : album;
    }

    String musicFilePath = savePath + safeFileName + fileExtension;
    String lrcFilePath = savePath + safeFileName + ".lrc";

    InputStream inputStream = null;
    BufferedOutputStream outputStream = null;

    try {
      Response response = OkHttpClientManager.getInstance()
          .newCall(new Request.Builder().url(musicUrl).build())
          .execute();

      inputStream = response.body().byteStream();
      long totalSize = response.body().contentLength();

      outputStream = new BufferedOutputStream(new FileOutputStream(musicFilePath));

      byte[] buffer = new byte[8192];
      int downloadedSize = 0;
      int readLength;

      while (downloadedSize != totalSize) {
        readLength = inputStream.read(buffer);
        if (readLength == -1) {
          break;
        }
        outputStream.write(buffer, 0, readLength);
        downloadedSize += readLength;
      }

      outputStream.flush();

      successCount++;

      downloadLyrics(musicUrl, musicId, lrcFilePath);

      writeMusicTags(musicUrl, musicId, musicFilePath, title, singer, album, lrcFilePath);

      Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
      mediaScanIntent.setData(Uri.fromFile(new File(musicFilePath)));
      sendBroadcast(mediaScanIntent);

    } catch (Exception e) {
      e.printStackTrace();

      failCount++;

      Message message = new Message();
      message.what = 0;
      uiHandler.sendMessage(message);

      String errorMessage;
      if (musicUrl == null || !musicUrl.startsWith("http")) {
        errorMessage = "无版权、或者为数字专辑";
      } else {
        errorMessage = "错误:" + e;
      }
      showFailNotification(singer + " - " + title, errorMessage);

    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (outputStream != null) {
        try {
          outputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void downloadLyrics(String musicUrl, String musicId, String lrcFilePath)
      throws JSONException, IOException {
    int lrcMode = preferences.getInt("lrcmode", 0);
    int textMode = preferences.getInt("textmode", 0);
    int encoding = preferences.getInt("type", 0);
    String encodingType = encoding == 0 ? "utf-8" : "gbk";

    if (musicUrl.contains("kugou")) {
      if (lrcMode == 1) {
        if (textMode == 0) {
          lrcDownloadSuccess = LyricDownloadUtils.downloadKugouLyric(musicId, lrcFilePath,
              encodingType);
        } else {
          LyricDownloadUtils.downloadKugouLyricAlt(musicId, lrcFilePath, encodingType);
        }
      }
    } else if (musicUrl.contains("ymusic")) {
      if (lrcMode == 1) {
        if (textMode == 0) {
          lrcDownloadSuccess = LyricDownloadUtils.downloadNeteaseCloudLyric(musicId, lrcFilePath,
              encodingType);
        } else {
          LyricDownloadUtils.downloadNeteaseCloudLyricWithTranslation(musicId, lrcFilePath,
              encodingType);
        }
      }
    } else if (musicUrl.contains("kuwo")) {
      if (lrcMode == 1) {
        if (textMode == 0) {
          lrcDownloadSuccess = LyricDownloadUtils.downloadKuwoLyric(musicId, lrcFilePath,
              encodingType);
        } else {
          LyricDownloadUtils.downloadKuwoLyric(musicId, lrcFilePath, encodingType);
        }
      }
    } else if (musicUrl.contains("vkey=")) {
      if (lrcMode == 1) {
        if (textMode == 0) {
          lrcDownloadSuccess = LyricDownloadUtils.downloadQQMusicLyric(musicId, lrcFilePath,
              encodingType);
        } else {
          LyricDownloadUtils.downloadQQMusicLyricWithTranslation(musicId, lrcFilePath,
              encodingType);
        }
      }
    }
  }

  private void writeMusicTags(String musicUrl, String musicId, String musicFilePath,
      String title, String singer, String album, String lrcFilePath) {
    try {
      String binPath = Environment.getExternalStorageDirectory().getAbsolutePath()
          + "/MusicDownloader/bin";

      int textMode = preferences.getInt("textmode", 0);
      String coverPath = null;

      if (preferences.getInt("lrcmode", 0) == 1 && !lrcDownloadSuccess) {
        lrcFilePath = null;
      }

      if (textMode == 0) {
        if (musicUrl.contains("kugou")) {
          if (ImageDownloadUtils.downloadKugouCover(musicId, binPath)) {
            coverPath = binPath;
          }
        } else if (musicUrl.contains("ymusic")) {
          if (ImageDownloadUtils.downloadNeteaseCloudCover(musicId, binPath)) {
            coverPath = binPath;
          }
        } else if (musicUrl.contains("kuwo")) {
          if (ImageDownloadUtils.downloadKuwoCover(musicId, binPath)) {
            coverPath = binPath;
          }
        } else if (musicUrl.contains("vkey=")) {
          if (ImageDownloadUtils.downloadQQMusicCoverBySongMid(musicId, binPath)) {
            coverPath = binPath;
          }
        }
      }

      FileUtils.writeId3Tags(musicFilePath, title, singer, album, coverPath, lrcFilePath);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void showFailNotification(String title, String errorMessage) {
    failNotificationId++;

    Notification.Builder builder = new Notification.Builder(this)
        .setSmallIcon(R.drawable.ic_music)
        .setContentTitle(title)
        .setSound(null)
        .setContentText("下载失败：" + errorMessage);

    if (Build.VERSION.SDK_INT >= 26) {
      notificationManager.createNotificationChannel(
          new NotificationChannel(CHANNEL_FAILED, "下载失败通知",
              NotificationManager.IMPORTANCE_LOW));
      builder.setChannelId(CHANNEL_FAILED);
    }

    notificationManager.notify(failNotificationId, builder.build());
  }

  private void initDownloadTasks(int source) {
    downloadTaskList = new ArrayList<>();

    downloadTaskList = source == 0 ? MainActivity.globalMusicList : SongListActivity.downloadList;

    totalCount = downloadTaskList.size();

    new DownloadThread(this).start();
  }

  private void updateForegroundNotification(int status) {
    Notification.Builder builder;

    if (Build.VERSION.SDK_INT < 26) {
      builder = new Notification.Builder(getApplicationContext())
          .setSmallIcon(R.drawable.ic_music)
          .setSound(null);

      if (status == 0) {
        builder.setContentTitle("下载器正在初始化中...")
            .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_music))
            .setContentText("如果长时间停留在此界面，请截图反馈给我们");
      } else {
        builder.setContentTitle("正在下载：" + currentFileName)
            .setContentText(String.format("总共/成功/失败/剩余：   %d/%d/%d/%d",
                totalCount, successCount, failCount, remainingCount));
      }
    } else {
      notificationManager.createNotificationChannel(
          new NotificationChannel(CHANNEL_DOWNLOAD, "后台下载服务",
              NotificationManager.IMPORTANCE_LOW));

      builder = new Notification.Builder(getApplicationContext(), CHANNEL_DOWNLOAD)
          .setSmallIcon(R.drawable.ic_music)
          .setSound(null);

      if (status == 0) {
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_music))
            .setContentTitle("下载器正在初始化中...")
            .setContentText("如果长时间停留在此界面，请截图反馈给我们");
      } else {
        builder.setContentTitle("正在下载：" + currentFileName)
            .setContentText(String.format("总共/成功/失败/剩余：   %d/%d/%d/%d",
                totalCount, successCount, failCount, remainingCount));
      }
    }

    startForeground(NOTIFICATION_FOREGROUND, builder.build());
  }

  private void showCompleteNotification() {
    long[] vibrationPattern = {0, 800};

    Notification.Builder builder = new Notification.Builder(this)
        .setSmallIcon(R.drawable.ic_music)
        .setContentTitle("批量下载完成")
        .setTicker("批量下载完成")
        .setContentText(String.format("总共／成功／失败：   %d／%d／%d",
            totalCount, successCount, failCount))
        .setSound(null)
        .setVibrate(vibrationPattern);

    if (Build.VERSION.SDK_INT >= 26) {
      notificationManager.createNotificationChannel(
          new NotificationChannel(CHANNEL_COMPLETE, "下载完成通知",
              NotificationManager.IMPORTANCE_LOW));
      builder.setChannelId(CHANNEL_COMPLETE);
    }

    notificationManager.notify(NOTIFICATION_COMPLETE, builder.build());
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    preferences = getSharedPreferences("pms", 0);
    musicUrlHelper = new MusicUrlHelper();
    notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    updateForegroundNotification(0);
    SQLiteDatabase database = new SongDatabaseHelper(getApplicationContext()).getWritableDatabase();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (intent != null) {
      String source = intent.getStringExtra("where");
      initDownloadTasks("0".equals(source) ? 0 : 1);
    } else {
      Toast.makeText(getApplicationContext(), "下载器出现异常，已自动关闭",
          Toast.LENGTH_SHORT).show();
      stopSelf();
    }
    return super.onStartCommand(intent, flags, startId);
  }

  class DownloadThread extends Thread {

    private final DownloadService service;

    DownloadThread(DownloadService service) {
      this.service = service;
    }

    @Override
    public void run() {
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException e) {
      }

      for (int i = 0; i < service.downloadTaskList.size(); i++) {
        service.remainingCount = service.downloadTaskList.size() - i;

        Message message = new Message();
        message.what = 0;
        service.uiHandler.sendMessage(message);

        Map<String, String> task = service.downloadTaskList.get(i);
        String downloadPath = task.get("path");
        String fileExtension = task.get("end");
        String musicId = task.get("id");
        String fileName = task.get("file");
        String title = task.get("title");
        String singer = task.get("singer");
        String album = task.get("album");
        String musicType = task.get("type");
        String bitrate = task.get("br");

        service.currentFileName = fileName;

        String musicUrl = null;
        try {
          musicUrl = service.musicUrlHelper.getMusicUrl(musicType, musicId, bitrate);
        } catch (JSONException e) {
          throw new RuntimeException(e);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

        String albumName = album.equals(" ") ? FrameBodyCOMM.DEFAULT : album;

        try {
          service.downloadMusic(musicUrl, downloadPath, fileExtension, musicId,
              fileName, title, singer, albumName);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      service.showCompleteNotification();
      service.stopSelf();
    }
  }

  class UIHandler extends Handler {

    private final DownloadService service;

    UIHandler(DownloadService service, Looper looper) {
      super(looper);
      this.service = service;
    }

    @Override
    public void handleMessage(Message message) {
      service.updateForegroundNotification(1);
      super.handleMessage(message);
    }
  }
}