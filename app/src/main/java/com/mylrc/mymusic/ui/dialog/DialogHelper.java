package com.mylrc.mymusic.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.mylrc.mymusic.R;
import java.util.Objects;

public class DialogHelper {

  public static void showDialog(Activity activity, String title, String message, String buttonText,
      boolean cancelable) {
    activity.runOnUiThread(new DialogRunnable(activity, cancelable, title, message, buttonText));
  }

  static class DialogRunnable implements Runnable {

    private final Activity mActivity;
    private final boolean mCancelable;
    private final String mTitle;
    private final String mMessage;
    private final String mButtonText;

    DialogRunnable(Activity activity, boolean cancelable, String title, String message,
        String buttonText) {
      this.mActivity = activity;
      this.mCancelable = cancelable;
      this.mTitle = title;
      this.mMessage = message;
      this.mButtonText = buttonText;
    }

    @Override
    public void run() {
      Dialog dialog = new Dialog(mActivity);
      dialog.setCancelable(mCancelable);
      View viewInflate = LayoutInflater.from(mActivity).inflate(R.layout.dialog, null);
      dialog.setContentView(viewInflate);

      // 设置对话框宽度
      Display defaultDisplay = mActivity.getWindowManager().getDefaultDisplay();
      WindowManager.LayoutParams attributes = Objects.requireNonNull(dialog.getWindow())
          .getAttributes();
      attributes.width = defaultDisplay.getWidth();
      dialog.getWindow().setAttributes(attributes);

      // 初始化视图组件
      TextView titleTextView = viewInflate.findViewById(R.id.dialogTextView1);
      TextView messageTextView = viewInflate.findViewById(R.id.dialogTextView2);
      Button positiveButton = viewInflate.findViewById(R.id.dialogButton1);
      Button negativeButton = viewInflate.findViewById(R.id.dialogButton2);

      // 设置文本内容
      titleTextView.setText(mTitle);
      messageTextView.setText(mMessage);
      positiveButton.setText(mButtonText);

      // 隐藏第二个按钮
      negativeButton.setVisibility(View.GONE);

      // 设置按钮点击事件
      positiveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          dialog.dismiss();
        }
      });

      // 只有在Activity未被销毁时才显示对话框
      if (!mActivity.isFinishing() && !mActivity.isDestroyed()) {
        dialog.show();
      }
    }
  }
}