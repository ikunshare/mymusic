package com.mylrc.mymusic.network;

import com.mylrc.mymusic.tool.APPApplication;
import com.mylrc.mymusic.utils.ZipUtils;
import java.io.IOException;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;

public class HttpRequestUtils {

  public static String httpGet(String url) {
    Response response = null;
    try {
      okhttp3.OkHttpClient client = OkHttpClient.getInstance();
      Request request = new Request.Builder().url(url)
          .addHeader("User-Agent", APPApplication.userAgent).build();
      response = client.newCall(request).execute();

      if (!response.isSuccessful()) {
        return FrameBodyCOMM.DEFAULT;
      }

      ResponseBody body = response.body();

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

  public static String httpGetWithHeaders(String url, Map<String, String> headers) {
    Response response = null;
    try {
      Request.Builder builder = new Request.Builder().url(url);

      if (headers != null) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
          builder.addHeader(entry.getKey(), entry.getValue());
        }
      }

      okhttp3.OkHttpClient client = OkHttpClient.getInstance();
      Request request = builder.build();
      response = client.newCall(request).execute();

      if (!response.isSuccessful()) {
        return FrameBodyCOMM.DEFAULT;
      }

      ResponseBody body = response.body();

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

      okhttp3.OkHttpClient client = OkHttpClient.getInstance();
      response = client.newCall(request).execute();

      int statusCode = response.code();
      ResponseBody body = response.body();

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
      okhttp3.OkHttpClient client = OkHttpClient.getInstance();
      response = client.newCall(request).execute();

      if (!response.isSuccessful()) {
        return FrameBodyCOMM.DEFAULT;
      }

      ResponseBody body = response.body();

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


  public static String postMap(String url, Map<String, String> param) {
    okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient();

    FormBody.Builder builder = new FormBody.Builder();

    for (String key : param.keySet()) {
      Object obj = param.get(key);
      if (obj != null) {
        builder.addEncoded(key, param.get(key));
      } else {
        builder.addEncoded(key, "");
      }
    }
    FormBody requestBody = builder.build();

    Request request = new Request.Builder().url(url).post(requestBody).build();
    try {
      Response response = okHttpClient.newCall(request).execute();
      if (response.isSuccessful()) {
        return response.body().string();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "{}";
  }
}