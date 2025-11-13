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
import android.view.WindowManager;
import android.widget.AdapterView;
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
import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.mp4.atom.Mp4NameBox;
import org.json.JSONArray;
import org.json.JSONObject;

public class QQPlayListActivity extends Activity {

  public static List<Map<String, Object>> playlistData;
  private final Handler handler = new MessageHandler(this, Looper.getMainLooper());
  private ListView listView;
  private Dialog loadingDialog;
  private SharedPreferences sharedPreferences;
  private SimpleAdapter adapter;

  public void dismissLoadingDialog() {
    sendHandlerMessage(3);
  }

  public void loginExpired() {
    sendHandlerMessage(6);
  }

  public void showLoadingDialog() {
    sendHandlerMessage(2);
  }

  public void loadUserPlaylists(String userId) {
    try {
      String createdJson = getUserCreatedPlaylistsJson(userId);
      JSONObject createdResponse = new JSONObject(createdJson);
      if (createdResponse.getString("code").equals("4000")) {
        loginExpired();
        return;
      }

      JSONArray createdList = createdResponse.getJSONObject("data").getJSONArray("disslist");
      for (int i = 0; i < createdList.length(); i++) {
        JSONObject playlist = createdList.getJSONObject(i);
        String id = playlist.getString("tid");
        String name = playlist.getString("diss_name");
        String count = playlist.getString("song_cnt");

        HashMap<String, Object> item = new HashMap<>();
        item.put("text", String.valueOf(playlistData.size() + 1));
        item.put(Mp4NameBox.IDENTIFIER, name);
        item.put("id", id);
        item.put("num", count);
        playlistData.add(item);
      }

      String likedJson = getUserLikedPlaylistsJson(userId);
      JSONObject likedResponse = new JSONObject(likedJson);
      JSONArray likedList = likedResponse.getJSONObject("data").getJSONArray("cdlist");
      for (int i = 0; i < likedList.length(); i++) {
        JSONObject playlist = likedList.getJSONObject(i);
        String id = playlist.getString("dissid");
        String name = playlist.getString("dissname");
        String count = playlist.getString("songnum");

        HashMap<String, Object> item = new HashMap<>();
        item.put("text", String.valueOf(playlistData.size() + 1));
        item.put(Mp4NameBox.IDENTIFIER, name);
        item.put("id", id);
        item.put("num", count);
        playlistData.add(item);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<Map<String, Object>> loadPlaylistSongs(String playlistId) {
    ArrayList<Map<String, Object>> result = new ArrayList<>();
    try {
      JSONObject response = new JSONObject(getPlaylistDetailJson(playlistId));
      if (response.getString("code").equals("4000")) {
        loginExpired();
        return null;
      }

      JSONArray tracks = response.getJSONObject("Songlist").getJSONObject("data")
          .getJSONArray("songlist");
      int index = 0;

      for (int i = 0; i < tracks.length(); i++) {
        index++;
        JSONObject track = tracks.getJSONObject(i);
        String album = track.getJSONObject("album").getString(Mp4NameBox.IDENTIFIER);
        String albumMid = track.getJSONObject("album").getString("mid");
        String actionAlert = track.getJSONObject("action").getString("alert");
        String title = track.getString("title").replace("/", " ");
        String songMid = track.getString("mid");
        String mvId = track.getJSONObject("mv").getString("vid");
        String duration = track.getString("interval");
        String mp3Size = track.getJSONObject("file").getString("size_128mp3");
        String hqSize = track.getJSONObject("file").getString("size_320mp3");
        String sqSize = track.getJSONObject("file").getString("size_flac");
        String hrSize = track.getJSONObject("file").getString("size_hires");

        JSONArray artists = track.getJSONArray("singer");
        StringBuffer artistNames = new StringBuffer();
        for (int j = 0; j < artists.length(); j++) {
          if (j > 0 && artists.length() > 1) {
            artistNames.append("、");
          }
          artistNames.append(artists.getJSONObject(j).getString(Mp4NameBox.IDENTIFIER));
        }
        String artist = artistNames.toString().replace("/", " ");

        HashMap<String, Object> item = new HashMap<>();
        String qualityType;

        if (!hrSize.equals("0")) {
          item.put("br", Integer.valueOf(R.drawable.hires));
          qualityType = "hr";
        } else if (!sqSize.equals("0")) {
          item.put("br", Integer.valueOf(R.drawable.sq));
          qualityType = "sq";
        } else if (!hqSize.equals("0")) {
          item.put("br", Integer.valueOf(R.drawable.hq));
          qualityType = "hq";
        } else {
          item.put("br", Integer.valueOf(R.drawable.mp3));
          qualityType = "mp3";
        }

        item.put("maxbr", qualityType);

        if (!actionAlert.equals("7")) {
          item.put("br", Integer.valueOf(R.drawable.pay));
        }

        item.put("id", songMid);
        item.put(Mp4NameBox.IDENTIFIER, title);
        item.put("singer", artist);
        item.put("album", album);
        item.put("time", CommonUtils.formatTime(Integer.parseInt(duration)));
        item.put("filename", artist + " - " + title);
        item.put("mvid", mvId);
        item.put("mp3size", mp3Size);
        item.put("hqsize", hqSize);
        item.put("sqsize", sqSize);
        item.put("albumid", albumMid);
        item.put("text", Integer.valueOf(index));
        result.add(item);
      }
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return result;
    }
  }

  private void initialize() {
    new LoadPlaylistThread(this).start();
    this.listView.setOnItemClickListener(new PlaylistItemClickListener(this));
    findViewById(R.id.listRelativeLayout1).setOnClickListener(new BackClickListener(this));
  }

  private void showToast(String message) {
    Message msg = new Message();
    msg.what = 0;
    msg.obj = message;
    this.handler.sendMessage(msg);
  }

  private String makeHttpRequest(String url) throws ProtocolException {
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setRequestMethod("GET");
      connection.setConnectTimeout(30000);
      connection.setReadTimeout(30000);
      connection.setRequestProperty("referer", "https://y.qq.com/portal/player.html");
      connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
      String cookie = "uin=" + this.sharedPreferences.getString("qquin", FrameBodyCOMM.DEFAULT) +
          ";qm_keyst=" + this.sharedPreferences.getString("qqkey", FrameBodyCOMM.DEFAULT);
      connection.setRequestProperty("Cookie", cookie);
      return new String(
          DownloadUtils.inputStreamToBytes(new BufferedInputStream(connection.getInputStream())),
          StandardCharsets.UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
      return FrameBodyCOMM.DEFAULT;
    }
  }

  private void sendHandlerMessage(int what) {
    Message message = new Message();
    message.what = what;
    this.handler.sendMessage(message);
  }

  public void showLoadingDialog2() {
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

  public void showMenu() {
    Dialog menuDialog = new DialogFactory().createDialog(this);
    View dialogView = LayoutInflater.from(this).inflate(R.layout.fgdialog, null);
    dialogView.findViewById(R.id.fgdialogRelativeLayout2).setVisibility(View.GONE);
    menuDialog.show();
    ((TextView) dialogView.findViewById(R.id.fgdialogTextView1)).setText("菜单");
    menuDialog.setContentView(dialogView);
    Display display = getWindowManager().getDefaultDisplay();
    WindowManager.LayoutParams params = menuDialog.getWindow().getAttributes();
    params.width = display.getWidth();
    menuDialog.getWindow().setAttributes(params);
    Button logoutButton = dialogView.findViewById(R.id.fgdialogButton1);
    logoutButton.setText("注销账号");
    logoutButton.setOnClickListener(new LogoutClickListener(this, menuDialog));
  }

  public String getPlaylistDetailJson(String playlistId) {
    try {
      String url =
          "http://u6.y.qq.com/cgi-bin/musicu.fcg?data=%7B%0A%20%20%22Songlist%22%20%3A%20%7B%0A%20%20%20%20%22module%22%20%3A%20%22music.srfDissInfo.DissInfo%22%2C%0A%20%20%20%20%22method%22%20%3A%20%22CgiGetDiss%22%2C%0A%20%20%20%20%22param%22%20%3A%20%7B%0A%20%20%20%20%20%20%22dirid%22%20%3A%200%2C%0A%20%20%20%20%20%20%22from%22%20%3A%2015%2C%0A%20%20%20%20%20%20%22ctx%22%20%3A%200%2C%0A%20%20%20%20%20%20%22onlysonglist%22%20%3A%200%2C%0A%20%20%20%20%20%20%22orderlist%22%20%3A%201%2C%0A%20%20%20%20%20%20%22tag%22%20%3A%201%2C%0A%20%20%20%20%20%20%22rec_flag%22%20%3A%201%2C%0A%20%20%20%20%20%20%22disstid%22%20%3A%20"
              +
              playlistId
              + "%2C%0A%20%20%20%20%20%20%22new_format%22%20%3A%201%2C%0A%20%20%20%20%20%20%22host_uin%22%20%3A%200%2C%0A%20%20%20%20%20%20%22optype%22%20%3A%202%2C%0A%20%20%20%20%20%20%22enc_host_uin%22%20%3A%20%220%22%0A%20%20%20%20%7D%0A%20%20%7D%2C%0A%22comm%22%20%3A%20%7B%0A%20%20%20%20%22ct%22%20%3A%20%221%22%2C%0A%20%20%20%20%22v%22%20%3A%20%2290%22%2C%0A%20%20%20%20%22cv%22%20%3A%20%22101805%22%2C%0A%20%20%20%20%22gzip%22%20%3A%20%220%22%0A%20%20%7D%0A%7D";
      return makeHttpRequest(url);
    } catch (Exception e) {
      return FrameBodyCOMM.DEFAULT;
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    setContentView(R.layout.list);
    this.listView = findViewById(R.id.listListView1);
    findViewById(R.id.listTextView1).setOnClickListener(new MenuClickListener(this));
    this.sharedPreferences = getSharedPreferences("pms", 0);
    initialize();
  }

  @Override
  protected void onDestroy() {
    dismissLoadingDialog();
    super.onDestroy();
  }

  @Override
  public void onPointerCaptureChanged(boolean hasCapture) {
  }

  public String getUserCreatedPlaylistsJson(String userId) {
    try {
      String url = "http://c.y.qq.com/rsc/fcgi-bin/fcg_user_created_diss?hostuin=" +
          userId + "&size=1000&format=json";
      String result = makeHttpRequest(url);
      if (!result.equals(FrameBodyCOMM.DEFAULT)) {
        JSONObject response = new JSONObject(result);
        if (response.getString("code").equals("4000")) {
          loginExpired();
        }
      }
      return result;
    } catch (Exception e) {
      return FrameBodyCOMM.DEFAULT;
    }
  }

  public String getUserLikedPlaylistsJson(String userId) {
    try {
      String url =
          "https://c.y.qq.com/fav/fcgi-bin/fcg_get_profile_order_asset.fcg?cv=4747474&ct=20&format=json&inCharset=utf-8&outCharset=utf-8&notice=0&platform=yqq.json&needNewCode=1&cid=205360956&userid="
              +
              userId + "&reqtype=3&sin=0&ein=1000";
      String result = makeHttpRequest(url);
      if (!new JSONObject(result).getString("code").equals("4000")) {
        return result;
      }
      loginExpired();
      return result;
    } catch (Exception e) {
      return FrameBodyCOMM.DEFAULT;
    }
  }

  class MenuClickListener implements View.OnClickListener {

    final QQPlayListActivity activity;

    MenuClickListener(QQPlayListActivity activity) {
      this.activity = activity;
    }

    @Override
    public void onClick(View view) {
      this.activity.showMenu();
    }
  }

  class LogoutClickListener implements View.OnClickListener {

    final Dialog dialog;
    final QQPlayListActivity activity;

    LogoutClickListener(QQPlayListActivity activity, Dialog dialog) {
      this.activity = activity;
      this.dialog = dialog;
    }

    @Override
    public void onClick(View view) {
      this.dialog.dismiss();
      this.activity.sharedPreferences.edit().putString("qquin", FrameBodyCOMM.DEFAULT).commit();
      this.activity.sharedPreferences.edit().putString("qqlike", FrameBodyCOMM.DEFAULT).commit();
      this.activity.showToast("已注销，请重新登录");
      this.activity.finish();
    }
  }

  class LoadPlaylistThread extends Thread {

    final QQPlayListActivity activity;

    LoadPlaylistThread(QQPlayListActivity activity) {
      this.activity = activity;
    }

    @Override
    public void run() {
      QQPlayListActivity.playlistData = new ArrayList<>();
      this.activity.showLoadingDialog();
      String userId = this.activity.sharedPreferences.getString("qquin", FrameBodyCOMM.DEFAULT);
      this.activity.loadUserPlaylists(userId);
      this.activity.dismissLoadingDialog();
    }
  }

  class PlaylistItemClickListener implements AdapterView.OnItemClickListener {

    final QQPlayListActivity activity;

    PlaylistItemClickListener(QQPlayListActivity activity) {
      this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
      if (QQPlayListActivity.playlistData.get(position).get("num").equals("0")) {
        this.activity.showToast("此歌单一首歌都木有～");
      } else {
        new LoadSongsThread(this, position).start();
      }
    }

    class LoadSongsThread extends Thread {

      final int position;
      final PlaylistItemClickListener listener;

      LoadSongsThread(PlaylistItemClickListener listener, int position) {
        this.listener = listener;
        this.position = position;
      }

      @Override
      public void run() {
        listener.activity.showLoadingDialog();
        String playlistId =
            QQPlayListActivity.playlistData.get(position).get("id") + FrameBodyCOMM.DEFAULT;
        QQPlayListActivity.playlistData = listener.activity.loadPlaylistSongs(playlistId);
        listener.activity.dismissLoadingDialog();
        if (QQPlayListActivity.playlistData != null) {
          Intent intent = new Intent(listener.activity.getApplicationContext(),
              SongListActivity.class);
          intent.putExtra("sta", "qq");
          listener.activity.startActivity(intent);
        }
      }
    }
  }

  class BackClickListener implements View.OnClickListener {

    final QQPlayListActivity activity;

    BackClickListener(QQPlayListActivity activity) {
      this.activity = activity;
    }

    @Override
    public void onClick(View view) {
      this.activity.finish();
    }
  }

  class MessageHandler extends Handler {

    final QQPlayListActivity activity;

    MessageHandler(QQPlayListActivity activity, Looper looper) {
      super(looper);
      this.activity = activity;
    }

    @Override
    public void handleMessage(Message message) {
      int what = message.what;
      if (what == 0) {
        ToastUtils.showToast(this.activity, message.obj + "");
      } else if (what == 2) {
        this.activity.showLoadingDialog2();
      } else if (what == 3) {
        this.activity.dismissLoadingDialog();
        String[] from = new String[]{"text", "name", "num"};
        int[] to = new int[]{R.id.listitemTextView1, R.id.listitemTextView2,
            R.id.listitemTextView3};
        SimpleAdapter adapter = new SimpleAdapter(this.activity, QQPlayListActivity.playlistData,
            R.layout.listitem, from, to);
        this.activity.listView.setAdapter(adapter);
      } else if (what == 5) {
        this.activity.dismissLoadingDialog();
        ToastUtils.showToast(this.activity, message.obj + "");
        this.activity.sharedPreferences.edit().putString("qquin", FrameBodyCOMM.DEFAULT).commit();
        this.activity.finish();
      } else if (what == 6) {
        this.activity.dismissLoadingDialog();
        ToastUtils.showToast(this.activity, "登录身份认证已过期，请重新登录。");
        this.activity.finish();
      }
    }
  }
}