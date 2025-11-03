package com.mylrc.mymusic.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.database.SongDatabaseHelper;
import com.mylrc.mymusic.enums.StatusBarColor;
import com.mylrc.mymusic.manager.StatusBarManager;
import com.mylrc.mymusic.model.GlobalData;
import com.mylrc.mymusic.service.DownloadService;
import com.mylrc.mymusic.service.PlayerService;
import com.mylrc.mymusic.tool.MusicUrlHelper;
import com.mylrc.mymusic.ui.dialog.DialogFactory;
import com.mylrc.mymusic.utils.CommonUtils;
import com.mylrc.mymusic.utils.ToastUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.mp4.atom.Mp4NameBox;

public class SongListActivity extends Activity {

  public static List<Map<String, String>> downloadList;
  private final Handler handler = new MessageHandler(this, Looper.getMainLooper());
  private ListView listView;
  private SimpleAdapter adapter;
  private List<Map<String, Object>> songList;
  private Dialog loadingDialog;
  private ImageView selectAllButton;
  private ImageView selectModeButton;
  private ImageView playerButton;
  private int totalDownloadCount;
  private int currentDownloadCount;
  private List<Map<String, Object>> selectedSongs;
  private ProgressDialog progressDialog;
  private boolean isDownloading;
  private String downloadPath = FrameBodyCOMM.DEFAULT;
  private boolean isSelectMode = false;
  private String source;
  private ArrayList<String> selectedItems;
  private Map<Integer, Boolean> selectionMap;
  private SharedPreferences sharedPreferences;
  private RotateAnimation rotateAnimation;
  private ExitBroadcastReceiver broadcastReceiver;

  private void toggleSelection(int position) {
    String songId = this.songList.get(position).get("id").toString();
    String filename = this.songList.get(position).get("filename").toString();

    if (this.sharedPreferences.getInt("filemode", 0) == 1) {
      filename = this.songList.get(position).get(Mp4NameBox.IDENTIFIER).toString() +
          " - " + this.songList.get(position).get("singer").toString();
    }

    String itemData = songId + "∮∮" + filename + "∮∮" +
        this.songList.get(position).get("maxbr").toString() + "∮∮" +
        (this.songList.get(position).get(Mp4NameBox.IDENTIFIER) + "∮∮" +
            this.songList.get(position).get("singer") + "∮∮" +
            this.songList.get(position).get("album") + " ");

    if (this.selectionMap == null) {
      this.selectionMap = new HashMap<>();
    }

    if (!this.selectionMap.containsKey(Integer.valueOf(position))) {
      this.selectionMap.put(Integer.valueOf(position), Boolean.TRUE);
      this.selectedItems.add(itemData);
    } else {
      this.selectionMap.remove(Integer.valueOf(position));
      this.selectedItems.remove(itemData);
    }
  }

  private void downloadSongs(String quality) {
    downloadList = new ArrayList<>();
    new DownloadThread(this, quality).start();
    this.isSelectMode = false;
    this.adapter.notifyDataSetChanged();
    this.selectAllButton.setVisibility(View.INVISIBLE);
    this.selectModeButton.setVisibility(View.INVISIBLE);
  }

  @SuppressLint("UnspecifiedRegisterReceiverFlag")
  private void initialize() {
    this.sharedPreferences = getSharedPreferences("pms", 0);
    this.progressDialog = new ProgressDialog(this);
    this.selectedSongs = new ArrayList<>();
    this.playerButton.setOnClickListener(new PlayerButtonClickListener(this));

    this.rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
    this.rotateAnimation.setDuration(8000L);
    this.rotateAnimation.setFillAfter(true);
    this.rotateAnimation.setRepeatMode(1);
    this.rotateAnimation.setInterpolator(new LinearInterpolator());
    this.rotateAnimation.setRepeatCount(-1);

    this.broadcastReceiver = new ExitBroadcastReceiver(this);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("com.music.exit");
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      registerReceiver(this.broadcastReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
    } else {
      registerReceiver(this.broadcastReceiver, intentFilter);
    }

    MediaPlayer mediaPlayer = PlayerService.mediaPlayer;
    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
      this.playerButton.startAnimation(this.rotateAnimation);
      this.playerButton.setVisibility(View.VISIBLE);
    }

    int storageType = this.sharedPreferences.getInt("storageType", 1);
    String directory = this.sharedPreferences.getString("downdirectory", "1");
    String externalPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    if (storageType == 1) {
      this.downloadPath = externalPath + "/MusicDownloader/Music";
    } else if (storageType == 2) {
      this.downloadPath = com.mylrc.mymusic.utils.FileUtils.getExternalStoragePath(
          getApplicationContext());
    } else if (storageType == 3) {
      this.downloadPath = externalPath + "/" + directory;
    }

    File downloadDir = new File(this.downloadPath + "/");
    if (!downloadDir.exists()) {
      downloadDir.mkdirs();
    }

    String sourceType = getIntent().getStringExtra("sta");
    if (sourceType.equals("wyy")) {
      this.source = "wyy";
      this.songList = WYYPlayListActivity.playlistData;
      if (this.songList == null) {
        Toast.makeText(getApplicationContext(), "数据异常，请截图反馈给管理员", Toast.LENGTH_LONG)
            .show();
        finish();
      }
    } else {
      this.source = "qq";
      this.songList = QQPlayListActivity.playlistData;
      if (this.songList == null) {
        Toast.makeText(getApplicationContext(), "数据异常，请截图反馈给管理员", Toast.LENGTH_LONG)
            .show();
        finish();
      }
    }

    MusicUrlHelper musicUrlHelper = new MusicUrlHelper();
    SongDatabaseHelper databaseHelper = new SongDatabaseHelper(getApplicationContext());
    this.selectionMap = new HashMap<>();
    this.selectedItems = new ArrayList<>();

    this.selectAllButton.setOnClickListener(new SelectAllButtonClickListener(this));

    if (this.songList != null) {
      String[] from = new String[]{Mp4NameBox.IDENTIFIER, "singer", "time", "br", "mv", "text"};
      int[] to = new int[]{R.id.songfTextView1, R.id.songfTextView2, R.id.songfTextView3,
          R.id.songfImageView1, R.id.songfImageView2, R.id.songfTextView4};
      SongListAdapter adapter = new SongListAdapter(this, getApplicationContext(),
          this.songList, R.layout.songf, from, to);
      this.adapter = adapter;
      this.listView.setAdapter(adapter);
    }

    this.listView.setOnItemClickListener(new ListItemClickListener(this));
    this.listView.setOnItemLongClickListener(new ListItemLongClickListener(this));
    this.selectModeButton.setOnClickListener(new SelectModeButtonClickListener(this));
    findViewById(R.id.songTextView1).setOnClickListener(new TipClickListener(this));
    findViewById(R.id.songRelativeLayout1).setOnClickListener(new BackClickListener(this));
  }

  private void showToast(String message) {
    Message msg = new Message();
    msg.what = 0;
    msg.obj = message;
    this.handler.sendMessage(msg);
  }

  private void showProgressDialog() {
    this.progressDialog.setTitle("正在下载中");
    this.progressDialog.setMax(this.totalDownloadCount);
    this.progressDialog.setCancelable(false);
    this.progressDialog.setProgressStyle(1);
    this.progressDialog.setProgress(this.currentDownloadCount);
    this.progressDialog.show();
  }

  private void dismissProgressDialog() {
    ProgressDialog dialog = this.progressDialog;
    if (dialog != null && dialog.isShowing()) {
      this.progressDialog.dismiss();
    }
  }

  private void showLoadingDialog() {
    if (this.loadingDialog == null) {
      Dialog dialog = new Dialog(this);
      this.loadingDialog = dialog;
      dialog.requestWindowFeature(1);
      this.loadingDialog.getWindow().setWindowAnimations(R.style.LoadingAnim);
      this.loadingDialog.setContentView(R.layout.loading);
      this.loadingDialog.setCancelable(false);
    }
    if (!this.loadingDialog.isShowing() && !isFinishing()) {
      this.loadingDialog.show();
    }
  }

  private void dismissLoadingDialog() {
    Dialog dialog = this.loadingDialog;
    if (dialog != null && dialog.isShowing()) {
      this.loadingDialog.dismiss();
    }
  }

  private void showQualityDialog() {
    ArrayList<String> items = this.selectedItems;
    if (items == null || items.size() == 0) {
      this.showToast("请先选择一些歌吧～");
      return;
    }

    Dialog qualityDialog = new DialogFactory().createDialog(this);
    View dialogView = LayoutInflater.from(this).inflate(R.layout.brdialog, null);
    qualityDialog.show();
    qualityDialog.setContentView(dialogView);

    Display display = getWindowManager().getDefaultDisplay();
    WindowManager.LayoutParams params = qualityDialog.getWindow().getAttributes();
    params.width = display.getWidth();
    qualityDialog.getWindow().setAttributes(params);

    Button mp3Button = dialogView.findViewById(R.id.brdialogButton1);
    Button hqButton = dialogView.findViewById(R.id.brdialogButton2);
    Button sqButton = dialogView.findViewById(R.id.brdialogButton3);
    Button hrButton = dialogView.findViewById(R.id.brdialogButton4);

    mp3Button.setOnClickListener(v -> {
      downloadSongs("mp3");
      qualityDialog.dismiss();
    });

    hqButton.setOnClickListener(v -> {
      downloadSongs("hq");
      qualityDialog.dismiss();
    });

    sqButton.setOnClickListener(v -> {
      downloadSongs("sq");
      qualityDialog.dismiss();
    });

    hrButton.setOnClickListener(v -> {
      downloadSongs("hr");
      qualityDialog.dismiss();
    });
  }

  public boolean isServiceRunning(String serviceName) {
    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
        Integer.MAX_VALUE)) {
      if (serviceName.equals(service.service.getClassName())) {
        return true;
      }
    }
    return false;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    setContentView(R.layout.song);
    this.listView = findViewById(R.id.songListView1);
    this.selectAllButton = findViewById(R.id.songImageView1);
    this.selectModeButton = findViewById(R.id.songImageView2);
    this.playerButton = findViewById(R.id.songImageView3);
    initialize();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    dismissLoadingDialog();
    dismissProgressDialog();
    if (this.broadcastReceiver != null) {
      try {
        unregisterReceiver(this.broadcastReceiver);
      } catch (IllegalArgumentException e) {
      }
    }
  }

  @Override
  public void onPointerCaptureChanged(boolean hasCapture) {
  }

  class QualityButtonClickListener implements View.OnClickListener {

    final Dialog dialog;
    final String quality;
    final SongListActivity activity;

    QualityButtonClickListener(SongListActivity activity, Dialog dialog, String quality) {
      this.activity = activity;
      this.dialog = dialog;
      this.quality = quality;
    }

    @Override
    public void onClick(View view) {
      this.dialog.dismiss();
      if (this.activity.selectedItems.size() == 0) {
        this.activity.showToast("你还没有选择歌曲哦～");
        return;
      }
      this.activity.downloadSongs(this.quality);
    }
  }

  class PlayerButtonClickListener implements View.OnClickListener {

    final SongListActivity activity;

    PlayerButtonClickListener(SongListActivity activity) {
      this.activity = activity;
    }

    @Override
    public void onClick(View view) {
      Intent intent = new Intent(this.activity, PlayerActivity.class);
      this.activity.startActivity(intent);
    }
  }

  class DownloadThread extends Thread {

    final String quality;
    final SongListActivity activity;

    DownloadThread(SongListActivity activity, String quality) {
      this.activity = activity;
      this.quality = quality;
    }

    @Override
    public void run() {
      this.activity.isDownloading = true;
      this.activity.totalDownloadCount = this.activity.selectedItems.size();
      Message msg = new Message();
      msg.what = 16;
      this.activity.handler.sendMessage(msg);
      String source = this.activity.source;
      for (int i = 0; i < this.activity.selectedItems.size(); i++) {
        String[] parts = this.activity.selectedItems.get(i).split("∮∮");
        String songId = parts[0];
        String filename = parts[1];
        String maxQuality = parts[2];
        String name = parts[3];
        String singer = parts[4];
        String album = parts[5];

        this.activity.currentDownloadCount = i + 1;
        this.activity.progressDialog.setProgress(this.activity.currentDownloadCount);

        Intent serviceIntent = new Intent(this.activity.getApplicationContext(),
            DownloadService.class);
        serviceIntent.putExtra("songid", songId);
        serviceIntent.putExtra("filename", filename);
        serviceIntent.putExtra("singer", singer);
        serviceIntent.putExtra("name", name);
        serviceIntent.putExtra("album", album);
        serviceIntent.putExtra("br", this.quality);
        serviceIntent.putExtra("maxbr", maxQuality);
        serviceIntent.putExtra("source", source);
        serviceIntent.putExtra("downloadpath", this.activity.downloadPath);
        if (Build.VERSION.SDK_INT >= 26) {
          this.activity.startForegroundService(serviceIntent);
        } else {
          this.activity.startService(serviceIntent);
        }

        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      Message finishMsg = new Message();
      finishMsg.what = 17;
      this.activity.handler.sendMessage(finishMsg);
      this.activity.isDownloading = false;
      this.activity.selectionMap.clear();
      this.activity.selectedItems.clear();
    }
  }

  class SelectAllButtonClickListener implements View.OnClickListener {

    final SongListActivity activity;

    SelectAllButtonClickListener(SongListActivity activity) {
      this.activity = activity;
    }

    @Override
    public void onClick(View view) {
      if (this.activity.selectionMap.size() == this.activity.songList.size()) {
        this.activity.selectionMap.clear();
        this.activity.selectedItems.clear();
        this.activity.adapter.notifyDataSetChanged();
        this.activity.showToast("已取消全选");
        return;
      }
      for (int i = 0; i < this.activity.songList.size(); i++) {
        this.activity.selectionMap.put(Integer.valueOf(i), Boolean.TRUE);
        String songId = this.activity.songList.get(i).get("id").toString();
        String filename = this.activity.songList.get(i).get("filename").toString();
        if (this.activity.sharedPreferences.getInt("filemode", 0) == 1) {
          filename = this.activity.songList.get(i).get(Mp4NameBox.IDENTIFIER).toString() +
              " - " + this.activity.songList.get(i).get("singer").toString();
        }
        this.activity.selectedItems.add(songId + "∮∮" + filename + "∮∮" +
            this.activity.songList.get(i).get("maxbr").toString() + "∮∮" +
            (this.activity.songList.get(i).get(Mp4NameBox.IDENTIFIER) + "∮∮" +
                this.activity.songList.get(i).get("singer") + "∮∮" +
                this.activity.songList.get(i).get("album") + " "));
      }
      this.activity.adapter.notifyDataSetChanged();
      this.activity.showToast("已全选，一共" + this.activity.selectedItems.size() + "首");
    }
  }

  class ListItemClickListener implements AdapterView.OnItemClickListener {

    final SongListActivity activity;

    ListItemClickListener(SongListActivity activity) {
      this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      if (!CommonUtils.checkNotificationPermission(this.activity.getApplicationContext())) {
        CommonUtils.showNotificationPermissionDialog(this.activity);
        return;
      }

      if (this.activity.isSelectMode) {
        this.activity.toggleSelection(position);
        this.activity.adapter.notifyDataSetChanged();
        return;
      }

      this.activity.playerButton.startAnimation(this.activity.rotateAnimation);
      this.activity.playerButton.setVisibility(View.VISIBLE);
      this.activity.selectedSongs.clear();

      if (this.activity.source.equals("wyy")) {
        this.activity.selectedSongs.addAll(WYYPlayListActivity.playlistData);
      } else {
        this.activity.selectedSongs.addAll(QQPlayListActivity.playlistData);
      }

      GlobalData.playState = position;
      GlobalData.currentSource = this.activity.source;
      GlobalData.playList = this.activity.selectedSongs;

      if (PlayerService.mediaPlayer == null) {
        Intent intent = new Intent(this.activity.getApplicationContext(), PlayerService.class);
        if (Build.VERSION.SDK_INT >= 26) {
          this.activity.startForegroundService(intent);
        } else {
          this.activity.startService(intent);
        }
      } else {
        Intent intent = new Intent(this.activity.getApplicationContext(),
            PlayerService.mediaPlayer.getClass());
        if (Build.VERSION.SDK_INT >= 26) {
          this.activity.startForegroundService(intent);
        } else {
          this.activity.startService(intent);
        }
      }
    }
  }

  class ListItemLongClickListener implements AdapterView.OnItemLongClickListener {

    final SongListActivity activity;

    ListItemLongClickListener(SongListActivity activity) {
      this.activity = activity;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
      if (!CommonUtils.checkNotificationPermission(this.activity.getApplicationContext())) {
        CommonUtils.showNotificationPermissionDialog(this.activity);
        return true;
      }

      if (this.activity.isServiceRunning("com.mylrc.mymusic.service.DownloadService")
          || this.activity.isDownloading) {
        this.activity.showToast("请等待数据加载完成后再试");
        return true;
      }

      if (this.activity.isSelectMode) {
        this.activity.isSelectMode = false;
        this.activity.adapter.notifyDataSetChanged();
        this.activity.selectAllButton.setVisibility(View.INVISIBLE);
        this.activity.selectModeButton.setVisibility(View.INVISIBLE);
        this.activity.selectionMap.clear();
        this.activity.selectedItems.clear();
      } else {
        this.activity.isSelectMode = true;
        this.activity.adapter.notifyDataSetChanged();
        this.activity.selectAllButton.setVisibility(View.VISIBLE);
        this.activity.selectModeButton.setVisibility(View.VISIBLE);
      }
      return true;
    }
  }

  class SelectModeButtonClickListener implements View.OnClickListener {

    final SongListActivity activity;

    SelectModeButtonClickListener(SongListActivity activity) {
      this.activity = activity;
    }

    @Override
    public void onClick(View view) {
      this.activity.showQualityDialog();
    }
  }

  class TipClickListener implements View.OnClickListener {

    final SongListActivity activity;

    TipClickListener(SongListActivity activity) {
      this.activity = activity;
    }

    @Override
    public void onClick(View view) {
      ToastUtils.showToast(this.activity, "长按列表即可进行多选操作～");
    }
  }

  class BackClickListener implements View.OnClickListener {

    final SongListActivity activity;

    BackClickListener(SongListActivity activity) {
      this.activity = activity;
    }

    @Override
    public void onClick(View view) {
      this.activity.finish();
    }
  }

  class MessageHandler extends Handler {

    final SongListActivity activity;

    MessageHandler(SongListActivity activity, Looper looper) {
      super(looper);
      this.activity = activity;
    }

    @Override
    public void handleMessage(Message message) {
      int what = message.what;
      if (what == 0) {
        ToastUtils.showToast(this.activity, message.obj.toString());
      } else if (what == 2) {
        this.activity.showLoadingDialog();
      } else if (what == 3) {
        if (this.activity.loadingDialog != null && this.activity.loadingDialog.isShowing()) {
          this.activity.loadingDialog.dismiss();
        }
      } else if (what == 16) {
        this.activity.showProgressDialog();
      } else if (what == 17) {
        this.activity.dismissProgressDialog();
        this.activity.showToast("下载完成");
      }
      super.handleMessage(message);
    }
  }

  class SongListAdapter extends SimpleAdapter {

    final SongListActivity activity;

    SongListAdapter(SongListActivity activity, Context context, List<? extends Map<String, ?>> data,
        int resource, String[] from, int[] to) {
      super(context, data, resource, from, to);
      this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View view = super.getView(position, convertView, parent);
      CheckBox checkbox = view.findViewById(R.id.songfCheckBox1);
      if (this.activity.isSelectMode) {
        checkbox.setVisibility(android.view.View.VISIBLE);
        checkbox.setChecked(this.activity.selectionMap.get(position) != null);
      } else {
        checkbox.setVisibility(View.INVISIBLE);
      }
      return view;
    }
  }

  class ExitBroadcastReceiver extends BroadcastReceiver {

    final SongListActivity activity;

    ExitBroadcastReceiver(SongListActivity activity) {
      this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
      if (intent.getAction().equals("com.music.exit")) {
        this.activity.playerButton.clearAnimation();
        this.activity.playerButton.setVisibility(View.GONE);
      }
    }
  }
}