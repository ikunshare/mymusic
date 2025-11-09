package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.database.SongDatabaseHelper;
import com.mylrc.mymusic.enums.MusicPlatform;
import com.mylrc.mymusic.enums.NumberEnum;
import com.mylrc.mymusic.enums.StatusBarColor;
import com.mylrc.mymusic.manager.AppUpdateManager;
import com.mylrc.mymusic.manager.HistoryManager;
import com.mylrc.mymusic.manager.StatusBarManager;
import com.mylrc.mymusic.model.GlobalData;
import com.mylrc.mymusic.network.DownloadUtils;
import com.mylrc.mymusic.network.HttpRequestUtils;
import com.mylrc.mymusic.network.ImageDownloadUtils;
import com.mylrc.mymusic.network.LyricDownloadUtils;
import com.mylrc.mymusic.network.MusicSearchUtils;
import com.mylrc.mymusic.network.MvDownloader;
import com.mylrc.mymusic.network.MvUrlParser;
import com.mylrc.mymusic.network.OkHttpClient;
import com.mylrc.mymusic.service.DownloadService;
import com.mylrc.mymusic.service.PlayerService;
import com.mylrc.mymusic.tool.MusicUrlHelper;
import com.mylrc.mymusic.ui.dialog.DialogFactory;
import com.mylrc.mymusic.ui.dialog.DialogHelper;
import com.mylrc.mymusic.ui.viewholder.CheckBoxHolder;
import com.mylrc.mymusic.utils.CommonUtils;
import com.mylrc.mymusic.utils.ContextHolder;
import com.mylrc.mymusic.utils.FileUtils;
import com.mylrc.mymusic.utils.ToastUtils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import okhttp3.Response;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.mp4.atom.Mp4DataBox;
import org.jaudiotagger.tag.mp4.atom.Mp4NameBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

  public static ArrayList globalMusicList;
  public static MediaPlayer mediaPlayer;
  static String sdCardPath;
  private static long aLong;
  final String SONG_LIST_TABLE = "song_list";
  final String HTTP_PREFIX = "http";
  final String SLASH = "/";
  final String LRC_EXT = ".lrc";
  final int BUFFER_SIZE = 8192;
  final int HTTP_OK = 200;
  final int MAX_FILENAME_BYTES = 249;
  final int ERROR_CODE = 22;
  final int COMPLETE_CODE = 17;
  MusicPlatform currentMusicPlatform;
  String currentSource;
  String mvUrl;
  View clearSearchLayout;
  SharedPreferences appPreferences;
  String playerSource;
  String noticeMessage1;
  String dialogMessage;
  View playerControlLayout;
  TextView downloadDialogMsg;
  DrawerLayout leftMenuLayout;
  TextView downloadProgressView;
  Button cancelDownloadButton;
  ImageView avatarImageView;
  ProgressBar downloadProgressBar;
  String currentDownloadFileName;
  ImageView qualityCoverImageView;
  boolean hasLyricFile;
  Dialog qualityDialog;
  EditText searchEditText;
  String searchText;
  ListView leftListView;
  Map<Integer, Boolean> leftSelectionMap;
  ListView rightListView;
  Bitmap tempBitmap;
  Button leftButton1;
  Button leftButton2;
  Button leftButton3;
  ImageView coverImageView;
  ImageView batchDownloadImageView;
  ImageView menuImageView;
  ImageView selectAllImageView;
  String coverImageId;
  TextView titleTextView;
  MusicUrlHelper musicUrlHelper;
  Map<String, Object> searchSuggestionMap;
  ContextHolder appContextHolder;
  Dialog loadingDialog;
  boolean isNoticeRead;
  ListView historyListView;
  String noticeContent;
  View noticeLayout;
  ImageView searchLoadingImageView;
  TextView noticeTextView;
  List<Map<String, Object>> searchSuggestions;
  TextView subtitleTextView;
  List<Map<String, Object>> playList;
  Dialog downloadProgressDialog;
  int noticeCountdownSeconds;
  List<Map<String, Object>> leftMusicList;
  String noticeButtonText;
  List<Map<String, Object>> leftDisplayList;
  List<Map<String, Object>> rightMusicList;
  List<Map<String, Object>> rightDisplayList;
  AudioManager audioManager;
  long lastSearchSuggestionTime;
  int leftListSelectedIndex;
  ArrayList<String> selectedSongs = new ArrayList<>();
  int playListIndex = 0;
  Bitmap coverBitmap = null;
  boolean isSearchEnabled = true;
  boolean isDownloading = false;
  NumberEnum currentSearchEngine = NumberEnum.ONE;
  boolean isDownloadCancelled = false;
  int searchControlFlag = 0;
  boolean isLeftMultiSelectMode = false;
  boolean isRightMultiSelectMode = false;
  int leftScrollPosition = 0;
  int rightScrollPosition = 0;
  String downloadDirectory = null;
  String searchKeyword = FrameBodyCOMM.DEFAULT;
  float touchDownX = 0.0f;
  float touchUpX = 0.0f;
  float touchDownY = 0.0f;
  float touchUpY = 0.0f;
  boolean canShowQualityDialog = true;
  Handler mainHandler = new i0(this, Looper.getMainLooper());
  private ArrayList<String> mvUrlList;
  private String kugouStandardHash;
  private String kugouHighQualityHash;
  private String kugouLosslessHash;
  private String kugouHiResHash;
  private String kugouDSDHash;
  private RotateAnimation rotateAnimation;
  private SQLiteDatabase database;
  private SimpleAdapter leftListAdapter;
  private SimpleAdapter rightListAdapter;
  private HashMap<Integer, Boolean> rightSelectionMap;
  private PopupWindow suggestionPopup;
  private View suggestionContentView;
  private SimpleAdapter suggestionAdapter;
  private boolean isBatchProcessing = false;
  private boolean isSearchCancelled = false;

  public void leftScrollPosition(String str) {
    globalMusicList = new ArrayList();
    new u(this, str).start();
    this.isLeftMultiSelectMode = false;
    this.isRightMultiSelectMode = false;
    this.rightListAdapter.notifyDataSetChanged();
    this.leftListAdapter.notifyDataSetChanged();
    this.batchDownloadImageView.setVisibility(View.INVISIBLE);
    this.selectAllImageView.setVisibility(View.INVISIBLE);
  }

  public void rightScrollPosition() {
    String str = getFilesDir().getParent() + "/app_tmpFile/listen_tmp.bin";
    this.coverImageView.setImageBitmap(new File(str).exists() ? BitmapFactory.decodeFile(str)
        : BitmapFactory.decodeResource(getResources(), R.drawable.defaultcover));
  }

  private void hasLyricFile(String str) {
    new e0(this, str).start();
  }

  public void V(int i2, int i3) {
    String str;
    String str2;
    Map<String, Object> map;
    String str3;
    String str4 = "id";
    if (i3 == 1) {
      String string = Objects.requireNonNull(this.leftMusicList.get(i2).get("id")).toString();
      String string2 = Objects.requireNonNull(this.leftMusicList.get(i2).get("filename"))
          .toString();
      if (this.appPreferences.getInt("filemode", 0) == 1) {
        str =
            Objects.requireNonNull(this.leftMusicList.get(i2).get(Mp4NameBox.IDENTIFIER)) + " - "
                + Objects.requireNonNull(this.leftMusicList.get(
                i2).get("singer"));
      } else {
        str = string2;
      }
      str2 =
          string + "∮∮" + str + "∮∮" + Objects.requireNonNull(
              this.leftMusicList.get(i2).get("maxbr")) + "∮∮" + (
              this.leftMusicList.get(i2).get(Mp4NameBox.IDENTIFIER) + "∮∮"
                  + this.leftMusicList.get(
                      i2)
                  .get("singer") + "∮∮" + this.leftMusicList.get(i2).get("album") + " ");
      Map<Integer, Boolean> map2 = this.leftSelectionMap;
      if (map2 == null || !map2.containsKey(i2)) {
        assert this.leftSelectionMap != null;
        this.leftSelectionMap.put(i2, Boolean.TRUE);
        this.selectedSongs.add(str2);
      } else {
        this.leftSelectionMap.remove(i2);
        this.selectedSongs.remove(str2);
      }
    }
    if (i3 != 2) {
      return;
    }
    if (this.currentSource.equals("kugou")) {
      map = this.rightMusicList.get(i2);
      str4 = "filehash";
    } else {
      map = this.rightMusicList.get(i2);
    }
    String string3 = Objects.requireNonNull(map.get(str4)).toString();
    String string4 = Objects.requireNonNull(this.rightMusicList.get(i2).get("filename"))
        .toString();
    if (this.appPreferences.getInt("filemode", 0) == 1) {
      str3 =
          Objects.requireNonNull(this.rightMusicList.get(i2).get(Mp4NameBox.IDENTIFIER))
              + " - "
              + Objects.requireNonNull(this.rightMusicList.get(i2)
              .get("singer"));
    } else {
      str3 = string4;
    }
    str2 = string3 + "∮∮" + str3 + "∮∮" + Objects.requireNonNull(
        this.rightMusicList.get(i2).get("maxbr")) + "∮∮"
        + (
        this.rightMusicList.get(i2).get(Mp4NameBox.IDENTIFIER) + "∮∮" + this.rightMusicList.get(
                i2)
            .get("singer")
            + "∮∮" + this.rightMusicList.get(i2).get("album") + " ");
    HashMap<Integer, Boolean> map3 = this.rightSelectionMap;
    if (map3 == null || !map3.containsKey(Integer.valueOf(i2))) {
      this.rightSelectionMap.put(Integer.valueOf(i2), Boolean.TRUE);
      this.selectedSongs.add(str2);
    } else {
      this.rightSelectionMap.remove(Integer.valueOf(i2));
      this.selectedSongs.remove(str2);
    }
  }

  public void searchEditText() {
    Dialog dialog = this.loadingDialog;
    if (dialog == null || !dialog.isShowing()) {
      return;
    }
    this.loadingDialog.dismiss();
  }

  private void downloadMusicFile(String downloadUrl, String originalFileName, String fileExtension,
      String songName, String artistName, String additionalParam) {
    if (!downloadUrl.startsWith(HTTP_PREFIX)) {
      this.dialogMessage = downloadUrl;
      o0(ERROR_CODE);
      return;
    }

    this.isDownloading = true;

    String sanitizedFileName = sanitizeFileName(originalFileName);

    if (sanitizedFileName.getBytes().length > MAX_FILENAME_BYTES) {
      if (songName.getBytes().length < MAX_FILENAME_BYTES) {
        showMessage("文件名过长,为保证体验,仅以歌曲名命名");
        sanitizedFileName = songName;
      } else {
        showMessage("文件名过长,为保证体验,仅以艺术家命名");
        sanitizedFileName = artistName;
      }
    }

    String fullFileName = sanitizedFileName + fileExtension;
    this.currentDownloadFileName = fullFileName;

    String musicFilePath = this.downloadDirectory + SLASH + fullFileName;
    String lrcFilePath = this.downloadDirectory + SLASH + sanitizedFileName + LRC_EXT;
    String binDirectory = Environment.getExternalStorageDirectory() + "/PMSLLM/bin";

    okhttp3.OkHttpClient client = OkHttpClient.getInstance();
    okhttp3.Request request = new okhttp3.Request.Builder()
        .url(downloadUrl)
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (response.code() != HTTP_OK) {
        this.dialogMessage = String.format(
            "文件下载链接无效,请重试或更换音质下载\n\n发生错误的歌曲:%s - %s",
            artistName, songName
        );
        deleteFileIfExists(lrcFilePath);
        this.isDownloading = false;
        this.isDownloadCancelled = false;
        o0(ERROR_CODE);
        return;
      }

      okhttp3.ResponseBody body = response.body();
      InputStream inputStream = body.byteStream();
      long contentLength = body.contentLength();

      this.leftScrollPosition = (int) contentLength;
      t0();
      this.downloadProgressBar.setMax(this.leftScrollPosition);

      try (BufferedOutputStream outputStream = new BufferedOutputStream(
          new FileOutputStream(musicFilePath))) {

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;

        while (this.isDownloading &&
            this.rightScrollPosition != this.leftScrollPosition &&
            (bytesRead = inputStream.read(buffer)) != -1) {

          this.rightScrollPosition += bytesRead;
          outputStream.write(buffer, 0, bytesRead);
          r0();
        }

        o0(COMPLETE_CODE);

        if (!this.isDownloading) {
          deleteFileIfExists(musicFilePath);
          deleteFileIfExists(lrcFilePath);
          outputStream.flush();
          showMessage(sanitizedFileName + ":停止下载成功,已删除已下载的文件");
          return;
        }

        processCover(downloadUrl, musicFilePath, songName, artistName,
            additionalParam, binDirectory, lrcFilePath);

        if (this.rightScrollPosition == this.leftScrollPosition) {
          handleDownloadSuccess(fullFileName, fileExtension, musicFilePath);
        } else {
          showMessage(sanitizedFileName + fileExtension + ":下载失败");
        }
      }

    } catch (Exception e) {
      handleDownloadError(e, musicFilePath, lrcFilePath);
    } finally {
      resetDownloadState();
    }
  }

  private String sanitizeFileName(String fileName) {
    return fileName
        .replace(":", "\uff1a")
        .replace("?", "\uff1f")
        .replace("|", "\uff5c")
        .replace("*", "\uff0a")
        .replace("\\", "\uff3c")
        .replace("/", "\uff0f")
        .replace("\"", "\uff02")
        .replace("<", "\u3008")
        .replace(">", "\u3009");
  }

  private void processCover(String downloadUrl, String musicFilePath, String songName,
      String artistName, String additionalParam,
      String binDirectory, String lrcFilePath) throws ProtocolException {
    SharedPreferences prefs = this.appPreferences;
    int textMode = prefs.getInt("textmode", 0);

    if (textMode != 0 || !this.isDownloading) {
      return;
    }

    int lrcMode = prefs.getInt("lrcmode", 0);
    String tempLrcPath = lrcMode == 0 ? lrcFilePath :
        (this.hasLyricFile ? getFilesDir().getParent() + "/app_tmpFile/downTmp.lrc" : null);

    if (downloadUrl.contains("kugou")) {
      if (ImageDownloadUtils.downloadKugouCover(this.coverImageId, binDirectory)) {
        FileUtils.writeId3Tags(musicFilePath, songName, artistName,
            additionalParam, binDirectory, tempLrcPath);
      }
    } else if (downloadUrl.contains("ymusic")) {
      if (ImageDownloadUtils.downloadNeteaseCloudCover(this.coverImageId, binDirectory)) {
        FileUtils.writeId3Tags(musicFilePath, songName, artistName,
            additionalParam, binDirectory, tempLrcPath);
      }
    } else if (downloadUrl.contains("migu")) {
      if (ImageDownloadUtils.downloadImageToFile(this.coverImageId, binDirectory)) {
        FileUtils.writeId3Tags(musicFilePath, songName, artistName,
            additionalParam, binDirectory, tempLrcPath);
      }
    } else if (downloadUrl.contains("kuwo")) {
      if (ImageDownloadUtils.downloadKuwoCover(this.coverImageId, binDirectory)) {
        FileUtils.writeId3Tags(musicFilePath, songName, artistName,
            additionalParam, binDirectory, tempLrcPath);
      }
    } else if (downloadUrl.contains("vkey=")) {
      if (ImageDownloadUtils.downloadQQMusicCover(this.coverImageId, binDirectory)) {
        FileUtils.writeId3Tags(musicFilePath, songName, artistName,
            additionalParam, binDirectory, tempLrcPath);
      }
    }
  }

  private void handleDownloadSuccess(String fileName, String extension, String filePath) {
    showMessage(fileName + ":下载完成");
    downloadProgressDialog.dismiss();

    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    mediaScanIntent.setData(Uri.fromFile(new File(filePath)));
    sendBroadcast(mediaScanIntent);

    ContentValues values = new ContentValues();
    values.put("name", this.currentDownloadFileName);
    values.put("path", filePath);

    this.database.delete("song_list", "name = ?", new String[]{this.currentDownloadFileName});
    this.database.insert("song_list", null, values);
  }

  private void handleDownloadError(Exception e, String musicPath, String lrcPath) {
    showMessage("下载过程出现错误!" + e.toString());
    deleteFileIfExists(musicPath);
    deleteFileIfExists(lrcPath);
    o0(COMPLETE_CODE);
  }

  private void deleteFileIfExists(String filePath) {
    new File(filePath).delete();
  }

  private void resetDownloadState() {
    this.rightScrollPosition = 0;
    this.leftScrollPosition = 0;
    this.isDownloading = false;
    this.isDownloadCancelled = false;
  }

  public void leftListView() {
    this.appPreferences.edit().putString("qqimg", FrameBodyCOMM.DEFAULT).apply();
    this.menuImageView.setImageResource(R.drawable.ic_avatar);
    this.avatarImageView.setImageResource(R.mipmap.ic_launcher);
    this.tempBitmap = null;
    this.appPreferences.edit().putString("token", FrameBodyCOMM.DEFAULT).apply();
  }

  private void getDownloadDirectory() {
    String strD;
    int i2 = this.appPreferences.getInt("storageType", 1);
    String string = this.appPreferences.getString("downdirectory", "MusicDownloader/Music");
    if (i2 == 1) {
      strD = sdCardPath + "/MusicDownloader/Music";
    } else {
      if (i2 != 2) {
        this.downloadDirectory = sdCardPath + "/" + string;
        String sb = this.downloadDirectory +
            "/";
        File file = new File(sb);
        if (file.exists()) {
          return;
        }
        file.mkdirs();
        return;
      }
      strD = FileUtils.getExternalStoragePath(getApplicationContext());
    }
    this.downloadDirectory = strD;
  }

  private void getSdcardPath() {
    this.appPreferences = getSharedPreferences("pms", 0);
    sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    getDownloadDirectory();
    if (Build.MODEL.equals("Subsystem for Android(TM)")) {
      this.searchControlFlag = 1;
    }
  }

  private void initVariables() {
    this.searchEditText = findViewById(R.id.eEditText1);
    this.titleTextView = findViewById(R.id.eTextView3);
    this.noticeTextView = findViewById(R.id.eTextView1);
    this.subtitleTextView = findViewById(R.id.eTextView2);
    this.playerControlLayout = findViewById(R.id.eRelativeLayout3);
    this.noticeLayout = findViewById(R.id.gklayout);
    this.coverImageView = findViewById(R.id.eImageView1);
    this.batchDownloadImageView = findViewById(R.id.eImageView3);
    this.searchLoadingImageView = findViewById(R.id.eImageView4);
    this.selectAllImageView = findViewById(R.id.eImageView6);
    this.menuImageView = findViewById(R.id.eImageView5);
    this.clearSearchLayout = findViewById(R.id.eLinearLayout1);
    this.leftListView = findViewById(R.id.eListView1);
    this.rightListView = findViewById(R.id.eListView2);
    this.leftButton1 = findViewById(R.id.eButton1);
    this.leftButton2 = findViewById(R.id.eButton2);
    this.leftButton3 = findViewById(R.id.eButton3);
    findViewById(R.id.searchTextView).setOnClickListener(new y0(this));
    findViewById(R.id.eLinearLayout2).setOnClickListener(new y0(this));
    this.clearSearchLayout.setOnClickListener(new y0(this));
    this.coverImageView.setOnClickListener(new y0(this));
    this.batchDownloadImageView.setOnClickListener(new y0(this));
    this.searchLoadingImageView.setOnClickListener(new y0(this));
    this.menuImageView.setOnClickListener(new y0(this));
    this.selectAllImageView.setOnClickListener(new y0(this));
    this.noticeLayout.setOnClickListener(new y0(this));
    x0 x0Var = new x0(this, this);
    x0Var.setImageResource(R.drawable.listen);
    x0Var.setClickable(true);
    this.menuImageView.setOnLongClickListener(new s0(this));
    this.downloadProgressDialog = new DialogFactory().createDialog(this);
    View downloadDialogRootView = LayoutInflater.from(this)
        .inflate(R.layout.download_dialog, null);
    this.downloadProgressDialog.setCancelable(false);
    this.downloadProgressDialog.setContentView(downloadDialogRootView);
    this.downloadDialogMsg = downloadDialogRootView.findViewById(R.id.mdTextView1);
    this.downloadProgressView = downloadDialogRootView.findViewById(R.id.mdTextView2);
    this.cancelDownloadButton = downloadDialogRootView.findViewById(R.id.mdButton1);
    Button button = downloadDialogRootView.findViewById(R.id.mdButton2);
    this.downloadProgressBar = downloadDialogRootView.findViewById(R.id.mdProgressBar1);
    this.cancelDownloadButton.setOnClickListener(new l0(this));
    button.setOnClickListener(new m0(this));
  }

  public void g0(String str, int i2, String str2) {
    new f0(this, str, i2, str2).start();
  }

  public void h0(String str) {
    this.mvUrlList = new ArrayList<>();
    new d0(this, str).start();
  }

  public void showMessage(String str) {
    Message message = new Message();
    message.what = 1;
    message.obj = str;
    this.mainHandler.sendMessage(message);
  }

  public void showPopupMenu() {
    PopupWindow popupWindow = this.suggestionPopup;
    if (popupWindow != null && popupWindow.isShowing() && !isFinishing() && !isDestroyed()) {
      this.suggestionPopup.dismiss();
    }
    if (isFinishing() || isDestroyed()) {
      return;
    }
    this.suggestionPopup.setContentView(this.suggestionContentView);
    this.suggestionPopup.showAsDropDown(this.searchEditText, 0, 0);
  }

  public void qualitySelector(int i2) {
    Dialog dialog = this.qualityDialog;
    if (dialog == null || !dialog.isShowing()) {
      if (!this.canShowQualityDialog) {
        this.canShowQualityDialog = true;
        return;
      }
      this.leftListAdapter.notifyDataSetChanged();
      this.qualityDialog = new DialogFactory().createDialog(this);
      View viewInflate = LayoutInflater.from(this).inflate(R.layout.hires, null);
      this.qualityDialog.show();
      this.qualityDialog.setContentView(viewInflate);
      Display defaultDisplay = getWindowManager().getDefaultDisplay();
      LayoutParams attributes = this.qualityDialog.getWindow().getAttributes();
      attributes.width = defaultDisplay.getWidth();
      this.qualityDialog.getWindow().setAttributes(attributes);
      this.qualityCoverImageView = viewInflate.findViewById(R.id.hiresi1);
      Button button = viewInflate.findViewById(R.id.hiresButton1);
      LinearLayout linearLayout = viewInflate.findViewById(R.id.hiresLinearLayout2);
      Button button2 = viewInflate.findViewById(R.id.hiresButton2);
      LinearLayout linearLayout2 = viewInflate.findViewById(R.id.hiresLinearLayout3);
      Button button3 = viewInflate.findViewById(R.id.hiresButton3);
      LinearLayout linearLayout3 = viewInflate.findViewById(R.id.hiresLinearLayout4);
      Button button4 = viewInflate.findViewById(R.id.hiresButton4);
      LinearLayout linearLayout4 = viewInflate.findViewById(R.id.hiresLinearLayout5);
      Button button5 = viewInflate.findViewById(R.id.hiresButton5);
      Button button6 = viewInflate.findViewById(R.id.hiresButton6);
      View viewFindViewById = viewInflate.findViewById(R.id.mvlay);
      TextView textView = viewInflate.findViewById(R.id.hiresTextView1);
      TextView textView2 = viewInflate.findViewById(R.id.hiresTextView2);
      MusicPlatform pVar = this.currentMusicPlatform;
      if (pVar == MusicPlatform.KUGOU) {
        String string = this.rightMusicList.get(i2).get("pay").toString();
        String string2 = this.rightMusicList.get(i2).get("cy").toString();
        if (string.equals("pay") || string2.equals("5")) {
          button6.setEnabled(false);
          button6.setTextColor(Color.parseColor("#FFA6A6A6"));
        }
        String sb = this.rightMusicList.get(i2).get(Mp4NameBox.IDENTIFIER) +
            FrameBodyCOMM.DEFAULT;
        textView.setText(sb);
        String sb2 = this.rightMusicList.get(i2).get("singer") +
            " -《" +
            this.rightMusicList.get(i2).get("album") +
            "》";
        textView2.setText(sb2);
        String sb3 = this.rightMusicList.get(i2).get("singer") +
            " " +
            this.rightMusicList.get(i2).get(Mp4NameBox.IDENTIFIER);
        this.searchKeyword = sb3;
        String string3 = this.rightMusicList.get(i2).get("filesize") +
            FrameBodyCOMM.DEFAULT;
        String strSubstring = string3.substring(0, string3.indexOf("mp"));
        String strSubstring2 = string3.substring(string3.indexOf("mp") + 2,
            string3.indexOf("hq"));
        String strSubstring3 = string3.substring(string3.indexOf("hq") + 2,
            string3.indexOf("sq"));
        String strSubstring4 = string3.substring(string3.indexOf("sq") + 2,
            string3.indexOf("hr"));
        String strSubstring5 = string3.substring(string3.indexOf("hr") + 2);
        String string4 = this.rightMusicList.get(i2).get("filehash").toString();
        hasLyricFile(string4.substring(0, string4.indexOf("低高")));
        button.setText("标准 - " + CommonUtils.formatFileSize(strSubstring));
        button2.setText("高品 - " + CommonUtils.formatFileSize(strSubstring2));
        button3.setText("无损 - " + CommonUtils.formatFileSize(strSubstring3));
        button4.setText("HR - " + CommonUtils.formatFileSize(strSubstring4));
        button5.setText("DSD - " + CommonUtils.formatFileSize(strSubstring5));
        button6.setText("播放 - " + CommonUtils.formatFileSize(strSubstring));
        String string5 = this.rightMusicList.get(i2).get("maxbr") +
            FrameBodyCOMM.DEFAULT;
        if (string5.equals("hr")) {
          linearLayout4.setVisibility(View.GONE);
        } else if (string5.equals("sq")) {
          linearLayout3.setVisibility(View.GONE);
          linearLayout4.setVisibility(View.GONE);
        } else if (string5.equals("hq")) {
          linearLayout2.setVisibility(View.GONE);
          linearLayout3.setVisibility(View.GONE);
          linearLayout4.setVisibility(View.GONE);
        } else if (string5.equals("mp3")) {
          linearLayout.setVisibility(View.GONE);
          linearLayout2.setVisibility(View.GONE);
          linearLayout3.setVisibility(View.GONE);
          linearLayout4.setVisibility(View.GONE);
        }
        StringBuilder sb6 = new StringBuilder();
        sb6.append(this.rightMusicList.get(i2).get("mvid"));
        sb6.append(FrameBodyCOMM.DEFAULT);
        if (sb6.toString().equals(FrameBodyCOMM.DEFAULT)) {
          viewFindViewById.setVisibility(View.GONE);
        }
      } else if (pVar == MusicPlatform.WYY) {
        String string6 = this.rightMusicList.get(i2).get("pay").toString();
        String string7 = this.rightMusicList.get(i2).get("cy").toString();
        if (string6.equals("4") || string7.equals("-200")) {
          button6.setEnabled(false);
          button6.setTextColor(Color.parseColor("#FFA6A6A6"));
        }
        String sb7 = this.rightMusicList.get(i2).get(Mp4NameBox.IDENTIFIER) +
            FrameBodyCOMM.DEFAULT;
        textView.setText(sb7);
        String sb8 = this.rightMusicList.get(i2).get("singer") +
            " -《" +
            this.rightMusicList.get(i2).get("album") +
            "》";
        textView2.setText(sb8);
        String sb9 = this.rightMusicList.get(i2).get("singer") +
            " " +
            this.rightMusicList.get(i2).get(Mp4NameBox.IDENTIFIER);
        this.searchKeyword = sb9;
        String string8 = this.rightMusicList.get(i2).get("maxbr") +
            FrameBodyCOMM.DEFAULT;
        String sb11 = this.rightMusicList.get(i2).get("id") +
            FrameBodyCOMM.DEFAULT;
        hasLyricFile(sb11);
        StringBuilder sb12 = new StringBuilder();
        sb12.append("标准 - ");
        String sb13 = this.rightMusicList.get(i2).get("mp3size") +
            FrameBodyCOMM.DEFAULT;
        sb12.append(CommonUtils.formatFileSize(sb13));
        button.setText(sb12.toString());
        StringBuilder sb14 = new StringBuilder();
        sb14.append("高品 - ");
        String sb15 = this.rightMusicList.get(i2).get("hqsize") +
            FrameBodyCOMM.DEFAULT;
        sb14.append(CommonUtils.formatFileSize(sb15));
        button2.setText(sb14.toString());
        StringBuilder sb16 = new StringBuilder();
        sb16.append("无损 - ");
        String sb17 = this.rightMusicList.get(i2).get("sqsize") +
            FrameBodyCOMM.DEFAULT;
        sb16.append(CommonUtils.formatFileSize(sb17));
        button3.setText(sb16.toString());
        StringBuilder sb18 = new StringBuilder();
        sb18.append("HR - ");
        String sb19 = this.rightMusicList.get(i2).get("hrsize") +
            FrameBodyCOMM.DEFAULT;
        sb18.append(CommonUtils.formatFileSize(sb19));
        button4.setText(sb18.toString());
        StringBuilder sb20 = new StringBuilder();
        sb20.append("播放 - ");
        String sb21 = this.rightMusicList.get(i2).get("mp3size") +
            FrameBodyCOMM.DEFAULT;
        sb20.append(CommonUtils.formatFileSize(sb21));
        button6.setText(sb20.toString());
        linearLayout4.setVisibility(View.GONE);
        if (string8.equals("sq")) {
          linearLayout3.setVisibility(View.GONE);
        }
        StringBuilder sb22 = new StringBuilder();
        sb22.append(this.rightMusicList.get(i2).get("mvid"));
        sb22.append(FrameBodyCOMM.DEFAULT);
        if (sb22.toString().equals(FrameBodyCOMM.DEFAULT)) {
          viewFindViewById.setVisibility(View.GONE);
        }
        if (string8.equals("hq")) {
          linearLayout2.setVisibility(View.GONE);
          linearLayout3.setVisibility(View.GONE);
        } else if (string8.equals("mp3")) {
          linearLayout.setVisibility(View.GONE);
          linearLayout2.setVisibility(View.GONE);
          linearLayout3.setVisibility(View.GONE);
        }
      }
      MusicPlatform pVar2 = this.currentMusicPlatform;
      if (pVar2 == MusicPlatform.MIGU) {
        String sb23 = this.leftMusicList.get(i2).get(Mp4NameBox.IDENTIFIER) +
            FrameBodyCOMM.DEFAULT;
        textView.setText(sb23);
        textView2.setText(
            this.leftMusicList.get(i2).get("singer") + " -《" + this.leftMusicList.get(i2)
                .get("album") + "》");
        String string9 = this.leftMusicList.get(i2).get("maxbr") +
            FrameBodyCOMM.DEFAULT;
        hasLyricFile(this.leftMusicList.get(i2).get("abimg") + FrameBodyCOMM.DEFAULT);
        StringBuilder sb25 = new StringBuilder();
        sb25.append("标准 - ");
        String sb26 = this.leftMusicList.get(i2).get("mp3size") +
            FrameBodyCOMM.DEFAULT;
        sb25.append(CommonUtils.formatFileSize(sb26));
        button.setText(sb25.toString());
        StringBuilder sb27 = new StringBuilder();
        sb27.append("高品 - ");
        String sb28 = this.leftMusicList.get(i2).get("hqsize") +
            FrameBodyCOMM.DEFAULT;
        sb27.append(CommonUtils.formatFileSize(sb28));
        button2.setText(sb27.toString());
        StringBuilder sb29 = new StringBuilder();
        sb29.append("无损 - ");
        String sb30 = this.leftMusicList.get(i2).get("sqsize") +
            FrameBodyCOMM.DEFAULT;
        sb29.append(CommonUtils.formatFileSize(sb30));
        button3.setText(sb29.toString());
        StringBuilder sb31 = new StringBuilder();
        sb31.append("HR - ");
        String sb32 = this.leftMusicList.get(i2).get("hrsize") +
            FrameBodyCOMM.DEFAULT;
        sb31.append(CommonUtils.formatFileSize(sb32));
        button4.setText(sb31.toString());
        StringBuilder sb33 = new StringBuilder();
        sb33.append("播放 - ");
        String sb34 = this.leftMusicList.get(i2).get("mp3size") +
            FrameBodyCOMM.DEFAULT;
        sb33.append(CommonUtils.formatFileSize(sb34));
        button6.setText(sb33.toString());
        viewFindViewById.setVisibility(View.GONE);
        if (string9.equals("hr")) {
          linearLayout4.setVisibility(View.GONE);
        } else {
          if (!string9.equals("sq")) {
            if (!string9.equals("hq")) {
              if (string9.equals("mp3")) {
                linearLayout.setVisibility(View.GONE);
              }
            }
            linearLayout2.setVisibility(View.GONE);
          }
          linearLayout3.setVisibility(View.GONE);
          linearLayout4.setVisibility(View.GONE);
        }
      } else if (pVar2 == MusicPlatform.KUWO) {
        String sb35 = this.leftMusicList.get(i2).get(Mp4NameBox.IDENTIFIER) +
            FrameBodyCOMM.DEFAULT;
        textView.setText(sb35);
        textView2.setText(
            this.leftMusicList.get(i2).get("singer") + " -《" + this.leftMusicList.get(i2)
                .get("album") + "》");
        String string10 = this.leftMusicList.get(i2).get("maxbr") +
            FrameBodyCOMM.DEFAULT;
        linearLayout4.setVisibility(View.GONE);
        viewFindViewById.setVisibility(View.GONE);
        hasLyricFile(this.leftMusicList.get(i2).get("id") + FrameBodyCOMM.DEFAULT);
        button.setText(
            "标准" + this.leftMusicList.get(i2).get("mp3size") + FrameBodyCOMM.DEFAULT);
        button2.setText(
            "高品" + this.leftMusicList.get(i2).get("hqsize") + FrameBodyCOMM.DEFAULT);
        button3.setText(
            "无损" + this.leftMusicList.get(i2).get("sqsize") + FrameBodyCOMM.DEFAULT);
        button4.setText("HR" + this.leftMusicList.get(i2).get("hrsize") + FrameBodyCOMM.DEFAULT);
        button6.setText(
            "播放" + this.leftMusicList.get(i2).get("mp3size") + FrameBodyCOMM.DEFAULT);
        if (string10.equals("sq")) {
          linearLayout3.setVisibility(View.GONE);
        } else {
          if (!string10.equals("hq")) {
            if (string10.equals("mp3")) {
              linearLayout.setVisibility(View.GONE);
            }
          }
          linearLayout2.setVisibility(View.GONE);
          linearLayout3.setVisibility(View.GONE);
        }
      } else if (pVar2 == MusicPlatform.QQ) {
        String sb37 = this.leftMusicList.get(i2).get(Mp4NameBox.IDENTIFIER) +
            FrameBodyCOMM.DEFAULT;
        textView.setText(sb37);
        textView2.setText(
            this.leftMusicList.get(i2).get("singer") + " -《" + this.leftMusicList.get(i2)
                .get("album") + "》");
        String string11 = this.leftMusicList.get(i2).get("maxbr") +
            FrameBodyCOMM.DEFAULT;
        hasLyricFile(this.leftMusicList.get(i2).get("albumid") + FrameBodyCOMM.DEFAULT);
        StringBuilder sb39 = new StringBuilder();
        sb39.append("标准 - ");
        String sb40 = this.leftMusicList.get(i2).get("mp3size") +
            FrameBodyCOMM.DEFAULT;
        sb39.append(CommonUtils.formatFileSize(sb40));
        button.setText(sb39.toString());
        StringBuilder sb41 = new StringBuilder();
        sb41.append("高品 - ");
        String sb42 = this.leftMusicList.get(i2).get("hqsize") +
            FrameBodyCOMM.DEFAULT;
        sb41.append(CommonUtils.formatFileSize(sb42));
        button2.setText(sb41.toString());
        StringBuilder sb43 = new StringBuilder();
        sb43.append("无损 - ");
        String sb44 = this.leftMusicList.get(i2).get("sqsize") +
            FrameBodyCOMM.DEFAULT;
        sb43.append(CommonUtils.formatFileSize(sb44));
        button3.setText(sb43.toString());
        StringBuilder sb45 = new StringBuilder();
        sb45.append("HR - ");
        String sb46 = this.leftMusicList.get(i2).get("hrsize") +
            FrameBodyCOMM.DEFAULT;
        sb45.append(CommonUtils.formatFileSize(sb46));
        button4.setText(sb45.toString());
        StringBuilder sb47 = new StringBuilder();
        sb47.append("播放 - ");
        String sb48 = this.leftMusicList.get(i2).get("mp3size") +
            FrameBodyCOMM.DEFAULT;
        sb47.append(CommonUtils.formatFileSize(sb48));
        button6.setText(sb47.toString());
        if ((this.leftMusicList.get(i2).get("mvid") + FrameBodyCOMM.DEFAULT).equals(
            FrameBodyCOMM.DEFAULT)) {
          viewFindViewById.setVisibility(View.GONE);
        }
        if (string11.equals("hr")) {
          linearLayout4.setVisibility(View.GONE);
        } else {
          if (!string11.equals("sq")) {
            if (!string11.equals("hq")) {
              if (string11.equals("mp3")) {
                linearLayout.setVisibility(View.GONE);
              }
            }
            linearLayout2.setVisibility(View.GONE);
          }
          linearLayout3.setVisibility(View.GONE);
          linearLayout4.setVisibility(View.GONE);
        }
      }
      button.setOnClickListener(new QualityClickListener(this, i2, "d", "mp3"));
      button2.setOnClickListener(new QualityClickListener(this, i2, "d", "hq"));
      button3.setOnClickListener(new QualityClickListener(this, i2, "d", "sq"));
      button4.setOnClickListener(new QualityClickListener(this, i2, "d", "hr"));
      button5.setOnClickListener(new QualityClickListener(this, i2, "d", "dsd"));
      button6.setOnClickListener(new QualityClickListener(this, i2, "l", "mp3"));
      viewFindViewById.setOnClickListener(new c0(this, i2));
    }
  }

  public void n0() {
    SimpleAdapter simpleAdapter;
    String string;
    StringBuilder sb;
    StringBuilder sb2;
    try {
      List<Map<String, Object>> list =
          (this.currentSource.equals("qq") || this.currentSource.equals("kuwo"))
              ? this.leftMusicList : this.rightMusicList;
      if (this.selectedSongs.size() != list.size()) {
        this.selectedSongs.clear();
        for (int i2 = 0; i2 < list.size(); i2++) {
          if (this.currentSource.equals("qq") || this.currentSource.equals("kuwo")) {
            this.leftSelectionMap.put(Integer.valueOf(i2), Boolean.TRUE);
          } else {
            this.rightSelectionMap.put(Integer.valueOf(i2), Boolean.TRUE);
          }
          String string2 = (this.currentSource.equals("kugou") ? this.rightMusicList.get(i2)
              .get("filehash")
              : (this.currentSource.equals("kuwo") || this.currentSource.equals("qq"))
                  ? this.leftMusicList.get(i2)
                  .get("id") : this.rightMusicList.get(i2).get("id")).toString();
          String string3 = ((this.currentSource.equals("qq") || this.currentSource.equals("kuwo"))
              ? this.leftMusicList.get(i2).get("filename")
              : this.rightMusicList.get(i2).get("filename")).toString();
          if (this.appPreferences.getInt("filemode", 0) == 1) {
            if (this.currentSource.equals("qq") || this.currentSource.equals("kuwo")) {
              StringBuilder sb3 = new StringBuilder();
              sb3.append(this.leftMusicList.get(i2).get(Mp4NameBox.IDENTIFIER).toString());
              sb3.append(" - ");
              sb3.append(this.leftMusicList.get(i2).get("singer").toString());
              sb2 = sb3;
            } else {
              StringBuilder sb4 = new StringBuilder();
              sb4.append(this.rightMusicList.get(i2).get(Mp4NameBox.IDENTIFIER).toString());
              sb4.append(" - ");
              sb4.append(this.leftMusicList.get(i2).get("singer").toString());
              sb2 = sb4;
            }
            string = sb2.toString();
          } else {
            string = string3;
          }
          String string4 = ((this.currentSource.equals("qq") || this.currentSource.equals("kuwo"))
              ? this.leftMusicList.get(i2).get("maxbr")
              : this.rightMusicList.get(i2).get("maxbr")).toString();
          if (this.currentSource.equals("qq") || this.currentSource.equals("kuwo")) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(this.leftMusicList.get(i2).get(Mp4NameBox.IDENTIFIER));
            sb5.append("∮∮");
            sb5.append(this.leftMusicList.get(i2).get("singer"));
            sb5.append("∮∮");
            sb5.append(this.leftMusicList.get(i2).get("album"));
            sb5.append(" ");
            sb = sb5;
          } else {
            StringBuilder sb6 = new StringBuilder();
            sb6.append(this.rightMusicList.get(i2).get(Mp4NameBox.IDENTIFIER));
            sb6.append("∮∮");
            sb6.append(this.rightMusicList.get(i2).get("singer"));
            sb6.append("∮∮");
            sb6.append(this.rightMusicList.get(i2).get("album"));
            sb6.append(" ");
            sb = sb6;
          }
          this.selectedSongs.add(string2 + "∮∮" + string + "∮∮" + string4 + "∮∮" + sb);
        }
        if (this.currentSource.equals("qq") || this.currentSource.equals("kuwo")) {
          showMessage("已全选，一共" + this.selectedSongs.size() + "首");
          simpleAdapter = this.leftListAdapter;
        } else {
          showMessage("已全选，一共" + this.selectedSongs.size() + "首");
          simpleAdapter = this.rightListAdapter;
        }
      } else if (this.currentSource.equals("qq") || this.currentSource.equals("kuwo")) {
        if (this.selectedSongs.size() == this.leftMusicList.size()) {
          this.selectedSongs.clear();
          this.leftSelectionMap.clear();
        }
        simpleAdapter = this.leftListAdapter;
      } else {
        if (this.selectedSongs.size() == this.rightMusicList.size()) {
          this.selectedSongs.clear();
          this.rightSelectionMap.clear();
        }
        simpleAdapter = this.rightListAdapter;
      }
      simpleAdapter.notifyDataSetChanged();
    } catch (Exception e2) {
      showMessage("异常，无法一键全选，错误信息：" + e2);
    }
  }

  public void o0(int i2) {
    Message message = new Message();
    message.what = i2;
    this.mainHandler.sendMessage(message);
  }

  public void p0() {
    new Thread(new p(this)).start();
  }

  public void q0() {
    View viewInflate = LayoutInflater.from(this).inflate(R.layout.user_img, null);
    EditText editText = viewInflate.findViewById(R.id.userimgEditText1);
    Button button = viewInflate.findViewById(R.id.userimgButton1);
    Button button2 = viewInflate.findViewById(R.id.userimgButton2);
    button2.setVisibility(View.GONE);
    editText.setText(this.appPreferences.getString("qqimg", FrameBodyCOMM.DEFAULT));
    Dialog dialogA = new DialogFactory().createDialog(this);
    dialogA.show();
    dialogA.setContentView(viewInflate);
    Display defaultDisplay = getWindowManager().getDefaultDisplay();
    LayoutParams attributes = dialogA.getWindow().getAttributes();
    attributes.width = defaultDisplay.getWidth();
    dialogA.getWindow().setAttributes(attributes);
    button.setOnClickListener(new n(this, editText, dialogA));
    button2.setOnClickListener(new o(this, dialogA));
  }

  private void r0() {
    runOnUiThread(new k0(this));
  }

  public void noselectsongsDialog() {
    ArrayList<String> arrayList = this.selectedSongs;
    if (arrayList == null || arrayList.size() == 0) {
      showMessage("请先选择一些歌吧～");
      return;
    }
    Dialog dialogA = new DialogFactory().createDialog(this);
    View viewInflate = LayoutInflater.from(this).inflate(R.layout.brdialog, null);
    dialogA.show();
    dialogA.setContentView(viewInflate);
    Display defaultDisplay = getWindowManager().getDefaultDisplay();
    LayoutParams attributes = dialogA.getWindow().getAttributes();
    attributes.width = defaultDisplay.getWidth();
    dialogA.getWindow().setAttributes(attributes);
    Button button = viewInflate.findViewById(R.id.brdialogButton1);
    Button button2 = viewInflate.findViewById(R.id.brdialogButton2);
    Button button3 = viewInflate.findViewById(R.id.brdialogButton3);
    Button button4 = viewInflate.findViewById(R.id.brdialogButton4);
    button.setOnClickListener(new q(this, dialogA));
    button2.setOnClickListener(new r(this, dialogA));
    button3.setOnClickListener(new s(this, dialogA));
    button4.setOnClickListener(new t(this, dialogA));
  }

  public void t0() {
    runOnUiThread(new j0(this));
  }

  public void switchyqListener() {
    Dialog dialog = new DialogFactory().createDialog(this);
    View viewInflate = LayoutInflater.from(this).inflate(R.layout.yq, null);
    TextView textView = viewInflate.findViewById(R.id.yqTextView1);
    TextView textView2 = viewInflate.findViewById(R.id.yqTextView2);
    TextView textView3 = viewInflate.findViewById(R.id.yqTextView3);
    TextView textView4 = viewInflate.findViewById(R.id.yqTextView4);
    TextView textView5 = viewInflate.findViewById(R.id.yqTextView5);
    TextView textView6 = viewInflate.findViewById(R.id.yqTextView6);
    int i2 = p0.f2299a[this.currentSearchEngine.ordinal()];
    if (i2 == 1) {
      textView.setTextColor(Color.parseColor("#0090FF"));
      textView2.setTextColor(Color.parseColor("#0090FF"));
    } else if (i2 == 2) {
      textView3.setTextColor(Color.parseColor("#0090FF"));
      textView4.setTextColor(Color.parseColor("#0090FF"));
    } else if (i2 == 3) {
      textView5.setTextColor(Color.parseColor("#0090FF"));
      textView6.setTextColor(Color.parseColor("#0090FF"));
    }
    dialog.show();
    dialog.setContentView(viewInflate);
    Display defaultDisplay = getWindowManager().getDefaultDisplay();
    LayoutParams attributes = dialog.getWindow().getAttributes();
    attributes.width = defaultDisplay.getWidth();
    dialog.getWindow().setAttributes(attributes);
    View yq1 = viewInflate.findViewById(R.id.yqLinearLayout1);
    View yq2 = viewInflate.findViewById(R.id.yqLinearLayout2);
    View yq3 = viewInflate.findViewById(R.id.yqLinearLayout3);
    View cloud = viewInflate.findViewById(R.id.yqLinearLayout4);
    RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
    rotateAnimation.setDuration(2000L);
    rotateAnimation.setFillAfter(true);
    rotateAnimation.setRepeatMode(1);
    rotateAnimation.setInterpolator(new LinearInterpolator());
    rotateAnimation.setRepeatCount(-1);
    yq1.setOnClickListener(new switchyq1(this, rotateAnimation, dialog));
    yq2.setOnClickListener(new switchyq2(this, rotateAnimation, dialog));
    yq3.setOnClickListener(new switchyq3(this, rotateAnimation, dialog));
    cloud.setOnClickListener(new switchcloud(this, rotateAnimation, dialog));
  }

  private void w0() {
    new t0(this).start();
  }

  public void showNoticeDialog() {
    this.isNoticeRead = false;
    Dialog dialog = new Dialog(this, R.style.dialog);
    dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
    dialog.getWindow().setGravity(80);
    dialog.requestWindowFeature(1);
    dialog.setCancelable(false);
    View viewInflate = LayoutInflater.from(this).inflate(R.layout.dialog, null);
    dialog.show();
    dialog.setContentView(viewInflate);
    Display defaultDisplay = getWindowManager().getDefaultDisplay();
    LayoutParams attributes = dialog.getWindow().getAttributes();
    attributes.width = defaultDisplay.getWidth();
    dialog.getWindow().setAttributes(attributes);
    TextView textView = viewInflate.findViewById(R.id.dialogTextView1);
    TextView textView2 = viewInflate.findViewById(R.id.dialogTextView2);
    Button button = viewInflate.findViewById(R.id.dialogButton1);
    Button button2 = viewInflate.findViewById(R.id.dialogButton2);
    textView2.setText("公告");
    textView.setText(this.noticeContent);
    button.setText(this.noticeButtonText);
    if (this.noticeMessage1.equals(FrameBodyCOMM.DEFAULT)) {
      button2.setVisibility(View.GONE);
    }
    button2.setText("取消");
    new u0(this, button).start();
    button.setOnClickListener(new v0(this, dialog));
    button2.setOnClickListener(new a(dialog));
  }

  public void showNotice2() {
    Dialog dialog = new Dialog(this, R.style.dialog);
    dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
    dialog.getWindow().setGravity(80);
    dialog.requestWindowFeature(1);
    View viewInflate = LayoutInflater.from(this).inflate(R.layout.dialog, null);
    dialog.show();
    dialog.setContentView(viewInflate);
    Display defaultDisplay = getWindowManager().getDefaultDisplay();
    LayoutParams attributes = dialog.getWindow().getAttributes();
    attributes.width = defaultDisplay.getWidth();
    dialog.getWindow().setAttributes(attributes);
    TextView textView = viewInflate.findViewById(R.id.dialogTextView1);
    TextView textView2 = viewInflate.findViewById(R.id.dialogTextView2);
    Button button = viewInflate.findViewById(R.id.dialogButton1);
    Button button2 = viewInflate.findViewById(R.id.dialogButton2);
    textView2.setText("公告");
    textView.setText(this.noticeContent);
    button.setText(this.noticeButtonText);
    button2.setVisibility(View.GONE);
    button.setOnClickListener(new b(this, dialog));
  }

  public void database() {
    if (this.loadingDialog == null) {
      Dialog dialog = new Dialog(this);
      this.loadingDialog = dialog;
      dialog.requestWindowFeature(1);
      this.loadingDialog.getWindow().setWindowAnimations(R.style.loadingAnim);
      this.loadingDialog.setContentView(R.layout.loading);
      this.loadingDialog.setCancelable(false);
    }
    if (this.loadingDialog.isShowing() || isFinishing()) {
      return;
    }
    this.loadingDialog.show();
  }

  public void c0() {
    w0 w0Var = new w0(this);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("com.music.exit");
    intentFilter.addAction("com.music.upview2");
    registerReceiver(w0Var, intentFilter);
    this.leftDisplayList = new ArrayList();
    this.rightDisplayList = new ArrayList();
    PopupWindow popupWindow = new PopupWindow(this);
    this.suggestionPopup = popupWindow;
    popupWindow.setWidth(600);
    this.playList = new ArrayList();
    this.suggestionPopup.setBackgroundDrawable(new ColorDrawable(0));
    this.suggestionPopup.setOutsideTouchable(true);
    this.suggestionPopup.setFocusable(false);
    this.suggestionPopup.setInputMethodMode(1);
    this.musicUrlHelper = new MusicUrlHelper();
    RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
    this.rotateAnimation = rotateAnimation;
    rotateAnimation.setDuration(20000L);
    this.rotateAnimation.setFillAfter(true);
    this.rotateAnimation.setRepeatMode(1);
    this.rotateAnimation.setInterpolator(new LinearInterpolator());
    this.rotateAnimation.setRepeatCount(-1);
    this.appContextHolder = new ContextHolder(getApplicationContext());
    mediaPlayer = new MediaPlayer();
    this.audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    this.searchEditText.addTextChangedListener(new c(this));
    this.leftListView.setOnItemClickListener(new d(this));
    this.leftListView.setOnItemLongClickListener(new e(this));
    this.rightListView.setOnItemClickListener(new f(this));
    this.rightListView.setOnItemLongClickListener(new g(this));
    this.searchEditText.setOnEditorActionListener(new h(this));
    p0();
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent motionEvent) {
    if (motionEvent.getAction() == 0) {
      this.touchDownX = motionEvent.getX();
      this.touchDownY = motionEvent.getY();
    }
    if (motionEvent.getAction() == 1) {
      this.touchUpX = motionEvent.getX();
      float y2 = motionEvent.getY();
      this.touchUpY = y2;
      if (this.touchUpX - this.touchDownX > 200.0f && Math.abs(this.touchDownY - y2) < 150.0f
          && !this.leftMenuLayout.isDrawerOpen(GravityCompat.START)) {
        this.canShowQualityDialog = false;
        this.mainHandler.postDelayed(new g0(this), 300L);
        Dialog dialog = this.qualityDialog;
        if (dialog == null || !dialog.isShowing()) {
          this.leftMenuLayout.isDrawerOpen(GravityCompat.START);
        }
      }
    }
    return super.dispatchTouchEvent(motionEvent);
  }

  @Override
  public Resources getResources() {
    Resources resources = super.getResources();
    Configuration configuration = new Configuration();
    configuration.setToDefaults();
    resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    return resources;
  }

  public void l0(String str) {
    new HistoryManager(getApplicationContext()).saveHistory(str);
  }

  public void m0() {
    this.isSearchEnabled = true;
    this.isSearchCancelled = false;
    new Thread(new h0(this)).start();
  }

  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setTheme(R.style.popup);
    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    setContentView(R.layout.left_menu);
    this.leftMenuLayout = findViewById(R.id.drawerLayout);
    NavigationView navigationView = findViewById(R.id.nav_view);
    ImageView imageView = navigationView.getHeaderView(0).findViewById(R.id.iv_avatar);
    this.avatarImageView = imageView;
    imageView.setOnClickListener(new k(this));
    navigationView.setNavigationItemSelectedListener(new v(this));
    initVariables();
    getSdcardPath();
    c0();
    w0();
    try {
      SongDatabaseHelper oVar = new SongDatabaseHelper(getApplicationContext());
      this.database = oVar.getWritableDatabase();
    } catch (Exception e2) {
      showMessage(e2.toString());
    }
    new AppUpdateManager(this).init();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    stopService(new Intent(getApplicationContext(), mediaPlayer.getClass()));
    searchEditText();
    PopupWindow popupWindow = this.suggestionPopup;
    if (popupWindow != null && popupWindow.isShowing()) {
      this.suggestionPopup.dismiss();
    }
    Dialog dialog = this.downloadProgressDialog;
    if (dialog == null || !dialog.isShowing()) {
      return;
    }
    this.downloadProgressDialog.dismiss();
  }

  @Override
  public boolean onKeyDown(int i2, KeyEvent keyEvent) {
    if (i2 == 4) {
      if (!isSearchEnabled && loadingDialog != null && loadingDialog.isShowing()) {
        isSearchCancelled = true;
        searchEditText();
        showMessage("搜索已取消");
        return true;
      }

      DrawerLayout drawerLayout = this.leftMenuLayout;
      if (drawerLayout == null || drawerLayout.isDrawerOpen(GravityCompat.START)) {
        DrawerLayout drawerLayout2 = this.leftMenuLayout;
        if (drawerLayout2 != null) {
          drawerLayout2.open();
        }
      } else {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - aLong <= 2000) {
          stopService(new Intent(getApplicationContext(), mediaPlayer.getClass()));
          finish();
          Process.killProcess(Process.myPid());
        } else {
          showMessage("再按一次退出程序");
          aLong = jCurrentTimeMillis;
        }
      }
      return true;
    }
    return super.onKeyDown(i2, keyEvent);
  }

  @Override
  protected void onResume() {
    MediaPlayer mediaPlayer = PlayerService.mediaPlayer;
    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
      this.coverImageView.startAnimation(this.rotateAnimation);
      this.coverImageView.setVisibility(View.VISIBLE);
    }
    getDownloadDirectory();
    super.onResume();
  }

  public void v0() {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    int screenWidth = displayMetrics.widthPixels;

    DialogFactory dialogHelper = new DialogFactory();
    Dialog dialog = dialogHelper.createDialog(this);

    View contentView = LayoutInflater.from(this).inflate(R.layout.tx, null);
    dialog.setContentView(contentView);
    dialog.show();

    Display display = getWindowManager().getDefaultDisplay();
    WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
    layoutParams.width = display.getWidth();
    dialog.getWindow().setAttributes(layoutParams);

    HistoryManager dataProvider = new HistoryManager(this);
    List<Map<String, Object>> dataList = dataProvider.getHistory();

    ViewGroup container = contentView.findViewById(R.id.txFlowLayout1);
    View closeButton = contentView.findViewById(R.id.txRelativeLayout1);

    closeButton.setOnClickListener(new n0(this, dialog));

    o0 itemClickListener = new o0(this, dialog);

    int buttonHeight = 85;
    int padding = 0;

    for (int i = 0; i < dataList.size(); i++) {
      Button button = new Button(this);
      button.setTextSize(11.0f);
      TextPaint textPaint = button.getPaint();
      button.setStateListAnimator(null);
      button.setBackgroundResource(R.drawable.selector_btn_gray_light_rounded);
      button.setSingleLine(true);

      String text = dataList.get(i).get("data").toString();
      button.setText(text);
      button.setTextColor(0xFF000000);

      float textWidth = textPaint.measureText(text) + 70.0f;
      if (textWidth < 150.0f) {
        textWidth = 150.0f;
      }

      button.setPadding(35, padding, 35, padding);
      button.setGravity(17);

      if (screenWidth >= 1440) {
        buttonHeight = 112;
      }

      LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
          (int) textWidth,
          buttonHeight
      );
      container.addView(button, layoutParams2);
      button.setOnClickListener(itemClickListener);
    }
  }

  public void z0() {
    o0(2);
  }

  static class p0 {

    static final int[] f2299a;

    static final int[] f2300b;

    static {
      int[] iArr = new int[MusicPlatform.values().length];
      f2300b = iArr;
      try {
        iArr[MusicPlatform.QQ.ordinal()] = 1;
      } catch (NoSuchFieldError ignored) {
      }
      try {
        f2300b[MusicPlatform.WYY.ordinal()] = 2;
      } catch (NoSuchFieldError ignored) {
      }
      try {
        f2300b[MusicPlatform.KUGOU.ordinal()] = 3;
      } catch (NoSuchFieldError ignored) {
      }
      try {
        f2300b[MusicPlatform.MIGU.ordinal()] = 4;
      } catch (NoSuchFieldError ignored) {
      }
      try {
        f2300b[MusicPlatform.KUWO.ordinal()] = 5;
      } catch (NoSuchFieldError ignored) {
      }
      int[] iArr2 = new int[NumberEnum.values().length];
      f2299a = iArr2;
      try {
        iArr2[NumberEnum.ONE.ordinal()] = 1;
      } catch (NoSuchFieldError ignored) {
      }
      f2299a[NumberEnum.TWO.ordinal()] = 2;
      try {
        f2299a[NumberEnum.THREE.ordinal()] = 3;
      } catch (NoSuchFieldError ignored) {
      }
    }
  }

  static class o0 implements OnClickListener {

    final Dialog f2294a;

    final MainActivity f2295b;

    o0(MainActivity music, Dialog dialog) {
      this.f2295b = music;
      this.f2294a = dialog;
    }

    @Override
    public void onClick(View view) {
      this.f2295b.isSearchEnabled = false;
      this.f2294a.dismiss();
      this.f2295b.searchText = ((Button) view).getText().toString();
      if (this.f2295b.searchText.equals(FrameBodyCOMM.DEFAULT)) {
        return;
      }
      MainActivity music = this.f2295b;
      music.searchEditText.setText(music.searchText);
      this.f2295b.z0();
      this.f2295b.m0();
    }
  }

  static class p implements Runnable {

    final MainActivity f2296a;

    p(MainActivity music) {
      this.f2296a = music;
    }

    @Override
    public void run() {
      String string = this.f2296a.appPreferences.getString("qqimg", FrameBodyCOMM.DEFAULT);
      if (string.equals(FrameBodyCOMM.DEFAULT)) {
        return;
      }
      this.f2296a.tempBitmap = ImageDownloadUtils.getBitmapFromUrl(
          "http://q1.qlogo.cn/g?b=qq&nk=" + string + "&s=640");
      this.f2296a.runOnUiThread(new a(this));
    }

    static class a implements Runnable {

      final p f2297a;

      a(p pVar) {
        this.f2297a = pVar;
      }

      @Override
      public void run() {
        MainActivity music = this.f2297a.f2296a;
        Bitmap bitmap = music.tempBitmap;
        ImageView imageView = music.menuImageView;
        if (bitmap != null) {
          imageView.setImageBitmap(bitmap);
          MainActivity mainActivity = this.f2297a.f2296a;
          mainActivity.avatarImageView.setImageBitmap(mainActivity.tempBitmap);
        } else {
          imageView.setImageResource(R.drawable.ic_avatar);
          this.f2297a.f2296a.avatarImageView.setImageResource(R.mipmap.ic_launcher);
          this.f2297a.f2296a.menuImageView.setOnLongClickListener(
              new ViewOnLongClickListenerC0020a(this));
        }
      }

      class ViewOnLongClickListenerC0020a implements OnLongClickListener {

        final a f2298a;

        ViewOnLongClickListenerC0020a(a aVar) {
          this.f2298a = aVar;
        }

        @Override
        public boolean onLongClick(View view) {
          return false;
        }
      }
    }
  }

  static class q implements OnClickListener {

    final Dialog f2301a;

    final MainActivity f2302b;

    q(MainActivity music, Dialog dialog) {
      this.f2302b = music;
      this.f2301a = dialog;
    }

    @Override
    public void onClick(View view) {
      this.f2302b.leftScrollPosition("mp3");
      this.f2301a.dismiss();
    }
  }

  static class r implements OnClickListener {

    final Dialog f2305a;

    final MainActivity f2306b;

    r(MainActivity music, Dialog dialog) {
      this.f2306b = music;
      this.f2305a = dialog;
    }

    @Override
    public void onClick(View view) {
      this.f2306b.leftScrollPosition("hq");
      this.f2305a.dismiss();
    }
  }

  static class b implements OnClickListener {

    final Dialog dialog;

    final MainActivity mainActivity;

    b(MainActivity music, Dialog dialog) {
      this.mainActivity = music;
      this.dialog = dialog;
    }

    @Override
    public void onClick(View view) {
      if (this.mainActivity.noticeMessage1.equals(FrameBodyCOMM.DEFAULT)) {
        this.dialog.dismiss();
        return;
      }
      try {
        this.dialog.dismiss();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(this.mainActivity.noticeMessage1));
        this.mainActivity.startActivity(intent);
      } catch (Exception ignored) {
      }
    }
  }

  static class c implements TextWatcher {

    final MainActivity mainActivity;

    c(MainActivity music) {
      this.mainActivity = music;
    }

    @Override
    public void afterTextChanged(Editable editable) {
      if (mainActivity.searchControlFlag == 0) {
        boolean zEquals = mainActivity.searchEditText.getText().toString()
            .equals(FrameBodyCOMM.DEFAULT);
        if (zEquals || !mainActivity.isSearchEnabled) {
          if (mainActivity.suggestionPopup == null
              || !this.mainActivity.suggestionPopup.isShowing()) {
            return;
          }
          this.mainActivity.suggestionPopup.dismiss();
          return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - mainActivity.lastSearchSuggestionTime > 600) {
          mainActivity.lastSearchSuggestionTime = jCurrentTimeMillis;
          new a(this).start();
        }
      }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
    }

    class a extends Thread {

      final c f2224a;

      a(c cVar) {
        this.f2224a = cVar;
      }

      @Override
      public void run() {
        this.f2224a.mainActivity.searchSuggestions = new ArrayList();
        try {
          JSONArray jSONArray = new JSONObject(HttpRequestUtils.httpGet(
              "http://msearchcdn.kugou.com/new/app/i/search.php?cmd=302&keyword="
                  + this.f2224a.mainActivity.searchEditText.getText().toString())).getJSONArray(
              Mp4DataBox.IDENTIFIER);
          for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            this.f2224a.mainActivity.searchSuggestionMap = new HashMap();
            this.f2224a.mainActivity.searchSuggestionMap.put("key",
                jSONArray.getJSONObject(i2).getString("keyword"));
            MainActivity music = this.f2224a.mainActivity;
            music.searchSuggestions.add(music.searchSuggestionMap);
          }
          this.f2224a.mainActivity.o0(7);
        } catch (Exception ignored) {
        }
      }
    }
  }

  static class c0 implements OnClickListener {

    final int f2225a;

    final MainActivity f2226b;

    c0(MainActivity music, int i2) {
      this.f2226b = music;
      this.f2225a = i2;
    }

    @Override
    public void onClick(View view) {
      this.f2226b.qualityDialog.dismiss();
      MainActivity music = this.f2226b;
      MusicPlatform pVar = music.currentMusicPlatform;
      if (pVar != MusicPlatform.KUGOU && pVar != MusicPlatform.WYY) {
        music.h0(this.f2226b.leftMusicList.get(this.f2225a).get("mvid") + FrameBodyCOMM.DEFAULT);
        return;
      }
      this.f2226b.h0(
          this.f2226b.rightMusicList.get(this.f2225a).get("mvid") + FrameBodyCOMM.DEFAULT);
    }
  }

  static class d implements OnItemClickListener {

    final MainActivity mainActivity;

    d(MainActivity music) {
      this.mainActivity = music;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
      MainActivity music;
      MusicPlatform pVar;
      if (mainActivity.isLeftMultiSelectMode) {
        mainActivity.V(i2, 1);
        this.mainActivity.leftListAdapter.notifyDataSetChanged();
        return;
      }
      List<Map<String, Object>> list = mainActivity.leftMusicList;
      if (list == null || list.size() < i2 + 1) {
        this.mainActivity.showMessage("数据异常，请重新搜索，或者截图发送给管理员！");
        return;
      }
      mainActivity.leftListSelectedIndex = i2;
      int i3 = p0.f2299a[mainActivity.currentSearchEngine.ordinal()];
      if (i3 == 1) {
        music = this.mainActivity;
        pVar = MusicPlatform.QQ;
      } else if (i3 == 3) {
        music = this.mainActivity;
        pVar = MusicPlatform.KUWO;
      } else {
        music = this.mainActivity;
        pVar = MusicPlatform.MIGU;
      }
      music.currentMusicPlatform = pVar;
      this.mainActivity.qualitySelector(i2);
    }
  }

  static class e0 extends Thread {

    final String coverUrl;

    final MainActivity mainActivity;

    e0(MainActivity music, String str) {
      this.mainActivity = music;
      this.coverUrl = str;
    }

    @Override
    public void run() {
      MainActivity music;
      Bitmap bitmapJ;
      int i2 = p0.f2300b[this.mainActivity.currentMusicPlatform.ordinal()];
      if (i2 == 1) {
        music = this.mainActivity;
        bitmapJ = ImageDownloadUtils.getQQMusicBitmap(this.coverUrl);
      } else if (i2 == 2) {
        music = this.mainActivity;
        bitmapJ = ImageDownloadUtils.getNeteaseCloudBitmap(
            this.coverUrl.replace(" ", FrameBodyCOMM.DEFAULT));
      } else if (i2 == 3) {
        music = this.mainActivity;
        bitmapJ = ImageDownloadUtils.getKugouAlbumCover(this.coverUrl);
      } else if (i2 == 5) {
        music = this.mainActivity;
        bitmapJ = ImageDownloadUtils.getKuwoBitmap(this.coverUrl);
      } else {
        if (i2 != 4) {
          Message message = new Message();
          message.what = 14;
          message.obj = mainActivity.coverBitmap;
          mainActivity.mainHandler.sendMessage(message);
        }
        music = this.mainActivity;
        bitmapJ = ImageDownloadUtils.getBitmapFromUrl(this.coverUrl);
      }
      music.coverBitmap = bitmapJ;
      Message message2 = new Message();
      message2.what = 14;
      message2.obj = mainActivity.coverBitmap;
      mainActivity.mainHandler.sendMessage(message2);
    }
  }

  static class f implements OnItemClickListener {

    final MainActivity f2233a;

    f(MainActivity music) {
      this.f2233a = music;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
      MainActivity music;
      MusicPlatform pVar;
      MainActivity mainActivity = this.f2233a;
      if (mainActivity.isRightMultiSelectMode) {
        mainActivity.V(i2, 2);
        this.f2233a.rightListAdapter.notifyDataSetChanged();
        return;
      }
      List<Map<String, Object>> list = mainActivity.rightMusicList;
      if (list == null || list.size() < i2 + 1) {
        this.f2233a.showMessage("数据异常，请重新搜索");
        return;
      }
      mainActivity.leftListSelectedIndex = i2;
      int i3 = p0.f2299a[mainActivity.currentSearchEngine.ordinal()];
      if (i3 == 1) {
        music = this.f2233a;
        pVar = MusicPlatform.WYY;
      } else if (i3 == 3) {
        music = this.f2233a;
        pVar = MusicPlatform.KUWO;
      } else {
        music = this.f2233a;
        pVar = MusicPlatform.KUGOU;
      }

      music.currentMusicPlatform = pVar;
      this.f2233a.qualitySelector(i2);
    }
  }

  static class f0 extends Thread {

    final String f2234a;
    final int f2235b;
    final String f2236c;
    final MainActivity mainActivity;

    f0(MainActivity mainActivity, String operation, int index, String quality) {
      this.mainActivity = mainActivity;
      this.f2234a = operation;
      this.f2235b = index;
      this.f2236c = quality;
    }

    @Override
    public void run() throws NumberFormatException {
      if (this.f2234a.equals("l")) {
        handlePlayOperation();
        return;
      }

      if (this.f2234a.equals("d")) {
        handleDownloadOperation();
      }
    }

    private void handlePlayOperation() {
      if (!CommonUtils.checkNotificationPermission(mainActivity.getApplicationContext())) {
        mainActivity.mainHandler.post(new NotificationPermissionDialog(this));
        return;
      }

      mainActivity.playListIndex = this.f2235b;
      MusicPlatform platform = mainActivity.currentMusicPlatform;

      List<Map<String, Object>> sourceList =
          (platform == MusicPlatform.KUGOU || platform == MusicPlatform.WYY)
              ? mainActivity.rightMusicList
              : mainActivity.leftMusicList;

      mainActivity.playList.clear();
      mainActivity.playList.addAll(sourceList);
      GlobalData.playList = mainActivity.playList;
      GlobalData.currentIndex = this.f2235b;

      String platformSource = getPlatformSource(platform);
      if (platformSource == null) {
        return;
      }
      mainActivity.playerSource = platformSource;
      GlobalData.currentSource = platformSource;

      Intent intent = new Intent(mainActivity.getApplicationContext(), PlayerService.class);
      if (Build.VERSION.SDK_INT >= 26) {
        mainActivity.startForegroundService(intent);
      } else {
        mainActivity.startService(intent);
      }
      mainActivity.o0(19);
    }

    private void handleDownloadOperation() {
      if (mainActivity.isDownloading) {
        mainActivity.isDownloadCancelled = false;
        mainActivity.t0();
        mainActivity.showMessage("下载器忙,如需批量下载,请长按列表");
        return;
      }

      mainActivity.z0();
      try {
        Thread.sleep(50L);
      } catch (InterruptedException ignored) {
      }

      MusicPlatform platform = mainActivity.currentMusicPlatform;
      String musicUrl = null;
      String fileExtension = null;

      try {
        if (platform == MusicPlatform.KUGOU) {
          DownloadResult result = downloadKugou();
          musicUrl = result.url;
          fileExtension = result.extension;
        } else if (platform == MusicPlatform.WYY) {
          DownloadResult result = downloadNetease();
          musicUrl = result.url;
          fileExtension = result.extension;
        } else if (platform == MusicPlatform.MIGU) {
          DownloadResult result = downloadMigu();
          musicUrl = result.url;
          fileExtension = result.extension;
        } else if (platform == MusicPlatform.KUWO) {
          DownloadResult result = downloadKuwo();
          musicUrl = result.url;
          fileExtension = result.extension;
        } else if (platform == MusicPlatform.QQ) {
          DownloadResult result = downloadQQ();
          musicUrl = result.url;
          fileExtension = result.extension;
        } else {
          return;
        }

        if (musicUrl != null && fileExtension != null) {
          SongInfo info = getSongInfo(platform);
          mainActivity.downloadMusicFile(musicUrl, info.fileName, fileExtension, info.title,
              info.singer, info.album);
        } else {
          String errorMsg = (platform == MusicPlatform.WYY)
              ? "获取资源失败"
              : "获取该品质失败,请尝试其它品质";
          mainActivity.showMessage(errorMsg);
        }
      } catch (IOException | JSONException e) {
        throw new RuntimeException(e);
      }
    }

    private DownloadResult downloadKugou() throws IOException, JSONException {
      Map<String, Object> songData = mainActivity.rightMusicList.get(this.f2235b);

      String fileHash = songData.get("filehash") + "";
      String hashSubstring = fileHash.substring(0, fileHash.indexOf("低高"));

      SongInfo info = getSongInfo(MusicPlatform.KUGOU);

      downloadLyric("kugou", hashSubstring, info.fileName, null);

      String hash = null;
      String extension = null;

      switch (this.f2236c) {
        case "mp3":
          hash = fileHash.substring(0, fileHash.indexOf("低高"));
          extension = ".mp3";
          break;
        case "hq":
          hash = fileHash.substring(fileHash.indexOf("低高") + 2, fileHash.indexOf("高无"));
          extension = ".mp3";
          break;
        case "sq":
          hash = fileHash.substring(fileHash.indexOf("高无") + 2, fileHash.indexOf("无h"));
          extension = ".flac";
          break;
        case "hr":
          hash = fileHash.substring(fileHash.indexOf("无h") + 2, fileHash.indexOf("高真"));
          extension = ".flac";
          break;
        case "dsd":
          hash = fileHash.substring(fileHash.indexOf("高真") + 2);
          extension = ".dff";
          break;
      }

      if (hash != null) {
        mainActivity.searchEditText();
        String url = mainActivity.musicUrlHelper.getMusicUrl("kugou", hash, this.f2236c);
        if (url != null && !url.isEmpty()) {
          mainActivity.coverImageId = hash;
          return new DownloadResult(url, extension);
        }
      }

      return new DownloadResult(null, null);
    }

    private DownloadResult downloadNetease() throws IOException, JSONException {
      Map<String, Object> songData = mainActivity.rightMusicList.get(this.f2235b);
      String songId = songData.get("id") + "";

      SongInfo info = getSongInfo(MusicPlatform.WYY);

      downloadLyric("wyy", songId, info.fileName, null);

      String quality = this.f2236c;
      String extension = (quality.equals("sq") || quality.equals("hr")) ? ".flac" : ".mp3";

      mainActivity.searchEditText();
      String url = mainActivity.musicUrlHelper.getMusicUrl("wyy", songId, quality);

      if (url != null && !url.isEmpty() && !url.equals("null")) {
        mainActivity.coverImageId = songId;
        return new DownloadResult(url, extension);
      }

      return new DownloadResult(null, null);
    }

    private DownloadResult downloadMigu() throws IOException, JSONException {
      Map<String, Object> songData = mainActivity.leftMusicList.get(this.f2235b);
      String songId = songData.get("id") + "";
      String lrcUrl = songData.get("lrc") + "";

      SongInfo info = getSongInfo(MusicPlatform.MIGU);

      downloadLyric("migu", songId, info.fileName, lrcUrl);

      String quality = this.f2236c;
      String extension = (quality.equals("sq") || quality.equals("hr")) ? ".flac" : ".mp3";

      mainActivity.searchEditText();
      String url = mainActivity.musicUrlHelper.getMusicUrl("migu", songId, quality);

      if (url != null && !url.isEmpty()) {
        mainActivity.coverImageId = songData.get("imgurl") + "";
        return new DownloadResult(url, extension);
      }

      return new DownloadResult(null, null);
    }

    private DownloadResult downloadKuwo() throws IOException, JSONException {
      Map<String, Object> songData = mainActivity.leftMusicList.get(this.f2235b);
      String songId = songData.get("id") + "";

      SongInfo info = getSongInfo(MusicPlatform.KUWO);

      downloadLyric("kuwo", songId, info.fileName, null);

      String quality = this.f2236c;
      String extension = (quality.equals("sq") || quality.equals("hr")) ? ".flac" : ".mp3";

      mainActivity.searchEditText();
      String url = mainActivity.musicUrlHelper.getMusicUrl("kuwo", songId, quality);

      if (url != null && !url.isEmpty()) {
        mainActivity.coverImageId = songId;
        return new DownloadResult(url, extension);
      }

      return new DownloadResult(null, null);
    }

    private DownloadResult downloadQQ() throws IOException, JSONException {
      Map<String, Object> songData = mainActivity.leftMusicList.get(this.f2235b);
      String songId = songData.get("id") + "";
      String albumId = songData.get("albumid") + "";

      SongInfo info = getSongInfo(MusicPlatform.QQ);

      downloadLyric("qq", songId, info.fileName, null);

      String quality = this.f2236c;
      String extension = (quality.equals("sq") || quality.equals("hr")) ? ".flac" : ".mp3";

      mainActivity.searchEditText();
      String url = mainActivity.musicUrlHelper.getMusicUrl("qq", songId, quality);

      if (url != null && !url.isEmpty()) {
        mainActivity.coverImageId = albumId;
        return new DownloadResult(url, extension);
      }

      return new DownloadResult(null, null);
    }

    private SongInfo getSongInfo(MusicPlatform platform) {
      List<Map<String, Object>> list =
          (platform == MusicPlatform.KUGOU || platform == MusicPlatform.WYY)
              ? mainActivity.rightMusicList
              : mainActivity.leftMusicList;

      Map<String, Object> songData = list.get(this.f2235b);

      String originalFileName = songData.get("filename") + "";
      String title = songData.get("name") + "";
      String singer = songData.get("singer") + "";
      String album = songData.get("album") + "";

      String fileName = (mainActivity.appPreferences.getInt("filemode", 0) == 1)
          ? title + " - " + singer
          : originalFileName;

      return new SongInfo(title, singer, album, fileName);
    }

    private void downloadLyric(String platform, String id, String fileName, String lrcUrl)
        throws IOException {
      String encoding = mainActivity.appPreferences.getInt("type", 0) == 0 ? "utf-8" : "gbk";
      int lrcMode = mainActivity.appPreferences.getInt("lrcmode", 0);
      int textMode = mainActivity.appPreferences.getInt("textmode", 0);

      if (lrcMode == 0) {
        String lrcPath = mainActivity.downloadDirectory + "/" + fileName + ".lrc";
        downloadLyricByPlatform(platform, id, lrcPath, encoding, lrcUrl);
      } else if (textMode == 0) {
        String tmpLrcPath = mainActivity.getFilesDir().getParent() + "/app_tmpFile/downTmp.lrc";
        mainActivity.hasLyricFile = downloadLyricByPlatform(platform, id, tmpLrcPath, encoding,
            lrcUrl);
      }
    }

    private boolean downloadLyricByPlatform(String platform, String id, String path,
        String encoding, String lrcUrl) throws IOException {
      switch (platform) {
        case "kugou":
          return LyricDownloadUtils.downloadKugouLyric(id, path, encoding);
        case "wyy":
          return LyricDownloadUtils.downloadNeteaseCloudLyric(id, path, encoding);
        case "migu":
          return LyricDownloadUtils.downloadLyricFromUrl(lrcUrl, path, encoding);
        case "kuwo":
          return LyricDownloadUtils.downloadKuwoLyric(id, path, encoding);
        case "qq":
          return LyricDownloadUtils.downloadQQMusicLyric(id, path, encoding);
        default:
          return false;
      }
    }

    private String getPlatformSource(MusicPlatform platform) {
      if (platform == MusicPlatform.KUGOU) {
        return "kugou";
      }
      if (platform == MusicPlatform.WYY) {
        return "wyy";
      }
      if (platform == MusicPlatform.MIGU) {
        return "migu";
      }
      if (platform == MusicPlatform.KUWO) {
        return "kuwo";
      }
      if (platform == MusicPlatform.QQ) {
        return "qq";
      }
      return null;
    }

    private static class SongInfo {

      final String title;
      final String singer;
      final String album;
      final String fileName;

      SongInfo(String title, String singer, String album, String fileName) {
        this.title = title;
        this.singer = singer;
        this.album = album;
        this.fileName = fileName;
      }
    }

    private static class DownloadResult {

      final String url;
      final String extension;

      DownloadResult(String url, String extension) {
        this.url = url;
        this.extension = extension;
      }
    }

    static class NotificationPermissionDialog implements Runnable {

      final f0 f2238a;

      NotificationPermissionDialog(f0 f0Var) {
        this.f2238a = f0Var;
      }

      @Override
      public void run() {
        CommonUtils.showNotificationPermissionDialog(this.f2238a.mainActivity);
      }
    }
  }

  static class g0 implements Runnable {

    final MainActivity f2240a;

    g0(MainActivity music) {
      this.f2240a = music;
    }

    @Override
    public void run() {
      this.f2240a.canShowQualityDialog = true;
    }
  }

  static class h implements OnEditorActionListener {

    final MainActivity mainActivity;

    h(MainActivity music) {
      this.mainActivity = music;
    }

    @Override
    public boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
      if (i2 != 3) {
        return false;
      }
      if (this.mainActivity.suggestionPopup != null) {
        this.mainActivity.suggestionPopup.dismiss();
      }
      mainActivity.searchText = mainActivity.searchEditText.getText().toString();
      if (this.mainActivity.searchText.isEmpty()) {
        return false;
      }
      this.mainActivity.z0();
      this.mainActivity.m0();
      return false;
    }
  }

  static class h0 implements Runnable {

    final MainActivity mainActivity;

    h0(MainActivity music) {
      this.mainActivity = music;
    }

    @Override
    public void run() {
      int i2 = 0;
      MusicPlatform pVar;
      String str;
      try {
        Thread.sleep(10L);
        i2 = p0.f2299a[this.mainActivity.currentSearchEngine.ordinal()];
      } catch (Exception e2) {
      }

      if (this.mainActivity.isSearchCancelled) {
        this.mainActivity.searchEditText();
        return;
      }

      if (i2 == 1) {
        try {
          mainActivity.leftMusicList = MusicSearchUtils.search(MusicPlatform.QQ,
              mainActivity.searchText);
        } catch (JSONException e) {
          throw new RuntimeException(e);
        }

        if (this.mainActivity.isSearchCancelled) {
          this.mainActivity.searchEditText();
          return;
        }

        pVar = MusicPlatform.WYY;
      } else {
        if (i2 != 2) {
          if (i2 == 3) {
            try {
              mainActivity.leftMusicList = MusicSearchUtils.search(MusicPlatform.KUWO,
                  mainActivity.searchText);
            } catch (JSONException e) {
              throw new RuntimeException(e);
            }
            this.mainActivity.rightMusicList = null;
          }

          if (this.mainActivity.isSearchCancelled) {
            this.mainActivity.searchEditText();
            return;
          }

          Message message = new Message();
          message.what = 0;
          this.mainActivity.mainHandler.sendMessage(message);
          this.mainActivity.searchEditText();
          this.mainActivity.o0(12);
          return;
        }
        try {
          mainActivity.leftMusicList = MusicSearchUtils.search(MusicPlatform.MIGU,
              mainActivity.searchText);
        } catch (JSONException e) {
          throw new RuntimeException(e);
        }

        if (this.mainActivity.isSearchCancelled) {
          this.mainActivity.searchEditText();
          return;
        }

        pVar = MusicPlatform.KUGOU;
      }
      str = mainActivity.searchText;

      try {
        mainActivity.rightMusicList = MusicSearchUtils.search(pVar, str);
      } catch (JSONException e) {
        throw new RuntimeException(e);
      }

      if (this.mainActivity.isSearchCancelled) {
        this.mainActivity.searchEditText();
        return;
      }

      Message message2 = new Message();
      message2.what = 0;
      this.mainActivity.mainHandler.sendMessage(message2);
      this.mainActivity.searchEditText();
      this.mainActivity.o0(12);
    }
  }

  static class QualityClickListener implements OnClickListener {

    final int index;
    final String action;
    final String quality;
    final MainActivity activity;

    QualityClickListener(MainActivity activity, int index, String action, String quality) {
      this.activity = activity;
      this.index = index;
      this.action = action;
      this.quality = quality;
    }

    @Override
    public void onClick(View view) {
      activity.qualityDialog.dismiss();
      activity.g0(action, index, quality);
    }
  }

  private static class x0 extends AppCompatImageView {

    final MainActivity f2331a;

    public x0(MainActivity music, Context context) {
      super(context);
      this.f2331a = music;
    }

    @Override
    protected void dispatchSetPressed(boolean z2) {
      super.dispatchSetPressed(z2);
      invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
      if (isPressed()) {
        canvas.drawColor(855638016);
      }
    }
  }

  public static class y0 implements OnClickListener {

    final MainActivity f2334a;

    public y0(MainActivity music) {
      this.f2334a = music;
    }

    @Override
    public void onClick(View view) {
      MainActivity music;
      int viewId = view.getId();
      if (viewId == R.id.eImageView1) {
        this.f2334a.startActivity(
            new Intent(this.f2334a.getApplicationContext(), PlayerActivity.class));
      } else if (viewId == R.id.eImageView3) {
        this.f2334a.noselectsongsDialog();
      } else if (viewId == R.id.eImageView4) {
        music = this.f2334a;
        if (!music.isLeftMultiSelectMode && !music.isRightMultiSelectMode) {
          music.switchyqListener();
        } else {
          music.showMessage("请先再次长按列表取消多选状态再试");
        }
      } else if (viewId == R.id.eImageView5) {
        this.f2334a.leftMenuLayout.openDrawer(Gravity.LEFT);
      } else if (viewId == R.id.eImageView6) {
        this.f2334a.n0();
      } else if (viewId == R.id.eLinearLayout1) {
        this.f2334a.searchEditText.setText(FrameBodyCOMM.DEFAULT);
      } else if (viewId == R.id.eLinearLayout2) {
        music = this.f2334a;
        if (!music.isLeftMultiSelectMode && !music.isRightMultiSelectMode) {
          music.v0();
        } else {
          music.showMessage("请先再次长按列表取消多选状态再试");
        }
      } else if (viewId == R.id.searchTextView) {
        MainActivity mainActivity = this.f2334a;
        mainActivity.searchText = mainActivity.searchEditText.getText().toString();
        if (!this.f2334a.searchText.equals(FrameBodyCOMM.DEFAULT)) {
          music = this.f2334a;
          if (!music.isLeftMultiSelectMode && !music.isRightMultiSelectMode) {
            music.l0(music.searchText);
            this.f2334a.z0();
            this.f2334a.m0();
          } else {
            music.showMessage("请先再次长按列表取消多选状态再试");
          }
        }
      } else if (viewId == R.id.gklayout) {
        MainActivity mainActivity = this.f2334a;
        if (mainActivity.noticeContent != null) {
          mainActivity.showNotice2();
        }
      }
    }
  }

  public static class v0 implements View.OnClickListener {

    final MainActivity mainActivity;
    final Dialog dialog;

    public v0(MainActivity mainActivity, Dialog dialog) {
      this.mainActivity = mainActivity;
      this.dialog = dialog;
    }

    @Override
    public void onClick(View v) {
      if (this.mainActivity.noticeMessage1.equals("")) {
        if (this.mainActivity.isNoticeRead) {
          SharedPreferences.Editor editor = this.mainActivity.appPreferences.edit();
          editor.putString("gk", this.mainActivity.noticeContent);
          editor.commit();
          this.dialog.dismiss();
        } else {
          String message =
              "请先阅读，剩余" + this.mainActivity.noticeCountdownSeconds + "秒才可以操作哦";
          mainActivity.showMessage(message);
        }
      } else {
        SharedPreferences.Editor editor = this.mainActivity.appPreferences.edit();
        editor.putString("gk", this.mainActivity.noticeContent);
        editor.commit();

        this.dialog.dismiss();

        try {
          Intent intent = new Intent(Intent.ACTION_VIEW);
          intent.setData(Uri.parse(this.mainActivity.noticeMessage1));
          this.mainActivity.startActivity(intent);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  class g implements AdapterView.OnItemLongClickListener {

    final MainActivity mainActivity;

    g(MainActivity music) {
      this.mainActivity = music;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
      if (!CommonUtils.checkNotificationPermission(this.mainActivity.getApplicationContext())) {
        CommonUtils.showNotificationPermissionDialog(this.mainActivity);
        return true;
      }

      if (this.mainActivity.rightMusicList == null
          || this.mainActivity.rightMusicList.size() < position + 1) {
        showMessage("数据异常,请重新搜索");
        return true;
      }

      switch (p0.f2299a[this.mainActivity.currentSearchEngine.ordinal()]) {
        case 1:
          this.mainActivity.currentSource = String.valueOf(MusicPlatform.WYY);
          break;
        case 2:
          this.mainActivity.currentSource = String.valueOf(MusicPlatform.KUGOU);
          break;
        case 3:
          this.mainActivity.currentSource = null;
          break;
      }

      boolean canOperate =
          !this.mainActivity.isBatchProcessing && !mainActivity.isLeftMultiSelectMode
              && !CommonUtils.isServiceRunning(this.mainActivity.getApplicationContext(),
              "com.mylrc.mymusic.service.DownloadService");

      if (canOperate) {
        if (this.mainActivity.isRightMultiSelectMode) {
          this.mainActivity.isRightMultiSelectMode = false;
          mainActivity.rightListAdapter.notifyDataSetChanged();
          this.mainActivity.batchDownloadImageView.setVisibility(View.INVISIBLE);
          this.mainActivity.selectAllImageView.setVisibility(View.INVISIBLE);
        } else {
          mainActivity.currentSource =
              mainActivity.currentSearchEngine == NumberEnum.ONE ? "wyy" : "kugou";
          mainActivity.isRightMultiSelectMode = true;
          mainActivity.rightSelectionMap.clear();
          this.mainActivity.selectedSongs.clear();
          this.mainActivity.rightListAdapter.notifyDataSetChanged();
          this.mainActivity.batchDownloadImageView.setVisibility(View.VISIBLE);
          this.mainActivity.selectAllImageView.setVisibility(
              View.VISIBLE);
          showMessage("再次长按可取消多选状态");
        }
      } else {
        showMessage("不允许操作双边对象或等数据加载完毕后重试或者该源不支持批量下载");
      }

      return true;
    }
  }

  class d0 extends Thread {

    final MainActivity mainActivity;
    final String searchString;

    d0(MainActivity mainActivity, String searchString) {
      this.mainActivity = mainActivity;
      this.searchString = searchString;
    }

    public void run() {
      int i2 = p0.f2300b[this.mainActivity.currentMusicPlatform.ordinal()];
      if (i2 == 1) {
        this.mainActivity.mvUrlList = MvUrlParser.parseMvUrl("qqMv", this.searchString);
        if (this.mainActivity.mvUrlList != null && !this.mainActivity.mvUrlList.isEmpty()) {
          Message message = new Message();
          message.what = 9;
          this.mainActivity.mainHandler.sendMessage(message);
        } else {
          this.mainActivity.showMessage("读取失败");
        }
      } else if (i2 == 2) {
        this.mainActivity.mvUrlList = MvUrlParser.parseMvUrl("wyyMv", this.searchString);
        if (this.mainActivity.mvUrlList != null && !this.mainActivity.mvUrlList.isEmpty()) {
          Message message = new Message();
          message.what = 9;
          this.mainActivity.mainHandler.sendMessage(message);
        } else {
          this.mainActivity.showMessage("读取失败");
        }
      } else if (i2 != 3) {
        this.mainActivity.mvUrlList = MvUrlParser.parseMvUrl("kugouMv", this.searchString);
        if (this.mainActivity.mvUrlList != null && !this.mainActivity.mvUrlList.isEmpty()) {
          Message message = new Message();
          message.what = 9;
          this.mainActivity.mainHandler.sendMessage(message);
        } else {
          this.mainActivity.showMessage("读取失败");
        }
      }
    }
  }

  class a implements View.OnClickListener {

    final Dialog noticeDialog;

    a(Dialog dialog) {
      this.noticeDialog = dialog;
    }

    @Override
    public void onClick(View view) {
      MainActivity mainActivity = MainActivity.this;
      if (mainActivity.noticeContent != null) {
        mainActivity.appPreferences.edit().putString("gk", mainActivity.noticeContent).apply();
        this.noticeDialog.dismiss();
        return;
      }
      mainActivity.showMessage(
          "请先阅读，剩余" + mainActivity.noticeCountdownSeconds + "秒才可以操作噢");
    }
  }

  class e implements OnItemLongClickListener {

    final MainActivity mainActivity;

    e(MainActivity music) {
      this.mainActivity = music;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i2, long j2) {
      String str = "";
      MusicPlatform pVar = null;

      boolean z2;
      boolean z3;
      if (CommonUtils.checkNotificationPermission(this.mainActivity.getApplicationContext())) {
        List<Map<String, Object>> list = this.mainActivity.leftMusicList;
        if (list == null || list.size() < i2 + 1) {
          str = "数据异常，请重新搜索";
        } else {
          int i3 = p0.f2299a[this.mainActivity.currentSearchEngine.ordinal()];
          if (i3 == 1) {
            pVar = MusicPlatform.QQ;
          } else if (i3 != 2) {
            if (i3 == 3) {
              pVar = MusicPlatform.KUWO;
            }
            z2 = mainActivity.isRightMultiSelectMode;
            z3 = mainActivity.isBatchProcessing;
            if (!((this.mainActivity.currentMusicPlatform == MusicPlatform.MIGU) & (!z2) & (!z3))
                || CommonUtils.isServiceRunning(getApplicationContext(),
                "com.mylrc.mymusic.service.DownloadService")) {
              if (mainActivity.isLeftMultiSelectMode) {
                mainActivity.isLeftMultiSelectMode = false;
                mainActivity.leftListAdapter.notifyDataSetChanged();
                this.mainActivity.batchDownloadImageView.setVisibility(View.INVISIBLE);
                this.mainActivity.selectAllImageView.setVisibility(View.INVISIBLE);
              } else {
                mainActivity.currentSource =
                    mainActivity.currentSearchEngine == NumberEnum.ONE ? "qq" : "kuwo";
                mainActivity.isLeftMultiSelectMode = true;
                mainActivity.leftSelectionMap.clear();
                this.mainActivity.selectedSongs.clear();
                this.mainActivity.leftListAdapter.notifyDataSetChanged();
                this.mainActivity.batchDownloadImageView.setVisibility(View.VISIBLE);
                this.mainActivity.selectAllImageView.setVisibility(View.VISIBLE);
                str = "再次长按可取消多选状态";
              }
            } else {
              str = "不允许操作双边对象或等数据加载完毕后重试或者该源不支持批量下载";
            }
          } else {
            pVar = MusicPlatform.MIGU;
          }
          this.mainActivity.currentMusicPlatform = pVar;
          MainActivity music32 = this.mainActivity;
          z2 = music32.isRightMultiSelectMode;
          z3 = music32.isBatchProcessing;
          if (!((this.mainActivity.currentMusicPlatform == MusicPlatform.MIGU) & (!z2) & (!z3)
              & (!CommonUtils.isServiceRunning(getApplicationContext(),
              "com.mylrc.mymusic.service.DownloadService")))) {
          }
        }
        this.mainActivity.showMessage(str);
      } else {
        CommonUtils.showNotificationPermissionDialog(this.mainActivity);
      }
      return true;
    }
  }

  class switchyq1 implements OnClickListener {

    final RotateAnimation f2243a;

    final Dialog f2244b;

    final MainActivity f2245c;

    switchyq1(MainActivity music, RotateAnimation rotateAnimation, Dialog dialog) {
      this.f2245c = music;
      this.f2243a = rotateAnimation;
      this.f2244b = dialog;
    }

    @Override
    public void onClick(View view) {
      MainActivity music = this.f2245c;
      music.currentSearchEngine = NumberEnum.ONE;
      music.searchText = music.searchEditText.getText().toString();
      if (!this.f2245c.searchText.equals(FrameBodyCOMM.DEFAULT)) {
        this.f2245c.searchLoadingImageView.startAnimation(this.f2243a);
        this.f2245c.z0();
        this.f2245c.m0();
      }
      this.f2245c.showMessage("切换引擎一");
      this.f2244b.dismiss();
    }
  }

  public class i0 extends Handler {

    final MainActivity mainActivity;

    public i0(MainActivity mainActivity, Looper looper) {
      this.mainActivity = mainActivity;
    }

    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case 0:
          updateMusicLists();
          break;

        case 1:
          String toastText = msg.obj.toString();
          ToastUtils.showToast(mainActivity, toastText);
          break;

        case 2:
          mainActivity.database();
          break;

        case 7:
          showPopupMenu();
          break;

        case 9:
          showMVDialog();
          break;

        case 12:
          mainActivity.coverImageView.clearAnimation();
          break;

        case 14:
          Bitmap albumArt = (Bitmap) msg.obj;
          updateAlbumArt(albumArt);
          break;

        case 16:
          mainActivity.t0();
          break;

        case 17:
          if (mainActivity.loadingDialog != null && mainActivity.loadingDialog.isShowing()) {
            mainActivity.loadingDialog.dismiss();
          }
          break;

        case 19:
          mainActivity.coverImageView.startAnimation(mainActivity.rotateAnimation);
          mainActivity.coverImageView.setVisibility(View.VISIBLE);
          break;

        case 22:
          String errorMsg = mainActivity.dialogMessage;
          DialogHelper.showDialog(mainActivity, "提示", errorMsg, "确定", true);
          break;
      }
    }

    private void updateMusicLists() {
      if (mainActivity.leftMusicList == null) {
        mainActivity.leftMusicList = new ArrayList<>();
      }
      mainActivity.leftDisplayList.clear();
      mainActivity.leftDisplayList.addAll(mainActivity.leftMusicList);

      String[] fromKeys = {"name", "singer", "time", "br", "mv", "yz", "album"};
      int[] toViewIds = {R.id.fTextView1, R.id.fTextView2, R.id.fTextView3, R.id.fImageView1,
          R.id.fImageView2, R.id.fImageView3, R.id.fTextView4};

      mainActivity.leftListAdapter = new d(
          this, mainActivity, mainActivity.leftDisplayList,
          R.layout.history_item, fromKeys, toViewIds
      );
      mainActivity.leftListView.setAdapter(mainActivity.leftListAdapter);

      if (mainActivity.rightMusicList == null) {
        mainActivity.rightMusicList = new ArrayList<>();
      }
      mainActivity.rightDisplayList.clear();
      mainActivity.rightDisplayList.addAll(mainActivity.rightMusicList);

      mainActivity.rightListAdapter = new e(
          this, mainActivity, mainActivity.rightMusicList,
          R.layout.history_item, fromKeys, toViewIds
      );
      mainActivity.rightListView.setAdapter(mainActivity.rightListAdapter);

      if (mainActivity.suggestionPopup != null && mainActivity.suggestionPopup.isShowing()) {
        mainActivity.suggestionPopup.dismiss();
      }

      mainActivity.leftSelectionMap = new HashMap<>();
      mainActivity.rightSelectionMap = new HashMap<>();
    }

    private void showPopupMenu() {
      mainActivity.suggestionContentView = LayoutInflater.from(mainActivity)
          .inflate(R.layout.popupitem, null);
      mainActivity.historyListView = mainActivity.suggestionContentView.findViewById(
          R.id.popupitemListView1);
      mainActivity.suggestionAdapter = new SimpleAdapter(
          mainActivity, mainActivity.searchSuggestions, R.layout.zdbz,
          new String[]{"key"}, new int[]{R.id.zdbzTextView1}
      );
      mainActivity.historyListView.setAdapter(mainActivity.suggestionAdapter);
      mainActivity.historyListView.setOnItemClickListener(new f(this));
      this.mainActivity.showPopupMenu();
    }

    private void showMVDialog() {
      String filename = "";
      switch (mainActivity.currentMusicPlatform) {
        case QQ:
        case KUGOU:
          if (mainActivity.leftMusicList != null
              && mainActivity.leftMusicList.size() > mainActivity.leftScrollPosition) {
            filename = mainActivity.leftMusicList.get(mainActivity.leftScrollPosition)
                .get("filename").toString();
          }
          break;
        case WYY:
          if (mainActivity.rightMusicList != null
              && mainActivity.rightMusicList.size() > mainActivity.rightScrollPosition) {
            filename = mainActivity.rightMusicList.get(mainActivity.rightScrollPosition)
                .get("filename")
                .toString();
          }
          break;
      }
      mainActivity.currentDownloadFileName = filename;

      Dialog dialog = new DialogFactory().createDialog(mainActivity);

      View dialogView = LayoutInflater.from(mainActivity).inflate(R.layout.mvbr, null);
      setupMVDialogView(dialogView, dialog);

      dialog.show();
      dialog.setContentView(dialogView);

      WindowManager wm = mainActivity.getWindowManager();
      Display display = wm.getDefaultDisplay();
      Window window = dialog.getWindow();
      WindowManager.LayoutParams params = window.getAttributes();
      params.width = display.getWidth();
      window.setAttributes(params);
    }

    private void setupMVDialogView(View dialogView, Dialog dialog) {
      View mvQuality1 = dialogView.findViewById(R.id.mvbrLinearLayout2);
      View mvQuality2 = dialogView.findViewById(R.id.mvbrLinearLayout3);
      View mvQuality3 = dialogView.findViewById(R.id.mvbrLinearLayout4);
      Button btnQuality1 = dialogView.findViewById(R.id.mvbrButton1);
      Button btnQuality2 = dialogView.findViewById(R.id.mvbrButton2);
      Button btnQuality3 = dialogView.findViewById(R.id.mvbrButton3);
      Button btnDownload = dialogView.findViewById(R.id.mvbrButton4);
      ImageView albumArt = dialogView.findViewById(R.id.mvbri1);
      TextView songTitle = dialogView.findViewById(R.id.mvbrTextView1);
      TextView songInfo = dialogView.findViewById(R.id.mvbrTextView2);
      dialogView.findViewById(R.id.mvlayt).setOnClickListener(new g(this));

      String title = "";
      String info = "";
      if (mainActivity.currentMusicPlatform == MusicPlatform.QQ ||
          mainActivity.currentMusicPlatform == MusicPlatform.KUGOU) {
        if (mainActivity.leftMusicList != null
            && mainActivity.leftMusicList.size() > mainActivity.leftScrollPosition) {
          Map<String, Object> song = mainActivity.leftMusicList.get(
              mainActivity.leftScrollPosition);
          title = song.get("name").toString();
          info = song.get("singer") + " -《" + song.get("album") + "》";
        }
      } else if (mainActivity.currentMusicPlatform == MusicPlatform.WYY) {
        if (mainActivity.rightMusicList != null
            && mainActivity.rightMusicList.size() > mainActivity.rightScrollPosition) {
          Map<String, Object> song = mainActivity.rightMusicList.get(
              mainActivity.rightScrollPosition);
          title = song.get("name").toString();
          info = song.get("singer") + " -《" + song.get("album") + "》";
        }
      }

      songTitle.setText(title);
      songInfo.setText(info);

      if (mainActivity.coverBitmap != null) {
        albumArt.setImageBitmap(mainActivity.coverBitmap);
      } else {
        albumArt.setImageResource(R.drawable.ic_avatar);
      }

      int mvCount = mainActivity.mvUrlList != null ? mainActivity.mvUrlList.size() : 0;
      if (mvCount == 1) {
        mvQuality1.setVisibility(View.GONE);
        mvQuality2.setVisibility(View.GONE);
        mvQuality3.setVisibility(View.GONE);
      } else if (mvCount == 2) {
        mvQuality1.setVisibility(View.GONE);
        mvQuality2.setVisibility(View.GONE);
      } else if (mvCount == 3) {
        mvQuality1.setVisibility(View.GONE);
      }

      setupMVButtonListeners(btnQuality1, btnQuality2, btnQuality3, btnDownload, dialog);
    }

    private void setupMVButtonListeners(Button btn240P, Button btn480P, Button btn720P,
        Button btn1080P, Dialog dialog) {
      Intent intent = new Intent();
      MvDownloader vVar = new MvDownloader();
      btn240P.setOnClickListener(new h(this, intent, dialog));
      btn240P.setOnLongClickListener(new i(this, dialog, vVar));
      btn480P.setOnClickListener(new j(this, intent, dialog));
      btn480P.setOnLongClickListener(new k(this, dialog, vVar));
      btn720P.setOnClickListener(new l(this, intent, dialog));
      btn720P.setOnLongClickListener(new a(this, dialog, vVar));
      btn1080P.setOnClickListener(new b(this, dialog, intent));
      btn1080P.setOnLongClickListener(new c(this, dialog, vVar));
    }

    private void updateAlbumArt(Bitmap albumArt) {
      if (albumArt != null) {
        mainActivity.qualityCoverImageView.setImageBitmap(albumArt);
      } else {
        mainActivity.qualityCoverImageView.setImageResource(R.drawable.ic_avatar);
      }
    }


    public class a implements View.OnLongClickListener {

      final i0 this$1;
      final Dialog val$dialog;
      final MvDownloader val$downloadHelper;

      public a(i0 this$1, Dialog dialog, MvDownloader downloadHelper) {
        this.this$1 = this$1;
        this.val$dialog = dialog;
        this.val$downloadHelper = downloadHelper;
      }

      @Override
      public boolean onLongClick(View v) {
        val$dialog.dismiss();
        this$1.mainActivity.showMessage("已加入下载，详情见状态栏");

        String mvUrl = this$1.mainActivity.mvUrlList.get(2);
        String fileName = this$1.mainActivity.currentDownloadFileName + ".mp4";

        val$downloadHelper.downloadMv(
            this$1.mainActivity.getApplicationContext(),
            mvUrl,
            fileName
        );

        return true;
      }
    }

    public class b implements View.OnClickListener {

      final i0 this$1;
      final Dialog val$dialog;
      final Intent val$intent;

      public b(i0 this$1, Dialog dialog, Intent intent) {
        this.this$1 = this$1;
        this.val$dialog = dialog;
        this.val$intent = intent;
      }

      @Override
      public void onClick(View v) {
        val$dialog.dismiss();

        val$intent.putExtra("text", "当前播放的是蓝光品质（1080P）");
        val$intent.putExtra("url", this$1.mainActivity.mvUrlList.get(3));
        val$intent.setClass(this$1.mainActivity.getApplicationContext(), MVActivity.class);

        this$1.mainActivity.startActivity(val$intent);
      }
    }

    public class c implements View.OnLongClickListener {

      final i0 this$1;
      final Dialog val$dialog;
      final MvDownloader val$downloadHelper;

      public c(i0 this$1, Dialog dialog, MvDownloader downloadHelper) {
        this.this$1 = this$1;
        this.val$dialog = dialog;
        this.val$downloadHelper = downloadHelper;
      }

      @Override
      public boolean onLongClick(View v) {
        val$dialog.dismiss();
        this$1.mainActivity.showMessage("已加入下载，详情见状态栏");

        String mvUrl = this$1.mainActivity.mvUrlList.get(3);
        String fileName = this$1.mainActivity.currentDownloadFileName + ".mp4";

        val$downloadHelper.downloadMv(
            this$1.mainActivity.getApplicationContext(),
            mvUrl,
            fileName
        );

        return true;
      }
    }

    public class d extends SimpleAdapter {

      final i0 this$1;

      public d(i0 this$1, Context context, List<? extends Map<String, ?>> data,
          int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.this$1 = this$1;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        CheckBoxHolder holder;

        if (convertView == null) {
          convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.f, null);
          holder = new CheckBoxHolder();
          holder.checkBox = convertView.findViewById(R.id.fCheckBox1);
          convertView.setTag(holder);
        } else {
          holder = (CheckBoxHolder) convertView.getTag();
        }

        if (this$1.mainActivity.isLeftMultiSelectMode) {
          holder.checkBox.setChecked(this$1.mainActivity.leftSelectionMap.containsKey(position));
          holder.checkBox.setVisibility(View.VISIBLE);
        } else {
          holder.checkBox.setVisibility(View.GONE);
        }

        return super.getView(position, convertView, parent);
      }
    }

    public class e extends SimpleAdapter {

      final i0 this$1;

      public e(i0 this$1, Context context, List<? extends Map<String, ?>> data,
          int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.this$1 = this$1;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        CheckBoxHolder holder;

        if (convertView == null) {
          convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.f, null);
          holder = new CheckBoxHolder();
          holder.checkBox = convertView.findViewById(R.id.fCheckBox1);
          convertView.setTag(holder);
        } else {
          holder = (CheckBoxHolder) convertView.getTag();
        }

        if (this$1.mainActivity.isRightMultiSelectMode) {
          holder.checkBox.setChecked(this$1.mainActivity.rightSelectionMap.containsKey(position));
          holder.checkBox.setVisibility(View.VISIBLE);
        } else {
          holder.checkBox.setVisibility(View.GONE);
        }

        return super.getView(position, convertView, parent);
      }
    }

    public class f implements AdapterView.OnItemClickListener {

      final i0 this$1;

      public f(i0 this$1) {
        this.this$1 = this$1;
      }

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!this$1.mainActivity.isLeftMultiSelectMode
            || !this$1.mainActivity.isRightMultiSelectMode) {
          if (this$1.mainActivity.searchSuggestions != null
              && this$1.mainActivity.searchSuggestions.size() > position + 1) {
            this$1.mainActivity.isSearchEnabled = false;

            String keyword = this$1.mainActivity.searchSuggestions.get(position).get("key")
                .toString();
            this$1.mainActivity.searchEditText.setText(keyword);
            this$1.mainActivity.searchText = keyword;

            new SearchThread().start();
          }
        } else {
          this$1.mainActivity.showMessage("请先再次长按列表取消多选状态再试");
        }
      }

      private class SearchThread extends Thread {

        @Override
        public void run() {
          this$1.mainActivity.l0(mainActivity.searchText);
          this$1.mainActivity.z0();
        }
      }
    }

    public class g implements View.OnClickListener {

      final i0 this$1;

      public g(i0 this$1) {
        this.this$1 = this$1;
      }

      @Override
      public void onClick(View v) {
        this$1.mainActivity.dialogMessage = "点击相应品质的按钮可直接观看MV，长按对应品质的按钮可下载视频文件，下载的视频文件存储在：内部存储/MusicDownloader/Mv目录下。";

        DialogHelper.showDialog(
            this$1.mainActivity,
            "提示",
            this$1.mainActivity.dialogMessage,
            "确定",
            true
        );
      }
    }

    class h implements OnClickListener {

      final Intent f2261a;

      final Dialog f2262b;

      final i0 f2263c;

      h(i0 i0Var, Intent intent, Dialog dialog) {
        this.f2263c = i0Var;
        this.f2261a = intent;
        this.f2262b = dialog;
      }

      @Override
      public void onClick(View view) {
        this.f2261a.putExtra("text", "当前播放的是标清品质（240P）");
        this.f2261a.putExtra("url", this.f2263c.mainActivity.mvUrlList.get(0));
        this.f2261a.setClass(this.f2263c.mainActivity.getApplicationContext(), MVActivity.class);
        this.f2263c.mainActivity.startActivity(this.f2261a);
        this.f2262b.dismiss();
      }
    }

    class i implements OnLongClickListener {

      final Dialog f2264a;

      final MvDownloader f2265b;

      final i0 f2266c;

      i(i0 i0Var, Dialog dialog, MvDownloader vVar) {
        this.f2266c = i0Var;
        this.f2264a = dialog;
        this.f2265b = vVar;
      }

      @Override
      public boolean onLongClick(View view) {
        this.f2264a.dismiss();
        this.f2266c.mainActivity.showMessage("已加入 下载，详情见状态栏");
        this.f2265b.downloadMv(this.f2266c.mainActivity.getApplicationContext(),
            this.f2266c.mainActivity.mvUrlList.get(0), this.f2266c.mainActivity.mvUrl + ".mp4");
        return true;
      }
    }

    class j implements OnClickListener {

      final Intent f2267a;

      final Dialog f2268b;

      final i0 f2269c;

      j(i0 i0Var, Intent intent, Dialog dialog) {
        this.f2269c = i0Var;
        this.f2267a = intent;
        this.f2268b = dialog;
      }

      @Override
      public void onClick(View view) {
        this.f2267a.putExtra("text", "当前播放的是高清品质（480P）");
        this.f2267a.putExtra("url", this.f2269c.mainActivity.mvUrlList.get(1));
        this.f2267a.setClass(this.f2269c.mainActivity.getApplicationContext(), MVActivity.class);
        this.f2269c.mainActivity.startActivity(this.f2267a);
        this.f2268b.dismiss();
      }
    }

    class k implements OnLongClickListener {

      final Dialog f2270a;

      final MvDownloader f2271b;

      final i0 f2272c;

      k(i0 i0Var, Dialog dialog, MvDownloader vVar) {
        this.f2272c = i0Var;
        this.f2270a = dialog;
        this.f2271b = vVar;
      }

      @Override
      public boolean onLongClick(View view) {
        this.f2270a.dismiss();
        this.f2272c.mainActivity.showMessage("已加入 下载，详情见状态栏");
        this.f2271b.downloadMv(this.f2272c.mainActivity.getApplicationContext(),
            this.f2272c.mainActivity.mvUrlList.get(1), this.f2272c.mainActivity.mvUrl + ".mp4");
        return true;
      }
    }

    class l implements OnClickListener {

      final Intent f2273a;

      final Dialog f2274b;

      final i0 f2275c;

      l(i0 i0Var, Intent intent, Dialog dialog) {
        this.f2275c = i0Var;
        this.f2273a = intent;
        this.f2274b = dialog;
      }

      @Override
      public void onClick(View view) {
        this.f2273a.putExtra("text", "当前播放的是超清品质（720P）");
        this.f2273a.putExtra("url", this.f2275c.mainActivity.mvUrlList.get(2));
        this.f2273a.setClass(this.f2275c.mainActivity.getApplicationContext(), MVActivity.class);
        this.f2275c.mainActivity.startActivity(this.f2273a);
        this.f2274b.dismiss();
      }
    }
  }

  class switchyq2 implements OnClickListener {

    final RotateAnimation f2276a;

    final Dialog f2277b;

    final MainActivity f2278c;

    switchyq2(MainActivity music, RotateAnimation rotateAnimation, Dialog dialog) {
      this.f2278c = music;
      this.f2276a = rotateAnimation;
      this.f2277b = dialog;
    }

    @Override
    public void onClick(View view) {
      MainActivity music = this.f2278c;
      music.currentSearchEngine = NumberEnum.TWO;
      music.searchText = music.searchEditText.getText().toString();
      if (!this.f2278c.searchText.equals(FrameBodyCOMM.DEFAULT)) {
        this.f2278c.searchLoadingImageView.startAnimation(this.f2276a);
        this.f2278c.z0();
        this.f2278c.m0();
      }
      this.f2278c.showMessage("切换引擎二");
      this.f2277b.dismiss();
    }
  }

  class j0 implements Runnable {

    final MainActivity f2279a;

    j0(MainActivity music) {
      this.f2279a = music;
    }

    @Override
    public void run() {
      boolean zIsShowing = this.f2279a.downloadProgressDialog.isShowing();
      MainActivity music = this.f2279a;
      if (((!zIsShowing) & (!music.isDownloadCancelled)) && (!music.isFinishing())) {
        this.f2279a.downloadProgressDialog.show();
      }
    }
  }

  class k extends y0 {

    final MainActivity f2280b;

    k(MainActivity music) {
      super(music);
      this.f2280b = music;
    }

    @Override
    public void onClick(View view) {
      this.f2280b.q0();
    }
  }

  class k0 implements Runnable {

    final MainActivity f2281a;

    k0(MainActivity music) {
      this.f2281a = music;
    }

    @Override
    public void run() {
      this.f2281a.downloadDialogMsg.setText("正在下载：" + this.f2281a.currentDownloadFileName);
      TextView textView = this.f2281a.downloadProgressView;
      String sb = "进度：" +
          CommonUtils.formatFileSize(this.f2281a.rightScrollPosition + FrameBodyCOMM.DEFAULT) +
          "/" +
          CommonUtils.formatFileSize(this.f2281a.leftScrollPosition + FrameBodyCOMM.DEFAULT);
      textView.setText(sb);
      MainActivity music = this.f2281a;
      music.downloadProgressBar.setProgress(music.rightScrollPosition);
    }
  }

  class switchyq3 implements OnClickListener {

    final RotateAnimation rotateAnimation;

    final Dialog dialog;

    final MainActivity mainActivity;

    switchyq3(MainActivity music, RotateAnimation rotateAnimation, Dialog dialog) {
      this.mainActivity = music;
      this.rotateAnimation = rotateAnimation;
      this.dialog = dialog;
    }

    @Override
    public void onClick(View view) {
      MainActivity music = this.mainActivity;
      music.currentSearchEngine = NumberEnum.THREE;
      music.searchText = music.searchEditText.getText().toString();
      if (!this.mainActivity.searchText.equals(FrameBodyCOMM.DEFAULT)) {
        this.mainActivity.searchLoadingImageView.startAnimation(this.rotateAnimation);
        this.mainActivity.z0();
        this.mainActivity.m0();
      }
      this.mainActivity.showMessage("切换引擎三");
      this.dialog.dismiss();
    }
  }

  class switchcloud implements OnClickListener {

    final RotateAnimation rotateAnimation;

    final Dialog dialog;

    final MainActivity mainActivity;

    switchcloud(MainActivity music, RotateAnimation rotateAnimation, Dialog dialog) {
      this.mainActivity = music;
      this.rotateAnimation = rotateAnimation;
      this.dialog = dialog;
    }

    @Override
    public void onClick(View view) {
      MainActivity mainActivity = this.mainActivity;
      startActivity(new Intent(mainActivity, CloudDiskActivity.class));
      this.dialog.dismiss();
    }
  }

  class l0 implements OnClickListener {

    final MainActivity f2285a;

    l0(MainActivity music) {
      this.f2285a = music;
    }

    @Override
    public void onClick(View view) {
      MainActivity music = this.f2285a;
      music.isDownloading = false;
      music.downloadProgressDialog.dismiss();
    }
  }

  class m0 implements OnClickListener {

    final MainActivity f2286a;

    m0(MainActivity music) {
      this.f2286a = music;
    }

    @Override
    public void onClick(View view) {
      MainActivity music = this.f2286a;
      music.isDownloadCancelled = true;
      music.downloadProgressDialog.dismiss();
    }
  }

  class n implements OnClickListener {

    final EditText f2287a;

    final Dialog f2288b;

    final MainActivity f2289c;

    n(MainActivity music, EditText editText, Dialog dialog) {
      this.f2289c = music;
      this.f2287a = editText;
      this.f2288b = dialog;
    }

    @Override
    public void onClick(View view) {
      if (!this.f2287a.getText().toString().equals(FrameBodyCOMM.DEFAULT)) {
        this.f2289c.appPreferences.edit().putString("qqimg", this.f2287a.getText().toString())
            .apply();
        Toast.makeText(this.f2289c.getApplicationContext(), "保存成功！", Toast.LENGTH_LONG)
            .show();
        this.f2289c.p0();
      }
      this.f2288b.dismiss();
    }
  }

  class n0 implements OnClickListener {

    final Dialog f2290a;

    final MainActivity f2291b;

    n0(MainActivity music, Dialog dialog) {
      this.f2291b = music;
      this.f2290a = dialog;
    }

    @Override
    public void onClick(View view) {
      this.f2291b.showMessage("已清空");
      this.f2290a.dismiss();
      new HistoryManager(this.f2291b).clearHistory();
    }
  }

  class o implements OnClickListener {

    final Dialog f2292a;

    final MainActivity f2293b;

    o(MainActivity music, Dialog dialog) {
      this.f2293b = music;
      this.f2292a = dialog;
    }

    @Override
    public void onClick(View view) {
      this.f2292a.dismiss();
    }
  }

  class s implements OnClickListener {

    final Dialog f2309a;

    final MainActivity f2310b;

    s(MainActivity music, Dialog dialog) {
      this.f2310b = music;
      this.f2309a = dialog;
    }

    @Override
    public void onClick(View view) {
      this.f2310b.leftScrollPosition("sq");
      this.f2309a.dismiss();
    }
  }

  class s0 implements OnLongClickListener {

    final MainActivity f2311a;

    s0(MainActivity music) {
      this.f2311a = music;
    }

    @Override
    public boolean onLongClick(View view) {
      this.f2311a.leftListView();
      this.f2311a.showMessage("已注销");
      return true;
    }
  }

  class t implements OnClickListener {

    final Dialog f2312a;

    final MainActivity f2313b;

    t(MainActivity music, Dialog dialog) {
      this.f2313b = music;
      this.f2312a = dialog;
    }

    @Override
    public void onClick(View view) {
      this.f2313b.leftScrollPosition("hr");
      this.f2312a.dismiss();
    }
  }


  class t0 extends Thread {

    final MainActivity mainActivity;

    t0(MainActivity mainActivity) {
      this.mainActivity = mainActivity;
    }

    @Override
    public void run() {
      String emptyStr = "";
      try {
        String noticeHTML = DownloadUtils.getYoudaoNote("ce57d80729e9d857dfbfcbc72428883d");

        this.mainActivity.noticeContent = noticeHTML.substring(
            noticeHTML.indexOf("【") + 1,
            noticeHTML.indexOf("】")
        );

        this.mainActivity.noticeMessage1 = noticeHTML.substring(
            noticeHTML.indexOf("〖") + 1,
            noticeHTML.indexOf("〗")
        );
        this.mainActivity.noticeMessage1 = this.mainActivity.noticeMessage1.replace("amp;",
            emptyStr);

        this.mainActivity.noticeButtonText = noticeHTML.substring(
            noticeHTML.indexOf("｛") + 1,
            noticeHTML.indexOf("｝")
        );

        this.mainActivity.noticeCountdownSeconds = Integer.parseInt(noticeHTML.substring(
            noticeHTML.indexOf("『") + 1,
            noticeHTML.indexOf("』")
        ));

        String savedGk = this.mainActivity.appPreferences.getString("gk", emptyStr);

        this.mainActivity.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            t0.this.mainActivity.noticeTextView.setText(t0.this.mainActivity.noticeContent);
            t0.this.mainActivity.noticeTextView.setTextSize(11.0f);
          }
        });

        if (!savedGk.equals(this.mainActivity.noticeContent)) {
          this.mainActivity.mainHandler.post(new Runnable() {
            @Override
            public void run() {
              t0.this.mainActivity.showNoticeDialog();
            }
          });
        }
      } catch (Exception e) {
      }
    }
  }

  class u extends Thread {

    final String quality;
    final MainActivity mainActivity;
    Map<String, String> f2317a;

    u(MainActivity music, String str) {
      this.mainActivity = music;
      this.quality = str;
    }

    @Override
    public void run() {
      String str;
      List<Map<String, String>> list = Collections.emptyList();
      Map<String, String> map = Collections.emptyMap();
      int iIndexOf;
      int iIndexOf2;
      String strSubstring;
      String str2 = "";
      MainActivity music;
      StringBuilder sb;
      String str3;
      if (this.mainActivity.selectedSongs.size() != 0) {
        this.mainActivity.isBatchProcessing = true;
        if (this.quality.equals("sq")) {
          music = this.mainActivity;
          sb = new StringBuilder();
          sb.append("已选");
          sb.append(this.mainActivity.selectedSongs.size());
          str3 = "首已加入下载，详情见状态栏，品质为：无损";
        } else if (this.quality.equals("hq")) {
          music = this.mainActivity;
          sb = new StringBuilder();
          sb.append("已选");
          sb.append(this.mainActivity.selectedSongs.size());
          str3 = "首已加入下载，详情见状态栏，品质为：高品";
        } else if (this.quality.equals("hr")) {
          music = this.mainActivity;
          sb = new StringBuilder();
          sb.append("已选");
          sb.append(this.mainActivity.selectedSongs.size());
          str3 = "首已加入下载，详情见状态栏，品质为：HR";
        } else {
          music = this.mainActivity;
          sb = new StringBuilder();
          sb.append("已选");
          sb.append(this.mainActivity.selectedSongs.size());
          str3 = "首已加入下载，详情见状态栏，品质为：标准";
        }
        sb.append(str3);
        music.showMessage(sb.toString());
      }
      int i2 = 0;
      while (true) {
        int i3 = i2;
        if (i3 >= this.mainActivity.selectedSongs.size()) {
          break;
        }
        String str4 = this.mainActivity.selectedSongs.get(i3);
        String[] strArrSplit = str4.split("∮∮");
        String str5 = strArrSplit[0];
        String str6 = strArrSplit[1];
        String str7 = strArrSplit[2];
        String str8 = strArrSplit[3];
        String str9 = strArrSplit[4];
        String str10 = strArrSplit[5];
        String str11 = ".flac";
        if (str7.equals("dsd") || str7.equals("hr")) {
          if (!this.quality.equals("mp3")) {
            if (!this.quality.equals("hq")) {
              if (!this.quality.equals("sq")) {
                str11 = ".flac";
                str = "hr";
              }
              str7 = "sq";
              str = str7;
            }
            str11 = ".mp3";
            str = str2;
          }
        } else {
          if (str7.trim().equals("sq")) {
            if (!this.quality.equals("mp3")) {
              if (!this.quality.equals("hq")) {
                if (this.quality.equals("sq") || this.quality.equals("hr")) {
                  str7 = "sq";
                } else {
                  str11 = null;
                }
                str = str7;
              }
            }
          } else {
            str2 = (str7.equals("hq") && (this.quality.equals("hr") || this.quality.equals("sq")
                || this.quality.equals("hq"))) ? "hq" : "mp3";
          }
        }
        str11 = ".mp3";
        str = str2;
        try {
          this.f2317a = new HashMap();
          if (this.mainActivity.currentSource.equals("kugou")) {
            try {
              String strSubstring2 = str4.substring(0, str4.indexOf("∮∮"));
              if (str.equals("mp3")) {
                strSubstring = strSubstring2.substring(0, strSubstring2.indexOf("低高"));
              } else {
                if (str.equals("hq")) {
                  iIndexOf = strSubstring2.indexOf("低高") + 2;
                  iIndexOf2 = strSubstring2.indexOf("高无");
                } else if (str.equals("sq")) {
                  iIndexOf = strSubstring2.indexOf("高无") + 2;
                  iIndexOf2 = strSubstring2.indexOf("无h");
                } else {
                  iIndexOf = strSubstring2.indexOf("无h") + 2;
                  iIndexOf2 = strSubstring2.indexOf("高真");
                }
                strSubstring = strSubstring2.substring(iIndexOf, iIndexOf2);
              }
              this.f2317a.put("type", "kugou");
              this.f2317a.put("path", this.mainActivity.downloadDirectory + "/");
              this.f2317a.put("end", str11);
              this.f2317a.put("id", strSubstring);
              this.f2317a.put("title", str8);
              this.f2317a.put("singer", str9);
              this.f2317a.put("br", str);
              this.f2317a.put("album", str10);
              this.f2317a.put("file", str6);
              MainActivity.globalMusicList.add(this.f2317a);
            } catch (Exception e2) {
              this.mainActivity.showMessage("错误：" + e2);
              i2 = i3 + 1;
            }
          } else {
            try {
              switch (this.mainActivity.currentSource) {
                case "wyy":
                  this.f2317a.put("type", "wyy");
                  this.f2317a.put("path", this.mainActivity.downloadDirectory + "/");
                  this.f2317a.put("end", str11);
                  this.f2317a.put("id", str5);
                  this.f2317a.put("title", str8);
                  this.f2317a.put("singer", str9);
                  this.f2317a.put("br", str);
                  this.f2317a.put("album", str10);
                  this.f2317a.put("file", str6);
                  list = MainActivity.globalMusicList;
                  map = this.f2317a;
                  break;
                case "kuwo":
                  this.f2317a.put("type", "kuwo");
                  this.f2317a.put("path", this.mainActivity.downloadDirectory + "/");
                  this.f2317a.put("end", str11);
                  this.f2317a.put("id", str5);
                  this.f2317a.put("title", str8);
                  this.f2317a.put("singer", str9);
                  this.f2317a.put("br", str);
                  this.f2317a.put("album", str10);
                  this.f2317a.put("file", str6);
                  list = MainActivity.globalMusicList;
                  map = this.f2317a;
                  break;
                case "qq":
                  this.f2317a.put("type", "qq");
                  this.f2317a.put("path", this.mainActivity.downloadDirectory + "/");
                  this.f2317a.put("end", str11);
                  this.f2317a.put("id", str5);
                  this.f2317a.put("title", str8);
                  this.f2317a.put("singer", str9);
                  this.f2317a.put("br", str);
                  this.f2317a.put("album", str10);
                  this.f2317a.put("file", str6);
                  list = MainActivity.globalMusicList;
                  map = this.f2317a;
                  break;
              }
              list.add(map);
            } catch (Exception e3) {
              this.mainActivity.showMessage("错误：" + e3);
              i2 = i3 + 1;
            }
          }
        } catch (Exception e4) {

        }
        i2 = i3 + 1;
      }
      this.mainActivity.isBatchProcessing = false;
      this.mainActivity.showMessage("正在启动下载器...");
      this.mainActivity.selectedSongs.clear();
      Intent intent = new Intent();
      intent.setClass(this.mainActivity.getApplicationContext(), DownloadService.class);
      intent.putExtra("where", "0");
      if (VERSION.SDK_INT >= 26) {
        this.mainActivity.startForegroundService(intent);
      } else {
        this.mainActivity.startService(intent);
      }
    }
  }

  class u0 extends Thread {

    final Button f2320a;

    final MainActivity f2321b;

    u0(MainActivity music, Button button) {
      this.f2321b = music;
      this.f2320a = button;
    }

    @Override
    public void run() {
      for (int i2 = this.f2321b.noticeCountdownSeconds; i2 > 0; i2--) {
        try {
          Thread.sleep(1000L);
          MainActivity music = this.f2321b;
          music.noticeCountdownSeconds = i2;
          music.mainHandler.post(new a(this));
        } catch (InterruptedException e2) {
        }
      }
      this.f2321b.isNoticeRead = true;
    }

    class a implements Runnable {

      final u0 f2322a;

      a(u0 u0Var) {
        this.f2322a = u0Var;
      }

      @Override
      public void run() {
        this.f2322a.f2320a.setText(
            this.f2322a.f2321b.noticeButtonText + "(" + this.f2322a.f2321b.noticeCountdownSeconds
                + ")");
        u0 u0Var = this.f2322a;
        MainActivity music = u0Var.f2321b;
        if (music.noticeCountdownSeconds <= 1) {
          u0Var.f2320a.setText(music.noticeButtonText);
        }
      }
    }
  }

  class v implements NavigationView.OnNavigationItemSelectedListener {

    final MainActivity mainActivity;

    v(MainActivity music) {
      this.mainActivity = music;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      int itemId = item.getItemId();
      Intent intent = null;

      if (itemId == R.id.item_down) {
        intent = new Intent(this.mainActivity, DownloadHistoryActivity.class);
        this.mainActivity.startActivity(intent);
      } else if (itemId == R.id.item_set) {
        intent = new Intent(this.mainActivity, AppSettingActivity.class);
        this.mainActivity.startActivity(intent);
      } else if (itemId == R.id.item_about) {
        intent = new Intent(this.mainActivity, AboutActivity.class);
        this.mainActivity.startActivity(intent);
      } else if (itemId == R.id.item_tips) {
        String message = "非原版歌词适配\n\n## 协议\n### 开头语\n本项目基于 MIT 协议发行，以下内容为对该协议的补充，如有冲突，以下方内容为准。\n\n----\n\n### 词语约定\n 1.  本协议中的'本项目'指 音乐下载器 项目\n 2.  '使用者'指签署本协议的项目使用者\n 3.  '版权内容'为本项目中可能引用到的所有包括**但不限于** 姓名、图片、音频 等他人拥有版权的所有数据\n\n----\n\n### 协议正文\n#### 一、数据来源\n 1.1 本项目从QQ音乐、网易云音乐等 APP 的公开服务器中拉取处理内容后返回，故不对此类数据的准确性、安全性、合法性等负责。\n 1.2 本项目使用中可能会产生本地内容，本项目不对此类数据的准确性、安全性、合法性等负责\n\n#### 二、版权数据\n 2.1 本项目在使用中可能会产生版权数据。对于这些版权数据，本项目不拥有其所有权。为了避免侵权，使用者务必在**24小时内**清除使用本项目的过程中所产生的版权数据。\n\n#### 三、本地资源\n 3.1 本项目中的部分本地内容（包括但不限于 图片、文字 等）来自互联网搜集。如出现侵权请联系删除。\n\n#### 四、免责声明\n 4.1 由于使用本项目产生的包括由于本协议或由于使用或无法使用本项目而引起的任何性质的任何直接、间接、特殊、偶然或结果性损害（包括但不限于因商誉损失、停工、计算机故障或故障引起的损害赔偿，或任何及所有其他商业损害或损失）由使用者负责。\n\n#### 五、使用限制\n 5.1 **禁止在违反当地法律法规的情况下使用本项目。** 对于使用者在明知或不知当地法律法规不允许的情况下使用本项目所造成的任何违法违规行为由使用者承担，本项目不承担由此造成的任何直接、间接、特殊、偶然或结果性责任。\n\n#### 六、版权保护\n 6.1 平台不易，建议支持正版。\n\n#### 七、非商业性质\n 7.1 本项目开发旨在对于技术可行性的探究，不接受任何商业性行为，使用者也不得使用本项目进行任何商业性行为。\n\n#### 八、协议生效\n 8.1 如您使用本项目，即代表您接受本协议。\n 8.2 因违反协议而造成的任何损失，本项目开发者不承担任何包括但不限于道德、法律责任。\n 8.3 本协议可能会发生变更，恕不另行通知，可自行前往查看。协议变更后，如您继续使用本项目，**即默认您接受变更后的新协议内容**如有疑问请联系: \n  ikun@ikunshare.com";
        Dialog dialog = new DialogFactory().createDialog(this.mainActivity);
        View helpView = LayoutInflater.from(this.mainActivity)
            .inflate(R.layout.dialog, null);

        TextView tvTitle = helpView.findViewById(R.id.dialogTextView2);
        TextView tvContent = helpView.findViewById(R.id.dialogTextView1);

        tvTitle.setText("说明");
        tvContent.setText(message);

        dialog.show();
        dialog.setContentView(helpView);

        Display display = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = display.getWidth();
        dialog.getWindow().setAttributes(params);
      } else if (itemId == R.id.item_pay) {
        intent = new Intent(this.mainActivity, BrowserActivity.class);
        intent.putExtra("url", "https://shop.ikunshare.com/");
        this.mainActivity.startActivity(intent);
      } else if (itemId == R.id.item_qqplaylist) {
        if (this.mainActivity.appPreferences.getString("qquin", FrameBodyCOMM.DEFAULT)
            .equals(FrameBodyCOMM.DEFAULT)) {
          intent = new Intent(this.mainActivity, BrowserActivity.class);
          intent.putExtra("url",
              "https://graph.qq.com/oauth2.0/show?which=Login&display=pc&response_type=code&client_id=100497308&redirect_uri=https%3A%2F%2Fy.qq.com%2Fportal%2Fwx_redirect.html%3Flogin_type%3D1%26surl%3Dhttps%3A%2F%2Fy.qq.com%2Fn%2Fryqq%2Ftoplist%2F62&state=state&display=pc&scope=get_user_info%2Cget_app_friends");
        } else {
          intent = new Intent(this.mainActivity, QQPlayListActivity.class);
        }
        this.mainActivity.startActivity(intent);
      } else if (itemId == R.id.item_wyy_playlist) {
        if (this.mainActivity.appPreferences.getString("wyyuid", FrameBodyCOMM.DEFAULT)
            .equals(FrameBodyCOMM.DEFAULT)) {
          intent = new Intent(this.mainActivity.getApplicationContext(), BrowserActivity.class);
          intent.putExtra("url", "https://music.163.com/m/login");
        } else {
          intent = new Intent(this.mainActivity.getApplicationContext(),
              WYYPlayListActivity.class);
        }
        this.mainActivity.startActivity(intent);
      }

      return true;
    }
  }

  class w0 extends BroadcastReceiver {

    final MainActivity f2328a;

    w0(MainActivity music) {
      this.f2328a = music;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
      if (intent.getAction().equals("com.music.exit")) {
        this.f2328a.coverImageView.clearAnimation();
        this.f2328a.playerControlLayout.setVisibility(
            View.INVISIBLE);
      } else if (intent.getAction().equals("com.music.upview2")) {
        this.f2328a.playerControlLayout.setVisibility(View.VISIBLE);
        this.f2328a.rightScrollPosition();
      }
    }
  }

}