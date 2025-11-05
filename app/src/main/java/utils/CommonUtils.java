package utils;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.tool.APPApplication;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.json.JSONException;
import org.json.JSONObject;

public class CommonUtils {

  public static String md5(String str) {
    if (!TextUtils.isEmpty(str)) {
      try {
        byte[] bArrDigest = MessageDigest.getInstance("MD5")
            .digest(str.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b2 : bArrDigest) {
          String hexString = Integer.toHexString(b2 & 255);
          if (hexString.length() == 1) {
            hexString = "0" + hexString;
          }
          sb.append(hexString);
        }
        return sb.toString();
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
    return FrameBodyCOMM.DEFAULT;
  }

  public static String getDeviceInfo() {
    JSONObject jSONObject = new JSONObject();
    try {
      if (APPApplication.versionName != null) {
        jSONObject.put("clientversion", APPApplication.versionName);
      }
      jSONObject.put("vercode", APPApplication.versionCode);
      if (APPApplication.deviceId != null) {
        jSONObject.put("devid", APPApplication.deviceId);
      }
      jSONObject.put("brand", Build.BRAND);
      jSONObject.put("model", Build.MODEL);
      jSONObject.put("osversion", Build.VERSION.RELEASE);
      jSONObject.put("clienttime", System.currentTimeMillis());
      return jSONObject.toString();
    } catch (JSONException e2) {
      e2.printStackTrace();
      return FrameBodyCOMM.DEFAULT;
    }
  }

  public static String formatFileSize(String str) {
    if (str == null || !isNumeric(str) || str.equals("0")) {
      return FrameBodyCOMM.DEFAULT;
    }
    String str2 =
        new DecimalFormat(".00").format((Double.parseDouble(str) / 1024.0d) / 1024.0d) + "MB";
    if (str2.length() != 5) {
      return str2;
    }
    return "0" + str2;
  }

  public static void installApk(Context context, String str) {
    Uri uriFromFile;
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    uriFromFile = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider",
        new File(str));
    intent.setDataAndType(uriFromFile, "application/vnd.android.package-archive");
    context.startActivity(intent);
  }

  public static boolean checkNotificationPermission(Context context) {
    NotificationManagerCompat manager = NotificationManagerCompat.from(context);
    return manager.areNotificationsEnabled();
  }

  public static boolean isNumeric(String str) {
    return Pattern.compile("[0-9]*").matcher(str).matches();
  }

  public static boolean isServiceRunning(Context context, String str) throws SecurityException {
    List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService(
        Context.ACTIVITY_SERVICE)).getRunningServices(200);
    if (runningServices.isEmpty()) {
      return false;
    }
    for (ActivityManager.RunningServiceInfo runningService : runningServices) {
      if (runningService.service.getClassName().equals(str)) {
        return true;
      }
    }
    return false;
  }

  public static void showNotificationPermissionDialog(Context context) {
    Dialog dialog = new Dialog(context, R.style.dialog);
    Objects.requireNonNull(dialog.getWindow()).setGravity(80);
    dialog.requestWindowFeature(1);
    View viewInflate = LayoutInflater.from(context).inflate(R.layout.dialog, null);
    dialog.show();
    dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
    dialog.setContentView(viewInflate);
    WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
    attributes.width = -1;
    dialog.getWindow().setAttributes(attributes);
    TextView textView = viewInflate.findViewById(R.id.dialogTextView1);
    TextView textView2 = viewInflate.findViewById(R.id.dialogTextView2);
    Button button = viewInflate.findViewById(R.id.dialogButton1);
    Button button2 = viewInflate.findViewById(R.id.dialogButton2);
    textView2.setText("提示");
    textView.setText(
        "请先授权显示通知栏权限,才可以使用\n方法指引:点击去授权,找到通知管理,然后允许通知打开即可");
    button.setText("去授权");
    button2.setVisibility(View.GONE);
    button.setOnClickListener(new viewOnlickListener(context, dialog));
  }

  public static String formatTime(int i2) {
    int i3 = (i2 / 60) % 60;
    int i4 = i2 % 60;
    String minuteStr;
    if (i3 <= 9) {
      minuteStr = "0" + i3;
    } else {
      minuteStr = String.valueOf(i3);
    }
    String separator = i4 > 9 ? ":" : ":0";
    return minuteStr + separator + i4;
  }

  static class viewOnlickListener implements View.OnClickListener {

    final Context mContext;
    final Dialog mDialog;

    public viewOnlickListener(Context context, Dialog dialog) {
      this.mContext = context;
      this.mDialog = dialog;
    }

    @Override
    public void onClick(View view) {
      this.mContext.startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS",
          Uri.parse("package:com.mylrc.mymusic")));
      this.mDialog.dismiss();
    }
  }
}