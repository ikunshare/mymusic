package com.mylrc.mymusic.tool;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;
import com.mylrc.mymusic.exception.CrashHandler;
import com.mylrc.mymusic.manager.ImageLoaderConfig;
import com.mylrc.mymusic.utils.CommonUtils;
import me.sukimon.utils.DeviceVerifyUtils;


public class APPApplication extends Application {

  public static SharedPreferences sharedPreferences;
  public static String deviceId;
  public static String versionName;
  public static String versionCode;
  public static String userAgent;

  public Context context;

  public static String getDeviceBrandModel() {
    String brand = Build.BRAND;
    String model = Build.MODEL;
    if (model.toLowerCase().contains(brand.toLowerCase())) {
      return model;
    }
    return brand + " " + model;
  }

  private static PackageInfo getPackageInfo(Context context) {
    try {
      return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private String generateDeviceId(Context context) {
    StringBuilder sb = new StringBuilder();
    try {
      sb.append(DeviceVerifyUtils.getUniqueDeviceRandomId(context));
    } catch (Exception ignored) {
    }
    sb.append(Build.BRAND);
    sb.append(Build.MODEL);
    return CommonUtils.createMD5(sb.toString());
  }

  private String getVersionCode() {
    return getPackageInfo(context).versionCode + "";
  }

  private String getVersionName() {
    return getPackageInfo(context).versionName;
  }

  public String getUserAgent() {
    String userAgent = System.getProperty("http.agent");
    if (TextUtils.isEmpty(userAgent)) {
      userAgent = WebSettings.getDefaultUserAgent(context);
    }
    return userAgent;
  }

  @Override
  public Resources getResources() {
    Resources resources = super.getResources();
    Configuration configuration = new Configuration();
    configuration.setToDefaults();
    resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    return resources;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
    deviceId = generateDeviceId(context);
    sharedPreferences = context.getSharedPreferences("pms", 0);
    userAgent = getUserAgent();
    getDeviceBrandModel();
    CrashHandler.getInstance().init(context, this);
    versionName = getVersionName();
    versionCode = getVersionCode();
    ImageLoaderConfig.initImageLoader(context);
  }
}