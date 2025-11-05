package utils;

import androidx.annotation.NonNull;
import com.mylrc.mymusic.R;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MusicSearchUtils {

  private static final String EMPTY = "";
  private static final String SEPARATOR = "、";
  private static final int MAX_SINGER_COUNT = 5;

  private static List<Map<String, Object>> cachedResults;

  public static List<Map<String, Object>> search(MusicPlatform platform, String keyword)
      throws JSONException {
    switch (platform) {
      case QQ:
        return searchQQMusic(keyword);
      case WYY:
        return searchNeteaseCloud(keyword);
      case MIGU:
        return searchMigu(keyword);
      case KUGOU:
        return searchKugou(keyword);
      case KUWO:
        return searchKuwo(keyword);
      default:
        return null;
    }
  }

  private static List<Map<String, Object>> searchKugou(String keyword) {
    try {
      List<Map<String, Object>> resultList = new ArrayList<>();
      String url =
          "http://songsearch.kugou.com/song_search_v2?platform=AndroidFilter&iscorrection=1&keyword="
              + keyword + "&hifiquality=0&pagesize=100&PrivilegeFilter=0&page=1";

      JSONObject response = new JSONObject(HttpRequestUtils.httpGet(url));
      JSONArray songs = response.getJSONObject("data").getJSONArray("lists");

      for (int i = 0; i < songs.length(); i++) {
        JSONObject song = songs.getJSONObject(i);
        Map<String, Object> musicInfo = new HashMap<>();

        String songName = song.getString("SongName");
        String suffix = song.optString("Suffix", EMPTY);
        if (!suffix.equals(EMPTY) && songName.indexOf(suffix) == -1) {
          songName = songName + suffix;
        }

        String singerName = cleanText(song.getString("SingerName"));
        String albumName = cleanText(song.getString("AlbumName"));
        String duration = song.getString("Duration");

        String fileHash = song.getString("FileHash");
        String hqHash = song.getString("HQFileHash");
        String sqHash = song.getString("SQFileHash");
        String hrHash = song.getString("ResFileHash");
        String dsdHash = song.getString("SuperFileHash");

        String fileSize = song.getString("FileSize");
        String hqSize = song.getString("HQFileSize");
        String sqSize = song.getString("SQFileSize");
        String hrSize = song.getString("ResFileSize");
        String dsdSize = song.getString("SuperFileSize");

        String mvHash = song.getString("MvHash");
        String privilege = song.getString("Privilege");
        String isOriginal = song.getString("IsOriginal");
        String topicUrl = song.getString("TopicUrl");

        String hashInfo =
            fileHash + "低高" + hqHash + "高无" + sqHash + "无h" + hrHash + "高真" + dsdHash;
        String sizeInfo = fileSize + "mp" + hqSize + "hq" + sqSize + "sq" + hrSize + "hr" + dsdSize;

        musicInfo.put("filehash", hashInfo);
        musicInfo.put("filesize", sizeInfo);
        musicInfo.put("mvid", mvHash);

        if (!mvHash.equals(EMPTY)) {
          musicInfo.put("mv", R.drawable.mv);
        }

        if (!dsdHash.equals(EMPTY)) {
          musicInfo.put("br", R.drawable.dsd);
          musicInfo.put("maxbr", "dsd");
        } else if (!hrHash.equals(EMPTY)) {
          musicInfo.put("br", R.drawable.hires);
          musicInfo.put("maxbr", "hr");
        } else if (!sqHash.equals("00000000000000000000000000000000") && !sqHash.equals(EMPTY)) {
          musicInfo.put("br", R.drawable.sq);
          musicInfo.put("maxbr", "sq");
        } else if (!hqHash.equals(EMPTY)) {
          musicInfo.put("br", R.drawable.hq);
          musicInfo.put("maxbr", "hq");
        } else {
          musicInfo.put("br", R.drawable.mp3);
          musicInfo.put("maxbr", "mp3");
        }

        if (privilege.equals("5")) {
          musicInfo.put("br", R.drawable.nohave);
        }

        if (isOriginal.equals("1")) {
          musicInfo.put("yz", R.drawable.yz);
        }

        String payStatus = EMPTY;
        if (!topicUrl.equals(EMPTY)) {
          musicInfo.put("br", R.drawable.pay);
          payStatus = "pay";
        }

        singerName = limitSingerCount(singerName);
        songName = cleanText(songName);

        musicInfo.put("name", songName);
        musicInfo.put("singer", singerName);
        musicInfo.put("time", CommonUtils.formatTime(Integer.parseInt(duration)));
        musicInfo.put("cy", privilege);
        musicInfo.put("pay", payStatus);
        musicInfo.put("album", albumName);
        musicInfo.put("filename", singerName + " - " + songName);

        resultList.add(musicInfo);
      }

      return resultList;
    } catch (Exception e) {
      return null;
    }
  }

  private static List<Map<String, Object>> searchKuwo(String keyword) {
    try {
      List<Map<String, Object>> resultList = new ArrayList<>();
      String url =
          "http://search.kuwo.cn/r.s?prod=kwplayer_ar_9.3.7.2&corp=kuwo&newver=2&vipver=9.3.7.2"
              + "&source=kwplayer_ar_9.3.7.2_meizu.apk&p2p=1&notrace=0&client=kt&all=" + keyword
              + "&pn=0&rn=100&ver=kwplayer_ar_9.3.7.2&vipver=1&show_copyright_off=1&newver=2"
              + "&correct=1&ft=music&cluster=0&strategy=2012&encoding=utf8&rformat=json"
              + "&vermerge=1&mobi=1&searchapi=2&issubtitle=1&spPrivilege=0";

      JSONObject response = new JSONObject(HttpRequestUtils.httpGet(url));
      JSONArray songs = response.getJSONArray("abslist");

      for (int i = 0; i < songs.length(); i++) {
        JSONObject song = songs.getJSONObject(i);
        Map<String, Object> musicInfo = new HashMap<>();

        String songName = song.getString("SONGNAME");
        String singerName = song.getString("ARTIST").replace("&", SEPARATOR);
        String albumName = song.getString("ALBUM");
        String duration = song.getString("DURATION");
        String musicRid = song.getString("MUSICRID");
        String songId = musicRid.substring(musicRid.indexOf("_") + 1);
        String formatInfo = song.getString("N_MINFO");

        String[] formats = formatInfo.split(";");
        String mp3Size = "0";
        String hqSize = "0";
        String sqSize = "0";
        String hrSize = "0";

        for (String format : formats) {
          String size = " - " + format.substring(format.indexOf("size") + 5);
          if (format.contains("level:hr,bitrate:4000")) {
            hrSize = size;
          } else if (format.contains("level:ff,bitrate:2000")) {
            sqSize = size;
          } else if (format.contains("level:p,bitrate:320")) {
            hqSize = size;
          } else if (format.contains("level:h,bitrate:128,format:mp3")) {
            mp3Size = size;
          }
        }

        if (formatInfo.contains("level:hr,bitrate:4000")) {
          musicInfo.put("br", R.drawable.hires);
          musicInfo.put("maxbr", "hr");
        } else if (formatInfo.contains("level:ff,bitrate:2000")) {
          musicInfo.put("br", R.drawable.sq);
          musicInfo.put("maxbr", "sq");
        } else if (formatInfo.contains("level:p,bitrate:320")) {
          musicInfo.put("br", R.drawable.hq);
          musicInfo.put("maxbr", "hq");
        } else if (formatInfo.contains("level:h,bitrate:128,format:mp3")) {
          musicInfo.put("br", R.drawable.mp3);
          musicInfo.put("maxbr", "mp3");
        } else {
          continue;
        }

        singerName = limitSingerCount(singerName);

        musicInfo.put("id", songId);
        musicInfo.put("name", songName);
        musicInfo.put("singer", singerName);
        musicInfo.put("time", CommonUtils.formatTime(Integer.parseInt(duration)));
        musicInfo.put("mvid", EMPTY);
        musicInfo.put("filename", singerName + " - " + songName);
        musicInfo.put("album", albumName);
        musicInfo.put("mp3size", mp3Size);
        musicInfo.put("hqsize", hqSize);
        musicInfo.put("sqsize", sqSize);
        musicInfo.put("hrsize", hrSize);

        resultList.add(musicInfo);
      }

      return resultList;
    } catch (Exception e) {
      return null;
    }
  }

  private static List<Map<String, Object>> searchMigu(String keyword) {
    try {
      List<Map<String, Object>> resultList = new ArrayList<>();

      String signText = keyword
          + "6cdc72a439cef99a3418d2a78aa28c73yyapp2d16148780a1dcc7408e06336b98cfd507B51A6ADBA32B0FD46773B37EC4355C61651662030";
      String sign = CommonUtils.md5(signText);

      Map<String, String> headers = new HashMap<>();
      headers.put("sign", sign);
      headers.put("timestamp", "1651662030");
      headers.put("uiVersion", "A_music_3.3.0");
      headers.put("deviceId", "7B51A6ADBA32B0FD46773B37EC4355C6");
      headers.put("User-Agent",
          "Mozilla/5.0 (Linux; U; Android 11; zh-CN; M2105K81AC Build/RKQ1.200826.002) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/78.0.3904.108 Quark/5.6.6.211 Mobile Safari/537.36");

      String url =
          String.format(
              "https://jadeite.migu.cn/music_search/v3/search/searchAll?isCorrect=0&isCopyright=1&searchSwitch={\"song\":1,\"album\":0,\"singer\":0,\"tagSong\":1,\"mvSong\":0,\"bestShow\":1,\"songlist\":0,\"lyricSong\":0}&pageSize=20&text=%s&pageNo=0&sort=0&sid=USS",
              keyword);

      JSONObject response = new JSONObject(HttpRequestUtils.httpGetWithHeaders(url, headers));
      JSONArray songs = response.getJSONObject("songResultData").getJSONArray("resultList");

      for (int i = 0; i < songs.length(); i++) {
        JSONArray songArray = songs.getJSONArray(i);
        if (songArray.length() == 0) {
          continue;
        }

        JSONObject song = songArray.getJSONObject(0);
        String songName = song.getString("name");

        if (!song.has("audioFormats") || song.getJSONArray("audioFormats").length() == 0) {
          continue;
        }

        Map<String, Object> musicInfo = new HashMap<>();
        JSONArray audioFormats = song.getJSONArray("audioFormats");
        String albumName = song.getString("album");
        int formatCount = audioFormats.length();

        musicInfo.put("mp3size", 0);
        musicInfo.put("hqsize", 0);
        musicInfo.put("sqsize", "0");
        musicInfo.put("hrsize", "0");

        if (formatCount >= 4) {
          setSizeIfExists(musicInfo, audioFormats, 0, "mp3size");
          setSizeIfExists(musicInfo, audioFormats, 1, "hqsize");
          setSizeIfExists(musicInfo, audioFormats, 2, "sqsize");

          JSONObject format3 = audioFormats.getJSONObject(3);
          if (!format3.has("formatType") || format3.getString("formatType").equals("Z3D")) {
            musicInfo.put("br", R.drawable.sq);
            musicInfo.put("maxbr", "sq");
          } else {
            setSizeIfExists(musicInfo, audioFormats, 3, "hrsize");
            musicInfo.put("br", R.drawable.hires);
            musicInfo.put("maxbr", "hr");
          }
        } else if (formatCount == 3) {
          musicInfo.put("br", R.drawable.sq);
          musicInfo.put("maxbr", "sq");
          setSizeIfExists(musicInfo, audioFormats, 0, "mp3size");
          setSizeIfExists(musicInfo, audioFormats, 1, "hqsize");
          setSizeIfExists(musicInfo, audioFormats, 2, "sqsize");
        } else if (formatCount == 2) {
          musicInfo.put("br", R.drawable.hq);
          musicInfo.put("maxbr", "hq");
          setSizeIfExists(musicInfo, audioFormats, 0, "mp3size");
          setSizeIfExists(musicInfo, audioFormats, 1, "hqsize");
        } else {
          musicInfo.put("br", R.drawable.mp3);
          musicInfo.put("maxbr", "mp3");
          setSizeIfExists(musicInfo, audioFormats, 0, "mp3size");
        }

        if (!song.has("singerList")) {
          continue;
        }

        JSONArray singers = song.getJSONArray("singerList");
        StringBuilder singerBuilder = new StringBuilder();
        for (int j = 0; j < singers.length() && j < MAX_SINGER_COUNT; j++) {
          if (j > 0) {
            singerBuilder.append(SEPARATOR);
          }
          singerBuilder.append(singers.getJSONObject(j).getString("name"));
        }
        String singerName = singerBuilder.toString();

        String duration = song.has("duration") ? CommonUtils.formatTime(
            Integer.parseInt(song.getString("duration"))) : "00:00";
        String contentId = song.getString("contentId");
        String imgUrl = "http://d.musicapp.migu.cn" + song.getString("img3");
        String albumImg = "http://d.musicapp.migu.cn" + song.getString("img1");
        String lrcUrl = song.has("lrcUrl") ? song.getString("lrcUrl") : EMPTY;

        musicInfo.put("id", contentId);
        musicInfo.put("lrc", lrcUrl);
        musicInfo.put("name", songName);
        musicInfo.put("singer", singerName);
        musicInfo.put("time", duration);
        musicInfo.put("filename", singerName + " - " + songName);
        musicInfo.put("album", albumName);
        musicInfo.put("imgurl", imgUrl);
        musicInfo.put("abimg", albumImg);

        resultList.add(musicInfo);
      }

      return resultList;
    } catch (Exception e) {
      return null;
    }
  }

  private static List<Map<String, Object>> searchQQMusic(String keyword) {
    cachedResults = new ArrayList<>();

    try {
      JSONObject requestBody = getStringObjectMap(keyword);

      JSONObject response = new JSONObject(
          HttpRequestUtils.postJson("http://u6.y.qq.com/cgi-bin/musicu.fcg",
              requestBody.toString()));

      JSONArray songs = response.getJSONObject("req")
          .getJSONObject("data")
          .getJSONObject("body")
          .getJSONArray("item_song");

      for (int i = 0; i < songs.length(); i++) {
        JSONObject song = songs.getJSONObject(i);
        Map<String, Object> musicInfo = new HashMap<>();

        String albumName = song.getJSONObject("album").getString("name");
        String albumId = song.getJSONObject("album").getString("mid");
        String songName = cleanText(song.getString("title"));
        String songId = song.getString("mid");
        String mvId = song.getJSONObject("mv").getString("vid");
        String duration = song.getString("interval");

        JSONObject fileInfo = song.getJSONObject("file");
        String mp3Size = fileInfo.getString("size_128mp3");
        String hqSize = fileInfo.getString("size_320mp3");
        String sqSize = fileInfo.getString("size_flac");
        String hrSize = fileInfo.getString("size_hires");
        String tag = song.getString("tag");

        JSONArray singers = song.getJSONArray("singer");
        StringBuilder singerBuilder = new StringBuilder();
        for (int j = 0; j < singers.length() && j < MAX_SINGER_COUNT; j++) {
          if (j > 0) {
            singerBuilder.append(SEPARATOR);
          }
          singerBuilder.append(singers.getJSONObject(j).getString("name"));
        }
        String singerName = singerBuilder.toString().replace("/", " ");

        if (!hrSize.equals("0")) {
          musicInfo.put("br", R.drawable.hires);
          musicInfo.put("maxbr", "hr");
        } else if (!sqSize.equals("0")) {
          musicInfo.put("br", R.drawable.sq);
          musicInfo.put("maxbr", "sq");
        } else if (!hqSize.equals("0")) {
          musicInfo.put("br", R.drawable.hq);
          musicInfo.put("maxbr", "hq");
        } else {
          musicInfo.put("br", R.drawable.mp3);
          musicInfo.put("maxbr", "mp3");
        }

        if (tag.equals("11")) {
          musicInfo.put("yz", R.drawable.yz);
        }

        if (!mvId.equals(EMPTY)) {
          musicInfo.put("mv", R.drawable.mv);
        }

        if (duration.equals("0")) {
          continue;
        }

        musicInfo.put("id", songId);
        musicInfo.put("name", songName);
        musicInfo.put("singer", singerName);
        musicInfo.put("album", albumName);
        musicInfo.put("time", CommonUtils.formatTime(Integer.parseInt(duration)));
        musicInfo.put("filename", singerName + " - " + songName);
        musicInfo.put("mvid", mvId);
        musicInfo.put("mp3size", mp3Size);
        musicInfo.put("hqsize", hqSize);
        musicInfo.put("sqsize", sqSize);
        musicInfo.put("hrsize", hrSize);
        musicInfo.put("albumid", albumId);

        cachedResults.add(musicInfo);
      }

      return cachedResults;
    } catch (Exception e) {
      return null;
    }
  }

  @NonNull
  private static JSONObject getStringObjectMap(String keyword) throws JSONException {
    JSONObject requestBody = new JSONObject();
    JSONObject comm = new JSONObject();

    comm.put("ct", 11);
    comm.put("cv", "1003006");
    comm.put("v", "1003006");
    comm.put("os_ver", "12");
    comm.put("phonetype", "0");
    comm.put("devicelevel", "31");
    comm.put("tmeAppID", "qqmusiclight");
    comm.put("nettype", "NETWORK_WIFI");

    JSONObject param = new JSONObject();
    param.put("searchid", getSearchId());
    param.put("query", keyword);
    param.put("search_type", 0);
    param.put("num_per_page", 50);
    param.put("page_num", 1);
    param.put("highlight", true);
    param.put("grp", true);

    JSONObject req = new JSONObject();
    req.put("module", "music.search.SearchCgiService");
    req.put("method", "DoSearchForQQMusicMobile");
    req.put("param", param);

    requestBody.put("comm", comm);
    requestBody.put("req", req);
    return requestBody;
  }

  private static List<Map<String, Object>> searchNeteaseCloud(String keyword) {
    try {
      List<Map<String, Object>> resultList = new ArrayList<>();

      JSONObject requestParams = new JSONObject(new LinkedHashMap<>());
      requestParams.put("s", keyword);
      requestParams.put("type", 1);
      requestParams.put("offset", 0);
      requestParams.put("limit", 100);
      requestParams.put("strategy", 5);

      String encryptedParams = AESUtils.encryptParams(requestParams.toString());
      String encSecKey = AESUtils.getEncSecKey();

      Map<String, String> postData = new HashMap<>();
      postData.put("params", URLEncoder.encode(encryptedParams, "utf-8"));
      postData.put("encSecKey", encSecKey);

      JSONObject response = new JSONObject(
          HttpRequestUtils.postMap("http://music.163.com/weapi/search/get", postData));
      JSONArray songs = response.getJSONObject("result").getJSONArray("songs");

      for (int i = 0; i < songs.length(); i++) {
        JSONObject song = songs.getJSONObject(i);
        Map<String, Object> musicInfo = new HashMap<>();

        String songId = song.getString("id");
        String songName = song.getString("name").replace("/", " ");
        String mvId = song.getString("mv");
        String duration = song.getString("dt");
        String isOriginal = song.getString("originCoverType");

        JSONObject privilege = song.getJSONObject("privilege");
        String maxBrLevel = privilege.getString("maxBrLevel");
        String fee = privilege.getString("fee");
        String status = privilege.getString("st");

        String albumName = song.getJSONObject("al").getString("name");

        String mp3Size = getFileSize(song, "l");
        String hqSize = getFileSize(song, "h");
        String sqSize = getFileSize(song, "sq");
        String hrSize = getFileSize(song, "hr");

        JSONArray singers = song.getJSONArray("ar");
        StringBuilder singerBuilder = new StringBuilder();
        for (int j = 0; j < singers.length() && j < MAX_SINGER_COUNT; j++) {
          if (j > 0) {
            singerBuilder.append(SEPARATOR);
          }
          singerBuilder.append(singers.getJSONObject(j).getString("name"));
        }
        String singerName = singerBuilder.toString().replace("/", " ");

        musicInfo.put("name", songName);
        musicInfo.put("singer", singerName);
        musicInfo.put("time", CommonUtils.formatTime(Integer.parseInt(duration) / 1000));

        switch (maxBrLevel) {
          case "hires":
            musicInfo.put("br", R.drawable.hires);
            musicInfo.put("maxbr", "hr");
            break;
          case "lossless":
            musicInfo.put("br", R.drawable.sq);
            musicInfo.put("maxbr", "sq");
            break;
          case "exhigh":
            musicInfo.put("br", R.drawable.hq);
            musicInfo.put("maxbr", "hq");
            break;
          default:
            musicInfo.put("br", R.drawable.mp3);
            musicInfo.put("maxbr", "mp3");
            break;
        }

        if (status.equals("-200")) {
          musicInfo.put("br", R.drawable.nohave);
          continue;
        }

        if (fee.equals("4")) {
          musicInfo.put("br", R.drawable.pay);
          continue;
        }

        if (!mvId.equals("0")) {
          musicInfo.put("mv", R.drawable.mv);
        } else {
          mvId = EMPTY;
        }

        if (isOriginal.equals("1")) {
          musicInfo.put("yz", R.drawable.yz);
        }

        musicInfo.put("id", songId);
        musicInfo.put("mvid", mvId);
        musicInfo.put("filename", singerName + " - " + songName);
        musicInfo.put("cy", status);
        musicInfo.put("album", albumName);
        musicInfo.put("pay", fee);
        musicInfo.put("mp3size", mp3Size);
        musicInfo.put("hqsize", hqSize);
        musicInfo.put("sqsize", sqSize);
        musicInfo.put("hrsize", hrSize);

        resultList.add(musicInfo);
      }

      return resultList;
    } catch (Exception e) {
      return null;
    }
  }

  private static String cleanText(String text) {
    return text.replace("<em>", EMPTY)
        .replace("</em>", EMPTY)
        .replace("/", " ");
  }

  private static String limitSingerCount(String singerNames) {
    if (!singerNames.contains(SEPARATOR)) {
      return singerNames;
    }

    String[] singers = singerNames.split(SEPARATOR);
    if (singers.length <= MAX_SINGER_COUNT) {
      return singerNames;
    }

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < MAX_SINGER_COUNT; i++) {
      result.append(singers[i]);
      if (i < MAX_SINGER_COUNT - 1) {
        result.append(SEPARATOR);
      }
    }
    return result.toString();
  }

  private static void setSizeIfExists(Map<String, Object> map, JSONArray formats, int index,
      String key) {
    try {
      JSONObject format = formats.getJSONObject(index);
      if (format.has("asize")) {
        map.put(key, format.getString("asize"));
      } else {
        map.put(key, key.equals("sqsize") || key.equals("hrsize") ? "0" : 0);
      }
    } catch (JSONException e) {
      map.put(key, key.equals("sqsize") || key.equals("hrsize") ? "0" : 0);
    }
  }

  private static String getFileSize(JSONObject song, String quality) {
    try {
      if (!song.isNull(quality) && song.getJSONObject(quality).has("size")) {
        return song.getJSONObject(quality).getString("size");
      }
    } catch (JSONException ignored) {
    }
    return "0";
  }

  public static int randomInt(int min, int max) {
    Random random = new Random();
    return random.nextInt(max - min + 1) + min;
  }

  public static String getSearchId() {
    int e = randomInt(1, 20);
    long t = e * 18014398509481984L;
    long n = (long) randomInt(0, 4194304) * 4294967296L;
    long a = System.currentTimeMillis();
    long r = Math.round(a * 1000) % (24 * 60 * 60 * 1000);

    return String.valueOf(t + n + r);
  }
}