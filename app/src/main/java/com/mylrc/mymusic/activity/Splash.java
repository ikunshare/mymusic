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
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylrc.mymusic.R;
import java.io.File;

import com.mylrc.mymusic.utils.FileUtils;
import com.mylrc.mymusic.manager.StatusBarManager;
import com.mylrc.mymusic.enums.StatusBarColor;
import com.mylrc.mymusic.ui.dialog.DialogFactory;
import com.mylrc.mymusic.utils.ToastUtils;
import java.io.IOException;

public class Splash extends Activity {
  private SharedPreferences sharedPreferences;
  private final String emptyString = "";
  private String externalStoragePath;
  private Dialog permissionDialog;
  private ImageView logoImageView;
  private String unusedField;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if ((getIntent().getFlags() & 0x400000) != 0) {
      finish();
      return;
    }

    sharedPreferences = getSharedPreferences("pms", 0);

    StatusBarManager statusBarHelper = new StatusBarManager(this);
    String statusBarColor = sharedPreferences.getString("status_bar_color", "");
    statusBarHelper.setStatusBarTheme(statusBarColor.equals("b") ? StatusBarColor.BLACK : StatusBarColor.WHITE);

    setContentView(R.layout.layout_image_viewer);
    this.logoImageView = findViewById(R.id.mlImageView1);
    permissionDialog = new DialogFactory().createDialog(this);

    requestStoragePermission();
    loadLogoImage();
  }

  private void requestStoragePermission() {
    if (Build.VERSION.SDK_INT >= 23) {
      if (checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 200);
        return;
      }
    }
    startMainActivity();
  }

  private void loadLogoImage() {
    String logoUrl = sharedPreferences.getString("logo_url", "");
    externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    String tmpFilePath = getFilesDir().getParent() + "/app_tmpFile/";
    String logoFilePath = tmpFilePath + "b";

    if (!logoUrl.equals("")) {
      File logoFile = new File(logoFilePath);
      if (logoFile.exists() && logoFile.length() > 10240 && logoFile.length() < 2097152) {
        logoImageView.setImageURI(Uri.parse(logoFilePath));
        logoImageView.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {
            String destPath = externalStoragePath + "歌词适配背景图.jpg";
            try {
              if (FileUtils.copyFile(logoFilePath, destPath)) {
                ToastUtils.showToast(getApplicationContext(), "保存图片成功,文件已保存到内部存储根目录下");
              }
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
            return true;
          }
        });
      } else {
        logoImageView.setImageResource(R.drawable.backgroundimage);
        sharedPreferences.edit().putString("logo_url", "").apply();
      }
    } else {
      logoImageView.setImageResource(R.drawable.backgroundimage);
    }
  }

  private void showPermissionDialog() {
    if (!permissionDialog.isShowing()) {
      permissionDialog.setCancelable(false);
      View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null);
      permissionDialog.show();
      permissionDialog.setContentView(dialogView);

      Display display = getWindowManager().getDefaultDisplay();
      WindowManager.LayoutParams params = permissionDialog.getWindow().getAttributes();
      params.width = display.getWidth();
      permissionDialog.getWindow().setAttributes(params);

      TextView messageTextView = dialogView.findViewById(R.id.dialogTextView1);
      TextView titleTextView = dialogView.findViewById(R.id.dialogTextView2);
      Button settingsButton = dialogView.findViewById(R.id.dialogButton1);
      Button cancelButton = dialogView.findViewById(R.id.dialogButton2);

      titleTextView.setText("提示");
      messageTextView.setText("请授予存储权限才可以正常使用哦~");
      settingsButton.setText("去设置");
      cancelButton.setVisibility(View.GONE);

      settingsButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS",
              Uri.parse("package:com.mylrc.mymusic"));
          startActivityForResult(intent, 305);
        }
      });
    }
  }

  private void startMainActivity() {
    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
      @Override
      public void run() {
        startActivity(new Intent(Splash.this, MainActivity.class));
        finish();
      }
    }, 2500);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    if (requestCode == 200) {
      if (grantResults.length != 0 && grantResults[0] == 0) {
        if (permissionDialog != null && permissionDialog.isShowing()) {
          permissionDialog.dismiss();
        }
        startMainActivity();
      } else {
        showPermissionDialog();
      }
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 305) {
      requestStoragePermission();
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  public void onPointerCaptureChanged(boolean hasCapture) {
  }
}