package com.mylrc.mymusic.network;

import android.util.Log;
import com.mylrc.mymusic.utils.CommonUtils;
import java.net.URLDecoder;
import java.util.ArrayList;
import org.jaudiotagger.tag.mp4.atom.Mp4DataBox;
import org.json.JSONArray;
import org.json.JSONObject;

public class MvUrlParser {

  private static final String TAG = "MvUrlParser";

  public static ArrayList<String> parseMvUrl(String str, String str2) {
    if (str == null || str2 == null) {
      Log.e(TAG, "解析MV URL失败: 参数为null");
      return null;
    }

    if (str.equals("qqMv")) {
      return parseQQMusicMv(str2);
    }
    if (str.equals("wyyMv")) {
      return parseNeteaseCloudMv(str2);
    }
    if (str.equals("kugouMv")) {
      return parseKugouMv(str2);
    }

    Log.e(TAG, "未知的MV类型: " + str);
    return null;
  }

  private static ArrayList<String> parseKugouMv(String str) {
    ArrayList<String> arrayList = new ArrayList<>();
    try {
      String sb = "http://trackermv.kugou.com/interface/index/cmd=100&hash=" +
          str.toUpperCase() +
          "&key=" +
          CommonUtils.md5(str.toUpperCase() + "kugoumvcloud") +
          "&ext=mp4";

      String response = HttpRequestUtils.httpGet(sb);
      if (response == null || response.isEmpty()) {
        Log.e(TAG, "酷狗MV API响应为空");
        return null;
      }

      JSONObject jSONObject = new JSONObject(response).getJSONObject("mvdata");

      if (jSONObject.getJSONObject("rq").length() != 0) {
        arrayList.add(jSONObject.getJSONObject("sd").getString("downurl"));
        arrayList.add(jSONObject.getJSONObject("hd").getString("downurl"));
        arrayList.add(jSONObject.getJSONObject("sq").getString("downurl"));
        arrayList.add(jSONObject.getJSONObject("rq").getString("downurl"));
      } else if (jSONObject.getJSONObject("sq").length() != 0) {
        arrayList.add(jSONObject.getJSONObject("sd").getString("downurl"));
        arrayList.add(jSONObject.getJSONObject("hd").getString("downurl"));
        arrayList.add(jSONObject.getJSONObject("sq").getString("downurl"));
      } else if (jSONObject.getJSONObject("hd").length() != 0) {
        arrayList.add(jSONObject.getJSONObject("sd").getString("downurl"));
        arrayList.add(jSONObject.getJSONObject("hd").getString("downurl"));
      } else {
        arrayList.add(jSONObject.getJSONObject("sd").getString("downurl"));
      }
      return arrayList;
    } catch (Exception e) {
      Log.e(TAG, "解析酷狗MV失败 - Hash: " + str, e);
      return null;
    }
  }

  private static ArrayList<String> parseQQMusicMv(String str) {
    ArrayList<String> arrayList = new ArrayList<>();
    try {
      String response = HttpRequestUtils.postJson(
          "http://u.y.qq.com/cgi-bin/musicu.fcg",
          URLDecoder.decode(
              "%7B%22comm%22%3A%7B%22ct%22%3A11%2C%22cv%22%3A%2221030600%22%2C%22v%22%3A%221003006%22%2C%22tmeAppID%22%3A%22qqmusiclight%22%2C%22tmeLoginType%22%3A%222%22%7D%2C%22request%22%3A%7B%22module%22%3A%22gosrf.Stream.MvUrlProxy%22%2C%22method%22%3A%22GetMvUrls%22%2C%22param%22%3A%7B%22vids%22%3A%5B%22"
                  + str
                  + "%22%5D%2C%22request_type%22%3A10003%2C%22videoformat%22%3A1%2C%22filetype%22%3A30%2C%22format%22%3A265%2C%22use_new_domain%22%3A1%7D%7D%7D"));

      if (response == null || response.isEmpty()) {
        Log.e(TAG, "QQ音乐MV API响应为空");
        return null;
      }

      JSONArray jSONArray = new JSONObject(response)
          .getJSONObject("request")
          .getJSONObject(Mp4DataBox.IDENTIFIER)
          .getJSONObject(str)
          .getJSONArray("mp4");

      for (int i2 = 0; i2 < jSONArray.length(); i2++) {
        JSONArray jSONArray2 = jSONArray.getJSONObject(i2).getJSONArray("freeflow_url");
        if (jSONArray2.length() != 0) {
          arrayList.add(jSONArray2.getString(0));
        }
        if (i2 == 4) {
          break;
        }
      }
      return arrayList;
    } catch (Exception e) {
      Log.e(TAG, "解析QQ音乐MV失败 - Vid: " + str, e);
      return null;
    }
  }

  private static ArrayList<String> parseNeteaseCloudMv(String str) {
    ArrayList<String> arrayList = new ArrayList<>();
    try {
      String response = HttpRequestUtils.httpGet("http://music.163.com/api/mv/detail?id=" + str);

      if (response == null || response.isEmpty()) {
        Log.e(TAG, "网易云MV API响应为空");
        return null;
      }

      JSONObject jSONObject = new JSONObject(response)
          .getJSONObject(Mp4DataBox.IDENTIFIER)
          .getJSONObject("brs");

      int length = jSONObject.length();
      if (length == 4) {
        arrayList.add(jSONObject.getString("240"));
        arrayList.add(jSONObject.getString("480"));
        arrayList.add(jSONObject.getString("720"));
        arrayList.add(jSONObject.getString("1080"));
      } else if (length == 3) {
        arrayList.add(jSONObject.getString("240"));
        arrayList.add(jSONObject.getString("480"));
        if (jSONObject.has("720")) {
          arrayList.add(jSONObject.getString("720"));
        } else {
          arrayList.add(jSONObject.getString("1080"));
        }
      } else if (length == 2) {
        arrayList.add(jSONObject.getString("240"));
        arrayList.add(jSONObject.getString("480"));
      } else if (length == 1) {
        arrayList.add(jSONObject.getString("240"));
      }
      return arrayList;
    } catch (Exception e) {
      Log.e(TAG, "解析网易云MV失败 - Id: " + str, e);
      return null;
    }
  }
}