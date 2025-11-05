package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.mylrc.mymusic.R;
import utils.StatusBarColor;
import utils.StatusBarManager;
import utils.ToastUtils;


public class WYYLoginActivity extends Activity {

  Dialog progressDialog;
  Handler handler = new MessageHandler(this, Looper.getMainLooper());

  private void showWarningDialog() {
    Dialog dialog = new Dialog(this, R.style.dialog);
    dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
    dialog.getWindow().setGravity(80);
    dialog.setCancelable(false);
    dialog.requestWindowFeature(1);
    View viewInflate = LayoutInflater.from(this).inflate(R.layout.dialog, null);
    if (!isFinishing()) {
      dialog.show();
    }
    dialog.setContentView(viewInflate);
    Display defaultDisplay = getWindowManager().getDefaultDisplay();
    WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
    attributes.width = defaultDisplay.getWidth();
    dialog.getWindow().setAttributes(attributes);
    TextView textView = viewInflate.findViewById(R.id.dialogTextView1);
    TextView textView2 = viewInflate.findViewById(R.id.dialogTextView2);
    Button button = viewInflate.findViewById(R.id.dialogButton1);
    Button button2 = viewInflate.findViewById(R.id.dialogButton2);
    textView2.setText("温馨提示");
    textView.setText(
        "请不要在一天内频繁的注销与登录，否则会被网易云判定为异常帐号（会被暂定冻结），如果愿意接受风险，请点击确定 即可继续操作，不愿意接受风险请点取消。");
    button.setText("确定");
    button.setOnClickListener(new ConfirmButtonClickListener(this, dialog));
    button2.setOnClickListener(new CancelButtonClickListener(this, dialog));
  }

  public void showProgressDialog() {
    if (isFinishing()) {
      return;
    }
    Dialog dialog = new Dialog(this);
    this.progressDialog = dialog;
    dialog.requestWindowFeature(1);
    this.progressDialog.getWindow().setWindowAnimations(R.style.loadingAnim);
    this.progressDialog.setContentView(R.layout.loading);
    this.progressDialog.setCancelable(false);
    this.progressDialog.show();
  }

  @Override // android.app.Activity
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    showWarningDialog();
  }

  class MessageHandler extends Handler {

    final WYYLoginActivity activity;

    MessageHandler(WYYLoginActivity WYYLoginActivity, Looper looper) {
      super(looper);
      this.activity = WYYLoginActivity;
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

  class ConfirmButtonClickListener implements View.OnClickListener {

    final Dialog dialog;
    final WYYLoginActivity activity;

    ConfirmButtonClickListener(WYYLoginActivity WYYLoginActivity, Dialog dialog) {
      this.activity = WYYLoginActivity;
      this.dialog = dialog;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      this.dialog.dismiss();
      Intent intent = new Intent(this.activity.getApplicationContext(), BrowserActivity.class);
      intent.putExtra("url",
          "https://music.163.com/m/login?redirect_url=%2Fprime%2Fm%2Fpurchase#/phone");
      this.activity.startActivity(intent);
      this.activity.finish();
    }
  }

  class CancelButtonClickListener implements View.OnClickListener {

    final Dialog dialog;
    final WYYLoginActivity activity;

    CancelButtonClickListener(WYYLoginActivity WYYLoginActivity, Dialog dialog) {
      this.activity = WYYLoginActivity;
      this.dialog = dialog;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      this.dialog.dismiss();
      this.activity.finish();
    }
  }
}