package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mylrc.mymusic.R;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import utils.FileUtils;
import utils.StatusBarColor;
import utils.StatusBarManager;
import utils.ToastUtils;

/* loaded from: classes.dex */
public class DownDirectoryActivity extends Activity {

  ImageView internalStorageCheckBox;
  ImageView externalStorageCheckBox;
  String currentDirectory;
  int storageType;
  SharedPreferences preferences;
  TextView currentPathTextView;
  TextView internalStorageTextView;
  TextView externalStorageTextView;

  private void initializeViews() {
    this.preferences = getSharedPreferences("pms", 0);
    findViewById(R.id.downdirectoryRelativeLayout1).setOnClickListener(
        new InternalStorageClickListener(this));
    findViewById(R.id.downdirectoryRelativeLayout2).setOnClickListener(
        new ExternalStorageClickListener(this));
    findViewById(R.id.downdirectoryTextView1).setOnClickListener(
        new CustomDirectoryClickListener(this));
    findViewById(R.id.downdirectoryRelativeLayout3).setOnClickListener(
        new BackButtonClickListener(this));
  }

  private void loadStorageInfo() {
    TextView textView;
    StringBuilder sb;
    TextView textView2;
    String externalStoragePath = FileUtils.getExternalStoragePath(this);
    if (!TextUtils.isEmpty(externalStoragePath)) {
      try {
        StatFs statFs = new StatFs(externalStoragePath);
        long availableBlocksLong = statFs.getAvailableBlocksLong();
        long blockSizeLong = statFs.getBlockSizeLong();
        long blockSizeLong2 = (statFs.getBlockSizeLong() * statFs.getBlockCountLong()) / 1073741824;
        long j2 = (availableBlocksLong * blockSizeLong) / 1048576;
        if (j2 >= 1024) {
          textView = this.externalStorageTextView;
          sb = new StringBuilder();
          sb.append("外置SD卡（可用");
          sb.append(j2 / 1024);
          sb.append("G,共");
          sb.append(blockSizeLong2);
          sb.append("G）");
        } else {
          textView = this.externalStorageTextView;
          sb = new StringBuilder();
          sb.append("外置SD卡（可用");
          sb.append(j2);
          sb.append("M,共");
          sb.append(blockSizeLong2);
          sb.append("G）");
        }
        textView.setText(sb.toString());
      } catch (Exception e2) {
        this.externalStorageTextView.setText("外置SD卡");
      }
    }
    StatFs statFs2 = new StatFs(Environment.getExternalStorageDirectory().getPath());
    double availableBlocksLong2 =
        (statFs2.getAvailableBlocksLong() * statFs2.getBlockSizeLong()) / 1048576.0d;
    String str = String.format("%.2f", Double.valueOf(
        (statFs2.getBlockSizeLong() * statFs2.getBlockCountLong()) / 1.073741824E9d));
    if (availableBlocksLong2 >= 1024.0d) {
      String str2 = String.format("%.2f", Double.valueOf(availableBlocksLong2 / 1024.0d));
      this.internalStorageTextView.setText("内部存储（可用" + str2 + "G,共" + str + "G）");
    } else {
      String str3 = String.format("%.2f", Double.valueOf(availableBlocksLong2));
      this.internalStorageTextView.setText("内部存储（可用" + str3 + "M,共" + str + "G）");
    }
    this.currentDirectory = this.preferences.getString("downdirectory", FrameBodyCOMM.DEFAULT);
    int i2 = this.preferences.getInt("storageType", 1);
    this.storageType = i2;
    if (i2 == 1) {
      this.internalStorageCheckBox.setVisibility(View.VISIBLE);
      this.externalStorageCheckBox.setVisibility(View.INVISIBLE);
      textView2 = this.internalStorageTextView;
    } else {
      if (i2 != 2) {
        if (i2 != 3) {
          return;
        }
        this.internalStorageCheckBox.setVisibility(View.INVISIBLE);
        this.externalStorageCheckBox.setVisibility(View.INVISIBLE);
        this.internalStorageTextView.setTextColor(Color.parseColor("#FF000000"));
        this.externalStorageTextView.setTextColor(Color.parseColor("#FF000000"));
        this.currentPathTextView.setText("当前目录：/内部存储/" + this.currentDirectory + "/");
        return;
      }
      this.internalStorageCheckBox.setVisibility(View.INVISIBLE);
      this.externalStorageCheckBox.setVisibility(View.VISIBLE);
      textView2 = this.externalStorageTextView;
    }
    textView2.setTextColor(Color.parseColor("#FF0096FF"));
  }

  @Override
  // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
  public Resources getResources() {
    Resources resources = super.getResources();
    Configuration configuration = new Configuration();
    configuration.setToDefaults();
    resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    return resources;
  }

  @Override // android.app.Activity
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    setContentView(R.layout.downdirectory);
    this.internalStorageCheckBox = findViewById(R.id.downdirectoryCheckBox1);
    this.externalStorageCheckBox = findViewById(R.id.downdirectoryCheckBox2);
    this.currentPathTextView = findViewById(R.id.downdirectoryTextView2);
    this.internalStorageTextView = findViewById(R.id.downdirectoryTextView3);
    this.externalStorageTextView = findViewById(R.id.downdirectoryTextView4);
    initializeViews();
  }

  @Override // android.app.Activity
  protected void onResume() {
    loadStorageInfo();
    super.onResume();
  }

  class InternalStorageClickListener implements View.OnClickListener {

    final DownDirectoryActivity activity;

    InternalStorageClickListener(DownDirectoryActivity downDirectoryActivity) {
      this.activity = downDirectoryActivity;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      this.activity.internalStorageCheckBox.setVisibility(View.VISIBLE);
      this.activity.externalStorageCheckBox.setVisibility(View.INVISIBLE);
      this.activity.internalStorageTextView.setTextColor(Color.parseColor("#FF0096FF"));
      this.activity.externalStorageTextView.setTextColor(Color.parseColor("#FF000000"));
      this.activity.currentPathTextView.setText(FrameBodyCOMM.DEFAULT);
      this.activity.preferences.edit().putInt("storageType", 1).commit();
    }
  }

  class ExternalStorageClickListener implements View.OnClickListener {

    final DownDirectoryActivity activity;

    ExternalStorageClickListener(DownDirectoryActivity downDirectoryActivity) {
      this.activity = downDirectoryActivity;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      if (TextUtils.isEmpty(
          FileUtils.getExternalStoragePath(this.activity))) {
        ToastUtils.showToast(this.activity, "未检测到SD卡或SD卡无读写权限");
        return;
      }
      this.activity.internalStorageCheckBox.setVisibility(View.INVISIBLE);
      this.activity.externalStorageCheckBox.setVisibility(View.VISIBLE);
      this.activity.internalStorageTextView.setTextColor(Color.parseColor("#FF000000"));
      this.activity.externalStorageTextView.setTextColor(Color.parseColor("#FF0096FF"));
      this.activity.currentPathTextView.setText(FrameBodyCOMM.DEFAULT);
      this.activity.preferences.edit().putInt("storageType", 2).commit();
    }
  }

  class CustomDirectoryClickListener implements View.OnClickListener {

    final DownDirectoryActivity activity;

    CustomDirectoryClickListener(DownDirectoryActivity downDirectoryActivity) {
      this.activity = downDirectoryActivity;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      this.activity.startActivity(new Intent(this.activity, FileChooserActivity.class));
    }
  }

  class BackButtonClickListener implements View.OnClickListener {

    final DownDirectoryActivity activity;

    BackButtonClickListener(DownDirectoryActivity downDirectoryActivity) {
      this.activity = downDirectoryActivity;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      this.activity.finish();
    }
  }
}