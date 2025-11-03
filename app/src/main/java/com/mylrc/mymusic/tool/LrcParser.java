package com.mylrc.mymusic.tool;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;

class LrcParser {

  private static final Pattern PATTERN_LINE = Pattern.compile(
      "((\\[\\d\\d:\\d\\d\\.\\d{2,3}])+)(.+)");

  private static final Pattern PATTERN_TIME = Pattern.compile("\\[(\\d\\d):(\\d\\d)\\.(\\d{2,3})]");

  static String formatTime(long timeMs) {
    return String.format(Locale.getDefault(), "%02d", (int) (timeMs / 60000))
        + ":"
        + String.format(Locale.getDefault(), "%02d", (int) ((timeMs / 1000) % 60));
  }

  private static List<LrcEntry> parseLine(String line) throws NumberFormatException {
    if (TextUtils.isEmpty(line)) {
      return null;
    }

    Matcher lineMatcher = PATTERN_LINE.matcher(line.trim());
    if (!lineMatcher.matches()) {
      return null;
    }

    String timeTagsGroup = lineMatcher.group(1);
    String lrcText = lineMatcher.group(3);

    ArrayList<LrcEntry> entryList = new ArrayList<>();
    Matcher timeMatcher = PATTERN_TIME.matcher(timeTagsGroup);

    while (timeMatcher.find()) {
      long minutes = Long.parseLong(Objects.requireNonNull(timeMatcher.group(1)));
      long seconds = Long.parseLong(timeMatcher.group(2));
      String millisecondsStr = timeMatcher.group(3);
      long milliseconds = Long.parseLong(millisecondsStr);

      if (millisecondsStr.length() == 2) {
        milliseconds *= 10;
      }

      long totalTime = milliseconds + (minutes * 60000) + (seconds * 1000);
      entryList.add(new LrcEntry(totalTime, lrcText));
    }

    return entryList;
  }

  private static List<LrcEntry> parseFromFile(File file) throws IOException, NumberFormatException {
    if (file == null || !file.exists()) {
      return null;
    }

    ArrayList<LrcEntry> entryList = new ArrayList<>();

    try {
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)
      );

      String line;
      while ((line = reader.readLine()) != null) {
        List<LrcEntry> lineEntries = parseLine(line);
        if (lineEntries != null && !lineEntries.isEmpty()) {
          entryList.addAll(lineEntries);
        }
      }

      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Collections.sort(entryList);
    return entryList;
  }

  private static List<LrcEntry> parseFromString(String lrcText) throws NumberFormatException {
    if (TextUtils.isEmpty(lrcText)) {
      return null;
    }

    if (lrcText.startsWith("\ufeff")) {
      lrcText = lrcText.replace("\ufeff", FrameBodyCOMM.DEFAULT);
    }

    ArrayList<LrcEntry> entryList = new ArrayList<>();

    for (String line : lrcText.split("\\n")) {
      List<LrcEntry> lineEntries = parseLine(line);
      if (lineEntries != null && !lineEntries.isEmpty()) {
        entryList.addAll(lineEntries);
      }
    }

    Collections.sort(entryList);
    return entryList;
  }

  static List<LrcEntry> parseDualLrcFiles(File[] files) throws IOException, NumberFormatException {
    if (files == null || files.length != 2) {
      return null;
    }

    File mainLrcFile = files[0];
    if (mainLrcFile == null) {
      return null;
    }

    File secondLrcFile = files[1];

    List<LrcEntry> mainEntries = parseFromFile(mainLrcFile);
    List<LrcEntry> secondEntries = parseFromFile(secondLrcFile);

    if (mainEntries != null && secondEntries != null) {
      for (LrcEntry mainEntry : mainEntries) {
        for (LrcEntry secondEntry : secondEntries) {
          if (mainEntry.getTimestamp() == secondEntry.getTimestamp()) {
            mainEntry.setSecondText(secondEntry.getText());
          }
        }
      }
    }

    return mainEntries;
  }

  static List<LrcEntry> parseDualLrcStrings(String[] lrcTexts) throws NumberFormatException {
    if (lrcTexts == null || lrcTexts.length != 2 || TextUtils.isEmpty(lrcTexts[0])) {
      return null;
    }

    String mainLrcText = lrcTexts[0];
    String secondLrcText = lrcTexts[1];

    List<LrcEntry> mainEntries = parseFromString(mainLrcText);
    List<LrcEntry> secondEntries = parseFromString(secondLrcText);

    if (mainEntries != null && secondEntries != null) {
      for (LrcEntry mainEntry : mainEntries) {
        for (LrcEntry secondEntry : secondEntries) {
          if (mainEntry.getTimestamp() == secondEntry.getTimestamp()) {
            mainEntry.setSecondText(secondEntry.getText());
          }
        }
      }
    }

    return mainEntries;
  }

  static void fixAnimationScale() throws IllegalAccessException, NoSuchFieldException,
      SecurityException, IllegalArgumentException {
    try {
      @SuppressLint("SoonBlockedPrivateApi") Field durationScaleField = ValueAnimator.class.getDeclaredField(
          "sDurationScale");
      durationScaleField.setAccessible(true);
      durationScaleField.setFloat(null, 1.0f);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}