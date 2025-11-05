package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.mylrc.mymusic.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.mp4.atom.Mp4NameBox;
import utils.StatusBarColor;
import utils.StatusBarManager;
import utils.ToastUtils;

public class FileChooserActivity extends Activity {

  private final Handler handler = new LoadCompleteHandler(this, Looper.getMainLooper());
  private List<Map<String, String>> directoryList;
  private ListView directoryListView;
  private TextView currentPathTextView;
  private String rootPath;
  private SharedPreferences preferences;
  private String currentPath = FrameBodyCOMM.DEFAULT;
  private boolean isLoading = true;

  public void loadDirectory(String str) {
    this.isLoading = false;
    this.currentPathTextView.setText("当前：" + str);
    new LoadDirectoryThread(this, str).start();
  }

  @Override
  protected void onCreate(Bundle bundle) {
    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    setContentView(R.layout.filechooser);
    this.directoryListView = findViewById(R.id.filechooserListView1);
    this.currentPathTextView = findViewById(R.id.filechooserTextView1);
    Button confirmButton = findViewById(R.id.filechooserButton1);
    this.rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    this.preferences = getSharedPreferences("pms", 0);
    confirmButton.setOnClickListener(new ConfirmButtonClickListener(this));
    this.directoryListView.setOnItemClickListener(new DirectoryItemClickListener(this));
    findViewById(R.id.filechooserRelativeLayout2).setOnClickListener(
        new BackButtonClickListener(this));
    loadDirectory(this.rootPath);
    super.onCreate(bundle);
  }

  @Override
  public boolean onKeyDown(int i2, KeyEvent keyEvent) {
    if (i2 != 4) {
      return true;
    }
    if (this.currentPath.equals(FrameBodyCOMM.DEFAULT) || this.currentPath.equals(this.rootPath)) {
      finish();
      return true;
    }
    String parent = new File(this.currentPath).getParent();
    this.currentPath = parent;
    loadDirectory(parent);
    return true;
  }

  class ConfirmButtonClickListener implements View.OnClickListener {

    final FileChooserActivity activity;

    ConfirmButtonClickListener(FileChooserActivity fileChooserActivity) {
      this.activity = fileChooserActivity;
    }

    @Override
    public void onClick(View view) {
      if (!this.activity.currentPath.equals(FrameBodyCOMM.DEFAULT)) {
        FileChooserActivity fileChooserActivity = this.activity;
        if (!fileChooserActivity.currentPath.equals(fileChooserActivity.rootPath)) {
          this.activity.preferences.edit().putString("downdirectory",
              this.activity.currentPath.replaceAll(this.activity.rootPath + "/",
                  FrameBodyCOMM.DEFAULT)).commit();
          this.activity.preferences.edit().putInt("storageType", 3).commit();
          Toast.makeText(this.activity.getApplicationContext(), "保存成功", Toast.LENGTH_LONG)
              .show();
          this.activity.finish();
          return;
        }
      }
      Toast.makeText(this.activity.getApplicationContext(), "不能保存在根目录", Toast.LENGTH_LONG)
          .show();
    }
  }

  class DirectoryItemClickListener implements AdapterView.OnItemClickListener {

    final FileChooserActivity activity;

    DirectoryItemClickListener(FileChooserActivity fileChooserActivity) {
      this.activity = fileChooserActivity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
      FileChooserActivity fileChooserActivity = this.activity;
      if (!fileChooserActivity.isLoading) {
        ToastUtils.showToast(fileChooserActivity.getApplicationContext(), "正在加载中，请稍候");
        return;
      }
      fileChooserActivity.currentPath = fileChooserActivity.directoryList.get(i2).get("path");
      FileChooserActivity fileChooserActivity2 = this.activity;
      fileChooserActivity2.loadDirectory(fileChooserActivity2.currentPath);
    }
  }

  class BackButtonClickListener implements View.OnClickListener {

    final FileChooserActivity activity;

    BackButtonClickListener(FileChooserActivity fileChooserActivity) {
      this.activity = fileChooserActivity;
    }

    @Override
    public void onClick(View view) {
      this.activity.finish();
    }
  }

  class LoadCompleteHandler extends Handler {

    final FileChooserActivity activity;

    LoadCompleteHandler(FileChooserActivity fileChooserActivity, Looper looper) {
      super(looper);
      this.activity = fileChooserActivity;
    }

    @Override
    public void handleMessage(Message message) {
      if (message.what == 1) {
        this.activity.directoryListView.setAdapter(
            new SimpleAdapter(this.activity.getApplicationContext(), this.activity.directoryList,
                R.layout.filechooserlist, new String[]{Mp4NameBox.IDENTIFIER},
                new int[]{R.id.filechooserlistTextView1}));
        this.activity.isLoading = true;
      }
      super.handleMessage(message);
    }
  }

  class LoadDirectoryThread extends Thread {

    final String directoryPath;
    final FileChooserActivity activity;

    LoadDirectoryThread(FileChooserActivity fileChooserActivity, String str) {
      this.activity = fileChooserActivity;
      this.directoryPath = str;
    }

    @Override
    public void run() {
      this.activity.directoryList = new ArrayList<>();
      File file = new File(this.directoryPath);
      if (file.isDirectory()) {
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
          List<File> listAsList = Arrays.asList(fileArrListFiles);
          Collections.sort(listAsList, new FileComparator(this));
          for (File file2 : listAsList) {
            if (file2.isDirectory()) {
              HashMap map = new HashMap();
              String name = file2.getName();
              String path = file2.getPath();
              map.put(Mp4NameBox.IDENTIFIER, name);
              map.put("path", path);
              this.activity.directoryList.add(map);
            }
          }
        } else {
          this.activity.directoryList = new ArrayList<>();
        }
        Message message = new Message();
        message.what = 1;
        this.activity.handler.sendMessage(message);
      }
    }

    class FileComparator implements Comparator<File> {

      final LoadDirectoryThread thread;

      FileComparator(LoadDirectoryThread loadDirectoryThread) {
        this.thread = loadDirectoryThread;
      }

      @Override
      public int compare(File file, File file2) {
        if (file.isFile() && file2.isDirectory()) {
          return 1;
        }
        if (file.isDirectory() && file2.isFile()) {
          return -1;
        }
        return file.getName().compareTo(file2.getName());
      }
    }
  }
}