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

/**
 * Activity displayed when an error occurs in the application.
 * Shows error message with options to copy or provide feedback.
 */
public class ErrorActivity extends Activity {

  private TextView errorMessageTextView;
  private Button copyButton;
  private Button feedbackButton;
  private String errorMessage = FrameBodyCOMM.DEFAULT;

  private void initializeViews() {
    Intent intent = getIntent();
    if (intent != null) {
      Bundle extras = intent.getExtras();
      if (extras != null) {
        String message = extras.getString("msg", FrameBodyCOMM.DEFAULT);
        this.errorMessage = message;
        this.errorMessageTextView.setText(message);
      }
    }
    this.copyButton.setOnClickListener(new CopyButtonClickListener(this));
    this.feedbackButton.setOnClickListener(new FeedbackButtonClickListener(this));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    setContentView(R.layout.error);

    this.errorMessageTextView = findViewById(R.id.errorTextView2);
    this.copyButton = findViewById(R.id.errorButton1);
    this.feedbackButton = findViewById(R.id.errorButton2);

    initializeViews();
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    // Handle back button to exit app
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      finishAffinity();  // Properly close all activities
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  class CopyButtonClickListener implements View.OnClickListener {

    final ErrorActivity activity;

    CopyButtonClickListener(ErrorActivity errorActivity) {
      this.activity = errorActivity;
    }

    @Override
    public void onClick(View clickedView) {
      try {
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(
            Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(Mp4DataBox.IDENTIFIER, activity.errorMessage);
        clipboard.setPrimaryClip(clip);
        ToastUtils.showToast(activity, "已复制");
      } catch (Exception e) {
        // Silently fail - clipboard operations are not critical
      }
    }
  }

  class FeedbackButtonClickListener implements View.OnClickListener {

    final ErrorActivity activity;

    FeedbackButtonClickListener(ErrorActivity errorActivity) {
      this.activity = errorActivity;
    }

    @Override
    public void onClick(View clickedView) {
      try {
        Intent intent = new Intent(activity.getApplicationContext(), BrowserActivity.class);
        intent.putExtra("url", "https://shop.ikunshare.com");
        activity.startActivity(intent);
      } catch (Exception e) {
        ToastUtils.showToast(activity, "错误：" + e.getMessage());
      }
    }
  }
}