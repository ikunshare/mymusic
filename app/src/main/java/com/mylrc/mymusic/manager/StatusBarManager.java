package com.mylrc.mymusic.manager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.mylrc.mymusic.enums.StatusBarColor;

public class StatusBarManager {

  private final Activity mActivity;

  public StatusBarManager(Activity activity) {
    if (activity == null) {
      throw new IllegalArgumentException("Activity 不能为空");
    }
    this.mActivity = activity;
  }

  public void setStatusBarTheme(StatusBarColor iconTheme) {
    Window window = this.mActivity.getWindow();

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

      int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

      if (iconTheme == StatusBarColor.BLACK) {
        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
      }

      window.getDecorView().setSystemUiVisibility(flags);
      window.setStatusBarColor(Color.TRANSPARENT);

    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

      int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

      window.getDecorView().setSystemUiVisibility(flags);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.setStatusBarColor(Color.TRANSPARENT);

    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

      window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
  }
}