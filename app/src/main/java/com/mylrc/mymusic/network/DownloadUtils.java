package com.mylrc.mymusic.network;

import android.util.Log;
import com.mylrc.mymusic.tool.APPApplication;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.json.JSONException;
import org.json.JSONObject;

public class DownloadUtils {

  private static final String TAG = "DownloadUtils";

  public static void downloadFile(String url, String outputPath) throws IOException {
    Response response = null;
    InputStream inputStream = null;
    FileOutputStream fileOutputStream = null;

    try {
      response = OkHttpClient.getInstance()
          .newCall(new Request.Builder()
              .url(url)
              .addHeader("User-Agent", APPApplication.userAgent)
              .build()).execute();

      if (!response.isSuccessful()) {
        throw new IOException("下载失败,响应码: " + response.code());
      }

      inputStream = response.body().byteStream();

      fileOutputStream = new FileOutputStream(outputPath);

      byte[] buffer = new byte[1024];

      int bytesRead;
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        fileOutputStream.write(buffer, 0, bytesRead);
      }

      fileOutputStream.flush();
    } finally {
      if (fileOutputStream != null) {
        try {
          fileOutputStream.close();
        } catch (IOException e) {
          Log.e(TAG, "关闭文件输出流失败", e);
        }
      }
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          Log.e(TAG, "关闭输入流失败", e);
        }
      }
      if (response != null) {
        response.close();
      }
    }
  }

  public static String getYoudaoNote(String noteId) {
    try {
      String apiUrl = "http://note.youdao.com/yws/public/note/" + noteId;

      String responseText = HttpRequestUtils.httpGet(apiUrl);

      JSONObject jsonObject = new JSONObject(responseText);
      String content = jsonObject.getString("content");

      return content
          .replaceAll(
              "</div><div yne-bulb-block=\"paragraph\" style=\"white-space: pre-wrap;text-align:left;\">",
              "\n")
          .replaceAll("<br>", FrameBodyCOMM.DEFAULT)
          .replaceAll("</div>", FrameBodyCOMM.DEFAULT)
          .replaceAll("&nbsp;", " ");
    } catch (JSONException e) {
      Log.e(TAG, "解析有道云笔记失败 - NoteId: " + noteId, e);
      return FrameBodyCOMM.DEFAULT;
    }
  }

  public static byte[] inputStreamToBytes(InputStream inputStream) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];

    try {
      int bytesRead;
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        byteArrayOutputStream.write(buffer, 0, bytesRead);
      }

      return byteArrayOutputStream.toByteArray();
    } finally {
      try {
        byteArrayOutputStream.close();
      } catch (IOException e) {
        Log.e(TAG, "关闭ByteArrayOutputStream失败", e);
      }
    }
  }

  public static String getWithCookie(String url, String cookieValue) {
    Response response = null;
    try {
      response = OkHttpClient.getInstance()
          .newCall(new Request.Builder()
              .url(url)
              .addHeader("User-Agent", APPApplication.userAgent)
              .addHeader("Cookie", "os=android;" + cookieValue)
              .method("GET", null)
              .build()).execute();

      if (!response.isSuccessful()) {
        return FrameBodyCOMM.DEFAULT;
      }

      return response.body().string();
    } catch (Exception e) {
      Log.e(TAG, "带Cookie的GET请求失败 - URL: " + url, e);
      return FrameBodyCOMM.DEFAULT;
    } finally {
      if (response != null) {
        response.close();
      }
    }
  }

  public static String postForm(String url, String formData) {
    Response response = null;
    try {
      response = OkHttpClient.getInstance()
          .newCall(new Request.Builder()
              .url(url)
              .addHeader("User-Agent", APPApplication.userAgent)
              .post(RequestBody.create(
                  formData.getBytes(),
                  MediaType.parse("application/x-www-form-urlencoded")
              ))
              .build()).execute();

      if (!response.isSuccessful()) {
        return "请求失败,响应码: " + response.code();
      }

      return new String(response.body().bytes(), StandardCharsets.UTF_8);
    } catch (Exception e) {
      Log.e(TAG, "POST表单请求失败 - URL: " + url, e);
      return "错误信息:" + e;
    } finally {
      if (response != null) {
        response.close();
      }
    }
  }
}