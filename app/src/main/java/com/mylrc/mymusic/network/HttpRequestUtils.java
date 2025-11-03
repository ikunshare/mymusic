package com.mylrc.mymusic.network;

import com.mylrc.mymusic.tool.APPApplication;
import com.mylrc.mymusic.utils.ZipUtils;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;

public class HttpRequestUtils {

  public static String get(String url) {
    Response response = null;
    try {
      OkHttpClient client = OkHttpClientManager.getInstance();
      Request request = new Request.Builder().url(url)
          .addHeader("User-Agent", APPApplication.userAgent).build();
      response = client.newCall(request).execute();

      if (!response.isSuccessful()) {
        return FrameBodyCOMM.DEFAULT;
      }

      ResponseBody body = response.body();
      if (body == null) {
        return FrameBodyCOMM.DEFAULT;
      }

      return body.string();
    } catch (Exception e) {
      e.printStackTrace();
      return FrameBodyCOMM.DEFAULT;
    } finally {
      if (response != null) {
        response.close();
      }
    }
  }

  public static String getWithHeaders(String url, Map<String, String> headers) {
    Response response = null;
    try {
      Request.Builder builder = new Request.Builder().url(url);

      if (headers != null) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
          builder.addHeader(entry.getKey(), entry.getValue());
        }
      }

      OkHttpClient client = OkHttpClientManager.getInstance();
      Request request = builder.build();
      response = client.newCall(request).execute();

      if (!response.isSuccessful()) {
        return FrameBodyCOMM.DEFAULT;
      }

      ResponseBody body = response.body();
      if (body == null) {
        return FrameBodyCOMM.DEFAULT;
      }

      return body.string();
    } catch (Exception e) {
      e.printStackTrace();
      return FrameBodyCOMM.DEFAULT;
    } finally {
      if (response != null) {
        response.close();
      }
    }
  }

  public static String postBytes(String url, byte[] data) {
    Response response = null;
    try {
      MediaType mediaType = MediaType.parse("application/octet-stream");
      RequestBody requestBody = RequestBody.create(mediaType, data);

      Request request = new Request.Builder().url(url)
          .addHeader("User-Agent", APPApplication.userAgent).post(requestBody).build();

      OkHttpClient client = OkHttpClientManager.getInstance();
      response = client.newCall(request).execute();

      int statusCode = response.code();
      ResponseBody body = response.body();

      if (body == null) {
        return "响应体为空";
      }

      if (statusCode == 200) {
        byte[] responseBytes = body.bytes();
        return new String(ZipUtils.decompress(responseBytes));
      } else {
        return "内部服务器错误，请稍候再试\n\n响应码：" + statusCode;
      }
    } catch (Exception e) {
      return "无法连接服务器，请稍候再试\n\n错误信息：" + e;
    } finally {
      if (response != null) {
        response.close();
      }
    }
  }

  public static String postString(String url, String data) {
    Response response = null;
    try {
      MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
      RequestBody requestBody = RequestBody.create(mediaType, data.getBytes());

      Request request = new Request.Builder().url(url)
          .addHeader("User-Agent", APPApplication.userAgent).post(requestBody).build();
      OkHttpClient client = OkHttpClientManager.getInstance();
      response = client.newCall(request).execute();

      if (!response.isSuccessful()) {
        return FrameBodyCOMM.DEFAULT;
      }

      ResponseBody body = response.body();
      if (body == null) {
        return FrameBodyCOMM.DEFAULT;
      }

      byte[] responseBytes = body.bytes();
      return new String(ZipUtils.decompress(responseBytes));
    } catch (Exception e) {
      e.printStackTrace();
      return FrameBodyCOMM.DEFAULT;
    } finally {
      if (response != null) {
        response.close();
      }
    }
  }

  public static String postJson(String url, String json) {
    return postString(url, json);
  }
}