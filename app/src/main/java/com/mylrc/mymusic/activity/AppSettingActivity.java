package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.enums.StatusBarColor;
import com.mylrc.mymusic.manager.StatusBarManager;
import com.mylrc.mymusic.ui.dialog.DialogFactory;
import com.mylrc.mymusic.utils.ToastUtils;

/**
 * 应用设置界面 Activity 用于管理应用的各种设置选项
 */
public class AppSettingActivity extends Activity {

  // 常量定义
  private static final int REQUEST_CODE_OVERLAY_PERMISSION = 1;
  private static final String PREF_NAME = "pms";
  private static final String ACTION_ADD_FLOATING = "com.music.add";
  private static final String ACTION_REMOVE_FLOATING = "com.music.remove";
  // 界面布局元素
  private RelativeLayout layoutSavePath;
  private RelativeLayout layoutFileMode;
  private RelativeLayout layoutLrcMode;
  private RelativeLayout layoutAutoTag;
  private RelativeLayout layoutFloatingWindow;
  private RelativeLayout layoutEncodingType;
  private RelativeLayout layoutScreenMode;
  // 设置相关
  private SharedPreferences preferences;
  private Dialog settingsDialog;
  // 设置项的值
  private int fileMode;        // 文件模式
  private int lrcMode;         // 歌词模式
  private int autoTagMode;     // 自动标签模式
  private int floatingWindow;  // 悬浮窗模式
  private int encodingType;    // 编码类型
  private int screenMode;      // 屏幕模式

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // 设置状态栏样式
    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    setContentView(R.layout.setting_two);

    // 初始化视图
    initViews();
    // 初始化设置
    initSettings();
  }

  /**
   * 初始化视图组件
   */
  private void initViews() {
    layoutSavePath = findViewById(R.id.settingtwoRelativeLayout2);
    layoutFileMode = findViewById(R.id.settingtwoRelativeLayout3);
    layoutLrcMode = findViewById(R.id.settingtwoRelativeLayout4);
    layoutAutoTag = findViewById(R.id.settingtwoRelativeLayout5);
    layoutFloatingWindow = findViewById(R.id.settingtwoRelativeLayout7);
    layoutEncodingType = findViewById(R.id.settingtwoRelativeLayout8);
    layoutScreenMode = findViewById(R.id.settingtwoRelativeLayout9);
  }

  /**
   * 初始化设置项
   */
  private void initSettings() {
    // 获取复选框控件
    CheckBox cbLrcMode = findViewById(R.id.settingtwoCheckBox1);
    CheckBox cbAutoTag = findViewById(R.id.settingtwoCheckBox2);
    CheckBox cbFloatingWindow = findViewById(R.id.settingtwoCheckBox4);
    CheckBox cbScreenMode = findViewById(R.id.settingtwoCheckBox5);

    // 初始化对话框
    settingsDialog = new DialogFactory().createDialog(this);

    // 读取保存的设置
    loadPreferences();

    // 设置复选框状态
    updateCheckBoxStates(cbLrcMode, cbAutoTag, cbFloatingWindow, cbScreenMode);

    // 设置点击监听器
    setupClickListeners(cbLrcMode, cbAutoTag, cbFloatingWindow, cbScreenMode);
  }

  /**
   * 从 SharedPreferences 加载设置
   */
  private void loadPreferences() {
    preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    fileMode = preferences.getInt("filemode", 0);
    lrcMode = preferences.getInt("lrcmode", 0);
    autoTagMode = preferences.getInt("zdbz", 0);
    floatingWindow = preferences.getInt("win", 1);
    screenMode = preferences.getInt("pm", 1);
  }

  /**
   * 更新复选框状态
   */
  private void updateCheckBoxStates(CheckBox cbLrcMode,
      CheckBox cbAutoTag, CheckBox cbFloatingWindow,
      CheckBox cbScreenMode) {
    cbLrcMode.setChecked(lrcMode == 0);
    cbAutoTag.setChecked(autoTagMode == 0);
    cbFloatingWindow.setChecked(floatingWindow == 0);
    cbScreenMode.setChecked(screenMode == 0);
  }

  /**
   * 设置各个控件的点击监听器
   */
  private void setupClickListeners(CheckBox cbLrcMode,
      CheckBox cbAutoTag, CheckBox cbFloatingWindow,
      CheckBox cbScreenMode) {
    // 返回按钮
    findViewById(R.id.settingtwoRelativeLayout1).setOnClickListener(v -> finish());

    // 帮助按钮
    findViewById(R.id.settingtwoRelativeLayout10).setOnClickListener(v -> showHelpDialog());

    layoutSavePath.setOnClickListener(
        v -> startActivity(new Intent(AppSettingActivity.this, DownDirectoryActivity.class)));

    // 文件模式设置
    layoutFileMode.setOnClickListener(
        v -> showSelectionDialog(3, fileMode, "下载的音乐文件命名格式", "歌曲名 - 歌手名",
            "歌手名 - 歌曲名"));

    // 歌词下载设置
    layoutLrcMode.setOnClickListener(v ->
        toggleSetting(cbLrcMode, "lrcmode", lrcMode,
            value -> lrcMode = value));

    // 自动标签设置
    layoutAutoTag.setOnClickListener(v ->
        toggleSetting(cbAutoTag, "zdbz", autoTagMode,
            value -> autoTagMode = value));

    // 悬浮窗设置
    layoutFloatingWindow.setOnClickListener(v ->
        handleFloatingWindowToggle(cbFloatingWindow));

    // 编码类型设置
    layoutEncodingType.setOnClickListener(v ->
        showSelectionDialog(6, encodingType, "歌词文件编码",
            "UTF-8", "GBK（MP3/4 播放器专用）"));

    // 屏幕模式设置
    layoutScreenMode.setOnClickListener(v ->
        toggleSetting(cbScreenMode, "pm", screenMode,
            value -> screenMode = value));
  }

  /**
   * 切换设置项的通用方法
   */
  private void toggleSetting(CheckBox checkBox, String prefKey, int currentValue,
      SettingValueSetter setter) {
    int newValue = checkBox.isChecked() ? 1 : 0;
    setter.setValue(newValue);
    preferences.edit().putInt(prefKey, newValue).apply();
    checkBox.setChecked(!checkBox.isChecked());
  }

  /**
   * 处理悬浮窗权限切换
   */
  private void handleFloatingWindowToggle(CheckBox checkBox) {
    if (!hasOverlayPermission()) {
      ToastUtils.showToast(this, "请先授权悬浮窗权限");
      startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION),
          REQUEST_CODE_OVERLAY_PERMISSION);
      return;
    }

    boolean isChecked = checkBox.isChecked();
    floatingWindow = isChecked ? 1 : 0;
    preferences.edit().putInt("win", floatingWindow).apply();
    checkBox.setChecked(!isChecked);

    // 发送广播通知服务
    Intent intent = new Intent();
    intent.setAction(isChecked ? ACTION_REMOVE_FLOATING : ACTION_ADD_FLOATING);
    sendBroadcast(intent);
  }

  /**
   * 显示选择对话框
   */
  private void showSelectionDialog(int type, int currentValue, String title,
      String option1, String option2) {
    View dialogView = LayoutInflater.from(this)
        .inflate(R.layout.myser, null);

    // 获取对话框视图组件
    TextView tvTitle = dialogView.findViewById(R.id.myserTextView1);
    TextView tvOption1 = dialogView.findViewById(R.id.myserTextView2);
    TextView tvOption2 = dialogView.findViewById(R.id.myserTextView3);
    View layoutOption1 = dialogView.findViewById(R.id.myserRelativeLayout1);
    View layoutOption2 = dialogView.findViewById(R.id.myserRelativeLayout2);
    ImageView ivCheck1 = dialogView.findViewById(R.id.myserImageView1);
    ImageView ivCheck2 = dialogView.findViewById(R.id.myserImageView2);

    // 设置文本
    tvTitle.setText(title);
    tvOption1.setText(option1);
    tvOption2.setText(option2);

    // 设置当前选中状态
    if (currentValue == 0) {
      ivCheck1.setVisibility(View.VISIBLE);
      tvOption1.setTextColor(Color.parseColor("#FF00B4FF"));
    } else {
      ivCheck2.setVisibility(View.VISIBLE);
      tvOption2.setTextColor(Color.parseColor("#FF00B4FF"));
    }

    // 显示对话框
    settingsDialog.show();
    settingsDialog.setContentView(dialogView);

    // 设置对话框宽度
    Display display = getWindowManager().getDefaultDisplay();
    WindowManager.LayoutParams params = settingsDialog.getWindow().getAttributes();
    params.width = display.getWidth();
    settingsDialog.getWindow().setAttributes(params);

    // 设置选项点击事件
    layoutOption1.setOnClickListener(v -> {
      handleDialogSelection(type, 0);
      settingsDialog.dismiss();
    });

    layoutOption2.setOnClickListener(v -> {
      handleDialogSelection(type, 1);
      settingsDialog.dismiss();
    });
  }

  /**
   * 处理对话框选择
   */
  private void handleDialogSelection(int type, int value) {
    String prefKey;

    switch (type) {
      case 3: // 文件模式
        fileMode = value;
        prefKey = "filemode";
        break;
      case 4: // 歌词模式
        lrcMode = value;
        prefKey = "lrcmode";
        break;
      case 6: // 编码类型
        encodingType = value;
        prefKey = "textmode";
        break;
      default:
        return;
    }

    preferences.edit().putInt(prefKey, value).commit();
  }

  /**
   * 显示帮助对话框
   */
  private void showHelpDialog() {
    Dialog helpDialog = new DialogFactory().createDialog(this);
    View helpView = LayoutInflater.from(this)
        .inflate(R.layout.dialog, null);

    TextView tvTitle = helpView.findViewById(R.id.dialogTextView2);
    TextView tvContent = helpView.findViewById(R.id.dialogTextView1);
    Button btnConfirm = helpView.findViewById(R.id.dialogButton1);
    Button btnCancel = helpView.findViewById(R.id.dialogButton2);

    tvTitle.setText("说明");
    tvContent.setText(getHelpText());
    btnConfirm.setText("确定");
    btnCancel.setVisibility(View.GONE);

    helpDialog.show();
    helpDialog.setContentView(helpView);

    // 设置对话框宽度
    Display display = getWindowManager().getDefaultDisplay();
    WindowManager.LayoutParams params = helpDialog.getWindow().getAttributes();
    params.width = display.getWidth();
    helpDialog.getWindow().setAttributes(params);

    btnConfirm.setOnClickListener(v -> helpDialog.dismiss());
  }

  /**
   * 获取帮助文本
   */
  private String getHelpText() {
    return "写入标签信息选项打开时：下载完成后会自动向音乐文件里写入标签信息，" +
        "包括，标题、歌手、专辑名称、专辑封面和歌词数据。\n\n" +
        "下载额外LRC歌词选项打开时：会在音乐文件所在目录下一同生成LRC歌词文件，" +
        "关闭后则不会生成LRC歌词文件。\n\n" +
        "建议：如果你使用的本地播放器支持读取音乐文件内的标签和歌词数据，" +
        "建议只 打开 写入标签信息选项即可，关闭 下载额外LRC歌词文件选项，" +
        "这样就不会生成多余的LRC歌词文件。";
  }

  /**
   * 检查是否有悬浮窗权限
   */
  private boolean hasOverlayPermission() {
    if (Build.VERSION.SDK_INT < 23) {
      return true;
    }

    boolean canDraw = false;
    try {
      WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
      View testView = new View(getApplicationContext());

      int windowType = Build.VERSION.SDK_INT >= 26 ?
          WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
          WindowManager.LayoutParams.TYPE_PHONE;

      WindowManager.LayoutParams params = new WindowManager.LayoutParams(
          0, 0, windowType,
          WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
          WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

      testView.setLayoutParams(params);
      windowManager.addView(testView, params);
      windowManager.removeView(testView);
      canDraw = true;
    } catch (Exception e) {
      canDraw = false;
    }

    return Settings.canDrawOverlays(this) && canDraw;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE_OVERLAY_PERMISSION && hasOverlayPermission()) {
      ToastUtils.showToast(this, "悬浮窗权限申请成功...");
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  /**
   * 设置值的函数式接口
   */
  private interface SettingValueSetter {

    void setValue(int value);
  }
}