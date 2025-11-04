package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import androidx.core.content.FileProvider;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.database.SongDatabaseHelper;
import com.mylrc.mymusic.enums.StatusBarColor;
import com.mylrc.mymusic.manager.StatusBarManager;
import com.mylrc.mymusic.ui.dialog.DialogFactory;
import com.mylrc.mymusic.utils.ToastUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jaudiotagger.tag.mp4.atom.Mp4NameBox;

/* loaded from: classes.dex */
public class DownloadHistoryActivity extends Activity {

  final Handler handler = new MessageHandler(this, Looper.getMainLooper());
  List<Map<String, Object>> historyList;
  Dialog progressDialog;
  ListView historyListView;

  /* JADX INFO: Access modifiers changed from: private */
  public void dismissProgressDialog() {
    Dialog dialog = this.progressDialog;
    if (dialog == null || !dialog.isShowing()) {
      return;
    }
    this.progressDialog.dismiss();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void showOperationDialog(String str) {
    Dialog dialogA = new DialogFactory().createDialog(this);
    dialogA.show();
    Display defaultDisplay = getWindowManager().getDefaultDisplay();
    WindowManager.LayoutParams attributes = dialogA.getWindow().getAttributes();
    attributes.width = defaultDisplay.getWidth();
    dialogA.getWindow().setAttributes(attributes);
    View viewInflate = LayoutInflater.from(this).inflate(R.layout.fgdialog, null);
    dialogA.setContentView(viewInflate);
    Button button = viewInflate.findViewById(R.id.fgdialogButton1);
    Button button2 = viewInflate.findViewById(R.id.fgdialogButton2);
    ((TextView) viewInflate.findViewById(R.id.fgdialogTextView1)).setText("选择操作");
    button.setText("打开");
    button2.setText("分享");
    button.setOnClickListener(new OpenFileClickListener(this, dialogA, str));
    button2.setOnClickListener(new ShareFileClickListener(this, dialogA, str));
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void sendMessage(int i2) {
    Message message = new Message();
    message.what = i2;
    this.handler.sendMessage(message);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void showProgressDialogMessage() {
    sendMessage(2);
  }

  public void showProgressDialog() {
    if (this.progressDialog == null) {
      Dialog dialog = new Dialog(this);
      this.progressDialog = dialog;
      dialog.requestWindowFeature(1);
      this.progressDialog.getWindow().setWindowAnimations(R.style.loadingAnim);
      this.progressDialog.setContentView(R.layout.loading);
      this.progressDialog.setCancelable(false);
    }
    if (this.progressDialog.isShowing() || isFinishing()) {
      return;
    }
    this.progressDialog.show();
  }

  public void loadHistory() {
    new LoadHistoryThread(this).start();
  }

  public void initializeViews() {
    loadHistory();
    this.historyListView.setOnItemClickListener(new ListItemClickListener(this));
    findViewById(R.id.lsRelativeLayout1).setOnClickListener(new BackButtonClickListener(this));
  }

  public void showToast(String str) {
    Message message = new Message();
    message.what = 0;
    message.obj = str;
    this.handler.sendMessage(message);
  }

  public void openFile(String str) {
    Intent intent;
    try {
      if (Build.VERSION.SDK_INT >= 24) {
        intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(
            FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileProvider",
                new File(str)), "audio/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      } else {
        intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(new File(str)), "audio/*");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      }
      startActivity(intent);
    } catch (Exception e2) {
      ToastUtils.showToast(this, e2.toString());
    }
  }

  public void shareFile(String str) {
    Intent intent;
    try {
      if (Build.VERSION.SDK_INT >= 24) {
        intent = new Intent("android.intent.action.SEND");
        intent.setType("audio/*");
        intent.putExtra("android.intent.extra.STREAM",
            FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileProvider",
                new File(str)));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      } else {
        intent = new Intent("android.intent.action.SEND");
        intent.setType("audio/*");
        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(str)));
      }
      startActivity(intent);
    } catch (Exception e2) {
      ToastUtils.showToast(this, e2.toString());
    }
  }

  @Override // android.app.Activity
  protected void onCreate(Bundle bundle) {
    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    setContentView(R.layout.history);
    this.historyListView = findViewById(R.id.lsListView1);
    this.historyList = new ArrayList<>();
    initializeViews();
    super.onCreate(bundle);
  }

  @Override // android.app.Activity
  protected void onDestroy() {
    dismissProgressDialog();
    super.onDestroy();
  }

  class ListItemClickListener implements AdapterView.OnItemClickListener {

    final DownloadHistoryActivity activity;

    ListItemClickListener(DownloadHistoryActivity downloadHistoryActivity) {
      this.activity = downloadHistoryActivity;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
      String string = this.activity.historyList.get(i2).get("path").toString();
      if (new File(string).exists()) {
        this.activity.showOperationDialog(string);
      } else {
        ToastUtils.showToast(this.activity, "无法操作，文件已被移动或删除");
      }
    }
  }

  class BackButtonClickListener implements View.OnClickListener {

    final DownloadHistoryActivity activity;

    BackButtonClickListener(DownloadHistoryActivity downloadHistoryActivity) {
      this.activity = downloadHistoryActivity;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      this.activity.finish();
    }
  }

  class MessageHandler extends Handler {

    final DownloadHistoryActivity activity;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MessageHandler(DownloadHistoryActivity downloadHistoryActivity, Looper looper) {
      super(looper);
      this.activity = downloadHistoryActivity;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i2 = message.what;
      if (i2 == 0) {
        ToastUtils.showToast(this.activity, message.obj.toString());
      } else if (i2 == 1) {
        this.activity.historyListView.setAdapter(
            new SimpleAdapter(
                this.activity.getApplicationContext(),
                this.activity.historyList,
                R.layout.history_item,
                new String[]{Mp4NameBox.IDENTIFIER, "path"},
                new int[]{R.id.lsitemTextView1, R.id.lsitemTextView2}
            )
        );
      } else if (i2 == 2) {
        this.activity.showProgressDialog();
      }
      super.handleMessage(message);
    }
  }

  class LoadHistoryThread extends Thread {

    final DownloadHistoryActivity activity;

    LoadHistoryThread(DownloadHistoryActivity downloadHistoryActivity) {
      this.activity = downloadHistoryActivity;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      this.activity.showProgressDialogMessage();
      try {
        Thread.sleep(500L);
      } catch (InterruptedException ignored) {
      }
      SQLiteDatabase writableDatabase = new SongDatabaseHelper(this.activity).getWritableDatabase();
      writableDatabase.beginTransaction();
      Cursor cursorQuery = writableDatabase.query("song_list", null, null, null, null, null,
          "id desc");
      while (cursorQuery.moveToNext()) {
        String string = cursorQuery.getString(
            cursorQuery.getColumnIndexOrThrow(Mp4NameBox.IDENTIFIER));
        String string2 = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("path"));
        HashMap map = new HashMap();
        map.put(Mp4NameBox.IDENTIFIER, string);
        if (new File(string2).exists()) {
          map.put("path", string2);
        } else {
          map.put("path", "文件已被移动或删除");
        }
        this.activity.historyList.add(map);
      }
      if (this.activity.historyList.size() == 0) {
        this.activity.showToast("下载历史空空如也");
      }
      this.activity.dismissProgressDialog();
      writableDatabase.endTransaction();
      writableDatabase.close();
      this.activity.sendMessage(1);
    }
  }

  class OpenFileClickListener implements View.OnClickListener {

    final Dialog dialog;
    final String filePath;
    final DownloadHistoryActivity activity;

    OpenFileClickListener(DownloadHistoryActivity downloadHistoryActivity, Dialog dialog,
        String str) {
      this.activity = downloadHistoryActivity;
      this.dialog = dialog;
      this.filePath = str;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      this.dialog.dismiss();
      this.activity.openFile(this.filePath);
    }
  }

  class ShareFileClickListener implements View.OnClickListener {

    final Dialog dialog;
    final String filePath;
    final DownloadHistoryActivity activity;

    ShareFileClickListener(DownloadHistoryActivity downloadHistoryActivity, Dialog dialog,
        String str) {
      this.activity = downloadHistoryActivity;
      this.dialog = dialog;
      this.filePath = str;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      this.dialog.dismiss();
      this.activity.shareFile(this.filePath);
    }
  }
}