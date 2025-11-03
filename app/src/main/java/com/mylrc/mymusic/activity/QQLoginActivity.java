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
import android.widget.Button;
import android.widget.EditText;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.enums.StatusBarColor;
import com.mylrc.mymusic.manager.StatusBarManager;
import com.mylrc.mymusic.utils.ToastUtils;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;

/* loaded from: classes.dex */
public class QQLoginActivity extends Activity {

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

  /* JADX INFO: Access modifiers changed from: private */
  public void saveAndStartPlaylist(String str) {
    this.preferences.edit().putString("qquin", str).commit();
    startActivity(new Intent(getApplicationContext(), QQPlayListActivity.class));
    finish();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void showToast(String str) {
    Message message = new Message();
    message.what = 0;
    message.obj = str;
    this.handler.sendMessage(message);
  }

  private void loadSavedQQNumber() {
    this.qqNumberEditText.setText(this.preferences.getString("qquin", FrameBodyCOMM.DEFAULT));
  }

  public void showProgressDialog() {
    Dialog dialog = new Dialog(this);
    this.progressDialog = dialog;
    dialog.requestWindowFeature(1);
    this.progressDialog.getWindow().setWindowAnimations(R.style.LoadingAnim);
    this.progressDialog.setContentView(R.layout.loading);
    this.progressDialog.setCancelable(false);
    this.progressDialog.show();
  }

  @Override // android.app.Activity
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    setContentView(R.layout.qqlogin);
    this.qqNumberEditText = findViewById(R.id.qqloginEditText1);
    this.confirmButton = findViewById(R.id.qqloginButton1);
    initializeViews();
  }

  @Override // android.app.Activity
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override // android.app.Activity
  protected void onRestart() {
    loadSavedQQNumber();
    super.onRestart();
  }

  static class ConfirmButtonClickListener implements View.OnClickListener {

    final QQLoginActivity activity;

    ConfirmButtonClickListener(QQLoginActivity qqLoginActivity) {
      this.activity = qqLoginActivity;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      QQLoginActivity qqLoginActivity = this.activity;
      qqLoginActivity.qqNumber = qqLoginActivity.qqNumberEditText.getText().toString();
      if (this.activity.qqNumber.equals(FrameBodyCOMM.DEFAULT)
          || this.activity.qqNumber.length() < 6 || this.activity.qqNumber.length() > 10) {
        this.activity.showToast("填写不正确哦～");
      } else {
        QQLoginActivity qqLoginActivity2 = this.activity;
        qqLoginActivity2.saveAndStartPlaylist(qqLoginActivity2.qqNumber);
      }
    }
  }

  class BackButtonClickListener implements View.OnClickListener {

    final QQLoginActivity activity;

    BackButtonClickListener(QQLoginActivity qqLoginActivity) {
      this.activity = qqLoginActivity;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      this.activity.finish();
    }
  }

  class MessageHandler extends Handler {

    final QQLoginActivity activity;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MessageHandler(QQLoginActivity qqLoginActivity, Looper looper) {
      super(looper);
      this.activity = qqLoginActivity;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      Dialog dialog;
      int i2 = message.what;
      if (i2 == 0) {
        ToastUtils.showToast(this.activity, message.obj.toString());
      } else if (i2 == 2) {
        this.activity.showProgressDialog();
      } else if (i2 == 3 && (dialog = this.activity.progressDialog) != null && dialog.isShowing()) {
        this.activity.progressDialog.dismiss();
      }
      super.handleMessage(message);
    }
  }
}