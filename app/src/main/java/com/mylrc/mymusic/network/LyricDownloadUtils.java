package com.mylrc.mymusic.network;

import android.util.Base64;
import android.util.Log;
import androidx.annotation.NonNull;
import com.mylrc.mymusic.utils.FileUtils;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.mp4.atom.Mp4DataBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LyricDownloadUtils {

  private static final String TAG = "LyricDownloadUtils";
  private static final int CONNECT_TIMEOUT = 30000;
  private static final int READ_TIMEOUT = 30000;
  private static final String TRANSLATION_MARKER = "\nGCSP_Have_Translation\n";
  private static final String EMPTY_STRING = FrameBodyCOMM.DEFAULT;

  public static boolean downloadKugouLyric(String hash, String outputPath, String fileName) {
    try {
      String url =
          "https://m3ws.kugou.com/api/v1/krc/get_krc?keyword=0&hash=" + hash + "&timelength=0";
      String response = HttpRequestUtils.get(url);

      JSONObject jsonObject = new JSONObject(response);
      String lyric = jsonObject.getJSONObject(Mp4DataBox.IDENTIFIER).getString("lrc");

      FileUtils.writeFile(lyric, outputPath, fileName);
      return true;
    } catch (Exception e) {
      Log.e(TAG, "下载酷狗歌词失败 - Hash: " + hash, e);
      return false;
    }
  }

  public static void downloadKugouLyricAlt(String hash, String outputPath, String fileName) {
    try {
      String url =
          "https://m3ws.kugou.com/api/v1/krc/get_krc?keyword=0&hash=" + hash + "&timelength=0";
      String response = HttpRequestUtils.get(url);

      JSONObject jsonObject = new JSONObject(response);
      String lyric = jsonObject.getJSONObject(Mp4DataBox.IDENTIFIER).getString("lrc");

      FileUtils.writeFile(lyric, outputPath, fileName);
    } catch (Exception e) {
      Log.e(TAG, "下载酷狗歌词失败(备用)- Hash: " + hash, e);
    }
  }

  public static boolean downloadKuwoLyric(String musicId, String outputPath, String fileName) {
    StringBuilder lyricBuilder = new StringBuilder();

    try {
      String url = "http://api.ikunshare.com/songinfoandlrc?musicId=" + musicId;
      String response = HttpRequestUtils.get(url);

      JSONObject jsonObject = new JSONObject(response);
      JSONArray lrcList = jsonObject.getJSONObject(Mp4DataBox.IDENTIFIER).getJSONArray("lrclist");

      for (int i = 0; i < lrcList.length(); i++) {
        JSONObject lineObj = lrcList.getJSONObject(i);
        String time = lineObj.getString("time");
        String lineLyric = lineObj.getString("lineLyric").replaceAll("&apos;", "'");

        int dotIndex = time.indexOf(".");
        int totalSeconds = Integer.parseInt(time.substring(0, dotIndex));
        String milliseconds = time.substring(dotIndex + 1);

        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        String minuteStr = String.valueOf(minutes);
        if (minutes <= 9) {
          minuteStr = "0" + minuteStr;
        }

        String secondStr = String.valueOf(seconds);
        if (seconds <= 9) {
          secondStr = "0" + secondStr;
        }

        if (milliseconds.length() > 2) {
          milliseconds = milliseconds.substring(0, 2);
        }
        if (milliseconds.length() == 1) {
          milliseconds = "0" + milliseconds;
        }

        lyricBuilder.append("[")
            .append(minuteStr)
            .append(":")
            .append(secondStr)
            .append(".")
            .append(milliseconds)
            .append("]")
            .append(lineLyric)
            .append("\n");
      }

      String finalLyric = lyricBuilder.toString()
          .replaceAll("ï¼Œ", " ")
          .replaceAll(",", " ");

      FileUtils.writeFile(finalLyric, outputPath, fileName);
      return true;
    } catch (Exception e) {
      Log.e(TAG, "下载酷我歌词失败 - MusicId: " + musicId, e);
      return false;
    }
  }

  public static void downloadLyricFromUrl(String url, String outputPath, String fileName)
      throws IOException {
    String lyric = HttpRequestUtils.get(url);

    if (lyric.equals(EMPTY_STRING)) {
      Log.w(TAG, "歌词内容为空 - URL: " + url);
      return;
    }

    FileUtils.writeFile(lyric, outputPath, fileName);
  }

  public static boolean downloadQQMusicLyric(String songMid, String outputPath, String fileName) {
    BufferedInputStream inputStream = null;
    try {
      inputStream = getBufferedInputStream(songMid);
      String response = new String(DownloadUtils.inputStreamToBytes(inputStream),
          StandardCharsets.UTF_8);

      JSONObject jsonObject = new JSONObject(response);

      String lyric = new String(Base64.decode(jsonObject.getString("lyric"), 0),
          StandardCharsets.UTF_8)
          .replace("&apos;", "'");

      String transField = jsonObject.getString("trans");
      String translation = null;
      if (!transField.equals(EMPTY_STRING)) {
        translation = new String(Base64.decode(transField, 0));
      }

      if (translation == null || translation.equals(EMPTY_STRING) || translation.equals("null")) {
        FileUtils.writeFile(lyric, outputPath, fileName);
      } else {
        FileUtils.writeFile(lyric + "\n" + translation, outputPath, fileName);
      }

      return true;
    } catch (Exception e) {
      Log.e(TAG, "下载QQ音乐歌词失败 - SongMid: " + songMid, e);
      return false;
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          Log.e(TAG, "关闭输入流失败", e);
        }
      }
    }
  }

  @NonNull
  private static BufferedInputStream getBufferedInputStream(String songMid) throws IOException {
    String url =
        "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?g_tk=5381&format=json&inCharset=utf-8&outCharset=utf-8&notice=0&platform=h5&needNewCode=1&ct=121&cv=0&songmid="
            + songMid;

    HttpURLConnection connection = null;
    try {
      connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setRequestMethod("GET");
      connection.setConnectTimeout(CONNECT_TIMEOUT);
      connection.setReadTimeout(READ_TIMEOUT);
      connection.setRequestProperty("Referer", "https://y.qq.com/portal/player.html");

      return new BufferedInputStream(connection.getInputStream());
    } catch (IOException e) {
      if (connection != null) {
        connection.disconnect();
      }
      throw e;
    }
  }

  public static void downloadQQMusicLyricWithTranslation(String songMid, String outputPath,
      String fileName) throws IOException {
    String lyric = null;
    String translation = null;
    BufferedInputStream inputStream = null;

    try {
      inputStream = getBufferedInputStream(songMid);
      String response = new String(DownloadUtils.inputStreamToBytes(inputStream),
          StandardCharsets.UTF_8);

      JSONObject jsonObject = new JSONObject(response);

      lyric = new String(Base64.decode(jsonObject.getString("lyric"), 0), StandardCharsets.UTF_8)
          .replace("&apos;", "'");

      String transField = jsonObject.getString("trans");
      if (!transField.equals(EMPTY_STRING)) {
        translation = new String(Base64.decode(transField, 0));
      }
    } catch (Exception e) {
      Log.e(TAG, "下载QQ音乐歌词(带翻译)失败 - SongMid: " + songMid, e);
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          Log.e(TAG, "关闭输入流失败", e);
        }
      }
    }

    if (translation == null || translation.equals(EMPTY_STRING) || translation.equals("null")) {
      FileUtils.writeFile(lyric, outputPath, fileName);
      return;
    }

    FileUtils.writeFile(lyric + TRANSLATION_MARKER + translation, outputPath, fileName);
  }

  public static boolean downloadNeteaseCloudLyric(String songId, String outputPath, String fileName)
      throws IOException {
    try {
      String url = "http://music.163.com/api/song/lyric?os=pc&id=" + songId + "&lv=-1&tv=1";
      String response = HttpRequestUtils.get(url);

      JSONObject jsonObject = new JSONObject(response);
      String lyric = jsonObject.getJSONObject("lrc").getString("lyric");

      JSONObject translationObj = jsonObject.getJSONObject("tlyric");

      if (translationObj.has("lyric")) {
        String translation = translationObj.getString("lyric");

        if (!translation.equals(EMPTY_STRING) && !translation.equals("null")
            && translation.length() > 80) {
          lyric = lyric + "\n" + translation;
        }
      }

      FileUtils.writeFile(lyric, outputPath, fileName);
      return true;
    } catch (JSONException e) {
      Log.e(TAG, "下载网易云音乐歌词失败 - SongId: " + songId, e);
      return false;
    }
  }

  public static void downloadNeteaseCloudLyricWithTranslation(String songId, String outputPath,
      String fileName) throws JSONException, IOException {
    String lyric = null;
    JSONObject translationObj = null;

    try {
      String url = "http://music.163.com/api/song/lyric?os=pc&id=" + songId + "&lv=-1&tv=1";
      String response = HttpRequestUtils.get(url);

      JSONObject jsonObject = new JSONObject(response);
      lyric = jsonObject.getJSONObject("lrc").getString("lyric");
      translationObj = jsonObject.getJSONObject("tlyric");
    } catch (JSONException e) {
      Log.e(TAG, "下载网易云音乐歌词(带翻译)失败 - SongId: " + songId, e);
    }

    if (translationObj == null || !translationObj.has("lyric")) {
      FileUtils.writeFile(lyric, outputPath, fileName);
      return;
    }

    String translation = translationObj.getString("lyric");

    if (translation.equals(EMPTY_STRING) || translation.equals("null")
        || translation.length() <= 80) {
      FileUtils.writeFile(lyric, outputPath, fileName);
      return;
    }

    FileUtils.writeFile(lyric + TRANSLATION_MARKER + translation, outputPath, fileName);
  }
}