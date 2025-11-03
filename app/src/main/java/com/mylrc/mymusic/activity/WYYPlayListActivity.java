package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.mp4.atom.Mp4NameBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WYYPlayListActivity extends Activity {

  public static List<Map<String, Object>> playlistData;
  private final Handler handler = new MessageHandler(this, Looper.getMainLooper());
  private ListView listView;
  private Dialog loadingDialog;
  private SharedPreferences sharedPreferences;
  private String wyyToken;

  public void dismissLoadingDialog() {
    Dialog dialog = this.loadingDialog;
    if (dialog != null && dialog.isShowing()) {
      this.loadingDialog.dismiss();
    }
  }

  public void loadUserPlaylists(String userId) {
    ArrayList<Map<String, Object>> result = new ArrayList<>();
    try {
      JSONObject response = new JSONObject(getUserPlaylistsJson(userId));
      if (response.getString("code").equals("301")) {
        Message message = new Message();
        message.what = 5;
        message.obj = "登录身份认证已过期，请重新登录。";
        this.handler.sendMessage(message);
        return;
      }
      JSONArray playlists = response.getJSONArray("playlist");
      for (int i = 0; i < playlists.length(); i++) {
        JSONObject playlist = playlists.getJSONObject(i);
        String name = playlist.getString(Mp4NameBox.IDENTIFIER);
        String trackCount = playlist.getString("trackCount");
        String id = playlist.getString("id");
        String coverUrl = playlist.getString("coverImgUrl");

        HashMap<String, Object> item = new HashMap<>();
        item.put("text", Integer.valueOf(i + 1));
        item.put(Mp4NameBox.IDENTIFIER, name);
        item.put("num", trackCount);
        item.put("id", id);
        item.put("img", coverUrl);
        result.add(item);
      }
      playlistData = result;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<Map<String, Object>> loadPlaylistSongs(String playlistId) {
    ArrayList<Map<String, Object>> result = new ArrayList<>();
    try {
      JSONObject response = new JSONObject(getPlaylistDetailJson(playlistId));
      if (response.getString("code").equals("301")) {
        Message message = new Message();
        message.what = 5;
        message.obj = "登录身份认证已过期，请重新登录。";
        this.handler.sendMessage(message);
        return null;
      }
      JSONArray tracks = response.getJSONObject("playlist").getJSONArray("tracks");
      int index = 0;
      for (int i = 0; i < tracks.length(); i++) {
        JSONObject track = tracks.getJSONObject(i);
        if (!track.isNull("sq") && !track.getString("sq").equals("")) {
          String songId = track.getString("id");
          String fee = track.getString("fee");
          String status = track.getString("st");
          String title = track.getString(Mp4NameBox.IDENTIFIER).replace("/", " ");
          String mvId = track.getString("mv");
          String duration = track.getString("dt");
          String quality = track.getJSONObject("privilege").getString("maxBrLevel");
          String album = track.getJSONObject("al").getString(Mp4NameBox.IDENTIFIER);

          JSONArray artists = track.getJSONArray("ar");
          StringBuffer artistNames = new StringBuffer();
          for (int j = 0; j < artists.length(); j++) {
            if (j > 0 && artists.length() > 1) {
              artistNames.append("、");
            }
            artistNames.append(artists.getJSONObject(j).getString(Mp4NameBox.IDENTIFIER));
            if (j == 4) {
              break;
            }
          }
          String artist = artistNames.toString().replace("/", " ");

          String qualityType = "mp3";
          Object qualityIcon = "mp3";
          if (quality.equals("hires")) {
            qualityIcon = Integer.valueOf(R.drawable.hires);
            qualityType = "hr";
          } else if (quality.equals("lossless")) {
            qualityIcon = Integer.valueOf(R.drawable.sq);
            qualityType = "sq";
          } else if (quality.equals("exhigh")) {
            qualityIcon = Integer.valueOf(R.drawable.hq);
            qualityType = "hq";
          }

          if (status.equals("-200")) {
            qualityIcon = Integer.valueOf(R.drawable.nohave);
          }
          if (fee.equals("4")) {
            qualityIcon = Integer.valueOf(R.drawable.pay);
          }
          if (mvId.equals("0")) {
            mvId = FrameBodyCOMM.DEFAULT;
          }

          index++;
          HashMap<String, Object> item = new HashMap<>();
          item.put("filename", title + " - " + artist);
          item.put(Mp4NameBox.IDENTIFIER, title);
          item.put("singer", artist);
          item.put("maxbr", qualityType);
          item.put("br", qualityIcon);
          item.put("album", album);
          item.put("time", CommonUtils.formatTime(Integer.parseInt(duration) / 1000));
          item.put("mvid", mvId);
          item.put("id", songId);
          item.put("pay", fee);
          item.put("cy", status);
          item.put("text", Integer.valueOf(index));
          result.add(item);
        }
      }
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return result;
    }
  }

  private void initialize() {
    this.wyyToken = this.sharedPreferences.getString("wyytoken", FrameBodyCOMM.DEFAULT);
    sendHandlerMessage(2);
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

  private void sendHandlerMessage(int what) {
    Message message = new Message();
    message.what = what;
    this.handler.sendMessage(message);
  }

  public void showLoadingDialog() {
    if (this.loadingDialog == null) {
      Dialog dialog = new Dialog(this);
      this.loadingDialog = dialog;
      dialog.requestWindowFeature(1);
      this.loadingDialog.getWindow().setWindowAnimations(R.style.LoadingAnim);
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
    menuDialog.show();
    View dialogView = LayoutInflater.from(this).inflate(R.layout.fgdialog, null);
    ((TextView) dialogView.findViewById(R.id.fgdialogTextView1)).setText("菜单");
    menuDialog.setContentView(dialogView);
    Display display = getWindowManager().getDefaultDisplay();
    WindowManager.LayoutParams params = menuDialog.getWindow().getAttributes();
    params.width = display.getWidth();
    menuDialog.getWindow().setAttributes(params);
    Button logoutButton = dialogView.findViewById(R.id.fgdialogButton1);
    logoutButton.setText("注销账号");
    dialogView.findViewById(R.id.fgdialogRelativeLayout2).setVisibility(View.GONE);
    logoutButton.setOnClickListener(new LogoutClickListener(this, menuDialog));
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

  public String getPlaylistDetailJson(String playlistId) {
    try {
      String url = "http://music.163.com/api/v6/playlist/detail?id=" + playlistId +
          "&offset=0&total=true&limit=100000&n=100000";
      return DownloadUtils.getWithCookie(url, this.wyyToken);
    } catch (Exception e) {
      return FrameBodyCOMM.DEFAULT;
    }
  }

  public String getUserPlaylistsJson(String userId) {
    try {
      String url = "http://music.163.com/api/user/playlist/?offset=0&limit=1000&uid=" + userId;
      return DownloadUtils.getWithCookie(url, this.wyyToken);
    } catch (Exception e) {
      return FrameBodyCOMM.DEFAULT;
    }
  }

  public List<Map<String, Object>> loadRecommendSongs(String jsonResponse) throws JSONException {
    JSONObject response;
    ArrayList<Map<String, Object>> result = new ArrayList<>();
    try {
      response = new JSONObject(jsonResponse);
    } catch (Exception e) {
      return result;
    }

    if (response.getString("code").equals("301")) {
      try {
        Message message = new Message();
        message.what = 5;
        message.obj = "登录身份认证已过期，请重新登录。";
        this.handler.sendMessage(message);
      } catch (Exception ignored) {
      }
      return null;
    }

    JSONArray songs = response.getJSONArray("recommend");
    String mp3Size = "0";
    String hqSize = "0";
    int index = 0;

    for (int i = 0; i < songs.length(); i++) {
      index++;
      HashMap<String, Object> item = new HashMap<>();
      JSONObject song = songs.getJSONObject(i);
      String songId = song.getString("id");
      String title = song.getString(Mp4NameBox.IDENTIFIER).replace("/", " ");
      String mvId = song.getString("mvid");
      String duration = song.getString("duration");
      String originCoverType = song.getString("originCoverType");
      String quality = song.getJSONObject("privilege").getString("maxBrLevel");
      String album = song.getJSONObject("album").getString(Mp4NameBox.IDENTIFIER);
      String fee = song.getJSONObject("privilege").getString("fee");
      String status = song.getJSONObject("privilege").getString("st");

      if (!song.isNull("l") && song.getJSONObject("l").has("size")) {
        mp3Size = song.getJSONObject("l").getString("size");
      }
      if (!song.isNull("h") && song.getJSONObject("h").has("size")) {
        hqSize = song.getJSONObject("h").getString("size");
      }

      JSONArray artists = song.getJSONArray("artists");
      StringBuffer artistNames = new StringBuffer();
      for (int j = 0; j < artists.length(); j++) {
        if (j > 0 && artists.length() > 1) {
          artistNames.append("、");
        }
        artistNames.append(artists.getJSONObject(j).getString(Mp4NameBox.IDENTIFIER));
        if (j == 4) {
          break;
        }
      }
      String artist = artistNames.toString().replace("/", " ");

      item.put(Mp4NameBox.IDENTIFIER, title);
      item.put("singer", artist);
      item.put("time", CommonUtils.formatTime(Integer.parseInt(duration) / 1000));

      String qualityType;
      if (quality.equals("hires")) {
        item.put("br", Integer.valueOf(R.drawable.hires));
        qualityType = "hr";
      } else if (quality.equals("lossless")) {
        item.put("br", Integer.valueOf(R.drawable.sq));
        qualityType = "sq";
      } else if (quality.equals("exhigh")) {
        item.put("br", Integer.valueOf(R.drawable.hq));
        qualityType = "hq";
      } else {
        item.put("br", Integer.valueOf(R.drawable.mp3));
        qualityType = "mp3";
      }
      item.put("maxbr", qualityType);

      if (status.equals("-200")) {
        item.put("br", Integer.valueOf(R.drawable.nohave));
      }
      if (fee.equals("4")) {
        item.put("br", Integer.valueOf(R.drawable.pay));
      }
      if (mvId.equals("0")) {
        mvId = FrameBodyCOMM.DEFAULT;
      } else {
        item.put("mv", Integer.valueOf(R.drawable.mv));
      }
      if (originCoverType.equals("1")) {
        item.put("yz", Integer.valueOf(R.drawable.yz));
      }

      item.put("id", songId);
      item.put("mvid", mvId);
      item.put("filename", artist + " - " + title);
      item.put("cy", status);
      item.put("album", album);
      item.put("pay", fee);
      item.put("mp3size", mp3Size);
      item.put("hqsize", hqSize);
      item.put("text", Integer.valueOf(index));
      result.add(item);
    }
    return result;
  }

  class MenuClickListener implements View.OnClickListener {

    final WYYPlayListActivity activity;

    MenuClickListener(WYYPlayListActivity activity) {
      this.activity = activity;
    }

    @Override
    public void onClick(View view) {
      this.activity.showMenu();
    }
  }

  class LoadPlaylistThread extends Thread {

    final WYYPlayListActivity activity;

    LoadPlaylistThread(WYYPlayListActivity activity) {
      this.activity = activity;
    }

    @Override
    public void run() {
      try {
        String userId = activity.sharedPreferences.getString("wyyuid", FrameBodyCOMM.DEFAULT);
        activity.loadUserPlaylists(userId);
        activity.sendHandlerMessage(3);
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }
  }

  class PlaylistItemClickListener implements AdapterView.OnItemClickListener {

    final WYYPlayListActivity activity;

    PlaylistItemClickListener(WYYPlayListActivity activity) {
      this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
      if (WYYPlayListActivity.playlistData.get(position).get("num").equals("0")) {
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
        listener.activity.sendHandlerMessage(2);
        if (position != 0) {
          WYYPlayListActivity.playlistData = listener.activity.loadPlaylistSongs(
              WYYPlayListActivity.playlistData.get(position).get("id") + FrameBodyCOMM.DEFAULT
          );
          listener.activity.sendHandlerMessage(3);
          List<Map<String, Object>> list = WYYPlayListActivity.playlistData;
          if (list == null || list.size() == 0) {
            return;
          }
          Intent intent = new Intent(listener.activity.getApplicationContext(),
              SongListActivity.class);
          intent.putExtra("sta", "wyy");
          listener.activity.startActivity(intent);
          return;
        }
        try {
          new JSONObject().put("wyytoken", listener.activity.wyyToken);
          String url = "http://music.163.com/api/v1/discovery/recommend/songs?total=true";
          WYYPlayListActivity.playlistData = listener.activity.loadRecommendSongs(
              DownloadUtils.getWithCookie(url, listener.activity.wyyToken)
          );
          listener.activity.sendHandlerMessage(3);
          List<Map<String, Object>> list2 = WYYPlayListActivity.playlistData;
          if (list2 == null || list2.size() == 0) {
            return;
          }
          Intent intent2 = new Intent(listener.activity.getApplicationContext(),
              SongListActivity.class);
          intent2.putExtra("sta", "wyy");
          listener.activity.startActivity(intent2);
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    }
  }

  class BackClickListener implements View.OnClickListener {

    final WYYPlayListActivity activity;

    BackClickListener(WYYPlayListActivity activity) {
      this.activity = activity;
    }

    @Override
    public void onClick(View view) {
      this.activity.finish();
    }
  }

  class LogoutClickListener implements View.OnClickListener {

    final Dialog dialog;
    final WYYPlayListActivity activity;

    LogoutClickListener(WYYPlayListActivity activity, Dialog dialog) {
      this.activity = activity;
      this.dialog = dialog;
    }

    @Override
    public void onClick(View view) {
      this.dialog.dismiss();
      this.activity.sharedPreferences.edit().putString("wyyuid", FrameBodyCOMM.DEFAULT).commit();
      this.activity.showToast("已注销，请重新登录");
      this.activity.finish();
    }
  }

  class MessageHandler extends Handler {

    final WYYPlayListActivity activity;

    MessageHandler(WYYPlayListActivity activity, Looper looper) {
      super(looper);
      this.activity = activity;
    }

    @Override
    public void handleMessage(Message message) {
      int what = message.what;
      if (what == 0) {
        ToastUtils.showToast(this.activity, message.obj + "");
      } else if (what == 2) {
        this.activity.showLoadingDialog();
      } else if (what == 3) {
        this.activity.dismissLoadingDialog();
        String[] from = new String[]{Mp4NameBox.IDENTIFIER, "text", "num"};
        int[] to = new int[]{R.id.listitemTextView1, R.id.listitemTextView3,
            R.id.listitemTextView2};
        PlaylistAdapter adapter = new PlaylistAdapter(this, this.activity,
            WYYPlayListActivity.playlistData, R.layout.list, from, to);
        this.activity.listView.setAdapter(adapter);
      } else if (what == 5) {
        this.activity.dismissLoadingDialog();
        ToastUtils.showToast(this.activity, message.obj + "");
        this.activity.sharedPreferences.edit().putString("wyyuid", FrameBodyCOMM.DEFAULT).commit();
        this.activity.finish();
      }
    }

    class PlaylistAdapter extends SimpleAdapter {

      final MessageHandler handler;

      PlaylistAdapter(MessageHandler handler, Context context, List<? extends Map<String, ?>> data,
          int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.handler = handler;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView countText = view.findViewById(R.id.listitemTextView2);
        try {
          String numStr = WYYPlayListActivity.playlistData.get(position).get("num").toString();
          double count = Double.parseDouble(numStr);
          countText.setText(new DecimalFormat("0.00").format(count / 10000.0d) + "万");
        } catch (NumberFormatException e) {
          countText.setText(
              WYYPlayListActivity.playlistData.get(position).get("num").toString());
        }
        return view;
      }
    }
  }
}