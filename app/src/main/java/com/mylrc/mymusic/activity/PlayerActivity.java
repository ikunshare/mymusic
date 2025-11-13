package com.mylrc.mymusic.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.enums.StatusBarColor;
import com.mylrc.mymusic.model.GlobalData;
import com.mylrc.mymusic.service.PlayerService;
import com.mylrc.mymusic.tool.LrcView;
import com.mylrc.mymusic.ui.dialog.DialogFactory;
import com.mylrc.mymusic.ui.viewholder.ViewHolder;
import com.mylrc.mymusic.utils.CommonUtils;
import com.mylrc.mymusic.utils.FileUtils;
import com.mylrc.mymusic.utils.ToastUtils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerActivity extends Activity {

  private static final String TAG = "PlayerActivity";

  private static final String ACTION_UPDATE_VIEW = "com.music.upview";
  private static final String ACTION_UPDATE_VIEW2 = "com.music.upview2";
  private static final String ACTION_EXIT = "com.music.exit";
  private static final String ACTION_PLAYER_CONTROL = "com.mylrc.mymusic.ac";
  private static final String ACTION_ADD_NOTIFICATION = "com.music.add";
  private static final String ACTION_REMOVE_NOTIFICATION = "com.music.remove";

  private static final int CONTROL_PLAY = 0x123;
  private static final int CONTROL_PAUSE = 0x124;
  private static final int CONTROL_NEXT = 0x126;
  private static final int CONTROL_PREV = 0x127;

  private static final int MSG_LOAD_LYRICS = 1;
  private static final int MSG_SHOW_TOAST = 0;

  private static final int UI_UPDATE_INTERVAL = 100;

  private static final int LYRICS_TIME_OFFSET = 150;

  private static final String DUAL_LYRICS_SEPARATOR = "GCSP_Have_Translation";

  private static final String PREF_NAME = "pms";
  private static final String PREF_KEY_WINDOW = "win";

  private static final int SCREEN_WIDTH_XLARGE = 1440;
  private static final int SCREEN_WIDTH_LARGE = 720;

  private static final int THUMB_SIZE_XLARGE = 85;
  private static final int THUMB_SIZE_LARGE = 55;
  private static final int THUMB_SIZE_NORMAL = 35;

  private static final int ALBUM_ART_SIZE = 720;
  private static final int ALBUM_ART_CORNER_RADIUS = 35;

  private static final String TEMP_LYRICS_FILE = "listen_tmp.lrc";
  private static final String TEMP_ALBUM_ART_FILE = "listen_tmp.bin";
  private static final String TEMP_DIR = "/app_tmpFile/";

  private final Handler uiHandler;
  private final Runnable uiUpdateRunnable;

  private TextView titleTextView;
  private TextView artistTextView;
  private TextView currentTimeTextView;
  private TextView totalTimeTextView;
  private SeekBar seekBar;
  private RelativeLayout backButton;
  private RelativeLayout playlistButton;
  private RelativeLayout playModeButton;
  private RelativeLayout nextButton;
  private RelativeLayout playPauseButton;
  private RelativeLayout prevButton;
  private ImageView playPauseIcon;
  private ImageView playModeIcon;
  private LrcView lrcView;
  private RelativeLayout albumArtToggleLayout;
  private ImageView albumArtImageView;

  private int currentPosition;
  private int duration;
  private MediaPlayer mediaPlayer;
  private String lyricFilePath;
  private SharedPreferences sharedPreferences;
  private SimpleAdapter playlistAdapter;
  private Bitmap albumArtBitmap;
  private String tempFilePath;
  private MusicBroadcastReceiver musicBroadcastReceiver;

  public PlayerActivity() {
    super();
    this.albumArtBitmap = null;
    this.uiHandler = new UIHandler(Looper.getMainLooper());
    this.uiUpdateRunnable = new UIUpdateRunnable();
  }

  public static Bitmap createRoundedBitmap(Bitmap bitmap, int width, int height,
      int cornerRadius, int borderWidth) {
    if (bitmap == null) {
      return null;
    }

    float scaleX = (float) width / bitmap.getWidth();
    float scaleY = (float) height / bitmap.getHeight();

    Matrix matrix = new Matrix();
    matrix.setScale(scaleX, scaleY);

    Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(output);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
        Shader.TileMode.CLAMP);
    shader.setLocalMatrix(matrix);
    paint.setShader(shader);

    RectF rect = new RectF(borderWidth, borderWidth,
        width - borderWidth, height - borderWidth);
    canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);

    if (borderWidth > 0) {
      Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
      borderPaint.setColor(0xFFFF00FF);
      borderPaint.setStyle(Paint.Style.STROKE);
      borderPaint.setStrokeWidth(borderWidth);
      canvas.drawRoundRect(rect, cornerRadius, cornerRadius, borderPaint);
    }

    return output;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    overridePendingTransition(R.anim.in_from_up, 0);

    new com.mylrc.mymusic.manager.StatusBarManager(this)
        .setStatusBarTheme(StatusBarColor.BLACK);

    setContentView(R.layout.player);

    initViews();
    initData();
    configureUI();
    setupListeners();
    registerBroadcastReceiver();
    loadAlbumArt();
    initUIAndListeners();
  }

  private void initViews() {
    titleTextView = findViewById(R.id.title);
    artistTextView = findViewById(R.id.singer);
    currentTimeTextView = findViewById(R.id.time1);
    totalTimeTextView = findViewById(R.id.time2);
    seekBar = findViewById(R.id.playerSeekBar1);
    prevButton = findViewById(R.id.next);
    backButton = findViewById(R.id.playerRelativeLayout2);
    playModeButton = findViewById(R.id.mode);
    nextButton = findViewById(R.id.up);
    playPauseButton = findViewById(R.id.playerRelativeLayout3);
    albumArtToggleLayout = findViewById(R.id.playerRelativeLayout4);
    playPauseIcon = findViewById(R.id.play);
    playModeIcon = findViewById(R.id.playerImageView1);
    playlistButton = findViewById(R.id.plist);
    albumArtImageView = findViewById(R.id.playerImageView2);
    lrcView = findViewById(R.id.playerLrcView1);
  }

  private void initData() {
    sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    tempFilePath = getFilesDir().getParent() + TEMP_DIR;
    lyricFilePath = tempFilePath + TEMP_LYRICS_FILE;
  }

  private void configureUI() {
    configureSeekBarThumb();
    updatePlayModeIcon();
  }

  private void configureSeekBarThumb() {
    DisplayMetrics metrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(metrics);
    int screenWidth = metrics.widthPixels;

    int thumbSize;
    if (screenWidth >= SCREEN_WIDTH_XLARGE) {
      thumbSize = THUMB_SIZE_XLARGE;
    } else if (screenWidth >= SCREEN_WIDTH_LARGE) {
      thumbSize = THUMB_SIZE_LARGE;
    } else {
      thumbSize = THUMB_SIZE_NORMAL;
    }

    BitmapDrawable seekBarThumb = createScaledDrawable(this,
        R.drawable.ic_seekbar_thumb, thumbSize, thumbSize);
    seekBar.setThumb(seekBarThumb);
  }

  private void setupListeners() {
    backButton.setOnClickListener(new BackButtonListener());
    prevButton.setOnClickListener(new PrevButtonListener());
    nextButton.setOnClickListener(new NextButtonListener());
    playlistButton.setOnClickListener(new PlaylistButtonListener());
    playModeButton.setOnClickListener(new PlayModeButtonListener());
    albumArtToggleLayout.setOnClickListener(new AlbumArtToggleListener());
    seekBar.setOnSeekBarChangeListener(new SeekBarChangeListener());
    playPauseButton.setOnClickListener(new PlayPauseButtonListener());
  }

  private void registerBroadcastReceiver() {
    musicBroadcastReceiver = new MusicBroadcastReceiver();
    IntentFilter filter = new IntentFilter();
    filter.addAction(ACTION_UPDATE_VIEW);
    filter.addAction(ACTION_UPDATE_VIEW2);
    filter.addAction(ACTION_EXIT);
    registerReceiver(musicBroadcastReceiver, filter);
  }

  private void initUIAndListeners() {
    titleTextView.setText(GlobalData.currentMusicName != null ?
        GlobalData.currentMusicName : "");
    artistTextView.setText(GlobalData.currentArtist != null ?
        GlobalData.currentArtist : "");

    if (playlistAdapter != null) {
      playlistAdapter.notifyDataSetChanged();
    }

    sendHandlerMessage();
  }

  private void sendHandlerMessage() {
    Message msg = Message.obtain();
    msg.what = MSG_LOAD_LYRICS;
    uiHandler.sendMessage(msg);
  }

  private void loadAlbumArt() {
    String albumArtPath = tempFilePath + TEMP_ALBUM_ART_FILE;
    File artFile = new File(albumArtPath);

    if (artFile.exists()) {
      albumArtBitmap = BitmapFactory.decodeFile(albumArtPath);
    } else {
      albumArtBitmap = BitmapFactory.decodeResource(getResources(),
          R.drawable.defaultcover);
    }

    if (albumArtBitmap != null) {
      Bitmap roundedArt = createRoundedBitmap(albumArtBitmap,
          ALBUM_ART_SIZE, ALBUM_ART_SIZE, ALBUM_ART_CORNER_RADIUS, 0);
      albumArtImageView.setImageBitmap(roundedArt);
    }
  }

  public BitmapDrawable createScaledDrawable(Activity activity, int resId,
      int width, int height) {
    Bitmap original = BitmapFactory.decodeResource(activity.getResources(), resId);
    Bitmap scaled = Bitmap.createScaledBitmap(original, width, height, true);
    BitmapDrawable drawable = new BitmapDrawable(activity.getResources(), scaled);

    if (drawable.getBitmap().getDensity() == 0) {
      drawable.setTargetDensity(activity.getResources().getDisplayMetrics());
    }
    return drawable;
  }

  protected void sendPlayerControlBroadcast(int controlCode) {
    Intent intent = new Intent(ACTION_PLAYER_CONTROL);
    intent.setPackage(getPackageName());
    intent.putExtra("control", controlCode);
    sendBroadcast(intent);
  }

  @SuppressLint("UseCompatLoadingForDrawables")
  private void updatePlayModeIcon() {
    int iconRes;
    switch (GlobalData.playMode) {
      case 0:
        iconRes = R.drawable.all;
        break;
      case 1:
        iconRes = R.drawable.one;
        break;
      case 2:
        iconRes = R.drawable.sj;
        break;
      default:
        iconRes = R.drawable.all;
        break;
    }
    playModeIcon.setBackground(getDrawable(iconRes));
  }

  @Override
  public void finish() {
    super.finish();
    overridePendingTransition(0, R.anim.out_to_down);
  }

  @Override
  protected void onDestroy() {
    if (uiHandler != null && uiUpdateRunnable != null) {
      uiHandler.removeCallbacks(uiUpdateRunnable);
    }

    if (musicBroadcastReceiver != null) {
      try {
        unregisterReceiver(musicBroadcastReceiver);
      } catch (IllegalArgumentException e) {
        Log.w(TAG, "Receiver not registered", e);
      }
    }

    super.onDestroy();
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      finish();
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (sharedPreferences.getInt(PREF_KEY_WINDOW, 1) == 0) {
      sendBroadcast(new Intent(ACTION_ADD_NOTIFICATION));
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (sharedPreferences.getInt(PREF_KEY_WINDOW, 1) == 0) {
      sendBroadcast(new Intent(ACTION_REMOVE_NOTIFICATION));
    }
  }

  private class UIUpdateRunnable implements Runnable {

    @SuppressLint("ResourceType")
    @Override
    public void run() {
      if (mediaPlayer != null) {
        try {
          if (mediaPlayer.isPlaying()) {
            duration = mediaPlayer.getDuration();
            seekBar.setMax(duration);
          }
          totalTimeTextView.setText(CommonUtils.formatTime(duration / 1000));

          if (mediaPlayer.isPlaying()) {
            currentPosition = mediaPlayer.getCurrentPosition();
            seekBar.setProgress(currentPosition);
            lrcView.updateTime(currentPosition + LYRICS_TIME_OFFSET);
          }

          if (mediaPlayer.isPlaying()) {
            playPauseIcon.setBackgroundResource(R.drawable.stop2);
          } else {
            playPauseIcon.setBackgroundResource(R.drawable.play);
          }

          currentTimeTextView.setText(CommonUtils.formatTime(currentPosition / 1000));
        } catch (IllegalStateException e) {
          Log.e(TAG, "MediaPlayer error in UI update", e);
        }
      }

      uiHandler.postDelayed(this, UI_UPDATE_INTERVAL);
    }
  }

  private class BackButtonListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
      finish();
    }
  }

  private class PrevButtonListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
      sendPlayerControlBroadcast(CONTROL_PREV);
    }
  }

  private class NextButtonListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
      sendPlayerControlBroadcast(CONTROL_NEXT);
    }
  }

  private class PlaylistButtonListener implements View.OnClickListener {

    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    @Override
    public void onClick(View v) {
      showPlaylistDialog();
    }

    private void showPlaylistDialog() {
      Dialog dialog = new DialogFactory().createDialog(PlayerActivity.this);
      View dialogView = LayoutInflater.from(PlayerActivity.this)
          .inflate(R.layout.playerlist, null);

      TextView playModeText = dialogView.findViewById(R.id.playerListTextView1);
      ImageView playModeIcon = dialogView.findViewById(R.id.playerListImageView);

      updatePlayModeInDialog(playModeText, playModeIcon);

      dialog.setContentView(dialogView);
      dialog.show();

      adjustDialogWidth(dialog);

      setupPlaylistListView(dialogView, playModeText, playModeIcon);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void updatePlayModeInDialog(TextView playModeText, ImageView playModeIcon) {
      int currentPlayMode = GlobalData.playMode;
      switch (currentPlayMode) {
        case 0:
          playModeText.setText(R.string.play_mode_order);
          playModeIcon.setBackground(getDrawable(R.drawable.all));
          break;
        case 1:
          playModeText.setText(R.string.play_mode_single);
          playModeIcon.setBackground(getDrawable(R.drawable.one));
          break;
        case 2:
          playModeText.setText(R.string.play_mode_random);
          playModeIcon.setBackground(getDrawable(R.drawable.sj));
          break;
      }
    }

    private void adjustDialogWidth(Dialog dialog) {
      Window window = dialog.getWindow();
      if (window != null) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(params);
      }
    }

    private void setupPlaylistListView(View dialogView, TextView playModeText,
        ImageView playModeIcon) {
      ListView playlistListView = dialogView.findViewById(R.id.playerListListView1);

      String[] from = {"name", "singer"};
      int[] to = {R.id.playerlistitemTextView1, R.id.playerlistitemTextView2};

      playlistAdapter = new PlaylistAdapter(
          PlayerActivity.this,
          GlobalData.playList,
          R.layout.playerlist_item,
          from,
          to,
          GlobalData.playMode,
          playModeText,
          playModeIcon
      );

      playlistListView.setAdapter(playlistAdapter);
      playlistListView.setSelectionFromTop(GlobalData.currentIndex, 3);
      playlistListView.setOnItemClickListener(new PlaylistItemClickListener());
    }
  }

  private class PlaylistAdapter extends SimpleAdapter {

    final int playMode;
    final TextView dialogPlayModeText;
    final ImageView dialogPlayModeIcon;

    public PlaylistAdapter(Context context, List<? extends Map<String, ?>> data,
        int resource, String[] from, int[] to, int playMode,
        TextView playModeText, ImageView playModeIcon) {
      super(context, data, resource, from, to);
      this.playMode = playMode;
      this.dialogPlayModeText = playModeText;
      this.dialogPlayModeIcon = playModeIcon;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;
      if (convertView == null) {
        convertView = LayoutInflater.from(PlayerActivity.this)
            .inflate(R.layout.playerlist_item, null);
        holder = new ViewHolder();
        holder.title = convertView.findViewById(R.id.playerlistitemTextView1);
        holder.artist = convertView.findViewById(R.id.playerlistitemTextView2);
        holder.removeButtonLayout = convertView.findViewById(R.id.playerlistitemRelativeLayout1);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }

      if (GlobalData.currentIndex == position) {
        holder.title.setTextColor(Color.parseColor("#FFFE4263"));
        holder.artist.setTextColor(Color.parseColor("#FFFE4263"));
      } else {
        holder.title.setTextColor(Color.parseColor("#FF000000"));
        holder.artist.setTextColor(Color.parseColor("#FFB2B2B2"));
      }

      holder.removeButtonLayout.setOnClickListener(
          new RemoveFromPlaylistListener(position, playMode,
              dialogPlayModeText, dialogPlayModeIcon));

      return super.getView(position, convertView, parent);
    }
  }

  private class RemoveFromPlaylistListener implements View.OnClickListener {

    final int position;
    final int playMode;
    final TextView playModeText;
    final ImageView playModeIcon;

    RemoveFromPlaylistListener(int position, int playMode,
        TextView text, ImageView icon) {
      this.position = position;
      this.playMode = playMode;
      this.playModeText = text;
      this.playModeIcon = icon;
    }

    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    @Override
    public void onClick(View v) {
      if (GlobalData.currentIndex == this.position) {
        ToastUtils.showToast(PlayerActivity.this,
            getString(R.string.cannot_remove_current_song));
        return;
      }

      GlobalData.playList.remove(this.position);

      if (GlobalData.currentIndex > this.position) {
        GlobalData.currentIndex--;
      }

      updatePlayModeDisplay();

      playlistAdapter.notifyDataSetChanged();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void updatePlayModeDisplay() {
      switch (this.playMode) {
        case 0:
          this.playModeText.setText(R.string.play_mode_order);
          this.playModeIcon.setBackground(getDrawable(R.drawable.all));
          break;
        case 1:
          this.playModeText.setText(R.string.play_mode_single);
          this.playModeIcon.setBackground(getDrawable(R.drawable.one));
          break;
        case 2:
          this.playModeText.setText(R.string.play_mode_random);
          this.playModeIcon.setBackground(getDrawable(R.drawable.sj));
          break;
      }
    }
  }

  private class PlaylistItemClickListener implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      GlobalData.currentIndex = position;
      Intent intent = new Intent(getApplicationContext(), PlayerService.class);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent);
      } else {
        startService(intent);
      }

      if (playlistAdapter != null) {
        playlistAdapter.notifyDataSetChanged();
      }
    }
  }

  private class PlayModeButtonListener implements View.OnClickListener {

    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    @Override
    public void onClick(View v) {
      if (GlobalData.playMode == 0) {
        GlobalData.playMode = 1;
        ToastUtils.showToast(PlayerActivity.this, getString(R.string.play_mode_single));
        playModeIcon.setBackground(getDrawable(R.drawable.one));
      } else if (GlobalData.playMode == 1) {
        GlobalData.playMode = 2;
        ToastUtils.showToast(PlayerActivity.this, getString(R.string.play_mode_random));
        playModeIcon.setBackground(getDrawable(R.drawable.sj));
      } else {
        GlobalData.playMode = 0;
        ToastUtils.showToast(PlayerActivity.this, getString(R.string.play_mode_order));
        playModeIcon.setBackground(getDrawable(R.drawable.all));
      }
    }
  }

  private class AlbumArtToggleListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
      if (albumArtImageView.getVisibility() == View.VISIBLE) {
        albumArtImageView.setVisibility(View.GONE);
      } else {
        albumArtImageView.setVisibility(View.VISIBLE);
      }
    }
  }

  private class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
      if (mediaPlayer != null) {
        try {
          mediaPlayer.seekTo(seekBar.getProgress());
        } catch (IllegalStateException e) {
          Log.e(TAG, "MediaPlayer seek error", e);
        }
      }
    }
  }

  private class PlayPauseButtonListener implements View.OnClickListener {

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
      if (mediaPlayer != null && mediaPlayer.isPlaying()) {
        playPauseIcon.setBackgroundResource(R.drawable.play);
        sendPlayerControlBroadcast(CONTROL_PAUSE);
      } else {
        playPauseIcon.setBackgroundResource(R.drawable.stop2);
        sendPlayerControlBroadcast(CONTROL_PLAY);
      }
    }
  }

  private class UIHandler extends Handler {

    public UIHandler(Looper looper) {
      super(looper);
    }

    @Override
    public void handleMessage(Message msg) {
      if (msg.what == MSG_LOAD_LYRICS) {
        loadLyrics();
      } else if (msg.what == MSG_SHOW_TOAST) {
        if (msg.obj != null) {
          ToastUtils.showToast(getApplicationContext(), msg.obj.toString());
        }
      }
      super.handleMessage(msg);
    }

    private void loadLyrics() {
      try {
        String lrcContent = FileUtils.readFileUTF8(lyricFilePath);

        if (lrcContent != null && lrcContent.contains(DUAL_LYRICS_SEPARATOR)) {
          String[] parts = lrcContent.split(DUAL_LYRICS_SEPARATOR);
          if (parts.length == 2) {
            lrcView.loadLrc(parts[0], parts[1]);
          } else {
            lrcView.loadLrc(new File(lyricFilePath));
          }
        } else {
          lrcView.loadLrc(new File(lyricFilePath));
        }

        lrcView.setDraggable(true, new LrcSeekListener());

        uiHandler.post(uiUpdateRunnable);

      } catch (IOException e) {
        Log.e(TAG, "Failed to load lyrics", e);
        uiHandler.post(uiUpdateRunnable);
      }
    }
  }

  private class LrcSeekListener implements LrcView.OnPlayClickListener {

    @Override
    public boolean onPlayClick(long time) {
      if (mediaPlayer != null) {
        try {
          mediaPlayer.seekTo((int) time);
          return true;
        } catch (IllegalStateException e) {
          Log.e(TAG, "MediaPlayer seek error in lyrics", e);
          return false;
        }
      }
      return false;
    }
  }

  private class MusicBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if (action == null) {
        return;
      }

      switch (action) {
        case ACTION_UPDATE_VIEW:
          initUIAndListeners();
          break;
        case ACTION_UPDATE_VIEW2:
          loadAlbumArt();
          break;
        case ACTION_EXIT:
          finish();
          break;
      }
    }
  }
}