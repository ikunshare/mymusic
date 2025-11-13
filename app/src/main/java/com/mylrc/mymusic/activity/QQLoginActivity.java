package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.enums.StatusBarColor;
import com.mylrc.mymusic.manager.StatusBarManager;
import com.mylrc.mymusic.utils.ToastUtils;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;

/**
 * Activity for QQ Music login.
 * Allows users to enter their QQ number to access their playlists.
 */
public class QQLoginActivity extends Activity {

  private static final int MSG_SHOW_TOAST = 0;
  private static final int MSG_SHOW_PROGRESS = 2;
  private static final int MSG_HIDE_PROGRESS = 3;

  private final Handler handler = new MessageHandler(this, Looper.getMainLooper());
  private SharedPreferences preferences;
  private EditText qqNumberEditText;
  private Button confirmButton;
  private Dialog progressDialog;
  private String qqNumber;

  private void initializeViews() {
    this.preferences = getSharedPreferences("pms", 0);
    loadSavedQQNumber();
    this.confirmButton.setOnClickListener(new ConfirmButtonClickListener(this));
    findViewById(R.id.qqloginRelativeLayout1).setOnClickListener(new BackButtonClickListener(this));
  }

  void saveAndStartPlaylist(String qqNumber) {
    this.preferences.edit().putString("qquin", qqNumber).apply();
    startActivity(new Intent(getApplicationContext(), QQPlayListActivity.class));
    finish();
  }

  void showToast(String message) {
    Message msg = Message.obtain();
    msg.what = MSG_SHOW_TOAST;
    msg.obj = message;
    this.handler.sendMessage(msg);
  }

  private void loadSavedQQNumber() {
    String savedQQNumber = this.preferences.getString("qquin", FrameBodyCOMM.DEFAULT);
    this.qqNumberEditText.setText(savedQQNumber);
  }

  public void showProgressDialog() {
    progressDialog = new Dialog(this);
    progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    progressDialog.getWindow().setWindowAnimations(R.style.loadingAnim);
    progressDialog.setContentView(R.layout.loading);
    progressDialog.setCancelable(false);
    progressDialog.show();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    setContentView(R.layout.qqlogin);
    this.qqNumberEditText = findViewById(R.id.qqloginEditText1);
    this.confirmButton = findViewById(R.id.qqloginButton1);
    initializeViews();
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    loadSavedQQNumber();
  }

  static class ConfirmButtonClickListener implements View.OnClickListener {

    final QQLoginActivity activity;

    ConfirmButtonClickListener(QQLoginActivity activity) {
      this.activity = activity;
    }

    @Override
    public void onClick(View clickedView) {
      String qqNumber = activity.qqNumberEditText.getText().toString();
      activity.qqNumber = qqNumber;

      // Validate QQ number (6-10 digits)
      if (qqNumber.isEmpty() || qqNumber.length() < 6 || qqNumber.length() > 10) {
        activity.showToast("填写不正确哦～");
      } else {
        activity.saveAndStartPlaylist(qqNumber);
      }
    }
  }

  class BackButtonClickListener implements View.OnClickListener {

    final QQLoginActivity activity;

    BackButtonClickListener(QQLoginActivity activity) {
      this.activity = activity;
    }

    @Override
    public void onClick(View clickedView) {
      activity.finish();
    }
  }

  class MessageHandler extends Handler {

    final QQLoginActivity activity;

    MessageHandler(QQLoginActivity activity, Looper looper) {
      super(looper);
      this.activity = activity;
    }

    @Override
    public void handleMessage(Message message) {
      int messageType = message.what;

      if (messageType == MSG_SHOW_TOAST) {
        ToastUtils.showToast(activity, message.obj.toString());

      } else if (messageType == MSG_SHOW_PROGRESS) {
        activity.showProgressDialog();

      } else if (messageType == MSG_HIDE_PROGRESS) {
        Dialog dialog = activity.progressDialog;
        if (dialog != null && dialog.isShowing()) {
          dialog.dismiss();
        }
      }

      super.handleMessage(message);
    }
  }
}