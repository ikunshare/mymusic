package utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtils {

  private static Toast mInstance;

  public static void showToast(Context context, String str) {
    new Handler(Looper.getMainLooper()).post(new Normal(context, str));
  }

  public static void showToastCenter(Context context, String str) {
    new Handler(Looper.getMainLooper()).post(new Center(context, str));
  }

  static class Normal implements Runnable {

    final Context mContext;

    final String mText;

    Normal(Context context, String str) {
      this.mContext = context;
      this.mText = str;
    }

    @Override
    public void run() {
      if (ToastUtils.mInstance != null) {
        ToastUtils.mInstance.cancel();
      }
      ToastUtils.mInstance = Toast.makeText(this.mContext, this.mText, Toast.LENGTH_LONG);
      ToastUtils.mInstance.show();
    }
  }

  static class Center implements Runnable {

    final Context mContext;

    final String mText;

    Center(Context context, String str) {
      this.mContext = context;
      this.mText = str;
    }

    @Override
    public void run() {
      if (ToastUtils.mInstance != null) {
        ToastUtils.mInstance.cancel();
      }
      ToastUtils.mInstance = Toast.makeText(this.mContext, this.mText, Toast.LENGTH_LONG);
      ToastUtils.mInstance.setGravity(17, 0, 0);
      ToastUtils.mInstance.show();
    }
  }
}