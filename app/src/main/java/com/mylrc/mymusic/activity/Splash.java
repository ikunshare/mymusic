package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.enums.StatusBarColor;
import com.mylrc.mymusic.manager.StatusBarManager;
import com.mylrc.mymusic.ui.dialog.DialogFactory;
import com.mylrc.mymusic.utils.FileUtils;
import com.mylrc.mymusic.utils.ToastUtils;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;

public class Splash extends Activity {

  private static final String TAG = "Splash";
  private static final int PERMISSION_REQUEST_CODE = 200;
  private static final int SETTINGS_REQUEST_CODE = 305;
  private static final int MANAGE_ALL_FILES_REQUEST_CODE = 306;
  private static final long SPLASH_DELAY_MS = 2500L;
  private static final int MIN_LOGO_SIZE = 10240;
  private static final int MAX_LOGO_SIZE = 2097152;

  private Dialog permissionDialog;
  private ImageView logoImageView;
  private SharedPreferences preferences = null;
  private String logoUrl = FrameBodyCOMM.DEFAULT;
  private String storagePath = null;
  private String tempFilePath = null;
  private Handler handler;
  private boolean isPermissionDeniedPermanently = false;

  public String getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  public String getTempFilePath() {
    return tempFilePath;
  }

  public void setTempFilePath(String tempFilePath) {
    this.tempFilePath = tempFilePath;
  }

  private void checkPermissions() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      startMainActivityDelayed();
      return;
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      if (Environment.isExternalStorageManager()) {
        Log.d(TAG, "已有MANAGE_EXTERNAL_STORAGE权限");
        startMainActivityDelayed();
      } else {
        Log.d(TAG, "需要请求MANAGE_EXTERNAL_STORAGE权限");
        if (isPermissionDeniedPermanently) {
          showPermissionDialog();
        } else {
          requestManageAllFilesPermission();
        }
      }
      return;
    }

    List<String> permissionsToRequest = new ArrayList<>();

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      if (checkSelfPermission("android.permission.READ_MEDIA_AUDIO")
          != PackageManager.PERMISSION_GRANTED) {
        permissionsToRequest.add("android.permission.READ_MEDIA_AUDIO");
      }
      if (checkSelfPermission("android.permission.READ_MEDIA_IMAGES")
          != PackageManager.PERMISSION_GRANTED) {
        permissionsToRequest.add("android.permission.READ_MEDIA_IMAGES");
      }
    } else {
      if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE")
          != PackageManager.PERMISSION_GRANTED) {
        permissionsToRequest.add("android.permission.READ_EXTERNAL_STORAGE");
      }
      if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE")
          != PackageManager.PERMISSION_GRANTED) {
        permissionsToRequest.add("android.permission.WRITE_EXTERNAL_STORAGE");
      }
    }

    if (permissionsToRequest.isEmpty()) {
      startMainActivityDelayed();
    } else {
      boolean shouldShowRationale = false;
      for (String permission : permissionsToRequest) {
        if (shouldShowRequestPermissionRationale(permission)) {
          shouldShowRationale = true;
          break;
        }
      }

      if (!shouldShowRationale && isPermissionDeniedPermanently) {
        showPermissionDialog();
      } else {
        requestPermissions(
            permissionsToRequest.toArray(new String[0]),
            PERMISSION_REQUEST_CODE);
      }
    }
  }

  private void requestManageAllFilesPermission() {
    try {
      Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
      intent.setData(Uri.parse("package:" + getPackageName()));
      startActivityForResult(intent, MANAGE_ALL_FILES_REQUEST_CODE);
    } catch (Exception e) {
      Log.e(TAG, "无法打开所有文件访问设置，尝试传统权限: " + e.getMessage());
      Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
      intent.setData(Uri.parse("package:" + getPackageName()));
      startActivityForResult(intent, SETTINGS_REQUEST_CODE);
    }
  }

  private void loadLogoImage() {
    try {
      String string = this.preferences.getString("logo_url", FrameBodyCOMM.DEFAULT);
      this.storagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
      String str = (getFilesDir().getParent() + "/app_tmpFile/") + "b";

      if (string.equals(FrameBodyCOMM.DEFAULT)) {
        this.logoImageView.setImageResource(R.drawable.backgroundimage);
        return;
      }

      File logoFile = new File(str);
      if (!logoFile.exists() || logoFile.length() < MIN_LOGO_SIZE
          || logoFile.length() > MAX_LOGO_SIZE) {
        this.logoImageView.setImageResource(R.drawable.backgroundimage);
        this.preferences.edit().putString("logo_url", FrameBodyCOMM.DEFAULT).apply();
      } else {
        this.logoImageView.setImageURI(Uri.parse(str));
        this.logoImageView.setOnLongClickListener(new LogoLongClickListener(this, str));
      }
    } catch (Exception e) {
      Log.e(TAG, "loadLogoImage failed: " + e.getMessage());
      this.logoImageView.setImageResource(R.drawable.backgroundimage);
    }
  }

  private void showPermissionDialog() {
    if (this.permissionDialog == null) {
      return;
    }
    if (this.permissionDialog.isShowing()) {
      return;
    }
    this.permissionDialog.setCancelable(false);
    View viewInflate = LayoutInflater.from(this).inflate(R.layout.dialog, null);
    this.permissionDialog.show();
    this.permissionDialog.setContentView(viewInflate);
    Display defaultDisplay = getWindowManager().getDefaultDisplay();
    WindowManager.LayoutParams attributes = Objects.requireNonNull(
        this.permissionDialog.getWindow()).getAttributes();
    attributes.width = defaultDisplay.getWidth();
    this.permissionDialog.getWindow().setAttributes(attributes);
    TextView textView = viewInflate.findViewById(R.id.dialogTextView1);
    TextView textView2 = viewInflate.findViewById(R.id.dialogTextView2);
    Button button = viewInflate.findViewById(R.id.dialogButton1);
    Button button2 = viewInflate.findViewById(R.id.dialogButton2);

    textView2.setText("权限说明");

    String message;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      if (isPermissionDeniedPermanently || !Environment.isExternalStorageManager()) {
        message = "应用需要\"所有文件访问权限\"才能正常保存和读取音乐文件。\n\n" +
            "请点击\"去设置\"，然后在设置页面中开启\"允许访问所有文件\"。\n\n" +
            "路径：设置中找到本应用 → 允许访问所有文件";
      } else {
        message = "应用需要存储权限才能正常使用。请授予相关权限。";
      }
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      message = "应用需要访问音频和图片权限才能正常使用。\n\n" +
          "请点击\"授予权限\"并在弹出的对话框中选择\"允许\"。";
    } else {
      if (isPermissionDeniedPermanently) {
        message = "应用需要存储权限才能正常使用。请在设置中手动开启存储权限。\n\n" +
            "路径：设置 → 应用 → " + getApplicationInfo().loadLabel(getPackageManager()) +
            " → 权限 → 存储";
      } else {
        message = "应用需要存储权限来保存和读取音乐文件。\n\n" +
            "请点击\"授予权限\"并在弹出的对话框中选择\"允许\"。";
      }
    }

    textView.setText(message);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R || isPermissionDeniedPermanently) {
      button.setText("去设置");
    } else {
      button.setText("授予权限");
    }

    button2.setText("退出应用");
    button2.setVisibility(View.VISIBLE);

    button.setOnClickListener(new PermissionButtonClickListener(this));
    button2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  private void startMainActivityDelayed() {
    if (handler == null) {
      handler = new Handler(Looper.getMainLooper());
    }
    handler.postDelayed(new StartMainActivityRunnable(this), SPLASH_DELAY_MS);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == SETTINGS_REQUEST_CODE || requestCode == MANAGE_ALL_FILES_REQUEST_CODE) {
      isPermissionDeniedPermanently = false;
      checkPermissions();
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0) {
      finish();
      return;
    }
    this.preferences = getSharedPreferences("pms", 0);
    if (this.preferences != null) {
      new StatusBarManager(this).setStatusBarTheme(
          this.preferences.getString("status_bar_color", FrameBodyCOMM.DEFAULT).equals("b")
              ? StatusBarColor.BLACK : StatusBarColor.WHITE);
    }
    setContentView(R.layout.layout_image_viewer);
    this.logoImageView = findViewById(R.id.mlImageView1);
    this.permissionDialog = new DialogFactory().createDialog(this);
    checkPermissions();
    loadLogoImage();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (this.permissionDialog != null && this.permissionDialog.isShowing()) {
      this.permissionDialog.dismiss();
      this.permissionDialog = null;
    }
    if (handler != null) {
      handler.removeCallbacksAndMessages(null);
      handler = null;
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == PERMISSION_REQUEST_CODE) {
      Log.d(TAG, "onRequestPermissionsResult - permissions: " +
          java.util.Arrays.toString(permissions));
      Log.d(TAG, "onRequestPermissionsResult - grantResults: " +
          java.util.Arrays.toString(grantResults));

      if (grantResults.length > 0) {
        boolean allGranted = true;
        for (int result : grantResults) {
          if (result != PackageManager.PERMISSION_GRANTED) {
            allGranted = false;
            break;
          }
        }

        if (allGranted) {
          Log.d(TAG, "所有权限已授予");
          Dialog dialog = this.permissionDialog;
          if (dialog != null && dialog.isShowing()) {
            this.permissionDialog.dismiss();
          }
          startMainActivityDelayed();
        } else {
          Log.w(TAG, "部分或全部权限被拒绝");

          boolean shouldShow = false;
          for (String permission : permissions) {
            if (shouldShowRequestPermissionRationale(permission)) {
              shouldShow = true;
              Log.d(TAG, "权限 " + permission + " 可以再次请求");
              break;
            }
          }

          if (!shouldShow) {
            Log.w(TAG, "用户选择了不再询问");
            isPermissionDeniedPermanently = true;
          }

          showPermissionDialog();
        }
      } else {
        Log.e(TAG, "onRequestPermissionsResult: grantResults为空");
        showPermissionDialog();
      }
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  static class LogoLongClickListener implements View.OnLongClickListener {

    final WeakReference<Splash> activityRef;
    final String imagePath;

    LogoLongClickListener(Splash splash, String str) {
      this.activityRef = new WeakReference<>(splash);
      this.imagePath = str;
    }

    @Override
    public boolean onLongClick(View view) {
      Splash activity = activityRef.get();
      if (activity == null || activity.isFinishing()) {
        return true;
      }

      try {
        if (activity.storagePath == null) {
          ToastUtils.showToast(activity.getApplicationContext(), "存储路径未初始化");
          return true;
        }

        boolean success = FileUtils.copyFile(this.imagePath,
            activity.storagePath + "歌词适配背景图.jpg");
        if (success) {
          ToastUtils.showToast(activity.getApplicationContext(),
              "保存图片成功，文件已保存到内部存储根目录下");
        } else {
          ToastUtils.showToast(activity.getApplicationContext(), "保存图片失败");
        }
      } catch (IOException e) {
        Log.e(TAG, "Copy file failed: " + e.getMessage());
        ToastUtils.showToast(activity.getApplicationContext(), "保存图片失败：" + e.getMessage());
      }
      return true;
    }
  }

  static class PermissionButtonClickListener implements View.OnClickListener {

    final WeakReference<Splash> activityRef;

    PermissionButtonClickListener(Splash splash) {
      this.activityRef = new WeakReference<>(splash);
    }

    @Override
    public void onClick(View view) {
      Splash activity = activityRef.get();
      if (activity == null || activity.isFinishing()) {
        return;
      }

      Dialog dialog = activity.permissionDialog;
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
      }

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        activity.requestManageAllFilesPermission();
      } else if (activity.isPermissionDeniedPermanently) {
        activity.startActivityForResult(
            new Intent("android.settings.APPLICATION_DETAILS_SETTINGS",
                Uri.parse("package:com.mylrc.mymusic")), SETTINGS_REQUEST_CODE);
      } else {
        activity.checkPermissions();
      }
    }
  }

  static class StartMainActivityRunnable implements Runnable {

    final WeakReference<Splash> activityRef;

    StartMainActivityRunnable(Splash splash) {
      this.activityRef = new WeakReference<>(splash);
    }

    @Override
    public void run() {
      Splash activity = activityRef.get();
      if (activity == null || activity.isFinishing()) {
        return;
      }
      activity.startActivity(new Intent(activity, MainActivity.class));
      activity.finish();
    }
  }
}