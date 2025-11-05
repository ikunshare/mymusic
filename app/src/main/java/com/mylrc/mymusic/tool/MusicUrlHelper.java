package com.mylrc.mymusic.tool;

import android.os.Build;
import com.mylrc.mymusic.network.HttpRequestUtils;
import com.mylrc.mymusic.utils.ZipUtils;
import java.io.IOException;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.mp4.atom.Mp4DataBox;
import org.json.JSONException;
import org.json.JSONObject;

public class MusicUrlHelper {

  static {
    System.loadLibrary("Mverify");
  }

  private final String apiUrl = "http://api.ikunshare.com/client/cgi-bin/api.fcg";

  private byte[] encryptData(String data) throws IOException {
    return ZipUtils.compress(data.getBytes());
  }

  private String parseResponse(String response) {
    try {
      JSONObject json = new JSONObject(response);
      String code = json.getString("code");

      if (code.equals("200")) {
        return json.getString(Mp4DataBox.IDENTIFIER);
      } else {
        return json.getString("error_msg");
      }
    } catch (JSONException e) {
      return response;
    }
  }

  private String buildDeviceInfo() throws JSONException {
    return buildDeviceInfoJson(
        APPApplication.sharedPreferences.getString("uin", FrameBodyCOMM.DEFAULT),
        APPApplication.sharedPreferences.getString("token", FrameBodyCOMM.DEFAULT),
        APPApplication.deviceId,
        APPApplication.versionName,
        Build.BRAND + " " + Build.MODEL,
        APPApplication.versionCode
    );
  }

  private String getKugouMusicUrl(String songId, String quality) throws JSONException, IOException {
    String lowerCaseSongId = songId.toLowerCase();

    return parseResponse(HttpRequestUtils.postBytes(
        this.apiUrl,
        encryptData(tmc(buildRequestJson("kugou", lowerCaseSongId, quality), buildDeviceInfo()))
    ));
  }

  private String getKuwoMusicUrl(String songId, String quality) throws JSONException, IOException {

    return parseResponse(HttpRequestUtils.postBytes(
        this.apiUrl,
        encryptData(tmc(buildRequestJson("kuwo", songId, quality), buildDeviceInfo()))
    ));
  }

  private String getMiguMusicUrl(String songId, String quality) throws JSONException, IOException {

    return parseResponse(HttpRequestUtils.postBytes(
        this.apiUrl,
        encryptData(tmc(buildRequestJson("mgu", songId, quality), buildDeviceInfo()))
    ));
  }

  private String getQQMusicUrl(String songId, String quality) throws JSONException, IOException {

    return parseResponse(HttpRequestUtils.postBytes(
        this.apiUrl,
        encryptData(tmc(buildRequestJson("qq", songId, quality), buildDeviceInfo()))
    ));
  }

  private String buildDeviceInfoJson(
      String uid,
      String token,
      String deviceId,
      String appVersion,
      String device,
      String versionCode
  ) {
    JSONObject json = new JSONObject();
    try {
      json.put("uid", uid);
      json.put("token", token);
      json.put("deviceid", deviceId);
      json.put("appVersion", appVersion);
      json.put("vercode", versionCode);
      json.put("device", device);
      json.put("osVersion", Build.VERSION.RELEASE);
      return json.toString();
    } catch (JSONException e) {
      return FrameBodyCOMM.DEFAULT;
    }
  }

  private String buildRequestJson(String platform, String t1, String t2) {
    JSONObject json = new JSONObject();
    try {
      json.put("method", "GetMusicUrl");
      json.put("platform", platform);
      json.put("t1", t1);
      json.put("t2", t2);
      return json.toString();
    } catch (JSONException e) {
      return FrameBodyCOMM.DEFAULT;
    }
  }

  private String getWyyMusicUrl(String songId, String quality) throws JSONException, IOException {

    return parseResponse(HttpRequestUtils.postBytes(
        this.apiUrl,
        encryptData(tmc(buildRequestJson("wyy", songId, quality), buildDeviceInfo()))
    ));
  }

  private String getYunMusicUrl(String songId) throws JSONException, IOException {

    return parseResponse(HttpRequestUtils.postBytes(
        this.apiUrl,
        encryptData(tmc(buildRequestJson("yun", songId, FrameBodyCOMM.DEFAULT), buildDeviceInfo()))
    ));
  }

  public String getMusicUrl(String platform, String songId, String quality)
      throws JSONException, IOException {
    switch (platform) {
      case "qq":
        return getQQMusicUrl(songId, quality);
      case "wyy":
        return getWyyMusicUrl(songId, quality);
      case "yun":
        return getYunMusicUrl(songId);
      case "kuwo":
        return getKuwoMusicUrl(songId, quality);
      case "migu":
        return getMiguMusicUrl(songId, quality);
      case "kugou":
        return getKugouMusicUrl(songId, quality);
      default:
        return null;
    }
  }

  public native String tmc(String data, String deviceInfo);
}