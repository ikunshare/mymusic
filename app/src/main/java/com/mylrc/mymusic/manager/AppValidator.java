package com.mylrc.mymusic.manager;

import android.app.Dialog;
import android.content.Context;
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
import com.mylrc.mymusic.network.DownloadUtils;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;

public class AppValidator {

  private static String banMessage;

  private final Context context;
  private final Dialog validationDialog;
  private final Handler validationHandler = new ValidationHandler(this, Looper.getMainLooper());
  private String validationStatus;

  public AppValidator(Context context) {
    this.context = context;
    this.validationDialog = new Dialog(context, R.style.dialog);
  }

  public void validate() {
    new ValidateThread(this).start();
  }

  static class ValidateThread extends Thread {

    private final AppValidator validator;

    ValidateThread(AppValidator validator) {
      this.validator = validator;
    }

    @Override
    public void run() {
      try {
        String validationData = DownloadUtils.getYoudaoNote("976be46aa84e2ec177615514865a5cf0");

        if (validationData == null || validationData.isEmpty()) {
          return;
        }

        int statusStart = validationData.indexOf("【");
        int statusEnd = validationData.indexOf("】");
        if (statusStart == -1 || statusEnd == -1 || statusEnd <= statusStart) {
          return;
        }
        this.validator.validationStatus = validationData.substring(statusStart + 1, statusEnd);

        int msgStart = validationData.indexOf("〖");
        int msgEnd = validationData.indexOf("〗");
        if (msgStart == -1 || msgEnd == -1 || msgEnd <= msgStart) {
          return;
        }
        AppValidator.banMessage = validationData.substring(msgStart + 1, msgEnd);

        if ("禁止".equals(this.validator.validationStatus)) {
          Message message = new Message();
          message.what = 1;
          message.obj = AppValidator.banMessage;
          this.validator.validationHandler.sendMessage(message);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  static class ValidationHandler extends Handler {

    private final AppValidator validator;

    ValidationHandler(AppValidator validator, Looper looper) {
      super(looper);
      this.validator = validator;
    }

    @Override
    public void handleMessage(Message message) {
      if (message.what == 1 && !this.validator.validationDialog.isShowing()) {
        try {
          this.validator.validationDialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
          this.validator.validationDialog.getWindow().setGravity(80);
          this.validator.validationDialog.setCancelable(false);
          this.validator.validationDialog.requestWindowFeature(1);

          View dialogView = LayoutInflater.from(this.validator.context).inflate(
              R.layout.dialog,
              null
          );

          this.validator.validationDialog.show();
          this.validator.validationDialog.setContentView(dialogView);

          Display display = ((WindowManager) this.validator.context.getSystemService(
              Context.WINDOW_SERVICE))
              .getDefaultDisplay();
          WindowManager.LayoutParams layoutParams = this.validator.validationDialog.getWindow()
              .getAttributes();
          layoutParams.width = display.getWidth();
          this.validator.validationDialog.getWindow().setAttributes(layoutParams);

          TextView titleTextView = dialogView.findViewById(R.id.dialogTextView1);
          TextView messageTextView = dialogView.findViewById(R.id.dialogTextView2);
          Button exitButton = dialogView.findViewById(R.id.dialogButton1);
          Button cancelButton = dialogView.findViewById(R.id.dialogButton2);

          exitButton.setText("退出");
          cancelButton.setVisibility(View.GONE);
          messageTextView.setText("提示");
          titleTextView.setText(message.obj + FrameBodyCOMM.DEFAULT);

          exitButton.setOnClickListener(new ExitButtonClickListener(this));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      super.handleMessage(message);
    }

    static class ExitButtonClickListener implements View.OnClickListener {

      private final ValidationHandler handler;

      ExitButtonClickListener(ValidationHandler handler) {
        this.handler = handler;
      }

      @Override
      public void onClick(View view) {
        if (this.handler.validator.context instanceof android.app.Activity) {
          ((android.app.Activity) this.handler.validator.context).finishAffinity();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
      }
    }
  }
}