package com.mylrc.mymusic.utils;

import android.util.Log;
import com.mylrc.mymusic.network.HttpRequestUtils;
import org.json.JSONObject;

public class TimeUtils {

  public static String getNetworkTime() {
    try {
      return new JSONObject(HttpRequestUtils.get("http://quan.suning.com/getSysTime.do")).getString(
          "sysTime2");
    } catch (Exception e2) {
      Log.e("TimeUtils", "getNetworkTime failed: " + e2.getMessage());
      return null;
    }
  }
}