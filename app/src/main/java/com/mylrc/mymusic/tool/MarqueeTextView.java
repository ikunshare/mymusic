package com.mylrc.mymusic.tool;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

public class MarqueeTextView extends androidx.appcompat.widget.AppCompatTextView {

  public MarqueeTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean isFocused() {
    return true;
  }

  @Override
  protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
    if (focused) {
      super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }
  }

  @Override
  public void onWindowFocusChanged(boolean hasWindowFocus) {
    if (hasWindowFocus) {
      super.onWindowFocusChanged(hasWindowFocus);
    }
  }
}