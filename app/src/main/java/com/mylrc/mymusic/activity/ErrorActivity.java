package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.enums.StatusBarColor;
import com.mylrc.mymusic.manager.StatusBarManager;
import com.mylrc.mymusic.utils.ToastUtils;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.mp4.atom.Mp4DataBox;

/* loaded from: classes.dex */
public class ErrorActivity extends Activity {

  private TextView errorMessageTextView;
  private Button copyButton;
  private Button feedbackButton;
  private String errorMessage = FrameBodyCOMM.DEFAULT;

  public void initializeViews() {
    Bundle extras;
    Intent intent = getIntent();
    if (intent != null && (extras = intent.getExtras()) != null) {
      String string = extras.getString("msg", FrameBodyCOMM.DEFAULT);
      this.errorMessage = string;
      this.errorMessageTextView.setText(string);
    }
    this.copyButton.setOnClickListener(new CopyButtonClickListener(this));
    this.feedbackButton.setOnClickListener(new FeedbackButtonClickListener(this));
  }

  @Override // android.app.Activity
  protected void onCreate(Bundle bundle) {
    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    setContentView(R.layout.error);
    this.errorMessageTextView = findViewById(R.id.errorTextView2);
    this.copyButton = findViewById(R.id.errorButton1);
    this.feedbackButton = findViewById(R.id.errorButton2);
    initializeViews();
    super.onCreate(bundle);
  }

  @Override // android.app.Activity, android.view.KeyEvent.Callback
  public boolean onKeyDown(int i2, KeyEvent keyEvent) {
    if (i2 != 4) {
      return true;
    }
    System.exit(0);
    return true;
  }

  class CopyButtonClickListener implements View.OnClickListener {

    final ErrorActivity activity;

    CopyButtonClickListener(ErrorActivity errorActivity) {
      this.activity = errorActivity;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      try {
        ((ClipboardManager) this.activity.getSystemService(
            Context.CLIPBOARD_SERVICE)).setPrimaryClip(
            ClipData.newPlainText(Mp4DataBox.IDENTIFIER, this.activity.errorMessage));
        ToastUtils.showToast(this.activity, "已复制");
      } catch (Exception ignored) {
      }
    }
  }

  class FeedbackButtonClickListener implements View.OnClickListener {

    final ErrorActivity activity;

    FeedbackButtonClickListener(ErrorActivity errorActivity) {
      this.activity = errorActivity;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      try {
        Intent intent = new Intent(this.activity.getApplicationContext(), BrowserActivity.class);
        intent.putExtra("url", "https://shop.ikunshare.com");
        this.activity.startActivity(intent);
      } catch (Exception e2) {
        ToastUtils.showToast(this.activity, "错误：" + e2);
      }
    }
  }
}