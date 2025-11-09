package com.mylrc.mymusic.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HistoryManager {

  private static final String SHARED_PREFERENCES_NAME = "pms";
  private static final String HISTORY_KEY = "history";
  private static final String HISTORY_SEPARATOR = "∮©∮";
  private static final int MAX_HISTORY_SIZE = 20;
  private static final String DEFAULT_HISTORY = "";

  private final Context context;

  public HistoryManager(Context context) {
    this.context = context;
  }

  public void clearHistory() {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
    sharedPreferences.edit().remove(HISTORY_KEY).apply();
  }

  public List<Map<String, Object>> getHistory() {
    List<Map<String, Object>> historyList = new ArrayList<>();
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
    String historyString = sharedPreferences.getString(HISTORY_KEY, DEFAULT_HISTORY);

    if (!TextUtils.isEmpty(historyString)) {
      String[] historyArray = historyString.split(HISTORY_SEPARATOR);
      for (String item : historyArray) {
        if (!TextUtils.isEmpty(item)) {
          Map<String, Object> map = new HashMap<>();
          map.put("data", item);
          historyList.add(map);
        }
      }
    }
    return historyList;
  }

  public List<String> getHistoryAsStringList() {
    List<String> historyList = new ArrayList<>();
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
    String historyString = sharedPreferences.getString(HISTORY_KEY, DEFAULT_HISTORY);

    if (!TextUtils.isEmpty(historyString)) {
      String[] historyArray = historyString.split(HISTORY_SEPARATOR);
      for (String item : historyArray) {
        if (!TextUtils.isEmpty(item)) {
          historyList.add(item);
        }
      }
    }
    return historyList;
  }

  public void saveHistory(String searchTerm) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);

    if (TextUtils.isEmpty(searchTerm)) {
      return;
    }

    String currentHistory = sharedPreferences.getString(HISTORY_KEY, DEFAULT_HISTORY);
    List<String> historyList = new ArrayList<>();

    if (!TextUtils.isEmpty(currentHistory)) {
      String[] historyArray = currentHistory.split(HISTORY_SEPARATOR);
      for (String item : historyArray) {
        if (!TextUtils.isEmpty(item)) {
          historyList.add(item);
        }
      }
    }

    Iterator<String> iterator = historyList.iterator();
    while (iterator.hasNext()) {
      if (searchTerm.equals(iterator.next())) {
        iterator.remove();
        break;
      }
    }

    historyList.add(0, searchTerm);

    if (historyList.size() > MAX_HISTORY_SIZE) {
      historyList = historyList.subList(0, MAX_HISTORY_SIZE);
    }

    StringBuilder newHistoryBuilder = new StringBuilder();
    for (int i = 0; i < historyList.size(); i++) {
      newHistoryBuilder.append(historyList.get(i));
      if (i < historyList.size() - 1) {
        newHistoryBuilder.append(HISTORY_SEPARATOR);
      }
    }

    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(HISTORY_KEY, newHistoryBuilder.toString());
    editor.apply();
  }

  public int getHistoryCount() {
    return getHistoryAsStringList().size();
  }

  public boolean contains(String searchTerm) {
    List<String> historyList = getHistoryAsStringList();
    return historyList.contains(searchTerm);
  }
}