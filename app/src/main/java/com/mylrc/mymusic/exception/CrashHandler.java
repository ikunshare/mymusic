package com.mylrc.mymusic.exception;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import com.mylrc.mymusic.activity.ErrorActivity;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

  private static final CrashHandler instance = new CrashHandler();
  private static Thread crashThread;
  private static Throwable crashException;
  private Thread.UncaughtExceptionHandler defaultHandler;

  private Context mContext;

  private CrashHandler() {
  }

  public static CrashHandler getInstance() {
    return instance;
  }

  public static Thread getCrashThread() {
    return crashThread;
  }

  public static Throwable getCrashException() {
    return crashException;
  }

  public static void clearCrashInfo() {
    crashThread = null;
    crashException = null;
  }

  public static String getStackTraceString(Throwable th) {
    try {
      StringWriter stringWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(stringWriter);
      th.printStackTrace(printWriter);
      printWriter.close();
      return stringWriter.toString();
    } catch (Exception e) {
      return FrameBodyCOMM.DEFAULT;
    }
  }

  public void init(Context context, Application application) {
    this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
    this.mContext = context.getApplicationContext();
  }

  private String getDeviceInfo() {
    try {
      PackageInfo packageInfo = this.mContext.getPackageManager()
          .getPackageInfo(this.mContext.getPackageName(), 1);

      String stringBuffer = "APP Version: " +
          packageInfo.versionName +
          "\n" +
          "OS Version: " +
          Build.VERSION.RELEASE +
          "_" +
          Build.VERSION.SDK_INT +
          "\n" +
          "Model: " +
          Build.MANUFACTURER + " " + Build.MODEL +
          "\n" +
          "CPU ABI: " +
          Build.CPU_ABI +
          "\n" +
          "Crash Time: " +
          getCurrentTime() +
          "\n\n";

      return stringBuffer;
    } catch (PackageManager.NameNotFoundException e) {
      return FrameBodyCOMM.DEFAULT;
    }
  }

  private String getCurrentTime() {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
  }

  @Override
  public void uncaughtException(Thread thread, Throwable th) {
    crashThread = thread;
    crashException = th;

    try {
      Intent intent = new Intent(this.mContext, ErrorActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      Bundle bundle = new Bundle();
      bundle.putString("msg", getDeviceInfo() + getStackTraceString(th));
      intent.putExtras(bundle);
      this.mContext.startActivity(intent);

      android.os.SystemClock.sleep(300);
    } catch (Exception e) {
      e.printStackTrace();
    }

    Process.killProcess(Process.myPid());
    System.exit(10);
  }
}