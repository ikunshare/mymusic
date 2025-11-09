package com.mylrc.mymusic.network;

import android.util.Base64;
import android.util.Log;
import androidx.annotation.NonNull;
import com.mylrc.mymusic.utils.FileUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.Inflater;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.mp4.atom.Mp4DataBox;
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
      String response = HttpRequestUtils.httpGet(url);

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
      String response = HttpRequestUtils.httpGet(url);

      JSONObject jsonObject = new JSONObject(response);
      String lyric = jsonObject.getJSONObject(Mp4DataBox.IDENTIFIER).getString("lrc");

      FileUtils.writeFile(lyric, outputPath, fileName);
    } catch (Exception e) {
      Log.e(TAG, "下载酷狗歌词失败(备用)- Hash: " + hash, e);
    }
  }

  private static String buildKuwoParams(String musicId, boolean isGetLyricx) {
    String params = "user=12345,web,web,web&requester=localhost&req=1&rid=MUSIC_" + musicId;
    if (isGetLyricx) {
      params += "&lrcx=1";
    }

    byte[] bufKey = "yeelion".getBytes(StandardCharsets.UTF_8);
    int bufKeyLen = bufKey.length;
    byte[] bufStr = params.getBytes(StandardCharsets.UTF_8);
    int bufStrLen = bufStr.length;

    byte[] output = new byte[bufStrLen];
    int i = 0;
    while (i < bufStrLen) {
      int j = 0;
      while (j < bufKeyLen && i < bufStrLen) {
        output[i] = (byte) (bufKey[j] ^ bufStr[i]);
        i++;
        j++;
      }
    }

    return Base64.encodeToString(output, Base64.NO_WRAP);
  }

  private static String decodeKuwoLyric(byte[] data, boolean isGetLyricx) throws Exception {
    String header = new String(data, 0, Math.min(10, data.length), StandardCharsets.UTF_8);
    if (!header.startsWith("tp=content")) {
      throw new Exception("Invalid lyric data format");
    }

    int separatorIndex = -1;
    for (int i = 0; i < data.length - 3; i++) {
      if (data[i] == '\r' && data[i + 1] == '\n' && data[i + 2] == '\r' && data[i + 3] == '\n') {
        separatorIndex = i;
        break;
      }
    }

    if (separatorIndex == -1) {
      throw new Exception("Separator not found");
    }

    byte[] compressedData = new byte[data.length - separatorIndex - 4];
    System.arraycopy(data, separatorIndex + 4, compressedData, 0, compressedData.length);

    byte[] decompressedData;
    try {
      Inflater inflater = new Inflater();
      inflater.setInput(compressedData);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      while (!inflater.finished()) {
        int count = inflater.inflate(buffer);
        outputStream.write(buffer, 0, count);
      }
      inflater.end();
      decompressedData = outputStream.toByteArray();
      outputStream.close();
    } catch (Exception e) {
      throw new Exception("Decompression failed: " + e.getMessage());
    }

    if (!isGetLyricx) {
      return new String(decompressedData, "GB18030");
    }

    byte[] base64Decoded;
    try {
      base64Decoded = Base64.decode(decompressedData, Base64.DEFAULT);
    } catch (Exception e) {
      throw new Exception("Base64 decode failed: " + e.getMessage());
    }

    byte[] bufKey = "yeelion".getBytes(StandardCharsets.UTF_8);
    int bufKeyLen = bufKey.length;
    byte[] output = new byte[base64Decoded.length];

    int i = 0;
    while (i < base64Decoded.length) {
      int j = 0;
      while (j < bufKeyLen && i < base64Decoded.length) {
        output[i] = (byte) (base64Decoded[i] ^ bufKey[j]);
        i++;
        j++;
      }
    }

    return new String(output, "GB18030");
  }

  public static boolean downloadKuwoLyric(String musicId, String outputPath, String fileName) {
    BufferedInputStream inputStream = null;
    HttpURLConnection connection = null;
    try {
      String encodedParams = buildKuwoParams(musicId, true);
      String url = "https://newlyric.kuwo.cn/newlyric.lrc?" + encodedParams;

      connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setRequestMethod("GET");
      connection.setConnectTimeout(CONNECT_TIMEOUT);
      connection.setReadTimeout(READ_TIMEOUT);

      inputStream = new BufferedInputStream(connection.getInputStream());
      byte[] responseBytes = DownloadUtils.inputStreamToBytes(inputStream);

      String lyric = decodeKuwoLyric(responseBytes, true);

      if (lyric == null || lyric.isEmpty()) {
        Log.e(TAG, "解码后的歌词为空 - MusicId: " + musicId);
        return false;
      }

      lyric = lyric.replaceAll("<-?\\d+,-?\\d+(?:,-?\\d+)?>", "");

      FileUtils.writeFile(lyric, outputPath, fileName);
      return true;
    } catch (Exception e) {
      Log.e(TAG, "下载酷我歌词失败 - MusicId: " + musicId, e);
      return false;
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          Log.e(TAG, "关闭输入流失败", e);
        }
      }
      if (connection != null) {
        connection.disconnect();
      }
    }
  }

  public static boolean downloadLyricFromUrl(String url, String outputPath, String fileName)
      throws IOException {
    String lyric = HttpRequestUtils.httpGet(url);

    if (lyric.equals(EMPTY_STRING)) {
      Log.w(TAG, "歌词内容为空 - URL: " + url);
      return false;
    }

    FileUtils.writeFile(lyric, outputPath, fileName);
    return false;
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
      String response = HttpRequestUtils.httpGet(url);

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
      String response = HttpRequestUtils.httpGet(url);

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