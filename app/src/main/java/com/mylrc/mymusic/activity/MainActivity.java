package com.mylrc.mymusic.activity;

import android.annotation.SuppressLint;
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
import android.os.Build.VERSION_CODES;
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
import com.mylrc.mymusic.service.DownloadService;
import com.mylrc.mymusic.service.PlayerService;
import com.mylrc.mymusic.tool.MusicUrlHelper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.mp4.atom.Mp4DataBox;
import org.jaudiotagger.tag.mp4.atom.Mp4NameBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.AppUpdateManager;
import utils.CheckBoxHolder;
import utils.CommonUtils;
import utils.ContextHolder;
import utils.DialogFactory;
import utils.DialogHelper;
import utils.DownloadUtils;
import utils.FileUtils;
import utils.GlobalData;
import utils.HistoryManager;
import utils.HttpRequestUtils;
import utils.ImageDownloadUtils;
import utils.LyricDownloadUtils;
import utils.MusicPlatform;
import utils.MusicSearchUtils;
import utils.MvDownloader;
import utils.MvUrlParser;
import utils.NumberEnum;
import utils.OkHttpClient;
import utils.SongDatabaseHelper;
import utils.StatusBarColor;
import utils.StatusBarManager;
import utils.ToastUtils;

public class MainActivity extends Activity {

  public static ArrayList globalMusicList;
  public static MediaPlayer mediaPlayer;
  static String sdCardPath;
  private static long aLong;
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

  public void searchText(String str, String str2, String str3, String str4, String str5,
      String str6)
      throws IOException {
    String str7;
    String str8;
    int i2;
    if (!str.startsWith("http")) {
      this.dialogMessage = str;
      o0(22);
      return;
    }
    this.isDownloading = true;
    String strReplace = str2.replace(":", "：").replace("?", "？").replace("|", "｜")
        .replace("*", "＊")
        .replace("\\", "＼").replace("/", "／").replace("\"", "＂").replace("<", "〈")
        .replace(">", "〉");
    if (strReplace.getBytes().length <= 249) {
      str7 = strReplace;
    } else if (str4.getBytes().length < 249) {
      i0("文件名过长，为保证体验，仅以歌曲名命名");
      str7 = str4;
    } else {
      i0("文件名过长，为保证体验，仅以艺术家命名");
      str7 = str5;
    }
    this.currentDownloadFileName = str7 + str3;
    String str9 = this.downloadDirectory + "/" + str7 + str3;
    String str10 = this.downloadDirectory + "/" + str7 + ".lrc";
    String str11 = sdCardPath + "/MusicDownloader/bin";
    try {
      Response e0VarH = OkHttpClient.getInstance()
          .newCall(new Builder().url(str).build()).execute();
      if (e0VarH.code() != 200) {
        this.dialogMessage =
            "文件下载链接无效，请重试或更换音质下载\n\n发生错误的歌曲：" + str5 + " - " + str4;
        new File(str10).delete();
        this.isDownloading = false;
        this.isDownloadCancelled = false;
        o0(22);
        return;
      }
      InputStream inputStreamW = e0VarH.body().byteStream();
      this.leftScrollPosition = (int) e0VarH.body().contentLength();
      t0();
      this.downloadProgressBar.setMax(this.leftScrollPosition);
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
          new FileOutputStream(str9));
      byte[] bArr = new byte[8192];
      while (this.isDownloading && this.rightScrollPosition != this.leftScrollPosition
          && -1 != (i2 = inputStreamW.read(bArr))) {
        this.rightScrollPosition += i2;
        try {
          bufferedOutputStream.write(bArr, 0, i2);
          r0();
        } catch (Exception e2) {
          i0("下载过程出现错误！" + e2);
          new File(str9).delete();
          new File(str10).delete();
          this.isDownloading = false;
          this.isDownloadCancelled = false;
          this.rightScrollPosition = 0;
          this.leftScrollPosition = 0;
          o0(17);
          return;
        }
      }
      o0(17);
      if (this.isDownloading) {
        bufferedOutputStream.close();
        inputStreamW.close();
        if (this.appPreferences.getInt("textmode", 0) == 0 && this.isDownloading) {
          if (this.appPreferences.getInt("lrcmode", 0) == 0) {
            str8 = str10;
          } else if (this.hasLyricFile) {
            str8 = getFilesDir().getParent() + "/app_tmpFile/downTmp.lrc";
          } else {
            str8 = null;
          }
          if (str.contains("kugou")) {
            if (ImageDownloadUtils.downloadKugouCover(this.coverImageId, str11)) {
              FileUtils.writeId3Tags(str9, str4, str5, str6, str11, str8);
            } else {
              FileUtils.writeId3Tags(str9, str4, str5, str6, null, str8);
            }
          } else if (str.contains("ymusic")) {
            if (ImageDownloadUtils.downloadNeteaseCloudCover(this.coverImageId, str11)) {
              FileUtils.writeId3Tags(str9, str4, str5, str6, str11, str8);
            }
            FileUtils.writeId3Tags(str9, str4, str5, str6, null, str8);
          } else if (str.contains("migu")) {
            if (ImageDownloadUtils.downloadImageToFile(this.coverImageId, str11)) {
              FileUtils.writeId3Tags(str9, str4, str5, str6, str11, str8);
            }
            FileUtils.writeId3Tags(str9, str4, str5, str6, null, str8);
          } else if (str.contains("kuwo")) {
            if (ImageDownloadUtils.downloadKuwoCover(this.coverImageId, str11)) {
              FileUtils.writeId3Tags(str9, str4, str5, str6, str11, str8);
            }
            FileUtils.writeId3Tags(str9, str4, str5, str6, null, str8);
          } else if (str.contains("vkey=")) {
            if (ImageDownloadUtils.downloadQQMusicCover(this.coverImageId, str11)) {

            }
          }
        }
        if (this.leftScrollPosition == this.rightScrollPosition) {
          i0(str7 + str3 + "：下载完成");
          Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
          intent.setData(Uri.fromFile(new File(str9)));
          sendBroadcast(intent);
          ContentValues contentValues = new ContentValues();
          contentValues.put(Mp4NameBox.IDENTIFIER, this.currentDownloadFileName);
          contentValues.put("path", str9);
          this.database.delete("song_list", "name = ?",
              new String[]{this.currentDownloadFileName});
          this.database.insert("song_list", null, contentValues);
        } else {
          i0(str7 + str3 + "：下载失败");
        }
      } else {
        new File(str9).delete();
        new File(str10).delete();
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        inputStreamW.close();
        i0(str7 + "：停止下载成功，已删除已下载的文件 ");
      }
      this.rightScrollPosition = 0;
      this.leftScrollPosition = 0;
      this.isDownloading = false;
      this.isDownloadCancelled = false;
    } catch (Exception e3) {
    }
  }

  public void leftListView() {
    this.appPreferences.edit().putString("qqimg", FrameBodyCOMM.DEFAULT).commit();
    this.menuImageView.setImageResource(R.drawable.ic_avatar);
    this.avatarImageView.setImageResource(R.mipmap.ic_launcher);
    this.tempBitmap = null;
    this.appPreferences.edit().putString("token", FrameBodyCOMM.DEFAULT).commit();
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

  public void i0(String str) {
    Message message = new Message();
    message.what = 1;
    message.obj = str;
    this.mainHandler.sendMessage(message);
  }

  public void j0() {
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
          i0("已全选，一共" + this.selectedSongs.size() + "首");
          simpleAdapter = this.leftListAdapter;
        } else {
          i0("已全选，一共" + this.selectedSongs.size() + "首");
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
      i0("异常，无法一键全选，错误信息：" + e2);
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
      i0("请先选择一些歌吧～");
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
    button.setOnClickListener(new v0(dialog));
    button2.setOnClickListener(new a(dialog));
  }

  public void y0() {
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

  public void a0() {
    Dialog dialogA = new DialogFactory().createDialog(this);
    dialogA.show();
    View viewInflate = LayoutInflater.from(this).inflate(R.layout.fgdialog, null);
    ((TextView) viewInflate.findViewById(R.id.fgdialogTextView1)).setText("赞赏方式");
    dialogA.setContentView(viewInflate);
    Display defaultDisplay = getWindowManager().getDefaultDisplay();
    LayoutParams attributes = dialogA.getWindow().getAttributes();
    attributes.width = defaultDisplay.getWidth();
    dialogA.getWindow().setAttributes(attributes);
    Button button = viewInflate.findViewById(R.id.fgdialogButton1);
    Button button2 = viewInflate.findViewById(R.id.fgdialogButton2);
    button.setText("微信");
    button2.setText("支付宝");
    button.setOnClickListener(new q0(this, dialogA));
    button2.setOnClickListener(new r0(this, dialogA));
  }

  @SuppressLint("UnspecifiedRegisterReceiverFlag")
  public void c0() {
    w0 w0Var = new w0(this);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("com.music.exit");
    intentFilter.addAction("com.music.upview2");
    if (VERSION.SDK_INT >= VERSION_CODES.O) {
      registerReceiver(w0Var, intentFilter, RECEIVER_NOT_EXPORTED);
    } else {
      registerReceiver(w0Var, intentFilter);
    }
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
      i0(e2.toString());
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
        i0("搜索已取消");
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
          i0("再按一次退出程序");
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
    try {
      WindowManager windowManager = getWindowManager();
      Display defaultDisplay = windowManager.getDefaultDisplay();
      DisplayMetrics displayMetrics = new DisplayMetrics();
      defaultDisplay.getMetrics(displayMetrics);
      int i2 = displayMetrics.widthPixels;

      Dialog dialogA = new DialogFactory().createDialog(this);

      View viewInflate = LayoutInflater.from(this).inflate(R.layout.tx, null);

      dialogA.setContentView(viewInflate);

      Window window = dialogA.getWindow();
      LayoutParams attributes = window.getAttributes();
      if (attributes != null) {
        attributes.width = defaultDisplay.getWidth();
        window.setAttributes(attributes);
      }

      dialogA.show();

      List<Map<String, Object>> listB = new HistoryManager(this).getHistory();
      if (listB == null || listB.isEmpty()) {
        return;
      }

      ViewGroup viewGroup = viewInflate.findViewById(R.id.txFlowLayout1);
      View closeView = viewInflate.findViewById(R.id.txRelativeLayout1);

      if (closeView != null) {
        closeView.setOnClickListener(new n0(this, dialogA));
      }

      o0 o0Var = new o0(this, dialogA);
      int i3 = 85;

      for (int i4 = 0; i4 < listB.size(); i4++) {
        try {
          Map<String, Object> item = listB.get(i4);
          if (item == null) {
            continue;
          }

          Object nameObj = item.get(Mp4DataBox.IDENTIFIER);
          if (nameObj == null) {
            continue;
          }

          String string = nameObj.toString();
          if (string == null || string.isEmpty()) {
            continue;
          }

          Button button = new Button(this);
          button.setTextSize(11.0f);
          TextPaint paint = button.getPaint();
          button.setStateListAnimator(null);
          button.setBackgroundResource(R.drawable.selector_btn_blue_rounded);
          button.setSingleLine(true);
          button.setText(string);
          button.setTextColor(-16777216);

          float fMeasureText = paint.measureText(string) + 70.0f;
          if (fMeasureText < 150.0f) {
            fMeasureText = 150.0f;
          }

          button.setPadding(35, 0, 35, 0);
          button.setGravity(17);

          if (i2 >= 1440) {
            i3 = 112;
          }

          viewGroup.addView(button, new LinearLayout.LayoutParams((int) fMeasureText, i3));
          button.setOnClickListener(o0Var);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      ToastUtils.showToast(this, "加载失败");
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
      } catch (NoSuchFieldError e2) {
      }
      try {
        f2300b[MusicPlatform.WYY.ordinal()] = 2;
      } catch (NoSuchFieldError e3) {
      }
      try {
        f2300b[MusicPlatform.KUGOU.ordinal()] = 3;
      } catch (NoSuchFieldError e4) {
      }
      try {
        f2300b[MusicPlatform.MIGU.ordinal()] = 4;
      } catch (NoSuchFieldError e5) {
      }
      try {
        f2300b[MusicPlatform.KUWO.ordinal()] = 5;
      } catch (NoSuchFieldError e6) {
      }
      int[] iArr2 = new int[NumberEnum.values().length];
      f2299a = iArr2;
      try {
        iArr2[NumberEnum.ONE.ordinal()] = 1;
      } catch (NoSuchFieldError e7) {
      }
      f2299a[NumberEnum.TWO.ordinal()] = 2;
      try {
        f2299a[NumberEnum.THREE.ordinal()] = 3;
      } catch (NoSuchFieldError e9) {
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

    class a implements Runnable {

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

  static class q0 implements OnClickListener {

    final Dialog f2303a;

    final MainActivity f2304b;

    q0(MainActivity music, Dialog dialog) {
      this.f2304b = music;
      this.f2303a = dialog;
    }

    @Override
    public void onClick(View view) {
      this.f2303a.dismiss();
      Intent intent = new Intent(this.f2304b.getApplicationContext(), DonateActivity.class);
      intent.putExtra("type", "wx");
      this.f2304b.startActivity(intent);
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
      } catch (Exception e2) {
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
      if (mainActivity.isNoticeRead) {
        mainActivity.appPreferences.edit().putString("gk",
            String.valueOf(mainActivity.isNoticeRead)).apply();
        this.noticeDialog.dismiss();
        return;
      }
      mainActivity.i0("请先阅读，剩余" + mainActivity.noticeCountdownSeconds + "秒才可以操作噢");
    }
  }

  class v0 implements View.OnClickListener {

    final Dialog noticeDialog;
    MainActivity mainActivity;

    v0(Dialog dialog) {
      this.noticeDialog = dialog;
      this.mainActivity = MainActivity.this;
    }

    @Override
    public void onClick(View view) {
      if (!mainActivity.noticeContent.equals(FrameBodyCOMM.DEFAULT)) {
        mainActivity.appPreferences.edit().putString("gk",
            String.valueOf(mainActivity.isNoticeRead)).apply();
        this.noticeDialog.dismiss();
        try {
          Intent intent = new Intent("android.intent.action.VIEW");
          intent.setData(Uri.parse(mainActivity.noticeContent));
          mainActivity.startActivity(intent);
          return;
        } catch (Exception unused) {
          return;
        }
      }

      if (mainActivity.isNoticeRead) {
        mainActivity.appPreferences.edit().putString("gk",
            String.valueOf(mainActivity.isNoticeRead)).apply();
        this.noticeDialog.dismiss();
      }
    }
  }

  class c implements TextWatcher {

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
        } catch (Exception e2) {
        }
      }
    }
  }

  class c0 implements OnClickListener {

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

  class d implements OnItemClickListener {

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
        this.mainActivity.i0("数据异常，请重新搜索，或者截图发送给管理员！");
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

  class d0 extends Thread {

    final String f2228a;

    final MainActivity f2229b;

    d0(MainActivity music, String str) {
      this.f2229b = music;
      this.f2228a = str;
    }

    @Override
    public void run() {
      int i2 = p0.f2300b[this.f2229b.currentMusicPlatform.ordinal()];
      if (i2 == 1) {
        this.f2229b.mvUrlList = MvUrlParser.parseMvUrl("qqMv", this.f2228a);
        if (this.f2229b.mvUrlList != null) {
        }
        this.f2229b.i0("读取失败");
      }
      if (i2 == 2) {
        this.f2229b.mvUrlList = MvUrlParser.parseMvUrl("wyyMv", this.f2228a);
        if (this.f2229b.mvUrlList != null) {
        }
        this.f2229b.i0("读取失败");
      }
      if (i2 != 3) {
        return;
      }
      this.f2229b.mvUrlList = MvUrlParser.parseMvUrl("kugouMv", this.f2228a);
      if (this.f2229b.mvUrlList != null) {
      }
      this.f2229b.i0("读取失败");
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
        this.mainActivity.i0(str);
      } else {
        CommonUtils.showNotificationPermissionDialog(this.mainActivity);
      }
      return true;
    }
  }

  class e0 extends Thread {

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

  class f implements OnItemClickListener {

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
        this.f2233a.i0("数据异常，请重新搜索");
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

  class f0 extends Thread {

    final String f2234a;

    final int f2235b;

    final String f2236c;
    final MainActivity mainActivity;

    f0(MainActivity music, String str, int i2, String str2) {
      this.mainActivity = music;
      this.f2234a = str;
      this.f2235b = i2;
      this.f2236c = str2;
    }

    public void run() throws NumberFormatException {

      if (!this.f2234a.equals("d")) {
        if (this.f2234a.equals("l")) {
          if (!CommonUtils.checkNotificationPermission(
              this.mainActivity.getApplicationContext())) {
            this.mainActivity.mainHandler.post(new a(this));
            return;
          }

          mainActivity.playListIndex = this.f2235b;
          MusicPlatform pVar = mainActivity.currentMusicPlatform;
          MusicPlatform pVar2 = MusicPlatform.QQ;

          List<Map<String, Object>> list;
          List<Map<String, Object>> list2;
          if (pVar == pVar2 || pVar == MusicPlatform.MIGU || pVar == MusicPlatform.KUWO) {
            mainActivity.playList.clear();
            list = mainActivity.playList;
            list2 = mainActivity.leftMusicList;
          } else {
            mainActivity.playList.clear();
            list = mainActivity.playList;
            list2 = mainActivity.rightMusicList;
          }
          list.addAll(list2);
          GlobalData.playList = this.mainActivity.playList;
          MusicPlatform pVar3 = mainActivity.currentMusicPlatform;
          Intent intent = new Intent(this.mainActivity.getApplicationContext(),
              mediaPlayer.getClass());
          if (pVar3 == MusicPlatform.KUGOU) {
            mainActivity.playerSource = "kugou";
            GlobalData.currentIndex = this.f2235b;
            GlobalData.currentSource = "kugou";
          } else if (pVar3 == MusicPlatform.WYY) {
            mainActivity.playerSource = "wyy";
            GlobalData.currentIndex = this.f2235b;
            GlobalData.currentSource = "wyy";
          } else if (pVar3 == MusicPlatform.MIGU) {
            mainActivity.playerSource = "migu";
            GlobalData.currentIndex = this.f2235b;
            GlobalData.currentSource = "migu";
          } else if (pVar3 == MusicPlatform.KUWO) {
            mainActivity.playerSource = "kuwo";
            GlobalData.currentIndex = this.f2235b;
            GlobalData.currentSource = "kuwo";
          } else if (pVar3 == pVar2) {
            mainActivity.playerSource = "qq";
            GlobalData.currentIndex = this.f2235b;
            GlobalData.currentSource = "qq";
          } else {
            return;
          }

          if (VERSION.SDK_INT >= 26) {
            this.mainActivity.startForegroundService(intent);
          } else {
            this.mainActivity.startService(intent);
          }
          this.mainActivity.o0(19);
          return;
        }
        return;
      }
      if (mainActivity.isDownloading) {
        mainActivity.isDownloadCancelled = false;
        mainActivity.t0();
        mainActivity.i0("下载器忙,如需批量下载,请长按列表");
        return;
      }

      mainActivity.z0();
      try {
        Thread.sleep(50L);
      } catch (InterruptedException e2) {
      }

      MusicPlatform pVar4 = this.mainActivity.currentMusicPlatform;
      String musicUrl = null;
      String title = null;
      String singer = null;
      String album = null;
      String fileName = null;
      String fileExtension = null;

      if (pVar4 == MusicPlatform.KUGOU) {
        String originalFileName =
            this.mainActivity.rightMusicList.get(this.f2235b).get("filename")
                + FrameBodyCOMM.DEFAULT;
        String fileHash =
            this.mainActivity.rightMusicList.get(this.f2235b).get("filehash")
                + FrameBodyCOMM.DEFAULT;
        String hashSubstring = fileHash.substring(0, fileHash.indexOf("低高"));

        title = this.mainActivity.rightMusicList.get(this.f2235b).get(Mp4NameBox.IDENTIFIER)
            + FrameBodyCOMM.DEFAULT;
        singer =
            this.mainActivity.rightMusicList.get(this.f2235b).get("singer")
                + FrameBodyCOMM.DEFAULT;
        album =
            this.mainActivity.rightMusicList.get(this.f2235b).get("album")
                + FrameBodyCOMM.DEFAULT;

        if (this.mainActivity.appPreferences.getInt("filemode", 0) == 1) {
          fileName = title + " - " + singer;
        } else {
          fileName = originalFileName;
        }

        if (this.mainActivity.appPreferences.getInt("lrcmode", 0) == 0) {
          String lrcPath = this.mainActivity.downloadDirectory + "/" + fileName + ".lrc";
          String encoding =
              this.mainActivity.appPreferences.getInt("type", 0) == 0 ? "utf-8" : "gbk";
          LyricDownloadUtils.downloadKugouLyric(hashSubstring, lrcPath, encoding);
        } else if (this.mainActivity.appPreferences.getInt("textmode", 0) == 0) {
          String tmpLrcPath =
              this.mainActivity.getFilesDir().getParent() + "/app_tmpFile/downTmp.lrc";
          String encoding =
              this.mainActivity.appPreferences.getInt("type", 0) == 0 ? "utf-8" : "gbk";
          boolean hasLyric = LyricDownloadUtils.downloadKugouLyric(hashSubstring, tmpLrcPath,
              encoding);
          mainActivity.hasLyricFile = hasLyric;
        }

        try {
          if (this.f2236c.equals("mp3")) {
            this.mainActivity.kugouStandardHash = fileHash.substring(0, fileHash.indexOf("低高"));
            musicUrl = this.mainActivity.musicUrlHelper.getMusicUrl("kugou",
                this.mainActivity.kugouStandardHash, "mp3");
            if (musicUrl != null && !musicUrl.equals(FrameBodyCOMM.DEFAULT)) {
              mainActivity.coverImageId = this.mainActivity.kugouStandardHash;
              fileExtension = ".mp3";
            }
          } else if (this.f2236c.equals("hq")) {
            this.mainActivity.kugouHighQualityHash = fileHash.substring(
                fileHash.indexOf("低高") + 2,
                fileHash.indexOf("高无"));
            musicUrl = this.mainActivity.musicUrlHelper.getMusicUrl("kugou",
                this.mainActivity.kugouHighQualityHash, "hq");
            if (musicUrl != null && !musicUrl.equals(FrameBodyCOMM.DEFAULT)) {
              mainActivity.coverImageId = this.mainActivity.kugouHighQualityHash;
              fileExtension = ".mp3";
            }
          } else if (this.f2236c.equals("sq")) {
            this.mainActivity.kugouLosslessHash = fileHash.substring(fileHash.indexOf("高无") + 2,
                fileHash.indexOf("无h"));
            musicUrl = this.mainActivity.musicUrlHelper.getMusicUrl("kugou",
                this.mainActivity.kugouLosslessHash, "sq");
            if (musicUrl != null && !musicUrl.equals(FrameBodyCOMM.DEFAULT)) {
              mainActivity.coverImageId = this.mainActivity.kugouLosslessHash;
              fileExtension = ".flac";
            }
          } else if (this.f2236c.equals("hr")) {
            this.mainActivity.kugouHiResHash = fileHash.substring(fileHash.indexOf("无h") + 2,
                fileHash.indexOf("高真"));
            musicUrl = this.mainActivity.musicUrlHelper.getMusicUrl("kugou",
                this.mainActivity.kugouHiResHash,
                "hires");
            if (musicUrl != null && !musicUrl.equals(FrameBodyCOMM.DEFAULT)) {
              mainActivity.coverImageId = this.mainActivity.kugouHiResHash;
              fileExtension = ".flac";
            }
          } else if (this.f2236c.equals("dsd")) {
            this.mainActivity.kugouDSDHash = fileHash.substring(fileHash.indexOf("高真") + 2);
            musicUrl = this.mainActivity.musicUrlHelper.getMusicUrl("kugou",
                this.mainActivity.kugouDSDHash,
                "dsd");
            if (musicUrl != null && !musicUrl.equals(FrameBodyCOMM.DEFAULT)) {
              mainActivity.coverImageId = this.mainActivity.kugouDSDHash;
              fileExtension = ".dff";
            }
          }
          this.mainActivity.searchEditText();
        } catch (JSONException | IOException e) {
          throw new RuntimeException(e);
        }

      } else if (pVar4 == MusicPlatform.WYY) {
        String songId =
            this.mainActivity.rightMusicList.get(this.f2235b).get("id") + FrameBodyCOMM.DEFAULT;
        String originalFileName =
            this.mainActivity.rightMusicList.get(this.f2235b).get("filename")
                + FrameBodyCOMM.DEFAULT;
        title = this.mainActivity.rightMusicList.get(this.f2235b).get(Mp4NameBox.IDENTIFIER)
            + FrameBodyCOMM.DEFAULT;
        singer =
            this.mainActivity.rightMusicList.get(this.f2235b).get("singer")
                + FrameBodyCOMM.DEFAULT;
        album =
            this.mainActivity.rightMusicList.get(this.f2235b).get("album")
                + FrameBodyCOMM.DEFAULT;

        if (this.mainActivity.appPreferences.getInt("filemode", 0) == 1) {
          fileName = title + " - " + singer;
        } else {
          fileName = originalFileName;
        }

        try {
          if (this.mainActivity.appPreferences.getInt("lrcmode", 0) == 0) {
            String lrcPath = this.mainActivity.downloadDirectory + "/" + fileName + ".lrc";
            String encoding =
                this.mainActivity.appPreferences.getInt("type", 0) == 0 ? "utf-8" : "gbk";
            LyricDownloadUtils.downloadNeteaseCloudLyric(songId, lrcPath, encoding);
          } else if (this.mainActivity.appPreferences.getInt("textmode", 0) == 0) {
            String tmpLrcPath =
                this.mainActivity.getFilesDir().getParent() + "/app_tmpFile/downTmp.lrc";
            String encoding =
                this.mainActivity.appPreferences.getInt("type", 0) == 0 ? "utf-8" : "gbk";
            boolean hasLyric = LyricDownloadUtils.downloadNeteaseCloudLyric(songId, tmpLrcPath,
                encoding);
            mainActivity.hasLyricFile = hasLyric;
          }
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

        try {
          String quality = this.f2236c;
          if (quality.equals("mp3") || quality.equals("hq")) {
            musicUrl = this.mainActivity.musicUrlHelper.getMusicUrl("wyy", songId, quality);
            fileExtension = ".mp3";
          } else if (quality.equals("sq") || quality.equals("hr")) {
            musicUrl = this.mainActivity.musicUrlHelper.getMusicUrl("wyy", songId, quality);
            fileExtension = ".flac";
          }
          this.mainActivity.searchEditText();

          if (musicUrl != null && !musicUrl.equals(FrameBodyCOMM.DEFAULT) && !musicUrl.equals(
              "null")) {
            mainActivity.coverImageId = songId;
          } else {
            musicUrl = null;
          }
        } catch (JSONException | IOException e) {
          throw new RuntimeException(e);
        }

      } else if (pVar4 == MusicPlatform.MIGU) {
        String songId =
            this.mainActivity.leftMusicList.get(this.f2235b).get("id") + FrameBodyCOMM.DEFAULT;
        String lrcUrl =
            this.mainActivity.leftMusicList.get(this.f2235b).get("lrc") + FrameBodyCOMM.DEFAULT;
        String originalFileName =
            this.mainActivity.leftMusicList.get(this.f2235b).get("filename")
                + FrameBodyCOMM.DEFAULT;
        singer =
            this.mainActivity.leftMusicList.get(this.f2235b).get("singer")
                + FrameBodyCOMM.DEFAULT;
        title = this.mainActivity.leftMusicList.get(this.f2235b).get(Mp4NameBox.IDENTIFIER)
            + FrameBodyCOMM.DEFAULT;
        album =
            this.mainActivity.leftMusicList.get(this.f2235b).get("album") + FrameBodyCOMM.DEFAULT;

        if (this.mainActivity.appPreferences.getInt("filemode", 0) == 1) {
          fileName = title + " - " + singer;
        } else {
          fileName = originalFileName;
        }

        try {
          String encoding =
              this.mainActivity.appPreferences.getInt("type", 0) == 0 ? "utf-8" : "gbk";
          if (this.mainActivity.appPreferences.getInt("lrcmode", 0) == 0) {
            String lrcPath = this.mainActivity.downloadDirectory + "/" + fileName + ".lrc";
            LyricDownloadUtils.downloadLyricFromUrl(lrcUrl, lrcPath, encoding);
          } else if (this.mainActivity.appPreferences.getInt("textmode", 0) == 0) {
            String tmpLrcPath =
                this.mainActivity.getFilesDir().getParent() + "/app_tmpFile/downTmp.lrc";
            LyricDownloadUtils.downloadLyricFromUrl(lrcUrl, tmpLrcPath, encoding);
          }
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

        try {
          String quality = this.f2236c;
          if (quality.equals("mp3") || quality.equals("hq")) {
            musicUrl = this.mainActivity.musicUrlHelper.getMusicUrl("migu", songId, quality);
            fileExtension = ".mp3";
          } else if (quality.equals("sq") || quality.equals("hr")) {
            musicUrl = this.mainActivity.musicUrlHelper.getMusicUrl("migu", songId, quality);
            fileExtension = ".flac";
          }
          this.mainActivity.searchEditText();

          if (musicUrl != null && !musicUrl.equals(FrameBodyCOMM.DEFAULT)) {
            String imgUrl =
                this.mainActivity.leftMusicList.get(this.f2235b).get("imgurl")
                    + FrameBodyCOMM.DEFAULT;
            mainActivity.coverImageId = imgUrl;
          } else {
            musicUrl = null;
          }
        } catch (JSONException | IOException e) {
          throw new RuntimeException(e);
        }

      } else if (pVar4 == MusicPlatform.KUWO) {
        String songId =
            this.mainActivity.leftMusicList.get(this.f2235b).get("id") + FrameBodyCOMM.DEFAULT;
        String originalFileName =
            this.mainActivity.leftMusicList.get(this.f2235b).get("filename")
                + FrameBodyCOMM.DEFAULT;
        title = this.mainActivity.leftMusicList.get(this.f2235b).get(Mp4NameBox.IDENTIFIER)
            + FrameBodyCOMM.DEFAULT;
        singer =
            this.mainActivity.leftMusicList.get(this.f2235b).get("singer")
                + FrameBodyCOMM.DEFAULT;
        album =
            this.mainActivity.leftMusicList.get(this.f2235b).get("album") + FrameBodyCOMM.DEFAULT;

        if (this.mainActivity.appPreferences.getInt("filemode", 0) == 1) {
          fileName = title + " - " + singer;
        } else {
          fileName = originalFileName;
        }

        if (this.mainActivity.appPreferences.getInt("lrcmode", 0) == 0) {
          String lrcPath = this.mainActivity.downloadDirectory + "/" + fileName + ".lrc";
          String encoding =
              this.mainActivity.appPreferences.getInt("type", 0) == 0 ? "utf-8" : "gbk";
          LyricDownloadUtils.downloadKuwoLyric(songId, lrcPath, encoding);
        } else if (this.mainActivity.appPreferences.getInt("textmode", 0) == 0) {
          String tmpLrcPath =
              this.mainActivity.getFilesDir().getParent() + "/app_tmpFile/downTmp.lrc";
          String encoding =
              this.mainActivity.appPreferences.getInt("type", 0) == 0 ? "utf-8" : "gbk";
          boolean hasLyric = LyricDownloadUtils.downloadKuwoLyric(songId, tmpLrcPath, encoding);
          mainActivity.hasLyricFile = hasLyric;
        }

        try {
          String quality = this.f2236c;
          if (quality.equals("mp3") || quality.equals("hq")) {
            musicUrl = this.mainActivity.musicUrlHelper.getMusicUrl("kuwo", songId, quality);
            fileExtension = ".mp3";
          } else if (quality.equals("sq") || quality.equals("hr")) {
            musicUrl = this.mainActivity.musicUrlHelper.getMusicUrl("kuwo", songId, quality);
            fileExtension = ".flac";
          }
          this.mainActivity.searchEditText();

          if (musicUrl != null && !musicUrl.equals(FrameBodyCOMM.DEFAULT)) {
            mainActivity.coverImageId = songId;
          } else {
            musicUrl = null;
          }
        } catch (JSONException | IOException e) {
          throw new RuntimeException(e);
        }

      } else if (pVar4 == MusicPlatform.QQ) {
        String songId =
            this.mainActivity.leftMusicList.get(this.f2235b).get("id") + FrameBodyCOMM.DEFAULT;
        String albumId =
            this.mainActivity.leftMusicList.get(this.f2235b).get("albumid")
                + FrameBodyCOMM.DEFAULT;
        String originalFileName =
            this.mainActivity.leftMusicList.get(this.f2235b).get("filename")
                + FrameBodyCOMM.DEFAULT;
        title = this.mainActivity.leftMusicList.get(this.f2235b).get(Mp4NameBox.IDENTIFIER)
            + FrameBodyCOMM.DEFAULT;
        singer =
            this.mainActivity.leftMusicList.get(this.f2235b).get("singer")
                + FrameBodyCOMM.DEFAULT;
        album =
            this.mainActivity.leftMusicList.get(this.f2235b).get("album") + FrameBodyCOMM.DEFAULT;

        if (this.mainActivity.appPreferences.getInt("filemode", 0) == 1) {
          fileName = title + " - " + singer;
        } else {
          fileName = originalFileName;
        }

        if (this.mainActivity.appPreferences.getInt("lrcmode", 0) == 0) {
          String lrcPath = this.mainActivity.downloadDirectory + "/" + fileName + ".lrc";
          String encoding =
              this.mainActivity.appPreferences.getInt("type", 0) == 0 ? "utf-8" : "gbk";
          LyricDownloadUtils.downloadQQMusicLyric(songId, lrcPath, encoding);
        } else if (this.mainActivity.appPreferences.getInt("textmode", 0) == 0) {
          String tmpLrcPath =
              this.mainActivity.getFilesDir().getParent() + "/app_tmpFile/downTmp.lrc";
          String encoding =
              this.mainActivity.appPreferences.getInt("type", 0) == 0 ? "utf-8" : "gbk";
          boolean hasLyric = LyricDownloadUtils.downloadQQMusicLyric(songId, tmpLrcPath,
              encoding);
          mainActivity.hasLyricFile = hasLyric;
        }

        try {
          String quality = this.f2236c;
          if (quality.equals("mp3") || quality.equals("hq")) {
            musicUrl = this.mainActivity.musicUrlHelper.getMusicUrl("qq", songId, quality);
            fileExtension = ".mp3";
          } else if (quality.equals("sq") || quality.equals("hr")) {
            musicUrl = this.mainActivity.musicUrlHelper.getMusicUrl("qq", songId, quality);
            fileExtension = ".flac";
          }
          this.mainActivity.searchEditText();

          if (musicUrl != null && !musicUrl.equals(FrameBodyCOMM.DEFAULT)) {
            mainActivity.coverImageId = albumId;
          } else {
            musicUrl = null;
          }
        } catch (JSONException | IOException e) {
          throw new RuntimeException(e);
        }
      } else {
        return;
      }

      if (musicUrl != null && fileExtension != null) {
        try {
          mainActivity.searchText(musicUrl, fileName, fileExtension, title, singer, album);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      } else {
        if (pVar4 == MusicPlatform.WYY) {
          mainActivity.i0("获取资源失败");
        } else {
          mainActivity.i0("获取该品质失败,请尝试其它品质");
        }
      }
    }

    class a implements Runnable {

      final f0 f2238a;

      a(f0 f0Var) {
        this.f2238a = f0Var;
      }

      @Override
      public void run() {
        CommonUtils.showNotificationPermissionDialog(this.f2238a.mainActivity);
      }
    }
  }

  class g implements OnItemLongClickListener {

    final MainActivity mainActivity;

    g(MainActivity music) {
      this.mainActivity = music;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i2, long j2) {
      String str = "";
      MusicPlatform currentSource = null;
      if (CommonUtils.checkNotificationPermission(this.mainActivity.getApplicationContext())) {
        List<Map<String, Object>> list = this.mainActivity.rightMusicList;
        if (list == null || list.size() < i2 + 1) {
          str = "数据异常，请重新搜索";
        } else {
          int i3 = p0.f2299a[this.mainActivity.currentSearchEngine.ordinal()];
          if (i3 == 1) {
            currentSource = MusicPlatform.WYY;
          } else if (i3 != 2) {
            if (i3 == 3) {
              currentSource = null;
            }
            if (!((!mainActivity.isBatchProcessing) & (!mainActivity.isLeftMultiSelectMode))
                || CommonUtils.isServiceRunning(
                this.mainActivity.getApplicationContext(),
                "com.mylrc.mymusic.service.DownloadService")) {
              if (mainActivity.isRightMultiSelectMode) {
                mainActivity.isRightMultiSelectMode = false;
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
                str = "再次长按可取消多选状态";
              }
            } else {
              str = "不允许操作双边对象或等数据加载完毕后重试或者该源不支持批量下载";
            }
          } else {
            currentSource = MusicPlatform.KUGOU;
          }
          mainActivity.currentMusicPlatform = currentSource;
          if (!((!mainActivity.isBatchProcessing) & (!mainActivity.isLeftMultiSelectMode) & (
              !CommonUtils.isServiceRunning(this.mainActivity.getApplicationContext(),
                  "com.mylrc.mymusic.service.DownloadService")))) {
          }
        }
        mainActivity.i0(str);
      } else {
        CommonUtils.showNotificationPermissionDialog(this.mainActivity);
      }
      return true;
    }
  }

  class g0 implements Runnable {

    final MainActivity f2240a;

    g0(MainActivity music) {
      this.f2240a = music;
    }

    @Override
    public void run() {
      this.f2240a.canShowQualityDialog = true;
    }
  }

  class h implements OnEditorActionListener {

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

  class h0 implements Runnable {

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
        str = mainActivity.searchText;
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
        str = mainActivity.searchText;
      }

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
      this.f2245c.i0("切换引擎一");
      this.f2244b.dismiss();
    }
  }

  class i0 extends Handler {

    final MainActivity mainActivity;

    i0(MainActivity music, Looper looper) {
      super(looper);
      this.mainActivity = music;
    }

    @Override
    public void handleMessage(Message message) {
      List<Map<String, Object>> list;
      TextView textView;
      MusicPlatform pVar;
      StringBuilder sb;
      List<Map<String, Object>> list2;
      Bitmap bitmap;
      int size;
      int i2 = message.what;
      if (i2 == 0) {
        String[] strArr = {Mp4NameBox.IDENTIFIER, "singer", "time", "br", "mv", "yz", "album"};
        int[] iArr = {R.id.fTextView1, R.id.fTextView2, R.id.fTextView3, R.id.fImageView1,
            R.id.fImageView2, R.id.fImageView3, R.id.fTextView4};
        if (mainActivity.leftMusicList == null) {
          mainActivity.leftMusicList = new ArrayList();
        }
        this.mainActivity.leftDisplayList.clear();
        mainActivity.leftDisplayList.addAll(mainActivity.leftMusicList);
        mainActivity.leftListAdapter = new d(this, mainActivity, mainActivity.leftDisplayList,
            R.layout.history_item,
            strArr, iArr);
        mainActivity.leftListView.setAdapter(mainActivity.leftListAdapter);
        if (mainActivity.rightMusicList == null) {
          mainActivity.rightMusicList = new ArrayList();
        }
        this.mainActivity.rightDisplayList.clear();
        mainActivity.rightDisplayList.addAll(mainActivity.rightMusicList);
        mainActivity.rightListAdapter = new e(this, mainActivity, mainActivity.rightMusicList,
            R.layout.history_item, strArr, iArr);
        mainActivity.rightListView.setAdapter(mainActivity.rightListAdapter);
        if (this.mainActivity.suggestionPopup != null
            && this.mainActivity.suggestionPopup.isShowing()) {
          this.mainActivity.suggestionPopup.dismiss();
        }
        this.mainActivity.leftSelectionMap = new HashMap();
        this.mainActivity.rightSelectionMap = new HashMap();
        return;
      }
      if (i2 == 1) {
        ToastUtils.showToast(this.mainActivity, message.obj.toString());
        return;
      }
      if (i2 == 2) {
        this.mainActivity.database();
        return;
      }
      if (i2 == 7) {
        mainActivity.suggestionContentView = LayoutInflater.from(mainActivity)
            .inflate(R.layout.popupitem, null);
        mainActivity.historyListView = mainActivity.suggestionContentView.findViewById(
            R.id.popupitemListView1);
        mainActivity.suggestionAdapter = new SimpleAdapter(mainActivity,
            mainActivity.searchSuggestions,
            R.layout.zdbz,
            new String[]{"key"}, new int[]{R.id.zdbzTextView1});
        mainActivity.historyListView.setAdapter(mainActivity.suggestionAdapter);
        this.mainActivity.historyListView.setOnItemClickListener(new f(this));
        this.mainActivity.j0();
        return;
      }
      if (i2 != 9) {
        if (i2 == 12) {
          this.mainActivity.searchLoadingImageView.clearAnimation();
          return;
        }
        if (i2 == 14) {
          Bitmap bitmap2 = (Bitmap) message.obj;
          if (bitmap2 != null) {
            this.mainActivity.qualityCoverImageView.setImageBitmap(bitmap2);
            return;
          } else {
            mainActivity.qualityCoverImageView.setImageDrawable(
                mainActivity.getDrawable(R.drawable.ic_avatar));
            return;
          }
        }
        if (i2 == 16) {
          this.mainActivity.t0();
          return;
        }
        if (i2 == 17) {
          Dialog dialog = this.mainActivity.downloadProgressDialog;
          if (dialog == null || !dialog.isShowing()) {
            return;
          }
          this.mainActivity.downloadProgressDialog.dismiss();
          return;
        }
        if (i2 == 19) {
          mainActivity.coverImageView.startAnimation(mainActivity.rotateAnimation);
          this.mainActivity.coverImageView.setVisibility(View.VISIBLE);
          return;
        } else {
          if (i2 == 22) {
            DialogHelper.showDialog(mainActivity, "提示", mainActivity.dialogMessage, "确定",
                true);
            return;
          }
          return;
        }
      }
      MusicPlatform pVar2 = mainActivity.currentMusicPlatform;
      MusicPlatform pVar3 = MusicPlatform.KUGOU;
      if (pVar2 != pVar3 && pVar2 != MusicPlatform.WYY) {
        if (pVar2 == MusicPlatform.QQ) {
          list = mainActivity.leftMusicList;
        }
        MvDownloader vVar = new MvDownloader();
        Dialog dialogA = new DialogFactory().createDialog(this.mainActivity);
        View viewInflate = LayoutInflater.from(this.mainActivity).inflate(R.layout.mvbr, null);
        View viewFindViewById = viewInflate.findViewById(R.id.mvbrLinearLayout2);
        View viewFindViewById2 = viewInflate.findViewById(R.id.mvbrLinearLayout3);
        View viewFindViewById3 = viewInflate.findViewById(R.id.mvbrLinearLayout4);
        Button button = viewInflate.findViewById(R.id.mvbrButton1);
        Button button2 = viewInflate.findViewById(R.id.mvbrButton2);
        Button button3 = viewInflate.findViewById(R.id.mvbrButton3);
        Button button4 = viewInflate.findViewById(R.id.mvbrButton4);
        ImageView imageView = viewInflate.findViewById(R.id.mvbri1);
        textView = viewInflate.findViewById(R.id.mvbrTextView1);
        TextView textView2 = viewInflate.findViewById(R.id.mvbrTextView2);
        viewInflate.findViewById(R.id.mvlayt).setOnClickListener(new g(this));
        pVar = this.mainActivity.currentMusicPlatform;
        if (pVar == pVar3 && pVar != MusicPlatform.WYY) {
          if (pVar == MusicPlatform.QQ) {
            MainActivity mainActivity = this.mainActivity;
            String sb2 =
                mainActivity.leftMusicList.get(mainActivity.leftListSelectedIndex)
                    .get(Mp4NameBox.IDENTIFIER)
                    +
                    FrameBodyCOMM.DEFAULT;
            textView.setText(sb2);
            sb = new StringBuilder();
            sb.append(
                mainActivity.leftMusicList.get(mainActivity.leftListSelectedIndex).get("singer"));
            sb.append(" -《");
            list2 = mainActivity.leftMusicList;
          }
          bitmap = mainActivity.coverBitmap;
          if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
          } else {
            imageView.setImageDrawable(mainActivity.getDrawable(R.drawable.ic_avatar));
          }
          size = this.mainActivity.mvUrlList.size();
          if (size != 3) {
            if (size != 2) {
              if (size == 1) {
                viewFindViewById.setVisibility(View.GONE);
              }
            }
            viewFindViewById2.setVisibility(View.GONE);
            viewFindViewById3.setVisibility(View.GONE);
          } else {
            viewFindViewById3.setVisibility(View.GONE);
          }
          dialogA.show();
          dialogA.setContentView(viewInflate);
          Display defaultDisplay = this.mainActivity.getWindowManager().getDefaultDisplay();
          LayoutParams attributes = dialogA.getWindow().getAttributes();
          attributes.width = defaultDisplay.getWidth();
          dialogA.getWindow().setAttributes(attributes);
          Intent intent = new Intent();
          button.setOnClickListener(new h(this, intent, dialogA));
          button.setOnLongClickListener(new i(this, dialogA, vVar));
          button2.setOnClickListener(new j(this, intent, dialogA));
          button2.setOnLongClickListener(new k(this, dialogA, vVar));
          button3.setOnClickListener(new l(this, intent, dialogA));
          button3.setOnLongClickListener(new a(this, dialogA, vVar));
          button4.setOnClickListener(new b(this, dialogA, intent));
          button4.setOnLongClickListener(new c(this, dialogA, vVar));
        }
        String sb3 =
            mainActivity.rightMusicList.get(mainActivity.leftListSelectedIndex)
                .get(Mp4NameBox.IDENTIFIER) +
                FrameBodyCOMM.DEFAULT;
        textView.setText(sb3);
        sb = new StringBuilder();
        sb.append(
            mainActivity.rightMusicList.get(mainActivity.leftListSelectedIndex).get("singer"));
        sb.append(" -《");
        list2 = mainActivity.rightMusicList;
        sb.append(list2.get(mainActivity.leftListSelectedIndex).get("album"));
        sb.append("》");
        textView2.setText(sb.toString());
        bitmap = mainActivity.coverBitmap;
        if (bitmap != null) {
        }
        size = this.mainActivity.mvUrlList.size();
        if (size != 3) {
        }
        dialogA.show();
        dialogA.setContentView(viewInflate);
        Display defaultDisplay2 = this.mainActivity.getWindowManager().getDefaultDisplay();
        LayoutParams attributes2 = dialogA.getWindow().getAttributes();
        attributes2.width = defaultDisplay2.getWidth();
        dialogA.getWindow().setAttributes(attributes2);
        Intent intent2 = new Intent();
        button.setOnClickListener(new h(this, intent2, dialogA));
        button.setOnLongClickListener(new i(this, dialogA, vVar));
        button2.setOnClickListener(new j(this, intent2, dialogA));
        button2.setOnLongClickListener(new k(this, dialogA, vVar));
        button3.setOnClickListener(new l(this, intent2, dialogA));
        button3.setOnLongClickListener(new a(this, dialogA, vVar));
        button4.setOnClickListener(new b(this, dialogA, intent2));
        button4.setOnLongClickListener(new c(this, dialogA, vVar));
      }
      list = mainActivity.rightMusicList;
      mainActivity.mvUrl = list.get(mainActivity.leftListSelectedIndex).get("filename")
          .toString();
      MvDownloader vVar2 = new MvDownloader();
      Dialog dialogA2 = new DialogFactory().createDialog(this.mainActivity);
      View viewInflate2 = LayoutInflater.from(this.mainActivity).inflate(R.layout.mvbr, null);
      View viewFindViewById4 = viewInflate2.findViewById(R.id.mvbrLinearLayout2);
      View viewFindViewById22 = viewInflate2.findViewById(R.id.mvbrLinearLayout3);
      View viewFindViewById32 = viewInflate2.findViewById(R.id.mvbrLinearLayout4);
      Button button5 = viewInflate2.findViewById(R.id.mvbrButton1);
      Button button22 = viewInflate2.findViewById(R.id.mvbrButton2);
      Button button32 = viewInflate2.findViewById(R.id.mvbrButton3);
      Button button42 = viewInflate2.findViewById(R.id.mvbrButton4);
      ImageView imageView2 = viewInflate2.findViewById(R.id.mvbri1);
      textView = viewInflate2.findViewById(R.id.mvbrTextView1);
      TextView textView22 = viewInflate2.findViewById(R.id.mvbrTextView2);
      viewInflate2.findViewById(R.id.mvlayt).setOnClickListener(new g(this));
      pVar = this.mainActivity.currentMusicPlatform;
      if (pVar == pVar3) {
        String sb32 =
            mainActivity.rightMusicList.get(mainActivity.leftListSelectedIndex)
                .get(Mp4NameBox.IDENTIFIER) +
                FrameBodyCOMM.DEFAULT;
        textView.setText(sb32);
        sb = new StringBuilder();
        sb.append(
            mainActivity.rightMusicList.get(mainActivity.leftListSelectedIndex).get("singer"));
        sb.append(" -《");
        list2 = mainActivity.rightMusicList;
        sb.append(list2.get(mainActivity.leftListSelectedIndex).get("album"));
        sb.append("》");
        textView22.setText(sb.toString());
      }
      bitmap = mainActivity.coverBitmap;
      if (bitmap != null) {
      }
      size = this.mainActivity.mvUrlList.size();
      if (size != 3) {
      }
      dialogA2.show();
      dialogA2.setContentView(viewInflate2);
      Display defaultDisplay22 = this.mainActivity.getWindowManager().getDefaultDisplay();
      LayoutParams attributes22 = dialogA2.getWindow().getAttributes();
      attributes22.width = defaultDisplay22.getWidth();
      dialogA2.getWindow().setAttributes(attributes22);
      Intent intent22 = new Intent();
      button5.setOnClickListener(new h(this, intent22, dialogA2));
      button5.setOnLongClickListener(new i(this, dialogA2, vVar2));
      button22.setOnClickListener(new j(this, intent22, dialogA2));
      button22.setOnLongClickListener(new k(this, dialogA2, vVar2));
      button32.setOnClickListener(new l(this, intent22, dialogA2));
      button32.setOnLongClickListener(new a(this, dialogA2, vVar2));
      button42.setOnClickListener(new b(this, dialogA2, intent22));
      button42.setOnLongClickListener(new c(this, dialogA2, vVar2));
    }

    class a implements OnLongClickListener {

      final Dialog f2247a;

      final MvDownloader f2248b;

      final i0 f2249c;

      a(i0 i0Var, Dialog dialog, MvDownloader vVar) {
        this.f2249c = i0Var;
        this.f2247a = dialog;
        this.f2248b = vVar;
      }

      @Override
      public boolean onLongClick(View view) {
        this.f2247a.dismiss();
        this.f2249c.mainActivity.i0("已加入 下载，详情见状态栏");
        String str = this.f2249c.mainActivity.mvUrlList.get(2);
        this.f2248b.downloadMv(this.f2249c.mainActivity.getApplicationContext(), str,
            this.f2249c.mainActivity.mvUrl + ".mp4");
        return true;
      }
    }

    class b implements OnClickListener {

      final Dialog f2250a;

      final Intent f2251b;

      final i0 f2252c;

      b(i0 i0Var, Dialog dialog, Intent intent) {
        this.f2252c = i0Var;
        this.f2250a = dialog;
        this.f2251b = intent;
      }

      @Override
      public void onClick(View view) {
        this.f2250a.dismiss();
        this.f2251b.putExtra("text", "当前播放的是蓝光品质（1080P）");
        this.f2251b.putExtra("url", this.f2252c.mainActivity.mvUrlList.get(3));
        this.f2251b.setClass(this.f2252c.mainActivity.getApplicationContext(), MVActivity.class);
        this.f2252c.mainActivity.startActivity(this.f2251b);
      }
    }

    class c implements OnLongClickListener {

      final Dialog f2253a;

      final MvDownloader f2254b;

      final i0 f2255c;

      c(i0 i0Var, Dialog dialog, MvDownloader vVar) {
        this.f2255c = i0Var;
        this.f2253a = dialog;
        this.f2254b = vVar;
      }

      @Override
      public boolean onLongClick(View view) {
        this.f2253a.dismiss();
        this.f2255c.mainActivity.i0("已加入 下载，详情见状态栏");
        String str = this.f2255c.mainActivity.mvUrlList.get(3);
        this.f2254b.downloadMv(this.f2255c.mainActivity.getApplicationContext(), str,
            this.f2255c.mainActivity.mvUrl + ".mp4");
        return true;
      }
    }

    class d extends SimpleAdapter {

      final i0 f2256a;

      d(i0 i0Var, Context context, List list, int i2, String[] strArr, int[] iArr) {
        super(context, list, i2, strArr, iArr);
        this.f2256a = i0Var;
      }

      @Override
      public View getView(int i2, View view, ViewGroup viewGroup) {
        CheckBoxHolder holder;

        if (view == null) {
          view = LayoutInflater.from(viewGroup.getContext())
              .inflate(R.layout.f, viewGroup, false);
          holder = new CheckBoxHolder();
          holder.mInstance = view.findViewById(R.id.fCheckBox1);
          view.setTag(holder);
          holder = holder;
        } else {
          holder = (CheckBoxHolder) view.getTag();
        }

        MainActivity music = this.f2256a.mainActivity;
        if (music.isLeftMultiSelectMode) {
          Map<Integer, Boolean> map = music.leftSelectionMap;
          holder.mInstance.setChecked(
              map != null && map.containsKey(Integer.valueOf(i2)));
          holder.mInstance.setVisibility(View.VISIBLE);
        } else {
          holder.mInstance.setVisibility(View.GONE);
        }

        return super.getView(i2, view, viewGroup);
      }

    }


    class e extends SimpleAdapter {

      final i0 f2257a;

      e(i0 i0Var, Context context, List list, int i2, String[] strArr, int[] iArr) {
        super(context, list, i2, strArr, iArr);
        this.f2257a = i0Var;
      }

      @Override
      public View getView(int i2, View view, ViewGroup viewGroup) {
        CheckBoxHolder tVar;
        if (view == null) {
          CheckBoxHolder tVar2 = new CheckBoxHolder();
          view = LayoutInflater.from(this.f2257a.mainActivity)
              .inflate(R.layout.history_item, null);
          tVar2.mInstance = view.findViewById(R.id.fCheckBox1);
          view.setTag(tVar2);
          tVar = tVar2;
        } else {
          Object tag = view.getTag();
          if (tag instanceof CheckBoxHolder) {
            tVar = (CheckBoxHolder) tag;
          } else {
            tVar = new CheckBoxHolder();
            tVar.mInstance = view.findViewById(R.id.fCheckBox1);
            view.setTag(tVar);
          }
        }

        if (tVar.mInstance == null) {
          return super.getView(i2, view, viewGroup);
        }

        MainActivity music = this.f2257a.mainActivity;
        if (music.isRightMultiSelectMode) {
          tVar.mInstance.setChecked(
              music.rightSelectionMap != null && music.rightSelectionMap.containsKey(
                  i2));
          tVar.mInstance.setVisibility(View.VISIBLE);
        } else {
          tVar.mInstance.setVisibility(View.INVISIBLE);
        }
        return super.getView(i2, view, viewGroup);
      }
    }

    class f implements OnItemClickListener {

      final i0 f2258a;

      f(i0 i0Var) {
        this.f2258a = i0Var;
      }

      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
        MainActivity music = this.f2258a.mainActivity;
        if (music.isLeftMultiSelectMode || music.isRightMultiSelectMode) {
          music.i0("请先再次长按列表取消多选状态再试");
          return;
        }
        List<Map<String, Object>> list = music.searchSuggestions;
        if (list == null || list.size() < i2 + 1) {
          return;
        }
        this.f2258a.mainActivity.isSearchEnabled = false;
        String str =
            this.f2258a.mainActivity.searchSuggestions.get(i2).get("key") + FrameBodyCOMM.DEFAULT;
        this.f2258a.mainActivity.searchEditText.setText(str);
        this.f2258a.mainActivity.searchText = str;
        new a(this).start();
      }

      class a extends Thread {

        final f f2259a;

        a(f fVar) {
          this.f2259a = fVar;
        }

        @Override
        public void run() {
          MainActivity music = this.f2259a.f2258a.mainActivity;
          music.l0(music.searchText);
          this.f2259a.f2258a.mainActivity.z0();
          this.f2259a.f2258a.mainActivity.m0();
        }
      }
    }

    class g implements OnClickListener {

      final i0 f2260a;

      g(i0 i0Var) {
        this.f2260a = i0Var;
      }

      @Override
      public void onClick(View view) {
        MainActivity music = this.f2260a.mainActivity;
        music.dialogMessage = "点击相应品质的按钮可直接观看MV，长按对应品质的按钮可下载视频文件，下载的视频文件存储在是：内部存储/MusicDownloader/Mv目录下。";
        DialogHelper.showDialog(music, "提示",
            "点击相应品质的按钮可直接观看MV，长按对应品质的按钮可下载视频文件，下载的视频文件存储在是：内部存储/MusicDownloader/Mv目录下。",
            "确定", true);
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
        this.f2266c.mainActivity.i0("已加入 下载，详情见状态栏");
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
        this.f2272c.mainActivity.i0("已加入 下载，详情见状态栏");
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
      this.f2278c.i0("切换引擎二");
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
      this.mainActivity.i0("切换引擎三");
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
      this.f2291b.i0("已清空");
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

  class r0 implements OnClickListener {

    final Dialog f2307a;

    final MainActivity f2308b;

    r0(MainActivity music, Dialog dialog) {
      this.f2308b = music;
      this.f2307a = dialog;
    }

    @Override
    public void onClick(View view) {
      this.f2307a.dismiss();
      Intent intent = new Intent(this.f2308b.getApplicationContext(), DonateActivity.class);
      intent.putExtra("type", "zfb");
      this.f2308b.startActivity(intent);
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
      this.f2311a.i0("已注销");
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

    final MainActivity f2314a;

    t0(MainActivity music) {
      this.f2314a = music;
    }

    @Override
    public void run() {
      try {
        String strB = DownloadUtils.getYoudaoNote("ce57d80729e9d857dfbfcbc72428883d");
        this.f2314a.noticeContent = strB.substring(strB.indexOf("【") + 1, strB.indexOf("】"));
        this.f2314a.noticeMessage1 = strB.substring(strB.indexOf("〖") + 1, strB.indexOf("〗"));
        MainActivity music = this.f2314a;
        music.noticeMessage1 = music.noticeMessage1.replace("amp;", FrameBodyCOMM.DEFAULT);
        this.f2314a.noticeButtonText = strB.substring(strB.indexOf("｛") + 1, strB.indexOf("｝"));
        this.f2314a.noticeCountdownSeconds = Integer.parseInt(
            strB.substring(strB.indexOf("『") + 1, strB.indexOf("』")));
        String string = this.f2314a.appPreferences.getString("gk", FrameBodyCOMM.DEFAULT);
        this.f2314a.runOnUiThread(new a(this));
        if (string.equals(this.f2314a.noticeContent)) {
          return;
        }
        this.f2314a.mainHandler.post(new b(this));
      } catch (Exception e2) {
      }
    }

    class a implements Runnable {

      final t0 f2315a;

      a(t0 t0Var) {
        this.f2315a = t0Var;
      }

      @Override
      public void run() {
        MainActivity music = this.f2315a.f2314a;
        music.noticeTextView.setText(music.noticeContent);
        this.f2315a.f2314a.noticeTextView.setTextSize(11.0f);
      }
    }

    class b implements Runnable {

      final t0 f2316a;

      b(t0 t0Var) {
        this.f2316a = t0Var;
      }

      @Override
      public void run() {
        this.f2316a.f2314a.showNoticeDialog();
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
        music.i0(sb.toString());
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
          str11 = ".mp3";
          str = str2;
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
          str11 = ".mp3";
          str = str2;
        }
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
              this.mainActivity.i0("错误：" + e2);
              i2 = i3 + 1;
            }
          } else {
            try {
              if (this.mainActivity.currentSource.equals("wyy")) {
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
              } else if (this.mainActivity.currentSource.equals("kuwo")) {
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
              } else if (this.mainActivity.currentSource.equals("qq")) {
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
              }
              list.add(map);
            } catch (Exception e3) {
              this.mainActivity.i0("错误：" + e3);
              i2 = i3 + 1;
            }
          }
        } catch (Exception e4) {

        }
        i2 = i3 + 1;
      }
      this.mainActivity.isBatchProcessing = false;
      this.mainActivity.i0("正在启动下载器...");
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
      } else if (itemId == R.id.item_feedback) {
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
      } else if (itemId == R.id.item_pay) {
        this.mainActivity.a0();
      }

      return true;
    }
  }

  class QualityClickListener implements OnClickListener {

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

  private class x0 extends AppCompatImageView {

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

  public class y0 implements OnClickListener {

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
          music.i0("请先再次长按列表取消多选状态再试");
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
          music.i0("请先再次长按列表取消多选状态再试");
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
            music.i0("请先再次长按列表取消多选状态再试");
          }
        }
      } else if (viewId == R.id.gklayout) {
        MainActivity mainActivity = this.f2334a;
        if (mainActivity.noticeContent != null) {
          mainActivity.y0();
        }
      }
    }
  }

}