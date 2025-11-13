package com.mylrc.mymusic.utils;

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

  public static String getMD5(String filePath) {
    byte[] buffer = new byte[1024];
    try {
      try (FileInputStream inputStream = new FileInputStream(filePath)) {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        while (true) {
          int bytesRead = inputStream.read(buffer);
          if (bytesRead <= 0) {
            inputStream.close();
            return bytesToHex(messageDigest.digest());
          }
          messageDigest.update(buffer, 0, bytesRead);
        }
      }
    } catch (Exception e) {
      Log.e("FileUtils", "getMD5 failed: " + e.getMessage());
      return FrameBodyCOMM.DEFAULT;
    }
  }

  public static boolean copyFile(String sourcePath, String destinationPath) throws IOException {
    try {
      File sourceFile = new File(sourcePath);
      if (!sourceFile.exists()) {
        Log.e("FileUtils", "copyFile: source file does not exist.");
        return false;
      }
      if (!sourceFile.isFile()) {
        Log.e("FileUtils", "copyFile: source is not a file.");
        return false;
      }
      if (!sourceFile.canRead()) {
        Log.e("FileUtils", "copyFile: source file cannot be read.");
        return false;
      }

      try (FileInputStream inputStream = new FileInputStream(sourcePath);
          FileOutputStream outputStream = new FileOutputStream(destinationPath)) {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
          outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        return true;
      }
    } catch (Exception e) {
      Log.e("FileUtils", "copyFile failed: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public static boolean deleteFile(String filePath) {
    File file = new File(filePath);
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

  public static void createDirectory(String directoryPath) {
    File directory = new File(directoryPath);
    if (!directory.exists()) {
      directory.mkdirs();
    }
  }

  public static String readFileUTF8(String filePath) throws IOException {
    try (FileInputStream inputStream = new FileInputStream(filePath);
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      StringBuilder content = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        content.append(line);
        content.append("\n");
      }
      return content.toString();
    } catch (Exception e) {
      Log.e("FileUtils", "readFileUTF8 failed: " + e.getMessage());
      return FrameBodyCOMM.DEFAULT;
    }
  }

  public static String readFileWithEncoding(String filePath) {
    try {
      // Determine encoding based on user preferences
      String encoding = StandardCharsets.UTF_8.name();
      if (APPApplication.sharedPreferences != null) {
        int encodingType = APPApplication.sharedPreferences.getInt("type", 0);
        encoding = (encodingType == 0) ? StandardCharsets.UTF_8.name() : "GBK";
      }

      try (FileInputStream inputStream = new FileInputStream(filePath);
          BufferedReader reader = new BufferedReader(
              new InputStreamReader(inputStream, encoding))) {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          content.append(line);
          content.append("\n");
        }
        return content.toString();
      }
    } catch (Exception e) {
      Log.e("FileUtils", "readFileWithEncoding failed: " + e.getMessage());
      return FrameBodyCOMM.DEFAULT;
    }
  }

  public static void writeFile(String content, String filePath, String encoding) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(filePath), encoding))) {
      writer.write(content);
      writer.flush();
    } catch (IOException e) {
      Log.e("FileUtils", "writeFile failed: " + e.getMessage());
      throw e;
    }
  }

  public static String bytesToHex(byte[] bytes) {
    char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    StringBuilder hexString = new StringBuilder(bytes.length * 2);
    for (byte b : bytes) {
      hexString.append(hexChars[(b & 0xF0) >>> 4]);
      hexString.append(hexChars[b & 0x0F]);
    }
    return hexString.toString();
  }

  public static void writeId3Tags(String filePath, String title, String artist, String album,
      String coverPath, String lyricsPath) {
    AudioFileWriter fileWriter;
    AudioFile audioFile;
    try {
      if (filePath.endsWith(".mp3")) {
        // Handle MP3 files
        audioFile = new MP3FileReader().read(new File(filePath));
        audioFile.setTag(audioFile.createDefaultTag());
        Tag tag = audioFile.getTag();
        tag.setField(FieldKey.TITLE, title);
        tag.setField(FieldKey.ARTIST, artist);
        tag.setField(FieldKey.ALBUM, album);
        if (lyricsPath != null) {
          tag.setField(FieldKey.LYRICS, readFileWithEncoding(lyricsPath));
        }
        if (coverPath != null) {
          tag.setField(ArtworkFactory.createArtworkFromFile(new File(coverPath)));
        }
        fileWriter = new MP3FileWriter();
        fileWriter.write(audioFile);

      } else if (filePath.endsWith(".flac")) {
        // Handle FLAC files
        audioFile = new FlacFileReader().read(new File(filePath));
        FlacTag flacTag = (FlacTag) audioFile.getTag();
        flacTag.setField(FieldKey.TITLE, title);
        flacTag.setField(FieldKey.ARTIST, artist);
        flacTag.setField(FieldKey.ALBUM, album);
        if (lyricsPath != null) {
          flacTag.setField(FieldKey.LYRICS, readFileWithEncoding(lyricsPath));
        }
        if (coverPath != null) {
          try (RandomAccessFile coverFile = new RandomAccessFile(new File(coverPath), "r")) {
            byte[] coverImageData = new byte[(int) coverFile.length()];
            coverFile.read(coverImageData);
            flacTag.setField(flacTag.createArtworkField(coverImageData, PictureTypes.DEFAULT_ID,
                ImageFormats.MIME_TYPE_JPEG, FrameBodyCOMM.DEFAULT, 800, 800, 0, 0));
          }
        }
        fileWriter = new FlacFileWriter();
        fileWriter.write(audioFile);

        Log.d("FileUtils",
            "writeId3 to " + filePath + " Success\nname: " + title + " cover: " + coverPath);
      }
    } catch (Exception e) {
      Log.e("FileUtils", "writeId3Tags failed: " + e.getMessage());
      Log.getStackTraceString(e);
    }
  }
}