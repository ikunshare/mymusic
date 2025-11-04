package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.enums.StatusBarColor;
import com.mylrc.mymusic.manager.StatusBarManager;
import com.mylrc.mymusic.network.HttpRequestUtils;
import com.mylrc.mymusic.tool.MusicUrlHelper;
import com.mylrc.mymusic.ui.dialog.DialogFactory;
import com.mylrc.mymusic.utils.CommonUtils;
import com.mylrc.mymusic.utils.ContextHolder;
import com.mylrc.mymusic.utils.ToastUtils;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.mp4.atom.Mp4DataBox;
import org.jaudiotagger.tag.mp4.atom.Mp4NameBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CloudDiskActivity extends Activity {

  // 消息类型
  private static final int MSG_SHOW_TOAST = 0;
  private static final int MSG_LOAD_SUCCESS = 1;
  private static final int MSG_SHOW_LOADING = 2;
  private static final int MSG_REFRESH_LIST = 3;
  // 数据
  private static List<Map<String, Object>> musicList;
  private static Map<String, Object> currentMusicItem;
  // Handler
  private final Handler messageHandler = new MessageHandler(Looper.getMainLooper());
  // UI组件
  private EditText searchEditText;
  private View clearButton;
  private ListView musicListView;
  private TextView helpTextView;
  private SimpleAdapter musicAdapter;
  private Dialog loadingDialog;
  // 工具类
  private MusicUrlHelper musicUrlHelper;
  private ContextHolder contextHolder;
  // 状态标志
  private boolean hasMoreData = true;
  private boolean isSearchMode = false;
  private boolean isLoading = false;
  private int currentPage = 1;

  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    setContentView(R.layout.yun);

    initViews();
    initData();
    setupListeners();
  }

  @Override
  protected void onDestroy() {
    dismissLoadingDialog();
    super.onDestroy();
  }

  @Override
  public Resources getResources() {
    Resources resources = super.getResources();
    Configuration configuration = new Configuration();
    configuration.setToDefaults();
    resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    return resources;
  }

  /**
   * 初始化视图
   */
  private void initViews() {
    searchEditText = findViewById(R.id.yunEditText1);
    clearButton = findViewById(R.id.yLinearLayout4);
    musicListView = findViewById(R.id.yunListView1);
    helpTextView = findViewById(R.id.yuny1);

    musicUrlHelper = new MusicUrlHelper();
    contextHolder = new ContextHolder(this);

    helpTextView.setText("点击此处查看云盘引擎帮助说明～");
  }

  /**
   * 初始化数据
   */
  private void initData() {
    createLoadingDialog();
    musicList = new ArrayList<>();
    loadCloudMusicList(currentPage);
  }

  /**
   * 设置监听器
   */
  private void setupListeners() {
    // 清除按钮
    clearButton.setOnClickListener(v -> searchEditText.setText(FrameBodyCOMM.DEFAULT));

    // 列表项点击
    musicListView.setOnItemClickListener(
        (parent, view, position, id) -> showDownloadConfirmDialog(position));

    // 列表滚动加载更多
    musicListView.setOnScrollListener(new AbsListView.OnScrollListener() {
      @Override
      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
          int totalItemCount) {
      }

      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
          if (musicListView.getLastVisiblePosition() == musicListView.getCount() - 1) {
            if (!isLoading && !isSearchMode && hasMoreData) {
              isLoading = true;
              createLoadingDialog();
              loadCloudMusicList(currentPage);
            }
          }
        }
      }
    });

    // 返回按钮
    findViewById(R.id.yunLinearLayout1).setOnClickListener(v -> finish());

    // 帮助说明
    helpTextView.setOnClickListener(v -> {
      Intent intent = new Intent(getApplicationContext(), BrowserActivity.class);
      intent.putExtra("url", "https://support.qq.com/embed/phone/306784/faqs/87536");
      startActivity(intent);
    });

    // 搜索按钮
    findViewById(R.id.yLinearLayout4).setOnClickListener(v -> {
      String keyword = searchEditText.getText().toString();
      if (keyword.equals(FrameBodyCOMM.DEFAULT)) {
        Toast.makeText(getApplicationContext(), "输入不能为空", Toast.LENGTH_LONG).show();
        return;
      }
      isSearchMode = true;
      createLoadingDialog();
      searchCloudMusic(keyword);
    });
  }

  /**
   * 创建加载对话框
   */
  private void createLoadingDialog() {
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
   * 显示加载对话框
   */
  private void showLoadingDialog() {
    sendMessage(MSG_SHOW_LOADING);
  }

  /**
   * 关闭加载对话框
   */
  private void dismissLoadingDialog() {
    if (loadingDialog != null && loadingDialog.isShowing()) {
      loadingDialog.dismiss();
    }
  }

  /**
   * 显示Toast消息
   */
  private void showToast(String message) {
    Message msg = new Message();
    msg.what = MSG_SHOW_TOAST;
    msg.obj = message;
    messageHandler.sendMessage(msg);
  }

  /**
   * 发送消息
   */
  private void sendMessage(int messageType) {
    Message msg = new Message();
    msg.what = messageType;
    messageHandler.sendMessage(msg);
  }

  /**
   * 加载云盘音乐列表
   */
  private void loadCloudMusicList(int page) {
    new LoadMusicListThread(page).start();
  }

  /**
   * 搜索云盘音乐
   */
  private void searchCloudMusic(String keyword) {
    new SearchMusicThread(keyword).start();
  }

  /**
   * 显示下载确认对话框
   */
  private void showDownloadConfirmDialog(int position) {
    Dialog dialog = new DialogFactory().createDialog(this);
    View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
    dialog.show();
    dialog.setContentView(view);

    Display display = getWindowManager().getDefaultDisplay();
    WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
    params.width = display.getWidth();
    dialog.getWindow().setAttributes(params);

    TextView titleTextView = view.findViewById(R.id.dialogTextView2);
    TextView contentTextView = view.findViewById(R.id.dialogTextView1);
    Button confirmButton = view.findViewById(R.id.dialogButton1);
    Button cancelButton = view.findViewById(R.id.dialogButton2);

    titleTextView.setText("提示");
    String fileName = musicList.get(position).get(Mp4NameBox.IDENTIFIER) + "."
        + musicList.get(position).get("type").toString().toLowerCase();
    contentTextView.setText("是否开始下载：" + fileName + " ？");
    confirmButton.setText("确定");
    cancelButton.setText("取消");

    confirmButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.dismiss();
        new DownloadMusicThread(position).start();
      }
    });

    cancelButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.dismiss();
      }
    });
  }

  /**
   * 显示下载链接对话框
   */
  private void showDownloadUrlDialog(String url) {
    Dialog dialog = new DialogFactory().createDialog(this);
    View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);

    TextView titleTextView = view.findViewById(R.id.dialogTextView2);
    TextView contentTextView = view.findViewById(R.id.dialogTextView1);
    Button confirmButton = view.findViewById(R.id.dialogButton1);
    Button cancelButton = view.findViewById(R.id.dialogButton2);

    dialog.show();
    dialog.setContentView(view);

    Display display = getWindowManager().getDefaultDisplay();
    WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
    params.width = display.getWidth();
    dialog.getWindow().setAttributes(params);

    titleTextView.setText("提示");
    contentTextView.setText("获取资源成功，是否现在跳转浏览器下载？");
    confirmButton.setText("确定");
    cancelButton.setText("取消");
    cancelButton.setVisibility(View.GONE);

    confirmButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.dismiss();
        try {
          Intent intent = new Intent("android.intent.action.VIEW");
          intent.setData(Uri.parse(url));
          startActivity(intent);
        } catch (Exception e) {
          showToast("没有安装浏览器类型的应用");
        }
      }
    });

    cancelButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.dismiss();
      }
    });
  }

  /**
   * 加载音乐列表线程
   */
  private class LoadMusicListThread extends Thread {

    private final int page;

    LoadMusicListThread(int page) {
      this.page = page;
    }

    @Override
    public void run() {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      try {
        JSONObject request = new JSONObject();
        request.put("page", page);
        request.put("pageSize", "20");

        String response = HttpRequestUtils.postBytes(
            "http://app.kzti.top/client/cgi-bin/yun_get",
            request.toString().getBytes()
        );

        JSONArray dataArray = new JSONObject(response).getJSONArray(Mp4DataBox.IDENTIFIER);

        for (int i = 0; i < dataArray.length(); i++) {
          JSONObject item = dataArray.getJSONObject(i);
          String name = item.getString("singer") + " - " + item.getString("title");
          String user = item.getString("user");
          String type = item.getString("type").toUpperCase();
          String hash = item.getString("hash");

          Map<String, Object> musicItem = new HashMap<>();
          musicItem.put(Mp4NameBox.IDENTIFIER, name);
          musicItem.put("user", user);
          musicItem.put("type", type);
          musicItem.put("hash", hash);

          musicList.add(musicItem);
        }
      } catch (JSONException e) {
        if (e.toString().contains("滑到")) {
          hasMoreData = false;
        }
      }

      dismissLoadingDialog();
      sendMessage(MSG_REFRESH_LIST);
      isLoading = false;
    }
  }

  /**
   * 搜索音乐线程
   */
  private class SearchMusicThread extends Thread {

    private final String keyword;

    SearchMusicThread(String keyword) {
      this.keyword = keyword;
    }

    @Override
    public void run() {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      try {
        musicList = new ArrayList<>();
        long timestamp = System.currentTimeMillis();

        JSONObject request = new JSONObject();
        request.put("s", keyword);
        request.put("sign", CommonUtils.md5(URLEncoder.encode(keyword) + timestamp + "yun_search"));
        request.put("t", timestamp);

        String response = HttpRequestUtils.postBytes(
            "http://app.kzti.top/client/cgi-bin/yun_search",
            request.toString().getBytes()
        );

        JSONArray dataArray = new JSONObject(response).getJSONArray(Mp4DataBox.IDENTIFIER);

        for (int i = 0; i < dataArray.length(); i++) {
          JSONObject item = dataArray.getJSONObject(i);
          String name = item.getString("singer") + " - " + item.getString("title");
          String user = item.getString("user");
          String type = item.getString("type").toUpperCase();
          String hash = item.getString("hash");

          Map<String, Object> musicItem = new HashMap<>();
          musicItem.put(Mp4NameBox.IDENTIFIER, name);
          musicItem.put("user", user);
          musicItem.put("type", type);
          musicItem.put("hash", hash);

          musicList.add(musicItem);
        }
      } catch (Exception e) {
        showToast("搜索结果空空如也");
      }

      sendMessage(MSG_LOAD_SUCCESS);
    }
  }

  /**
   * 下载音乐线程
   */
  private class DownloadMusicThread extends Thread {

    private final int position;

    DownloadMusicThread(int position) {
      this.position = position;
    }

    @Override
    public void run() {
      showLoadingDialog();

      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      try {
        String hash = musicList.get(position).get("hash").toString();
        String response = musicUrlHelper.getMusicUrl("yun", hash, FrameBodyCOMM.DEFAULT);

        dismissLoadingDialog();

        JSONObject result = new JSONObject(response);
        if (!result.getString("code").equals("200")) {
          showToast(result.getString("error_msg"));
        } else {
          final String downloadUrl = result.getString(Mp4DataBox.IDENTIFIER);
          messageHandler.post(() -> showDownloadUrlDialog(downloadUrl));
        }
      } catch (JSONException e) {
        e.printStackTrace();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * 消息处理器
   */
  private class MessageHandler extends Handler {

    MessageHandler(Looper looper) {
      super(looper);
    }

    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case MSG_SHOW_TOAST:
          loadingDialog.dismiss();
          ToastUtils.showToast(CloudDiskActivity.this, msg.obj.toString());
          break;

        case MSG_LOAD_SUCCESS:
          loadingDialog.dismiss();
          String[] from = {Mp4NameBox.IDENTIFIER, "user", "type"};
          int[] to = {R.id.ysTextView1, R.id.ysTextView2, R.id.ysTextView3};
          musicAdapter = new SimpleAdapter(
              getApplicationContext(),
              musicList,
              R.layout.ys,
              from,
              to
          );
          musicListView.setAdapter(musicAdapter);
          break;

        case MSG_SHOW_LOADING:
          createLoadingDialog();
          break;

        case MSG_REFRESH_LIST:
          currentPage++;
          if (musicAdapter == null) {
            String[] fromArray = {Mp4NameBox.IDENTIFIER, "user", "type"};
            int[] toArray = {R.id.ysTextView1, R.id.ysTextView2, R.id.ysTextView3};
            musicAdapter = new SimpleAdapter(
                getApplicationContext(),
                musicList,
                R.layout.ys,
                fromArray,
                toArray
            );
            musicListView.setAdapter(musicAdapter);
          } else {
            musicAdapter.notifyDataSetChanged();
          }
          break;
      }
      super.handleMessage(msg);
    }
  }
}