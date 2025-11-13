package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mylrc.mymusic.R;
import com.mylrc.mymusic.enums.StatusBarColor;
import com.mylrc.mymusic.manager.StatusBarManager;
import com.mylrc.mymusic.network.DownloadUtils;
import com.mylrc.mymusic.ui.dialog.DialogFactory;
import com.mylrc.mymusic.utils.CommonUtils;
import com.mylrc.mymusic.utils.ToastUtils;

import org.jaudiotagger.tag.mp4.atom.Mp4NameBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网易云音乐歌单Activity
 * 功能:
 * 1. 展示用户歌单列表
 * 2. 每日推荐歌曲(第一个歌单)
 * 3. 加载歌单详情并跳转到歌曲列表
 */
public class WYYPlayListActivity extends Activity {

  // Handler消息类型常量
  private static final int MSG_SHOW_TOAST = 0;
  private static final int MSG_SHOW_LOADING = 2;
  private static final int MSG_UPDATE_LIST = 3;
  private static final int MSG_TOKEN_EXPIRED = 5;

  // 歌单数据(static供SongListActivity访问)
  public static List<Map<String, Object>> playlistData;

  // UI组件
  private ListView listView;
  private Dialog loadingDialog;

  // 数据
  private SharedPreferences sharedPreferences;
  private String wyyToken;
  private final Handler handler = new MessageHandler(this, Looper.getMainLooper());

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    setContentView(R.layout.list);

    initViews();
    initData();
    loadPlaylistData();
  }

  /**
   * 初始化视图和监听器
   */
  private void initViews() {
    listView = findViewById(R.id.listListView1);

    // 菜单按钮
    findViewById(R.id.listTextView1).setOnClickListener(v -> showMenu());

    // 返回按钮
    findViewById(R.id.listRelativeLayout1).setOnClickListener(v -> finish());

    // 歌单点击监听
    listView.setOnItemClickListener((parent, view, position, id) -> {
      if ("0".equals(String.valueOf(playlistData.get(position).get("num")))) {
        showToast("此歌单一首歌都木有～");
      } else {
        loadPlaylistSongs(position);
      }
    });
  }

  /**
   * 初始化数据
   */
  private void initData() {
    sharedPreferences = getSharedPreferences("pms", MODE_PRIVATE);
    wyyToken = sharedPreferences.getString("wyytoken", "");
  }

  /**
   * 加载用户歌单列表
   */
  private void loadPlaylistData() {
    sendHandlerMessage(MSG_SHOW_LOADING);

    new Thread(() -> {
      String userId = sharedPreferences.getString("wyyuid", "");
      loadUserPlaylists(userId);
      sendHandlerMessage(MSG_UPDATE_LIST);
    }).start();
  }

  /**
   * 加载歌单歌曲并跳转
   */
  private void loadPlaylistSongs(int position) {
    sendHandlerMessage(MSG_SHOW_LOADING);

    new Thread(() -> {
      try {
        if (position == 0) {
          // 第一个歌单是每日推荐
          loadRecommendPlaylist();
        } else {
          // 其他歌单
          loadNormalPlaylist(position);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
  }

  /**
   * 加载每日推荐
   */
  private void loadRecommendPlaylist() throws JSONException {
    String url = "http://music.163.com/api/v1/discovery/recommend/songs?total=true";
    String response = DownloadUtils.getWithCookie(url, wyyToken);
    playlistData = loadRecommendSongs(response);

    sendHandlerMessage(MSG_UPDATE_LIST);

    if (playlistData != null && !playlistData.isEmpty()) {
      navigateToSongList();
    }
  }

  /**
   * 加载普通歌单
   */
  private void loadNormalPlaylist(int position) {
    String playlistId = String.valueOf(playlistData.get(position).get("id"));
    playlistData = loadPlaylistSongs(playlistId);

    sendHandlerMessage(MSG_UPDATE_LIST);

    if (playlistData != null && !playlistData.isEmpty()) {
      navigateToSongList();
    }
  }

  /**
   * 跳转到歌曲列表页面
   */
  private void navigateToSongList() {
    Intent intent = new Intent(getApplicationContext(), SongListActivity.class);
    intent.putExtra("sta", "wyy");
    startActivity(intent);
  }

  /**
   * 显示菜单对话框
   */
  public void showMenu() {
    Dialog menuDialog = new DialogFactory().createDialog(this);
    View dialogView = LayoutInflater.from(this).inflate(R.layout.fgdialog, null);

    // 设置标题
    ((TextView) dialogView.findViewById(R.id.fgdialogTextView1)).setText("菜单");
    menuDialog.setContentView(dialogView);

    // 设置对话框宽度为屏幕宽度
    Display display = getWindowManager().getDefaultDisplay();
    WindowManager.LayoutParams params = menuDialog.getWindow().getAttributes();
    params.width = display.getWidth();
    menuDialog.getWindow().setAttributes(params);

    // 注销按钮
    Button logoutButton = dialogView.findViewById(R.id.fgdialogButton1);
    logoutButton.setText("注销账号");
    dialogView.findViewById(R.id.fgdialogRelativeLayout2).setVisibility(View.GONE);

    logoutButton.setOnClickListener(v -> {
      menuDialog.dismiss();
      sharedPreferences.edit().putString("wyyuid", "").apply();
      showToast("已注销，请重新登录");
      finish();
    });

    menuDialog.show();
  }

  /**
   * 显示加载对话框
   */
  public void showLoadingDialog() {
    if (loadingDialog == null) {
      loadingDialog = new Dialog(this);
      loadingDialog.requestWindowFeature(1);
      loadingDialog.getWindow().setWindowAnimations(R.style.loadingAnim);
      loadingDialog.setContentView(R.layout.loading);
      loadingDialog.setCancelable(false);
    }
    if (!loadingDialog.isShowing() && !isFinishing()) {
      loadingDialog.show();
    }
  }

  /**
   * 关闭加载对话框
   */
  public void dismissLoadingDialog() {
    if (loadingDialog != null && loadingDialog.isShowing()) {
      loadingDialog.dismiss();
    }
  }

  /**
   * 显示Toast消息
   */
  private void showToast(String message) {
    Message msg = Message.obtain();
    msg.what = MSG_SHOW_TOAST;
    msg.obj = message;
    handler.sendMessage(msg);
  }

  /**
   * 发送Handler消息
   */
  private void sendHandlerMessage(int what) {
    handler.sendEmptyMessage(what);
  }

  /**
   * 获取用户歌单列表
   * API: /api/user/playlist/
   */
  public void loadUserPlaylists(String userId) {
    List<Map<String, Object>> result = new ArrayList<>();
    try {
      String response = getUserPlaylistsJson(userId);
      JSONObject jsonObject = new JSONObject(response);

      // 检查登录状态
      if ("301".equals(jsonObject.getString("code"))) {
        sendTokenExpiredMessage("登录身份认证已过期，请重新登录。");
        return;
      }

      JSONArray playlists = jsonObject.getJSONArray("playlist");
      for (int i = 0; i < playlists.length(); i++) {
        JSONObject playlist = playlists.getJSONObject(i);

        Map<String, Object> item = new HashMap<>();
        item.put("text", i + 1);
        item.put(Mp4NameBox.IDENTIFIER, playlist.getString(Mp4NameBox.IDENTIFIER));
        item.put("num", playlist.getString("trackCount"));
        item.put("id", playlist.getString("id"));
        item.put("img", playlist.getString("coverImgUrl"));
        result.add(item);
      }

      playlistData = result;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取歌单详情(歌曲列表)
   * API: /api/v6/playlist/detail
   */
  public List<Map<String, Object>> loadPlaylistSongs(String playlistId) {
    List<Map<String, Object>> result = new ArrayList<>();
    try {
      String response = getPlaylistDetailJson(playlistId);
      JSONObject jsonObject = new JSONObject(response);

      // 检查登录状态
      if ("301".equals(jsonObject.getString("code"))) {
        sendTokenExpiredMessage("登录身份认证已过期，请重新登录。");
        return null;
      }

      JSONArray tracks = jsonObject.getJSONObject("playlist").getJSONArray("tracks");
      int index = 0;

      for (int i = 0; i < tracks.length(); i++) {
        JSONObject track = tracks.getJSONObject(i);

        // 过滤无sq字段或sq为空的歌曲
        if (track.isNull("sq") || track.getString("sq").isEmpty()) {
          continue;
        }

        index++;
        result.add(parseTrackToMap(track, index));
      }

      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return result;
    }
  }

  /**
   * 获取每日推荐歌曲
   * API: /api/v1/discovery/recommend/songs
   */
  public List<Map<String, Object>> loadRecommendSongs(String jsonResponse) throws JSONException {
    List<Map<String, Object>> result = new ArrayList<>();
    try {
      JSONObject response = new JSONObject(jsonResponse);

      // 检查登录状态
      if ("301".equals(response.getString("code"))) {
        sendTokenExpiredMessage("登录身份认证已过期，请重新登录。");
        return null;
      }

      JSONArray songs = response.getJSONArray("recommend");
      int index = 0;

      for (int i = 0; i < songs.length(); i++) {
        index++;
        JSONObject song = songs.getJSONObject(i);
        result.add(parseRecommendSongToMap(song, index));
      }

      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return result;
    }
  }

  /**
   * 解析歌单歌曲为Map
   */
  private Map<String, Object> parseTrackToMap(JSONObject track, int index) throws JSONException {
    Map<String, Object> item = new HashMap<>();

    // 基本信息
    String songId = track.getString("id");
    String title = track.getString(Mp4NameBox.IDENTIFIER).replace("/", " ");
    String duration = track.getString("dt");
    String mvId = track.getString("mv");
    String fee = track.getString("fee");
    String status = track.getString("st");

    // 音质信息
    String quality = track.getJSONObject("privilege").getString("maxBrLevel");
    String qualityType = getQualityType(quality);
    int qualityIcon = getQualityIcon(quality, status, fee);

    // 专辑和艺术家
    String album = track.getJSONObject("al").getString(Mp4NameBox.IDENTIFIER);
    String artist = parseArtists(track.getJSONArray("ar"));

    // MV处理
    mvId = "0".equals(mvId) ? "" : mvId;

    // 填充数据
    item.put("text", index);
    item.put("id", songId);
    item.put(Mp4NameBox.IDENTIFIER, title);
    item.put("singer", artist);
    item.put("album", album);
    item.put("time", CommonUtils.formatTime(Integer.parseInt(duration) / 1000));
    item.put("filename", title + " - " + artist);
    item.put("mvid", mvId);
    item.put("maxbr", qualityType);
    item.put("br", qualityIcon);
    item.put("pay", fee);
    item.put("cy", status);

    return item;
  }

  /**
   * 解析每日推荐歌曲为Map
   */
  private Map<String, Object> parseRecommendSongToMap(JSONObject song, int index) throws JSONException {
    Map<String, Object> item = new HashMap<>();

    // 基本信息
    String songId = song.getString("id");
    String title = song.getString(Mp4NameBox.IDENTIFIER).replace("/", " ");
    String duration = song.getString("duration");
    String mvId = song.getString("mvid");
    String originCoverType = song.getString("originCoverType");

    // 权限信息
    JSONObject privilege = song.getJSONObject("privilege");
    String quality = privilege.getString("maxBrLevel");
    String fee = privilege.getString("fee");
    String status = privilege.getString("st");

    // 音质信息
    String qualityType = getQualityType(quality);
    int qualityIcon = getQualityIcon(quality, status, fee);

    // 专辑和艺术家
    String album = song.getJSONObject("album").getString(Mp4NameBox.IDENTIFIER);
    String artist = parseArtists(song.getJSONArray("artists"));

    // 文件大小
    String mp3Size = extractFileSize(song, "l");
    String hqSize = extractFileSize(song, "h");

    // MV处理
    if (!"0".equals(mvId)) {
      item.put("mv", R.drawable.mv);
    } else {
      mvId = "";
    }

    // 原唱标识
    if ("1".equals(originCoverType)) {
      item.put("yz", R.drawable.yz);
    }

    // 填充数据
    item.put("text", index);
    item.put("id", songId);
    item.put(Mp4NameBox.IDENTIFIER, title);
    item.put("singer", artist);
    item.put("album", album);
    item.put("time", CommonUtils.formatTime(Integer.parseInt(duration) / 1000));
    item.put("filename", artist + " - " + title);
    item.put("mvid", mvId);
    item.put("maxbr", qualityType);
    item.put("br", qualityIcon);
    item.put("pay", fee);
    item.put("cy", status);
    item.put("mp3size", mp3Size);
    item.put("hqsize", hqSize);

    return item;
  }

  /**
   * 解析艺术家列表(最多5个)
   */
  private String parseArtists(JSONArray artists) throws JSONException {
    StringBuilder artistNames = new StringBuilder();
    int limit = Math.min(artists.length(), 5);

    for (int i = 0; i < limit; i++) {
      if (i > 0) {
        artistNames.append("、");
      }
      artistNames.append(artists.getJSONObject(i).getString(Mp4NameBox.IDENTIFIER));
    }

    return artistNames.toString().replace("/", " ");
  }

  /**
   * 提取文件大小
   */
  private String extractFileSize(JSONObject song, String key) {
    try {
      if (!song.isNull(key) && song.getJSONObject(key).has("size")) {
        return song.getJSONObject(key).getString("size");
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return "0";
  }

  /**
   * 获取音质类型标识
   */
  private String getQualityType(String quality) {
    switch (quality) {
      case "hires":
        return "hr";
      case "lossless":
        return "sq";
      case "exhigh":
        return "hq";
      default:
        return "mp3";
    }
  }

  /**
   * 获取音质图标(优先级: 无版权 > VIP > 音质)
   */
  private int getQualityIcon(String quality, String status, String fee) {
    if ("-200".equals(status)) {
      return R.drawable.nohave;
    }
    if ("4".equals(fee)) {
      return R.drawable.pay;
    }

    switch (quality) {
      case "hires":
        return R.drawable.hires;
      case "lossless":
        return R.drawable.sq;
      case "exhigh":
        return R.drawable.hq;
      default:
        return R.drawable.mp3;
    }
  }

  /**
   * 发送Token过期消息
   */
  private void sendTokenExpiredMessage(String message) {
    Message msg = Message.obtain();
    msg.what = MSG_TOKEN_EXPIRED;
    msg.obj = message;
    handler.sendMessage(msg);
  }

  /**
   * 获取用户歌单列表JSON
   */
  public String getUserPlaylistsJson(String userId) {
    try {
      String url = "http://music.163.com/api/user/playlist/?offset=0&limit=1000&uid=" + userId;
      return DownloadUtils.getWithCookie(url, wyyToken);
    } catch (Exception e) {
      return "";
    }
  }

  /**
   * 获取歌单详情JSON
   */
  public String getPlaylistDetailJson(String playlistId) {
    try {
      String url = "http://music.163.com/api/v6/playlist/detail?id=" + playlistId +
          "&offset=0&total=true&limit=100000&n=100000";
      return DownloadUtils.getWithCookie(url, wyyToken);
    } catch (Exception e) {
      return "";
    }
  }

  @Override
  protected void onDestroy() {
    dismissLoadingDialog();
    super.onDestroy();
  }

  @Override
  public void onPointerCaptureChanged(boolean hasCapture) {
    // 空实现
  }

  /**
   * 消息处理Handler(使用静态内部类避免内存泄漏)
   */
  private static class MessageHandler extends Handler {
    private final WYYPlayListActivity activity;

    MessageHandler(WYYPlayListActivity activity, Looper looper) {
      super(looper);
      this.activity = activity;
    }

    @Override
    public void handleMessage(Message message) {
      switch (message.what) {
        case MSG_SHOW_TOAST:
          ToastUtils.showToast(activity, String.valueOf(message.obj));
          break;

        case MSG_SHOW_LOADING:
          activity.showLoadingDialog();
          break;

        case MSG_UPDATE_LIST:
          activity.dismissLoadingDialog();
          updatePlaylistView();
          break;

        case MSG_TOKEN_EXPIRED:
          activity.dismissLoadingDialog();
          ToastUtils.showToast(activity, String.valueOf(message.obj));
          activity.sharedPreferences.edit().putString("wyyuid", "").apply();
          activity.finish();
          break;
      }
    }

    /**
     * 更新歌单列表视图
     */
    private void updatePlaylistView() {
      String[] from = {Mp4NameBox.IDENTIFIER, "text", "num"};
      int[] to = {R.id.listitemTextView1, R.id.listitemTextView3, R.id.listitemTextView2};

      PlaylistAdapter adapter = new PlaylistAdapter(
          activity,
          playlistData,
          R.layout.list,
          from,
          to
      );

      activity.listView.setAdapter(adapter);
    }

    /**
     * 歌单列表适配器
     */
    private static class PlaylistAdapter extends SimpleAdapter {
      private final WYYPlayListActivity activity;

      PlaylistAdapter(WYYPlayListActivity activity, List<? extends Map<String, ?>> data,
          int resource, String[] from, int[] to) {
        super(activity, data, resource, from, to);
        this.activity = activity;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(activity).inflate(R.layout.listitem, null);
        TextView countText = view.findViewById(R.id.listitemTextView2);

        try {
          String numStr = String.valueOf(playlistData.get(position).get("num"));
          double count = Double.parseDouble(numStr);

          // 歌曲数量大于等于1万时显示"万"单位
          if (count >= 10000) {
            String formatted = new DecimalFormat("0.00").format(count / 10000.0) + "万";
            countText.setText(formatted);
          } else {
            countText.setText(numStr);
          }
        } catch (NumberFormatException e) {
          countText.setText(String.valueOf(playlistData.get(position).get("num")));
        }

        return view;
      }
    }
  }
}