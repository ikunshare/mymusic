package com.mylrc.mymusic.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.model.GlobalData;
import com.mylrc.mymusic.network.ImageDownloadUtils;
import com.mylrc.mymusic.network.LyricDownloadUtils;
import com.mylrc.mymusic.receiver.HeadsetButtonReceiver;
import com.mylrc.mymusic.tool.LrcView;
import com.mylrc.mymusic.tool.MusicUrlHelper;
import com.mylrc.mymusic.utils.FileUtils;
import com.mylrc.mymusic.utils.ToastUtils;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.jaudiotagger.tag.mp4.atom.Mp4NameBox;
import org.json.JSONException;

public class PlayerService extends Service {

  private static final String PLATFORM_KUGOU = "kugou";
  private static final String PLATFORM_NETEASE = "wyy";
  private static final String PLATFORM_MIGU = "migu";
  private static final String PLATFORM_QQ = "qq";
  private static final String PLATFORM_KUWO = "kuwo";

  private static final int STATE_PLAYING = 291;
  private static final int STATE_PAUSED = 292;

  public static MediaPlayer mediaPlayer = new MediaPlayer();
  private final Runnable lyricUpdateRunnable = new LyricUpdateRunnable(this);
  private final Handler uiHandler = new UIHandler(this, Looper.getMainLooper());
  private final AudioManager.OnAudioFocusChangeListener audioFocusChangeListener =
      new AudioFocusChangeListener(this);
  private View floatingLyricView;
  private LrcView lrcView;
  private int lyricDisplayMode;
  private int coverDisplayMode;
  private String lyricFilePath;
  private String coverImagePath;
  private List<Map<String, Object>> playList;
  private MusicUrlHelper musicUrlHelper;
  private SharedPreferences preferences;
  private WindowManager windowManager;
  private AudioManager audioManager;
  private NotificationManager notificationManager;
  private String currentPlatform;
  private String currentMusicTitle;
  private WindowManager.LayoutParams floatingWindowParams;
  private WifiManager.WifiLock wifiLock;

  private void initService() {
    preferences = getSharedPreferences("pms", 0);
    musicUrlHelper = new MusicUrlHelper();
    windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    if (mediaPlayer == null) {
      mediaPlayer = new MediaPlayer();
    }

    WifiManager wifiManager = (WifiManager) getApplicationContext()
        .getSystemService(WIFI_SERVICE);
    if (wifiManager != null) {
      wifiLock = wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL, "MusicPlayer");
      wifiLock.acquire();
    }

    String basePath = getFilesDir().getParent() + "/app_tmpFile/";
    lyricFilePath = basePath + "listen_tmp.lrc";
    coverImagePath = basePath + "listen_tmp.jpg";

    initFloatingLyric();

    registerReceivers();
  }

  private void initFloatingLyric() {
    try {
      LayoutInflater inflater = LayoutInflater.from(this);
      floatingLyricView = inflater.inflate(R.layout.desktop_lyric, null);

      TextView currentLyricTextView = floatingLyricView.findViewById(R.id.lrclayTextView1);
      TextView nextLyricTextView = floatingLyricView.findViewById(R.id.lrclayTextView2);

      floatingWindowParams = new WindowManager.LayoutParams();
      if (Build.VERSION.SDK_INT >= 26) {
        floatingWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
      } else {
        floatingWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
      }
      floatingWindowParams.format = 1;
      floatingWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
      floatingWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
      floatingWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @SuppressLint("UnspecifiedRegisterReceiverFlag")
  private void registerReceivers() {
    IntentFilter controlFilter = new IntentFilter();
    controlFilter.addAction("com.mylrc.mymusic.ac");
    controlFilter.addAction("android.intent.action.HEADSET_PLUG");
    controlFilter.addAction("com.music.remove");
    controlFilter.addAction("com.music.add");
    registerReceiver(new PlayControlReceiver(), controlFilter);

    ComponentName component = new ComponentName(getPackageName(),
        HeadsetButtonReceiver.class.getName());
    audioManager.registerMediaButtonEventReceiver(component);
  }

  private void prepareAndPlay() {
    try {
      if (playList == null || playList.isEmpty()) {
        showToast("播放列表为空");
        return;
      }

      int currentIndex = GlobalData.currentIndex;
      currentPlatform = GlobalData.currentSource;

      Map<String, Object> currentSong = playList.get(currentIndex);

      Object nameObj = currentSong.get(Mp4NameBox.IDENTIFIER);
      Object singerObj = currentSong.get("singer");

      GlobalData.currentMusicName = nameObj != null ? nameObj.toString() : "Unknown";
      GlobalData.currentArtist = singerObj != null ? singerObj.toString() : "Unknown";
      currentMusicTitle = GlobalData.currentMusicName + " - " + GlobalData.currentArtist;

      String musicUrl = null;

      if (PLATFORM_KUGOU.equals(currentPlatform)) {
        musicUrl = getMusicUrlKugou(currentSong);
      } else if (PLATFORM_NETEASE.equals(currentPlatform)) {
        musicUrl = getMusicUrlNetease(currentSong);
      } else if (PLATFORM_MIGU.equals(currentPlatform)) {
        musicUrl = getMusicUrlMigu(currentSong);
      } else if (PLATFORM_QQ.equals(currentPlatform)) {
        musicUrl = getMusicUrlQQ(currentSong);
      } else if (PLATFORM_KUWO.equals(currentPlatform)) {
        musicUrl = getMusicUrlKuwo(currentSong);
      }

      if (musicUrl != null) {
        playMusic(musicUrl);
      }

    } catch (Exception e) {
      e.printStackTrace();
      showToast("播放失败");
    }
  }

  private String getMusicUrlKugou(Map<String, Object> song) throws JSONException, IOException {
    Object fileHashObj = song.get("filehash");
    if (fileHashObj == null) {
      showToast("歌曲信息不完整");
      playNext();
      return null;
    }

    String fileHash = fileHashObj.toString();
    String musicId = fileHash.substring(0, fileHash.indexOf("低高"));

    Object payTypeObj = song.get("pay");
    Object copyrightObj = song.get("cy");
    String payType = payTypeObj != null ? payTypeObj.toString() : "";
    String copyright = copyrightObj != null ? copyrightObj.toString() : "";

    if ("pay".equals(payType) || "5".equals(copyright)) {
      showToast(currentMusicTitle + "：无版权或者为数字专辑，不能播放！下一曲");
      playNext();
      return null;
    }

    if (LyricDownloadUtils.downloadKugouLyric(musicId, lyricFilePath, "utf-8")) {
      lyricDisplayMode = 2;
    } else {
      lyricDisplayMode = 1;
    }

    return musicUrlHelper.getMusicUrl(PLATFORM_KUGOU, musicId, "mp3");
  }

  private String getMusicUrlNetease(Map<String, Object> song) throws JSONException, IOException {
    Object musicIdObj = song.get("id");
    if (musicIdObj == null) {
      showToast("歌曲信息不完整");
      playNext();
      return null;
    }

    String musicId = musicIdObj.toString();

    Object payTypeObj = song.get("pay");
    Object copyrightObj = song.get("cy");
    String payType = payTypeObj != null ? payTypeObj.toString() : "";
    String copyright = copyrightObj != null ? copyrightObj.toString() : "";

    if ("4".equals(payType) || "-200".equals(copyright)) {
      showToast(currentMusicTitle + "：无版权或者为数字专辑，不能播放！下一曲");
      playNext();
      return null;
    }

    LyricDownloadUtils.downloadNeteaseCloudLyric(musicId, lyricFilePath, "utf-8");

    return musicUrlHelper.getMusicUrl(PLATFORM_NETEASE, musicId, "mp3");
  }

  private String getMusicUrlMigu(Map<String, Object> song) throws IOException, JSONException {
    Object musicIdObj = song.get("id");
    Object lrcUrlObj = song.get("lrc");

    if (musicIdObj == null) {
      showToast("歌曲信息不完整");
      playNext();
      return null;
    }

    String musicId = musicIdObj.toString();
    String lrcUrl = lrcUrlObj != null ? lrcUrlObj.toString() : "";

    if (!lrcUrl.isEmpty()) {
      LyricDownloadUtils.downloadLyricFromUrl(lrcUrl, lyricFilePath, "utf-8");
    }
    lyricDisplayMode = 1;

    return musicUrlHelper.getMusicUrl(PLATFORM_MIGU, musicId, "mp3");
  }

  private String getMusicUrlQQ(Map<String, Object> song) throws IOException, JSONException {
    Object musicIdObj = song.get("id");
    if (musicIdObj == null) {
      showToast("歌曲信息不完整");
      playNext();
      return null;
    }

    String musicId = musicIdObj.toString();

    LyricDownloadUtils.downloadQQMusicLyric(musicId, lyricFilePath, "utf-8");

    return musicUrlHelper.getMusicUrl(PLATFORM_QQ, musicId, "mp3");
  }

  private String getMusicUrlKuwo(Map<String, Object> song) throws JSONException, IOException {
    Object musicIdObj = song.get("id");
    if (musicIdObj == null) {
      showToast("歌曲信息不完整");
      playNext();
      return null;
    }

    String musicId = musicIdObj.toString();

    LyricDownloadUtils.downloadKuwoLyric(musicId, lyricFilePath, "utf-8");
    lyricDisplayMode = 1;

    return musicUrlHelper.getMusicUrl(PLATFORM_KUWO, musicId, "mp3");
  }

  private void playMusic(String musicUrl) {
    try {
      if (mediaPlayer == null) {
        mediaPlayer = new MediaPlayer();
      }

      mediaPlayer.reset();

      if (musicUrl == null || musicUrl.contains("版本")) {
        uiHandler.post(new VersionCheckRunnable(this, musicUrl));
        if (musicUrl == null || !musicUrl.contains("版本")) {
          uiHandler.postDelayed(new CoverDownloadRunnable(this), 5000);
        }
        return;
      }

      mediaPlayer.setWakeMode(this, 1);
      mediaPlayer.setDataSource(musicUrl);
      mediaPlayer.prepareAsync();

      mediaPlayer.setOnPreparedListener(new PreparedListener(this));
      mediaPlayer.setOnCompletionListener(new CompletionListener(this));
      mediaPlayer.setOnErrorListener(new ErrorListener(this));

    } catch (Exception e) {
      e.printStackTrace();
      showToast("播放失败");
    }

    downloadCover();
  }

  private void downloadCover() {
    new CoverDownloadThread(this, currentPlatform).start();
  }

  private void startPlayMusic() {
    new PlayControlThread(this).start();
  }

  public void playNext() {
    try {
      if (playList == null || playList.isEmpty()) {
        showToast("播放列表为空");
        return;
      }

      int currentIndex = GlobalData.currentIndex;
      int playMode = GlobalData.playMode;

      // Play mode 0: Sequential play, move to next
      // Play mode 1: Single repeat, should NOT be called from here
      // Play mode 2: List repeat, wrap around to first song
      if (playMode == 2) {
        currentIndex = (currentIndex + 1) % playList.size();
      } else {
        currentIndex++;
        if (currentIndex >= playList.size()) {
          showToast("已经是最后一首了");
          return;
        }
      }

      GlobalData.currentIndex = currentIndex;
      startPlayMusic();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void playPrevious() {
    try {
      if (playList == null || playList.isEmpty()) {
        showToast("播放列表为空");
        return;
      }

      int currentIndex = GlobalData.currentIndex;
      currentIndex--;

      if (currentIndex < 0) {
        showToast("已经是第一首了");
        return;
      }

      GlobalData.currentIndex = currentIndex;
      startPlayMusic();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void playRandom() {
    try {
      if (playList == null || playList.isEmpty()) {
        showToast("播放列表为空");
        return;
      }

      Random random = new Random();
      int randomIndex = random.nextInt(playList.size());
      GlobalData.currentIndex = randomIndex;

      startPlayMusic();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void resume() {
    try {
      int previousPlayIndex = 0;

      if (mediaPlayer != null) {
        Message message = new Message();
        message.what = 1;
        uiHandler.sendMessage(message);

        mediaPlayer.start();
        mediaPlayer.setWakeMode(this, 1);
        GlobalData.playState = STATE_PLAYING;

        requestAudioFocus();
      }
    } catch (IllegalStateException e) {
      e.printStackTrace();
    }
  }

  public void pause() {
    try {
      if (mediaPlayer != null) {
        Message message = new Message();
        message.what = 1;
        uiHandler.sendMessage(message);

        mediaPlayer.pause();
        GlobalData.playState = STATE_PAUSED;
      }
    } catch (IllegalStateException e) {
      e.printStackTrace();
    }
  }

  public void stopPlayback() {
    try {
      if (mediaPlayer != null) {
        mediaPlayer.stop();
        mediaPlayer.reset();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void showFloatingLyric() {
    try {
      if (windowManager != null) {
        windowManager.addView(floatingLyricView, floatingWindowParams);
      } else {
        removeFloatingLyric();
      }
      updateLyric();
    } catch (Exception e) {
      showToast("请先授权悬浮窗权限，才能显示桌面歌词！");
    }
  }

  public void removeFloatingLyric() {
    try {
      if (windowManager != null) {
        windowManager.removeView(floatingLyricView);
        uiHandler.removeCallbacks(lyricUpdateRunnable);
      }
    } catch (Exception e) {
      showToast("移除桌面歌词出错！");
    }
  }

  public void updateLyric() {
    uiHandler.post(lyricUpdateRunnable);
  }

  private void updateNotification() {
    try {
      RemoteViews remoteViews = new RemoteViews(getPackageName(),
          R.layout.nplayer);

      remoteViews.setTextViewText(R.id.bigplayerTextView1, GlobalData.currentMusicName);
      remoteViews.setTextViewText(R.id.bigplayerTextView2, GlobalData.currentArtist);

      remoteViews.setOnClickPendingIntent(R.id.bigplayerImageView3,
          createPendingIntent(1));
      remoteViews.setOnClickPendingIntent(R.id.bigplayerImageView5,
          createPendingIntent(2));
      remoteViews.setOnClickPendingIntent(R.id.bigplayerImageView2,
          createPendingIntent(3));

      Notification.Builder builder = null;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        builder = new Notification.Builder(this)
            .setSmallIcon(R.drawable.ic_music)
            .setCustomContentView(remoteViews)
            .setOngoing(true);
      }

      if (Build.VERSION.SDK_INT >= 26) {
        notificationManager.createNotificationChannel(
            new NotificationChannel("channel_8", "后台播放服务",
                NotificationManager.IMPORTANCE_LOW));
        builder.setChannelId("channel_8");
      }

      startForeground(1, builder.build());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected Intent createControlIntent(int control) {
    Intent intent = new Intent();
    intent.setAction("com.mylrc.mymusic.ac");
    intent.putExtra("control", control);
    return intent;
  }

  private PendingIntent createPendingIntent(int control) {
    Intent intent = createControlIntent(control);
    return PendingIntent.getBroadcast(this, control, intent,
        PendingIntent.FLAG_UPDATE_CURRENT);
  }

  public void requestAudioFocus() {
    if (audioFocusChangeListener != null) {
      int result = audioManager.requestAudioFocus(audioFocusChangeListener,
          AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }
  }

  protected void broadcastAction() {
    Intent intent = new Intent();
    intent.setAction("com.music.upview");
    sendBroadcast(intent);
  }

  private void showToast(String message) {
    uiHandler.post(() -> ToastUtils.showToast(this, message));
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    initService();
  }

  @Override
  public void onDestroy() {
    try {
      if (wifiLock != null) {
        wifiLock.release();
      }

      if (preferences.getInt("win", 1) == 0) {
        removeFloatingLyric();
      }

      if (mediaPlayer != null) {
        mediaPlayer.stop();
      }

      audioManager.abandonAudioFocus(audioFocusChangeListener);

      stopForeground(true);

    } catch (IllegalStateException e) {
      e.printStackTrace();
    }

    super.onDestroy();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    playList = GlobalData.playList;
    FileUtils.createDirectory(lyricFilePath);
    FileUtils.createDirectory(coverImagePath);

    new PlayControlThread(this).start();

    return super.onStartCommand(intent, flags, startId);
  }

  class VersionCheckRunnable implements Runnable {

    private final String message;
    private final PlayerService service;

    VersionCheckRunnable(PlayerService service, String message) {
      this.service = service;
      this.message = message;
    }

    @Override
    public void run() {
      if (message != null && message.contains("版本")) {
        ToastUtils.showToast(service, "当前版本过低，请先升级版本");
      }
    }
  }

  class CoverDownloadRunnable implements Runnable {

    private final PlayerService service;

    CoverDownloadRunnable(PlayerService service) {
      this.service = service;
    }

    @Override
    public void run() {
      FileUtils.createDirectory(service.lyricFilePath);
      FileUtils.createDirectory(service.coverImagePath);

      int playMode = GlobalData.playMode;
      if (playMode == 0 || playMode == 1) {
        service.playNext();
      } else {
        service.startPlayMusic();
      }
    }
  }

  class CoverDownloadThread extends Thread {

    private final String platform;
    private final PlayerService service;

    CoverDownloadThread(PlayerService service, String platform) {
      this.service = service;
      this.platform = platform;
    }

    @Override
    public void run() {
      try {
        int currentIndex = GlobalData.currentIndex;
        Map<String, Object> currentSong = GlobalData.playList.get(currentIndex);

        if (PLATFORM_KUGOU.equals(platform)) {
          Object fileHashObj = currentSong.get("filehash");
          if (fileHashObj != null) {
            String fileHash = fileHashObj.toString();
            int separatorIndex = fileHash.indexOf("低高");
            if (separatorIndex > 0) {
              String id = fileHash.substring(0, separatorIndex);
              ImageDownloadUtils.downloadKugouCover(id, service.coverImagePath);
            }
          }

        } else if (PLATFORM_NETEASE.equals(platform)) {
          Object idObj = currentSong.get("id");
          if (idObj != null) {
            String id = idObj.toString();
            ImageDownloadUtils.downloadNeteaseCloudCover(id, service.coverImagePath);
          }

        } else if (PLATFORM_MIGU.equals(platform)) {
          Object imgUrlObj = currentSong.get("imgurl");
          if (imgUrlObj != null) {
            String imgUrl = imgUrlObj.toString();
            ImageDownloadUtils.downloadImageToFile(imgUrl, service.coverImagePath);
          }

        } else if (PLATFORM_QQ.equals(platform)) {
          Object albumIdObj = currentSong.get("albumid");
          if (albumIdObj != null) {
            String albumId = albumIdObj.toString();
            ImageDownloadUtils.downloadQQMusicCover(albumId, service.coverImagePath);
          }

        } else if (PLATFORM_KUWO.equals(platform)) {
          Object idObj = currentSong.get("id");
          if (idObj != null) {
            String id = idObj.toString();
            ImageDownloadUtils.downloadKuwoCover(id, service.coverImagePath);
          }
        }

        Intent intent = new Intent();
        intent.setAction("com.music.upview2");
        service.sendBroadcast(intent);

        Message message = new Message();
        message.what = 1;
        service.uiHandler.sendMessage(message);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  class AudioFocusChangeListener implements AudioManager.OnAudioFocusChangeListener {

    private final PlayerService service;

    AudioFocusChangeListener(PlayerService service) {
      this.service = service;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
      try {
        switch (focusChange) {
          case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
            if (mediaPlayer != null && mediaPlayer.isPlaying()
                && service.preferences.getInt("pm", 1) == 1) {
              service.pause();
            }
            break;

          case AudioManager.AUDIOFOCUS_LOSS:
            if (mediaPlayer != null && mediaPlayer.isPlaying()
                && service.preferences.getInt("pm", 1) == 1) {
              service.pause();
            }
            break;

          case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
            if (mediaPlayer != null && mediaPlayer.isPlaying()
                && service.preferences.getInt("pm", 1) == 1) {
              service.pause();
            }
            break;

          case AudioManager.AUDIOFOCUS_GAIN:
            if (mediaPlayer != null && !mediaPlayer.isPlaying()
                && service.preferences.getInt("pm", 1) == 1) {
              service.resume();
            }
            break;
        }
      } catch (IllegalStateException e) {
        e.printStackTrace();
      }
    }
  }

  class LyricUpdateRunnable implements Runnable {

    private final PlayerService service;

    LyricUpdateRunnable(PlayerService service) {
      this.service = service;
    }

    @Override
    public void run() {
      try {
        if (mediaPlayer != null && service.lrcView != null) {
          long currentPosition = mediaPlayer.getCurrentPosition();
          service.lrcView.findShowLine(currentPosition);

          service.uiHandler.postDelayed(this, 100);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  class UIHandler extends Handler {

    private final PlayerService service;

    UIHandler(PlayerService service, Looper looper) {
      super(looper);
      this.service = service;
    }

    @Override
    public void handleMessage(Message message) {
      if (message.what == 1) {
        service.updateNotification();
      }
      super.handleMessage(message);
    }
  }

  class PreparedListener implements MediaPlayer.OnPreparedListener {

    private final PlayerService service;

    PreparedListener(PlayerService service) {
      this.service = service;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
      try {
        service.resume();
        service.broadcastAction();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  class CompletionListener implements MediaPlayer.OnCompletionListener {

    private final PlayerService service;

    CompletionListener(PlayerService service) {
      this.service = service;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
      try {
        int playMode = GlobalData.playMode;
        // Play mode 0: Sequential play - go to next song
        // Play mode 1: Single repeat - replay current song
        // Play mode 2: List repeat - replay from beginning
        // Play mode 3: Random play - play random song
        if (playMode == 0) {
          service.playNext();
        } else if (playMode == 1) {
          service.startPlayMusic();  // Replay current song
        } else if (playMode == 2) {
          service.playNext();  // Will wrap around to first song
        } else if (playMode == 3) {
          service.playRandom();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  class ErrorListener implements MediaPlayer.OnErrorListener {

    private final PlayerService service;

    ErrorListener(PlayerService service) {
      this.service = service;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
      try {
        service.showToast("播放出错，尝试播放下一首");
        service.playNext();
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
  }

  class PlayControlThread extends Thread {

    private final PlayerService service;

    PlayControlThread(PlayerService service) {
      this.service = service;
    }

    @Override
    public void run() {
      try {
        service.prepareAndPlay();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  class PlayControlReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      try {
        String action = intent.getAction();

        // Handle headset plug/unplug
        if (intent.hasExtra("state")) {
          int state = intent.getIntExtra("state", 0);
          if (state == 0) {  // Headset unplugged
            pause();
          }
        }

        // Handle notification actions
        if ("com.music.remove".equals(action)) {
          removeFloatingLyric();
        } else if ("com.music.add".equals(action)) {
          showFloatingLyric();
        }

        // Handle playback control codes
        int control = intent.getIntExtra("control", -1);
        switch (control) {
          case 0x123:  // 291 - Play/Resume
            resume();
            break;

          case 0x124:  // 292 - Pause
            pause();
            break;

          case 0x125:  // 293 - Stop
            stopPlayback();
            break;

          case 0x126:  // 294 - Next
            playNext();
            break;

          case 0x127:  // 295 - Previous
            playPrevious();
            break;

          case 0x128:  // 296 - Exit
            Intent exitIntent = new Intent("com.music.exit");
            sendBroadcast(exitIntent);
            stopSelf();
            break;

          // Legacy control codes (for backward compatibility)
          case 1:
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
              pause();
            } else {
              resume();
            }
            break;

          case 2:
            playNext();
            break;

          case 3:
            playPrevious();
            break;

          case 4:
            stopPlayback();
            break;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}