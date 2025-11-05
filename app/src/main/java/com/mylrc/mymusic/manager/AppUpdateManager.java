package com.mylrc.mymusic.manager;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.network.DownloadUtils;
import com.mylrc.mymusic.network.HttpRequestUtils;
import com.mylrc.mymusic.network.OkHttpClient;
import com.mylrc.mymusic.tool.APPApplication;
import com.mylrc.mymusic.ui.dialog.DialogFactory;
import com.mylrc.mymusic.utils.CommonUtils;
import com.mylrc.mymusic.utils.FileUtils;
import com.mylrc.mymusic.utils.TimeUtils;
import com.mylrc.mymusic.utils.ToastUtils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.mp4.atom.Mp4DataBox;
import org.json.JSONObject;

public class AppUpdateManager {

  public static SharedPreferences sharedPreferences;
  public static String externalStoragePath;
  final Activity activity;
  Dialog progressDialog;
  Dialog updateDialog;
  String fileMd5;
  AppValidator appValidator;
  boolean isNetworkAvailable = false;
  int retryCount = 0;
  String updateCheckStatus = "yes";
  String tempFilePath;
  private String updateTitle;
  private String updateLog;
  private String downloadUrl;
  private String shareUrl;
  private String isCompulsory;

  public AppUpdateManager(Activity activity) {
    this.activity = activity;
  }

  private void getSplashConfig() {
    SharedPreferences.Editor editor;
    try {
      String host = new URL("http://api.ikunshare.com/client/cgi-bin/Splash").getHost();
      JSONObject jsonObject = new JSONObject(
          HttpRequestUtils.httpGet("http://api.ikunshare.com/client/cgi-bin/Splash"));

      String state = jsonObject.getString("state");
      String color = jsonObject.getString("color");
      String imageUrl = jsonObject.getString("imageUrl");

      sharedPreferences.edit().putString("status_bar_color", color).commit();

      if (state.equals("1")) {
        editor = sharedPreferences.edit().putString("logo_url", FrameBodyCOMM.DEFAULT);
      } else {
        if (sharedPreferences.getString("logo_url", FrameBodyCOMM.DEFAULT).equals(imageUrl)) {
          return;
        }
        DownloadUtils.downloadFile(imageUrl, this.tempFilePath + "b");
        editor = sharedPreferences.edit().putString("logo_url", imageUrl);
      }
      editor.commit();
    } catch (Exception e) {
    }
  }

  private void dismissProgressDialog() {
    Dialog dialog = this.progressDialog;
    if (dialog == null || !dialog.isShowing() ||
        this.activity.isFinishing() || this.activity.isDestroyed()) {
      return;
    }
    this.progressDialog.dismiss();
  }

  private void createDirectories() {
    String musicDir = externalStoragePath + "MusicDownloader/Music/";
    String mvDir = externalStoragePath + "MusicDownloader/Mv/";
    String apkDir = externalStoragePath + "MusicDownloader/Apk/";
    this.tempFilePath = this.activity.getFilesDir().getParent() + "/app_tmpFile/";

    FileUtils.createDirectory(musicDir);
    FileUtils.createDirectory(mvDir);
    FileUtils.createDirectory(apkDir);
    FileUtils.createDirectory(this.tempFilePath);
  }

  private void showProgressDialog() {
    this.activity.runOnUiThread(new ShowProgressDialogRunnable(this));
  }

  private void showUpdateDialog() {
    Dialog dialog = new DialogFactory().createDialog(this.activity);
    View dialogView = LayoutInflater.from(this.activity).inflate(R.layout.dialog, null);

    TextView titleTextView = dialogView.findViewById(R.id.dialogTextView1);
    TextView contentTextView = dialogView.findViewById(R.id.dialogTextView2);
    Button directUpdateButton = dialogView.findViewById(R.id.dialogButton1);
    Button browserUpdateButton = dialogView.findViewById(R.id.dialogButton2);

    dialog.show();
    dialog.setContentView(dialogView);

    Display display = this.activity.getWindowManager().getDefaultDisplay();
    WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
    layoutParams.width = display.getWidth();
    dialog.getWindow().setAttributes(layoutParams);

    contentTextView.setText(this.updateLog);
    titleTextView.setText(this.updateTitle);
    directUpdateButton.setText("直接更新");
    browserUpdateButton.setText("浏览器更新");

    if (this.downloadUrl.equals(FrameBodyCOMM.DEFAULT) || this.shareUrl.equals(
        FrameBodyCOMM.DEFAULT)) {
      browserUpdateButton.setVisibility(View.GONE);
      directUpdateButton.setText("更新");
    }

    String compulsory = this.isCompulsory;
    if (compulsory != null && compulsory.equals("yes")) {
      dialog.setCancelable(false);
    }

    directUpdateButton.setOnClickListener(new DownloadButtonClickListener(this));
    browserUpdateButton.setOnClickListener(new BrowserUpdateButtonClickListener(this));
  }

  public void checkStoragePermission() {
    int storageType = sharedPreferences.getInt("storageType", 1);
    String sdCardPath = FileUtils.getExternalStoragePath(this.activity);

    if (storageType == 2 && TextUtils.isEmpty(sdCardPath)) {
      sharedPreferences.edit().putInt("storageType", 1).commit();
      ToastUtils.showToast(this.activity, "SD卡不存在或SD卡无权限读写，已将下载路径改为内部存储");
    }
  }

  public void checkUpdate() {
    try {
      JSONObject responseJson = new JSONObject(
          OkHttpClient.getInstance()
              .newCall(new Request.Builder()
                  .url("http://api.ikunshare.com/client/cgi-bin/check_version")
                  .header("User-Agent", APPApplication.userAgent)
                  .post(RequestBody.create(MediaType.parse("gcsp/stream"),
                      CommonUtils.getDeviceInfo()))
                  .build())
              .execute()
              .body()
              .string()
      );

      if (responseJson.getString("code").equals("200")) {
        JSONObject dataJson = responseJson.getJSONObject(Mp4DataBox.IDENTIFIER);
        String latestVersion = dataJson.getString("version");
        this.updateTitle = dataJson.getString("update_title");
        this.updateLog = dataJson.getString("update_log");
        this.downloadUrl = dataJson.getString("down_url");
        this.shareUrl = dataJson.getString("share_url");
        this.isCompulsory = dataJson.getString("compulsory");
        this.fileMd5 = dataJson.getString("file_md5");
        this.activity.runOnUiThread(new ShowUpdateDialogRunnable(this));
      }
      this.updateCheckStatus = "yes";
    } catch (Exception e) {
      this.updateCheckStatus = "error";
    }
  }

  public void downloadAndInstallApk(String downloadUrl) {
    new DownloadAndInstallThread(this,
        externalStoragePath + "MusicDownloader/Apk/MusicDownloader.apk", downloadUrl).start();
  }

  public void init() {
    sharedPreferences = this.activity.getSharedPreferences("pms", 0);
    Dialog dialog = new DialogFactory().createDialog(this.activity);
    this.updateDialog = dialog;
    dialog.setCancelable(false);
    externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

    createDirectories();
    checkStoragePermission();

    new Thread(new InitThread(this)).start();

    AppValidator validator = new AppValidator(this.activity);
    this.appValidator = validator;
    validator.validate();

    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    this.activity.registerReceiver(new NetworkChangeReceiver(this), intentFilter);
  }

  public void showGreetingByTime(String currentTime) throws NumberFormatException {
    Activity activity;
    String greeting;

    try {
      int hour = Integer.parseInt(
          currentTime.substring(currentTime.indexOf(" ") + 1, currentTime.indexOf(":")));

      if ((hour >= 0) && (hour <= 4)) {
        activity = this.activity;
        greeting = "深夜，现在的夜，熬得只是还未改变的习惯";
      } else if ((hour >= 5) && (hour <= 10)) {
        activity = this.activity;
        greeting = "早安，清晨熹微的阳光， 是你在微笑吗";
      } else if ((hour >= 11) && (hour <= 13)) {
        activity = this.activity;
        greeting = "午好，伴随着熟悉的乐曲，聆听着动人的旋律";
      } else if ((hour >= 14) && (hour <= 17)) {
        activity = this.activity;
        greeting = "夕暮，似清风醉晚霞，不经意间盈笑回眸";
      } else if ((hour >= 18) && (hour <= 23)) {
        activity = this.activity;
        greeting = "夜晚，一个安静的角落，静静地聆听夜曲";
      } else {
        return;
      }
      ToastUtils.showToast(activity, greeting);
    } catch (Exception e) {
    }
  }

  class InitThread implements Runnable {

    final AppUpdateManager manager;

    InitThread(AppUpdateManager manager) {
      this.manager = manager;
    }

    @Override
    public void run() throws NumberFormatException {
      this.manager.checkUpdate();
      this.manager.getSplashConfig();
      this.manager.showGreetingByTime(TimeUtils.getNetworkTime());
    }
  }

  class ShowUpdateDialogRunnable implements Runnable {

    final AppUpdateManager manager;

    ShowUpdateDialogRunnable(AppUpdateManager manager) {
      this.manager = manager;
    }

    @Override
    public void run() {
      this.manager.showUpdateDialog();
    }
  }

  class DownloadButtonClickListener implements View.OnClickListener {

    final AppUpdateManager manager;

    DownloadButtonClickListener(AppUpdateManager manager) {
      this.manager = manager;
    }

    @Override
    public void onClick(View view) {
      if (!this.manager.downloadUrl.equals(FrameBodyCOMM.DEFAULT)) {
        AppUpdateManager manager = this.manager;
        manager.downloadAndInstallApk(manager.downloadUrl);
        return;
      }
      try {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(this.manager.shareUrl));
        this.manager.activity.startActivity(intent);
      } catch (Exception e) {
        ToastUtils.showToast(this.manager.activity, "没有安装浏览器类型的应用");
      }
    }
  }

  class BrowserUpdateButtonClickListener implements View.OnClickListener {

    final AppUpdateManager manager;

    BrowserUpdateButtonClickListener(AppUpdateManager manager) {
      this.manager = manager;
    }

    @Override
    public void onClick(View view) {
      try {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(this.manager.shareUrl));
        this.manager.activity.startActivity(intent);
      } catch (Exception e) {
        ToastUtils.showToast(this.manager.activity, "没有安装浏览器类型的应用");
      }
    }
  }

  class DownloadAndInstallThread extends Thread {

    final String localFilePath;
    final String downloadUrl;
    final AppUpdateManager manager;

    DownloadAndInstallThread(AppUpdateManager manager, String localFilePath, String downloadUrl) {
      this.manager = manager;
      this.localFilePath = localFilePath;
      this.downloadUrl = downloadUrl;
    }

    @Override
    public void run() {
      String cachedMd5 = null;
      Activity activity = null;
      String filePath = null;

      try {
        cachedMd5 = this.manager.fileMd5;
      } catch (Exception e) {
        ToastUtils.showToast(this.manager.activity, "更新失败，请选择浏览器更新：" + e);
      }

      if (cachedMd5 == null || cachedMd5.equals(FrameBodyCOMM.DEFAULT) ||
          !new File(this.localFilePath).exists() ||
          !this.manager.fileMd5.equals(FileUtils.getMD5(this.localFilePath))) {

        ToastUtils.showToast(this.manager.activity, "正在下载中...");
        this.manager.showProgressDialog();
        try {
          DownloadUtils.downloadFile(this.downloadUrl, this.localFilePath);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        try {
          Thread.sleep(100L);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }

        String calculatedMd5 = FileUtils.getMD5(this.localFilePath);
        if (!this.manager.fileMd5.equals(FrameBodyCOMM.DEFAULT) &&
            !this.manager.fileMd5.equals(calculatedMd5)) {
          ToastUtils.showToast(this.manager.activity, "安装包校验失败，文件已损坏，请尝试重新下载");
          this.manager.dismissProgressDialog();
        } else {
          activity = this.manager.activity;
          filePath = this.localFilePath;
        }
      } else {
        activity = this.manager.activity;
        filePath = this.localFilePath;
      }
      CommonUtils.installApk(activity, filePath);
      this.manager.dismissProgressDialog();
    }
  }

  class ShowProgressDialogRunnable implements Runnable {

    final AppUpdateManager manager;

    ShowProgressDialogRunnable(AppUpdateManager manager) {
      this.manager = manager;
    }

    @Override
    public void run() {
      AppUpdateManager manager = this.manager;
      if (manager.progressDialog == null) {
        manager.progressDialog = new Dialog(this.manager.activity);
        this.manager.progressDialog.requestWindowFeature(1);
        this.manager.progressDialog.getWindow().setWindowAnimations(R.style.loadingAnim);
        this.manager.progressDialog.setContentView(R.layout.loading);
        this.manager.progressDialog.setCancelable(false);
      }
      if (this.manager.progressDialog.isShowing() ||
          this.manager.activity.isFinishing() ||
          this.manager.activity.isDestroyed()) {
        return;
      }
      this.manager.progressDialog.show();
    }
  }

  class NetworkChangeReceiver extends BroadcastReceiver {

    final AppUpdateManager manager;

    NetworkChangeReceiver(AppUpdateManager manager) {
      this.manager = manager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
      NetworkInfo networkInfo;
      if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction()) &&
          (networkInfo = intent.getParcelableExtra("networkInfo")) != null &&
          NetworkInfo.State.CONNECTED == networkInfo.getState() &&
          networkInfo.isAvailable()) {

        this.manager.appValidator.validate();
        if (this.manager.updateCheckStatus.equals("error")) {
          new RetryUpdateCheckThread(this).start();
        }
      }
    }

    class RetryUpdateCheckThread extends Thread {

      final NetworkChangeReceiver receiver;

      RetryUpdateCheckThread(NetworkChangeReceiver receiver) {
        this.receiver = receiver;
      }

      @Override
      public void run() {
        this.receiver.manager.checkUpdate();
      }
    }
  }
}