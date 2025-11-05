package utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.mylrc.mymusic.tool.APPApplication;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.flac.FlacFileReader;
import org.jaudiotagger.audio.flac.FlacFileWriter;
import org.jaudiotagger.audio.generic.AudioFileWriter;
import org.jaudiotagger.audio.mp3.MP3FileReader;
import org.jaudiotagger.audio.mp3.MP3FileWriter;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.flac.FlacTag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.id3.valuepair.ImageFormats;
import org.jaudiotagger.tag.images.ArtworkFactory;
import org.jaudiotagger.tag.reference.PictureTypes;

public class FileUtils {

  public static String getMD5(String str) {
    byte[] bArr = new byte[1024];
    try {
      try (FileInputStream fileInputStream = new FileInputStream(str)) {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        while (true) {
          int i2 = fileInputStream.read(bArr);
          if (i2 <= 0) {
            fileInputStream.close();
            return bytesToHex(messageDigest.digest());
          }
          messageDigest.update(bArr, 0, i2);
        }
      }
    } catch (Exception e2) {
      Log.e("FileUtils", "getMD5 failed: " + e2.getMessage());
      return FrameBodyCOMM.DEFAULT;
    }
  }

  public static boolean copyFile(String str, String str2) throws IOException {
    FileInputStream fileInputStream = null;
    FileOutputStream fileOutputStream = null;
    try {
      File file = new File(str);
      if (!file.exists()) {
        Log.e("--Method--", "copyFile:  oldFile not exist.");
        return false;
      }
      if (!file.isFile()) {
        Log.e("--Method--", "copyFile:  oldFile not file.");
        return false;
      }
      if (!file.canRead()) {
        Log.e("--Method--", "copyFile:  oldFile cannot read.");
        return false;
      }
      fileInputStream = new FileInputStream(str);
      fileOutputStream = new FileOutputStream(str2);
      byte[] bArr = new byte[1024];
      while (true) {
        int i2 = fileInputStream.read(bArr);
        if (-1 == i2) {
          fileOutputStream.flush();
          return true;
        }
        fileOutputStream.write(bArr, 0, i2);
      }
    } catch (Exception e2) {
      Log.e("FileUtils", "copyFile failed: " + e2.getMessage());
      e2.printStackTrace();
      return false;
    } finally {
      if (fileInputStream != null) {
        try {
          fileInputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (fileOutputStream != null) {
        try {
          fileOutputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static boolean deleteFile(String str) {
    File file = new File(str);
    if (file.exists()) {
      return file.delete();
    }
    return false;
  }

  public static String getExternalStoragePath(Context context) {
    try {
      File file = Environment.getExternalStorageDirectory();
      if (file.canWrite()) {
        return file.getPath();
      }
    } catch (Exception e) {
      Log.e("FileUtils", "getExternalStoragePath failed: " + e.getMessage());
    }
    return null;
  }

  public static void createDirectory(String str) {
    File file = new File(str);
    if (!file.exists()) {
      file.mkdirs();
    }
  }

  public static String readFileUTF8(String str) throws IOException {
    try (FileInputStream fileInputStream = new FileInputStream(
        str); BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(fileInputStream, StandardCharsets.UTF_8))) {
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        sb.append(line);
        sb.append("\n");
      }
      return sb.toString();
    } catch (Exception e2) {
      Log.e("FileUtils", "readFileUTF8 failed: " + e2.getMessage());
      return FrameBodyCOMM.DEFAULT;
    }
  }

  public static String readFileWithEncoding(String str) {
    FileInputStream fileInputStream = null;
    BufferedReader bufferedReader = null;
    try {
      fileInputStream = new FileInputStream(str);
      if (APPApplication.sharedPreferences != null) {
        bufferedReader = new BufferedReader(
            APPApplication.sharedPreferences.getInt("type", 0) == 0 ? new InputStreamReader(
                fileInputStream, StandardCharsets.UTF_8)
                : new InputStreamReader(fileInputStream, "GBK"));
      } else {
        bufferedReader = new BufferedReader(
            new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
      }
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        sb.append(line);
        sb.append("\n");
      }
      return sb.toString();
    } catch (Exception e2) {
      Log.e("FileUtils", "readFileWithEncoding failed: " + e2.getMessage());
      return FrameBodyCOMM.DEFAULT;
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (fileInputStream != null) {
        try {
          fileInputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void writeFile(String str, String str2, String str3) throws IOException {
    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(str2), str3))) {
      bufferedWriter.write(str);
      bufferedWriter.flush();
    } catch (IOException e2) {
      Log.e("FileUtils", "writeFile failed: " + e2.getMessage());
      throw e2;
    }
  }

  public static String bytesToHex(byte[] bArr) {
    char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    StringBuilder sb = new StringBuilder(bArr.length * 2);
    for (byte b : bArr) {
      sb.append(cArr[(b & 240) >>> 4]);
      sb.append(cArr[b & 15]);
    }
    return sb.toString();
  }

  public static void writeId3Tags(String filePath, String title, String artist, String album,
      String coverPath,
      String lyrics) {
    AudioFileWriter flacFileWriter;
    AudioFile audioFile;
    try {
      if (!filePath.endsWith(".mp3")) {
        if (filePath.endsWith(".flac")) {
          AudioFile audioFile2 = new FlacFileReader().read(new File(filePath));
          FlacTag flacTag = (FlacTag) audioFile2.getTag();
          flacTag.setField(FieldKey.TITLE, title);
          flacTag.setField(FieldKey.ARTIST, artist);
          flacTag.setField(FieldKey.ALBUM, album);
          if (lyrics != null) {
            flacTag.setField(FieldKey.LYRICS, readFileWithEncoding(lyrics));
          }
          if (coverPath != null) {
            try (RandomAccessFile randomAccessFile = new RandomAccessFile(new File(coverPath),
                "r")) {
              byte[] bArr = new byte[(int) randomAccessFile.length()];
              randomAccessFile.read(bArr);
              flacTag.setField(flacTag.createArtworkField(bArr, PictureTypes.DEFAULT_ID,
                  ImageFormats.MIME_TYPE_JPEG, FrameBodyCOMM.DEFAULT, 800, 800, 0, 0));
            }
          }
          flacFileWriter = new FlacFileWriter();
          audioFile = audioFile2;
          flacFileWriter.write(audioFile);

          Log.d("FileUtils",
              "writeId3 to " + filePath + "Success" + "\n" + "name" + title + "cover" + coverPath);
        }
        return;
      }
      audioFile = new MP3FileReader().read(new File(filePath));
      audioFile.setTag(audioFile.createDefaultTag());
      Tag tag = audioFile.getTag();
      tag.setField(FieldKey.TITLE, title);
      tag.setField(FieldKey.ARTIST, artist);
      tag.setField(FieldKey.ALBUM, album);
      if (lyrics != null) {
        tag.setField(FieldKey.LYRICS, readFileWithEncoding(lyrics));
      }
      if (coverPath != null) {
        tag.setField(ArtworkFactory.createArtworkFromFile(new File(coverPath)));
      }
      flacFileWriter = new MP3FileWriter();
      flacFileWriter.write(audioFile);
    } catch (Exception e2) {
      Log.e("FileUtils", "writeId3Tags failed: " + e2.getMessage());
      Log.getStackTraceString(e2);
    }
  }
}