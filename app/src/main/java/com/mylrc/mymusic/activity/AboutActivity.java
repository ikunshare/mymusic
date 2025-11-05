package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mylrc.mymusic.R;
import utils.DialogHelper;
import utils.StatusBarColor;
import utils.StatusBarManager;
import utils.ToastUtils;

/* loaded from: classes.dex */
public class AboutActivity extends Activity {

  RelativeLayout backButton;
  RelativeLayout qqGroupButton;
  RelativeLayout announcementButton;
  RelativeLayout updateButton;
  TextView versionTextView;
  Handler handler = new MessageHandler(this, Looper.getMainLooper());

  public static PackageInfo getPackageInfo(Context context) {
    try {
      return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
    } catch (Exception e2) {
      e2.printStackTrace();
      return null;
    }
  }

  public void initializeViews() {
    this.versionTextView.setText("版本号:" + getPackageInfo(this).versionName);
    this.backButton.setOnClickListener(new BackButtonClickListener(this));
    this.qqGroupButton.setOnClickListener(new QQGroupButtonClickListener(this));
    this.announcementButton.setOnClickListener(new AnnouncementButtonClickListener(this));
  }

  public void openQQGroup(String str) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.setData(Uri.parse(
        "mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3F_wv%3D1027%26k%3DqE_iy7evGGR2XyDOPx2cno1qhxarmRlo%26authKey%3DulcUW4CoJHc3i7t%252BNyK%252FWj8WOuK4pEA1x9tOTqlx9rqL7Va2ab%252BxdajKERPPrJ1L%26noverify%3D0%26group_code%3D951962664"
            + str));
    try {
      startActivity(intent);
    } catch (Exception e2) {
      Toast.makeText(getApplicationContext(), e2.toString(), Toast.LENGTH_LONG).show();
    }
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
    setContentView(R.layout.about);
    this.versionTextView = findViewById(R.id.aboutTextView1);
    this.backButton = findViewById(R.id.aboutRelativeLayout1);
    this.qqGroupButton = findViewById(R.id.aboutRelativeLayout2);
    this.announcementButton = findViewById(R.id.aboutRelativeLayout4);
    this.updateButton = findViewById(R.id.aboutRelativeLayout6);
    initializeViews();
  }

  @Override // android.view.Window.Callback
  public void onPointerCaptureChanged(boolean z2) {
  }

  @Override // android.app.Activity
  protected void onStop() {
    super.onStop();
  }

  class BackButtonClickListener implements View.OnClickListener {

    final AboutActivity activity;

    BackButtonClickListener(AboutActivity aboutActivity) {
      this.activity = aboutActivity;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      this.activity.finish();
    }
  }

  class QQGroupButtonClickListener implements View.OnClickListener {

    final AboutActivity activity;

    QQGroupButtonClickListener(AboutActivity aboutActivity) {
      this.activity = aboutActivity;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      this.activity.openQQGroup("0QPd5uPTINGPimzHvdIfhGQvfd5f-apX");
    }
  }

  class AnnouncementButtonClickListener implements View.OnClickListener {

    final AboutActivity activity;

    AnnouncementButtonClickListener(AboutActivity aboutActivity) {
      this.activity = aboutActivity;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      DialogHelper.showDialog(this.activity, "提示",
          "本软件为修改版歌词适配，希望大家不要到处乱发。本人授权可转发至公开平台者：科技长青，YHY分享，星之墨辰，宝藏分享",
          "确定", true);
    }
  }

  class MessageHandler extends Handler {

    final AboutActivity activity;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MessageHandler(AboutActivity aboutActivity, Looper looper) {
      super(looper);
      this.activity = aboutActivity;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i2 = message.what;
      if (i2 != 1 && i2 == 0) {
        ToastUtils.showToast(this.activity, message.obj.toString());
      }
      super.handleMessage(message);
    }
  }
}