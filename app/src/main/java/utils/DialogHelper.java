package utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.mylrc.mymusic.R;

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
      Dialog helpDialog = new DialogFactory().createDialog(mActivity);
      View helpView = LayoutInflater.from(mActivity)
          .inflate(R.layout.dialog, null);

      TextView tvTitle = helpView.findViewById(R.id.dialogTextView2);
      TextView tvContent = helpView.findViewById(R.id.dialogTextView1);
      Button btnConfirm = helpView.findViewById(R.id.dialogButton1);
      Button btnCancel = helpView.findViewById(R.id.dialogButton2);

      tvTitle.setText(mTitle);
      tvContent.setText(mMessage);
      btnConfirm.setText(mButtonText);
      if (!mCancelable) {
        btnCancel.setVisibility(View.GONE);
      }

      helpDialog.show();
      helpDialog.setContentView(helpView);

      // 设置对话框宽度
      Display display = mActivity.getWindowManager().getDefaultDisplay();
      WindowManager.LayoutParams params = helpDialog.getWindow().getAttributes();
      params.width = display.getWidth();
      helpDialog.getWindow().setAttributes(params);

      btnConfirm.setOnClickListener(v -> helpDialog.dismiss());
    }
  }
}