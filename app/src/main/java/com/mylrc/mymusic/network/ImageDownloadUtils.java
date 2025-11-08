package com.mylrc.mymusic.network;

import static com.mylrc.mymusic.network.DownloadUtils.inputStreamToBytes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.mylrc.mymusic.tool.APPApplication;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.mp4.atom.Mp4DataBox;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageDownloadUtils {

  private static final String TAG = "ImageDownloadUtils";

  public static Bitmap getKugouAlbumCover(String songHash) {
    try {
      String apiUrl = "https://m.kugou.com/api/v1/song/get_song_info?cmd=playInfo&hash=" + songHash
          + "&mid=" + System.currentTimeMillis();
      String responseData = HttpRequestUtils.httpGet(apiUrl);

      JSONObject jsonObject = new JSONObject(responseData);

      String albumImageUrl = jsonObject.getString("album_img").replace("{size}", "120");
      String imageUrl = jsonObject.getString("imgUrl").replace("{size}", "120");

      if (!albumImageUrl.equals(FrameBodyCOMM.DEFAULT)) {
        imageUrl = albumImageUrl;
      }

      return getBitmapFromUrl(imageUrl);
    } catch (Exception e) {
      Log.e(TAG, "获取酷狗封面失败: " + songHash, e);
      return null;
    }
  }

  public static boolean downloadKugouCover(String songHash, String outputPath)
      throws ProtocolException {
    boolean downloadSuccess = false;
    try {
      String apiUrl = "https://m.kugou.com/api/v1/song/get_song_info?cmd=playInfo&hash=" + songHash
          + "&mid=" + System.currentTimeMillis();
      String responseData = HttpRequestUtils.httpGet(apiUrl);

      JSONObject jsonObject = new JSONObject(responseData);

      String albumImageUrl = jsonObject.getString("album_img").replace("{size}", "720");
      String imageUrl = jsonObject.getString("imgUrl").replace("{size}", "480");
      if (albumImageUrl.equals(FrameBodyCOMM.DEFAULT)) {
        downloadSuccess = downloadImageToFile(imageUrl, outputPath);
      } else {
        downloadSuccess = downloadImageToFile(albumImageUrl, outputPath);
      }
    } catch (JSONException e) {
      Log.e(TAG, "下载酷狗封面失败-JSON解析错误: " + songHash, e);
    }
    return downloadSuccess;
  }

  public static boolean downloadKuwoCover(String musicId, String outputPath) {
    String apiResponse = HttpRequestUtils.httpGet("http://artistpicserver.kuwo.cn/pic.web?corp=kuwo&type=rid_pic&pictype=500&size=500&rid=" + musicId);
    return downloadImageToFile(apiResponse, outputPath);
  }

  public static Bitmap getKuwoBitmap(String musicId) {
    String apiResponse = HttpRequestUtils.httpGet("http://artistpicserver.kuwo.cn/pic.web?corp=kuwo&type=rid_pic&pictype=500&size=500&rid=" + musicId);
    return getBitmapFromUrl(apiResponse);
  }

  public static Bitmap getBitmapFromUrl(String imageUrl) {
    try {
      return BitmapFactory.decodeStream(new BufferedInputStream(
          OkHttpClient.getInstance()
              .newCall(new okhttp3.Request.Builder().url(imageUrl).get().build()).execute().body()
              .byteStream()));
    } catch (Exception unused) {
      return null;
    }
  }

  public static boolean downloadImageToFile(String imageUrl, String outputPath) {
    if (imageUrl != null) {
      try {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(
            imageUrl).openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(10000);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
            httpURLConnection.getInputStream());
        byte[] bArr = new byte[1024];
        FileOutputStream fileOutputStream = new FileOutputStream(new File(outputPath));
        while (true) {
          int i2 = bufferedInputStream.read(bArr);
          if (i2 == -1) {
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
          }
          fileOutputStream.write(bArr, 0, i2);
        }
      } catch (Exception unused) {
      }
    }
    return false;
  }


  public static Bitmap getQQMusicBitmap(String albumMid) {
    try {
      return getBitmapFromUrl(
          "http://y.qq.com/music/photo_new/T002R300x300M000" + albumMid + ".jpg");
    } catch (Exception e) {
      Log.e(TAG, "获取QQ音乐Bitmap失败: " + albumMid, e);
      return null;
    }
  }

  public static boolean downloadQQMusicCover(String albumMid, String outputPath) {
    try {
      return downloadImageToFile(
          "http://y.qq.com/music/photo_new/T002R800x800M000" + albumMid + ".jpg",
          outputPath);
    } catch (Exception e) {
      Log.e(TAG, "下载QQ音乐封面失败: " + albumMid, e);
      return false;
    }
  }

  public static boolean downloadQQMusicCoverBySongMid(String songMid, String outputPath) {
    try {
      String apiUrl = "http://c.y.qq.com/v8/fcg-bin/fcg_play_single_song.fcg?songmid=" + songMid
          + "&tp=yqq_song_detail&format=json";
      String apiResponse = HttpRequestUtils.httpGet(apiUrl);

      JSONObject jsonObject = new JSONObject(apiResponse);

      String albumMid = jsonObject
          .getJSONArray(Mp4DataBox.IDENTIFIER)
          .getJSONObject(0)
          .getJSONObject("album")
          .getString("mid");

      return downloadImageToFile(
          "http://y.qq.com/music/photo_new/T002R800x800M000" + albumMid + ".jpg", outputPath);
    } catch (Exception e) {
      Log.e(TAG, "通过songmid下载QQ音乐封面失败: " + songMid, e);
      return false;
    }
  }

  public static Bitmap getNeteaseCloudBitmap(String songId) {
    try {
      String apiUrl = "http://music.163.com/api/song/detail/?ids=%5B" + songId + "%5D";
      String apiResponse = httpGetNetease(apiUrl);

      JSONObject jsonObject = new JSONObject(apiResponse);

      String coverUrl = jsonObject
          .getJSONArray("songs")
          .getJSONObject(0)
          .getJSONObject("album")
          .getString("picUrl") + "?param=130y130";

      return getBitmapFromUrl(coverUrl);
    } catch (Exception e) {
      Log.e(TAG, "获取网易云Bitmap失败: " + songId, e);
      return null;
    }
  }

  public static String httpGetNetease(String url) throws ProtocolException {
    HttpURLConnection httpConnection = null;
    BufferedInputStream bufferedInputStream = null;
    try {
      httpConnection = (HttpURLConnection) new URL(url).openConnection();
      httpConnection.setRequestMethod("GET");
      httpConnection.setConnectTimeout(30000);
      httpConnection.setRequestProperty("User-Agent", APPApplication.userAgent);

      bufferedInputStream = new BufferedInputStream(httpConnection.getInputStream());
      return new String(inputStreamToBytes(bufferedInputStream), StandardCharsets.UTF_8);
    } catch (Exception e) {
      Log.e(TAG, "网易云HTTP GET请求失败: " + url, e);
      return FrameBodyCOMM.DEFAULT;
    } finally {
      if (bufferedInputStream != null) {
        try {
          bufferedInputStream.close();
        } catch (IOException e) {
          Log.e(TAG, "关闭输入流失败", e);
        }
      }
      if (httpConnection != null) {
        httpConnection.disconnect();
      }
    }
  }

  public static boolean downloadNeteaseCloudCover(String songId, String outputPath) {
    try {
      String apiUrl = "http://music.163.com/api/song/detail/?ids=%5B" + songId + "%5D";
      String apiResponse = httpGetNetease(apiUrl);

      JSONObject jsonObject = new JSONObject(apiResponse);

      String coverUrl = jsonObject
          .getJSONArray("songs")
          .getJSONObject(0)
          .getJSONObject("album")
          .getString("picUrl") +
          "?param=700y700";

      if (coverUrl.equals(FrameBodyCOMM.DEFAULT)) {
        return false;
      }

      return downloadImageToFile(coverUrl, outputPath);
    } catch (JSONException | ProtocolException e) {
      Log.e(TAG, "下载网易云封面失败: " + songId, e);
      return false;
    }
  }
}