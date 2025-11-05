package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.enums.StatusBarColor;
import com.mylrc.mymusic.manager.StatusBarManager;
import com.mylrc.mymusic.network.HttpRequestUtils;
import com.mylrc.mymusic.network.OkHttpClient;
import com.mylrc.mymusic.tool.APPApplication;
import com.mylrc.mymusic.utils.ToastUtils;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import okhttp3.Request;
import org.json.JSONObject;


public class DonateActivity extends Activity {

  final Handler handler = new MessageHandler(this, Looper.getMainLooper());
  Button openButton;
  Dialog progressDialog;
  ImageView qrCodeImageView;
  Bitmap qrCodeBitmap;
  String paymentType;
  String alipayCode;

  private void showProgressDialog() {
    Dialog dialog = new Dialog(this);
    this.progressDialog = dialog;
    Objects.requireNonNull(dialog.getWindow()).setWindowAnimations(R.style.loadingAnim);
    this.progressDialog.requestWindowFeature(1);
    this.progressDialog.setContentView(R.layout.loading);
    this.progressDialog.setCancelable(false);
    this.progressDialog.show();
  }

  public void showToast(String str) {
    Message message = new Message();
    message.what = 0;
    message.obj = str;
    this.handler.sendMessage(message);
  }

  public void sendMessage(int i2) {
    Message message = new Message();
    message.what = i2;
    this.handler.sendMessage(message);
  }

  public void downloadQRCode(String str) {
    try {
      this.qrCodeBitmap = BitmapFactory.decodeStream(
          OkHttpClient.getInstance()
              .newCall(new Request.Builder()
                  .url(str)
                  .header("User-Agent", APPApplication.userAgent)
                  .build())
              .execute()
              .body()
              .byteStream()
      );
      sendMessage(1);
    } catch (Exception e2) {
      sendMessage(2);
    }
  }

  public void loadQRCode(String str) {
    new LoadQRCodeThread(this, str).start();
  }

  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    setContentView(R.layout.donate);
    this.openButton = findViewById(R.id.zzButton1);
    this.qrCodeImageView = findViewById(R.id.zzImageView1);
    this.paymentType = getIntent().getStringExtra("type");
    findViewById(R.id.zzRelativeLayout1).setOnClickListener(new BackButtonClickListener(this));
    this.openButton.setOnClickListener(new OpenButtonClickListener(this));
    showProgressDialog();
    loadQRCode(this.paymentType);
  }

  class BackButtonClickListener implements View.OnClickListener {

    final DonateActivity activity;

    BackButtonClickListener(DonateActivity donateActivity) {
      this.activity = donateActivity;
    }

    @Override
    public void onClick(View view) {
      this.activity.finish();
    }
  }

  class OpenButtonClickListener implements View.OnClickListener {

    final DonateActivity activity;

    OpenButtonClickListener(DonateActivity donateActivity) {
      this.activity = donateActivity;
    }

    @Override
    public void onClick(View view) {
      Intent uri = new Intent();
      try {
        if (this.activity.paymentType.equals("wx")) {
          uri.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
          uri.putExtra("LauncherUI.From.Scaner.Shortcut", true);
          uri.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        } else {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            uri = Intent.parseUri(
                ("alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode="
                    + URLEncoder.encode(this.activity.alipayCode, StandardCharsets.UTF_8))
                    + "%3F_s%3Dweb-other", Intent.URI_INTENT_SCHEME);
          }
        }
        this.activity.startActivity(uri);
      } catch (Exception e2) {
        this.activity.showToast("哎呀 出现了一些小错误 可能是因为没有安装相关应用的原因");
      }
    }
  }

  class MessageHandler extends Handler {

    final DonateActivity activity;

    MessageHandler(DonateActivity donateActivity, Looper looper) {
      super(looper);
      this.activity = donateActivity;
    }

    @Override
    public void handleMessage(Message message) {
      int i2 = message.what;
      if (i2 == 0) {
        ToastUtils.showToast(this.activity, message.obj.toString());
      } else {
        if (i2 == 1) {
          this.activity.openButton.setEnabled(true);
          this.activity.openButton.setText("打开扫一扫");
          DonateActivity donateActivity = this.activity;
          donateActivity.qrCodeImageView.setImageBitmap(donateActivity.qrCodeBitmap);
        } else if (this.activity.progressDialog.isShowing()) {
        }
        this.activity.progressDialog.dismiss();
      }
      super.handleMessage(message);
    }
  }

  class LoadQRCodeThread extends Thread {

    final String paymentType;
    final DonateActivity activity;

    LoadQRCodeThread(DonateActivity donateActivity, String str) {
      this.activity = donateActivity;
      this.paymentType = str;
    }

    @Override
    public void run() {
      JSONObject jSONObject = new JSONObject();
      try {
        jSONObject.put("type", this.paymentType);
        new HttpRequestUtils();
        JSONObject jSONObject2 = new JSONObject(
            HttpRequestUtils.postBytes("http://api.ikunshare.com/client/cgi-bin/zz",
                jSONObject.toString().getBytes()));
        this.activity.downloadQRCode(jSONObject2.getString("url"));
        if (this.paymentType.equals("zfb")) {
          this.activity.alipayCode = jSONObject2.getString("text");
        }
      } catch (Exception e2) {
        this.activity.sendMessage(2);
      }
    }
  }
}