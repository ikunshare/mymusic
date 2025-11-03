package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.mylrc.mymusic.R;
import com.mylrc.mymusic.enums.StatusBarColor;
import com.mylrc.mymusic.manager.AppUpdateManager;
import com.mylrc.mymusic.manager.StatusBarManager;
import com.mylrc.mymusic.network.HttpRequestUtils;
import com.mylrc.mymusic.utils.ToastUtils;
import java.util.HashMap;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class BrowserActivity extends Activity {

  WebView webView;
  ProgressBar progressBar;
  TextView titleTextView;
  View backButton;
  private ValueCallback<Uri> filePathCallback;
  private ValueCallback<Uri[]> filePathCallbackArray;

  /* JADX INFO: Access modifiers changed from: private */
  public void processNeteaseLogin(String str) {
    new Thread(new NeteaseLoginThread(this, str)).start();
  }

  private void handleFileChooserResult(int i2, int i3, Intent intent) {
    Uri[] uriArr;
    if (i2 != 400 || this.filePathCallbackArray == null) {
      return;
    }
    if (i3 != -1 || intent == null) {
      uriArr = null;
    } else {
      String dataString = intent.getDataString();
      ClipData clipData = intent.getClipData();
      if (clipData != null) {
        Uri[] uriArr2 = new Uri[clipData.getItemCount()];
        for (int i4 = 0; i4 < clipData.getItemCount(); i4++) {
          uriArr2[i4] = clipData.getItemAt(i4).getUri();
        }
        uriArr = uriArr2;
      } else {
        uriArr = null;
      }
      if (dataString != null) {
        uriArr = new Uri[]{Uri.parse(dataString)};
      }
    }
    this.filePathCallbackArray.onReceiveValue(uriArr);
    this.filePathCallbackArray = null;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void openFileChooser() {
    Intent intent = new Intent("android.intent.action.GET_CONTENT");
    intent.addCategory("android.intent.category.OPENABLE");
    intent.setType("image/*");
    startActivityForResult(Intent.createChooser(intent, "Image Chooser"), 400);
  }

  @Override
  // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
  public Resources getResources() {
    Resources resources = super.getResources();
    Configuration configuration = new Configuration();
    configuration.setToDefaults();
    resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    return resources;
  }

  @Override // android.app.Activity
  protected void onActivityResult(int i2, int i3, Intent intent) {
    super.onActivityResult(i2, i3, intent);
    if (i2 == 400) {
      if (this.filePathCallback == null && this.filePathCallbackArray == null) {
        return;
      }
      Uri data = (intent == null || i3 != -1) ? null : intent.getData();
      if (this.filePathCallbackArray != null) {
        handleFileChooserResult(i2, i3, intent);
        return;
      }
      ValueCallback<Uri> valueCallback = this.filePathCallback;
      if (valueCallback != null) {
        valueCallback.onReceiveValue(data);
        this.filePathCallback = null;
      }
    }
  }

  @Override // android.app.Activity
  public void onBackPressed() {
    if (this.webView.canGoBack()) {
      this.webView.goBack();
    } else {
      super.onBackPressed();
      finish();
    }
  }

  @Override // android.app.Activity
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    new StatusBarManager(this).setStatusBarTheme(StatusBarColor.BLACK);
    setContentView(R.layout.browser);
    this.webView = findViewById(R.id.myX5WebView);
    this.titleTextView = findViewById(R.id.webTextView1);
    this.backButton = findViewById(R.id.webRelativeLayout1);
    this.progressBar = findViewById(R.id.progressBar);
    WebSettings settings = this.webView.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setDomStorageEnabled(true);
    settings.setMixedContentMode(0);
    this.webView.addJavascriptInterface(new JavaScriptInterface(this), "local_obj");
    String stringExtra = getIntent().getStringExtra("url");
    this.webView.setWebViewClient(new CustomWebViewClient(this));
    this.webView.setWebChromeClient(new CustomWebChromeClient(this));
    this.webView.setDownloadListener(new CustomDownloadListener(this));
    this.backButton.setOnClickListener(new BackButtonClickListener(this));
    this.webView.loadUrl(stringExtra);
  }

  @Override // android.app.Activity
  protected void onDestroy() {
    deleteDatabase("webview.db");
    deleteDatabase("webviewCache.db");
    this.webView.clearCache(true);
    super.onDestroy();
  }

  @Override // android.app.Activity, android.view.KeyEvent.Callback
  public boolean onKeyDown(int i2, KeyEvent keyEvent) {
    if (i2 != 4) {
      return super.onKeyDown(i2, keyEvent);
    }
    onBackPressed();
    return true;
  }

  @Override // android.app.Activity
  protected void onNewIntent(Intent intent) {
    if (intent == null) {
      return;
    }
    Uri data = intent.getData();
    if (data != null) {
      this.webView.loadUrl(data.toString());
    }
    super.onNewIntent(intent);
  }

  static class CustomWebViewClient extends WebViewClient {

    final BrowserActivity activity;

    CustomWebViewClient(BrowserActivity browserActivity) {
      this.activity = browserActivity;
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
      super.onPageFinished(webView, str);
      if (str.equals("https://y.music.163.com/m")) {
        String cookie = CookieManager.getInstance().getCookie(str);
        AppUpdateManager.sharedPreferences.edit().putString("wyytoken", cookie).apply();
        this.activity.processNeteaseLogin(cookie);
        return;
      }
      if (str.equals(
          "https://i.y.qq.com/n2/m/share/details/toplist.html?ADTAG=ryqq.toplist&type=0&id=62")) {
        String cookie2 = CookieManager.getInstance().getCookie(str);
        HashMap map = new HashMap();
        String[] strArrSplit = cookie2.split(";");
        for (String str2 : strArrSplit) {
          String[] strArrSplit2 = str2.trim().split("=");
          if (strArrSplit2.length == 1) {
            map.put(strArrSplit2[0], FrameBodyCOMM.DEFAULT);
          } else {
            map.put(strArrSplit2[0], strArrSplit2[1]);
          }
        }
        AppUpdateManager.sharedPreferences.edit().putString("qquin", (String) map.get("uin"))
            .apply();
        AppUpdateManager.sharedPreferences.edit().putString("qqkey", (String) map.get("qm_keyst"))
            .apply();
        AppUpdateManager.sharedPreferences.edit()
            .putString("qqtoken", (String) map.get("psrf_qqaccess_token")).apply();
        ToastUtils.showToast(this.activity, "登录成功，请重新获取");
        this.activity.finish();
      }
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
      Log.d("swallow", "onPageStarted------->" + str);
      super.onPageStarted(webView, str, bitmap);
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler,
        SslError sslError) {
      sslErrorHandler.proceed();
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
      if (str.startsWith("http://") || str.startsWith("https://")) {
        webView.loadUrl(str);
      } else {
        try {
          this.activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Exception e2) {
          Toast.makeText(this.activity, "没有找到相应的应用执行该操作", Toast.LENGTH_LONG).show();
        }
      }
      return true;
    }
  }

  class CustomWebChromeClient extends WebChromeClient {

    final BrowserActivity activity;

    CustomWebChromeClient(BrowserActivity browserActivity) {
      this.activity = browserActivity;
    }

    @Override // android.webkit.WebChromeClient
    public void onProgressChanged(WebView webView, int i2) {
      if (i2 == 100) {
        this.activity.progressBar.setVisibility(View.GONE);
      } else {
        this.activity.progressBar.setVisibility(View.VISIBLE);
        this.activity.progressBar.setProgress(i2);
      }
      super.onProgressChanged(webView, i2);
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedTitle(WebView webView, String str) {
      this.activity.titleTextView.setText(str);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback,
        WebChromeClient.FileChooserParams fileChooserParams) {
      this.activity.filePathCallbackArray = valueCallback;
      this.activity.openFileChooser();
      return true;
    }
  }

  class CustomDownloadListener implements DownloadListener {

    final BrowserActivity activity;

    CustomDownloadListener(BrowserActivity browserActivity) {
      this.activity = browserActivity;
    }

    @Override // android.webkit.DownloadListener
    public void onDownloadStart(String str, String str2, String str3, String str4, long j2) {
      this.activity.progressBar.setVisibility(View.GONE);
      try {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        this.activity.startActivity(intent);
      } catch (Exception e2) {
        ToastUtils.showToast(this.activity, "未安装浏览器类型的应用");
      }
    }
  }

  class BackButtonClickListener implements View.OnClickListener {

    final BrowserActivity activity;

    BackButtonClickListener(BrowserActivity browserActivity) {
      this.activity = browserActivity;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
      this.activity.finish();
    }
  }

  class NeteaseLoginThread implements Runnable {

    final String cookie;
    final BrowserActivity activity;

    NeteaseLoginThread(BrowserActivity browserActivity, String str) {
      this.activity = browserActivity;
      this.cookie = str;
    }

    @Override // java.lang.Runnable
    public void run() {
      try {
        HashMap map = new HashMap();
        map.put("Cookie", this.cookie);
        AppUpdateManager.sharedPreferences.edit().putString("wyyuid", new JSONObject(
            HttpRequestUtils.getWithHeaders("https://interface.music.163.com/api/nuser/account/get",
                map)).getJSONObject("profile").getString("userId")).apply();
        ToastUtils.showToast(this.activity.getApplicationContext(), "登录成功，请重新获取");
        this.activity.finish();
      } catch (Exception ignored) {
      }
    }
  }

  final class JavaScriptInterface {

    final BrowserActivity activity;

    JavaScriptInterface(BrowserActivity browserActivity) {
      this.activity = browserActivity;
    }

    @JavascriptInterface
    public void showSource(String str) {
      try {
        AppUpdateManager.sharedPreferences.edit().putString("wyyuid",
            str.substring(str.indexOf("userId") + 8, str.indexOf("userType") - 2)).apply();
        ToastUtils.showToast(this.activity.getApplicationContext(), "登录成功，请重新获取");
        this.activity.finish();
      } catch (Exception ignored) {
      }
    }
  }
}