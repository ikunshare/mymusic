package com.mylrc.mymusic.tool;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;

public class LrcEntry implements Comparable<LrcEntry> {

  private final long timestamp;

  private final String mainText;

  private String secondText;

  private StaticLayout staticLayout;

  private float offset = Float.MIN_VALUE;

  public LrcEntry(long timestamp, String mainText) {
    this.timestamp = timestamp;
    this.mainText = mainText;
  }

  private String getFullText() {
    if (TextUtils.isEmpty(this.secondText)) {
      return this.mainText;
    }
    return this.mainText + "\n" + this.secondText;
  }

  @Override
  public int compareTo(LrcEntry other) {
    if (other == null) {
      return -1;
    }
    return (int) (this.timestamp - other.getTimestamp());
  }

  public int getHeight() {
    if (this.staticLayout == null) {
      return 0;
    }
    return this.staticLayout.getHeight();
  }

  public float getOffset() {
    return this.offset;
  }

  public void setOffset(float offset) {
    this.offset = offset;
  }

  public StaticLayout getStaticLayout() {
    return this.staticLayout;
  }

  public String getText() {
    return this.mainText;
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public void initStaticLayout(TextPaint textPaint, int width, int align) {
    Layout.Alignment alignment;
    if (align == 1) {
      alignment = Layout.Alignment.ALIGN_NORMAL;
    } else if (align == 2) {
      alignment = Layout.Alignment.ALIGN_OPPOSITE;
    } else {
      alignment = Layout.Alignment.ALIGN_CENTER;
    }

    this.staticLayout = new StaticLayout(
        getFullText(),
        textPaint,
        width,
        alignment,
        1.0f,
        0.0f,
        false
    );
    this.offset = Float.MIN_VALUE;
  }

  public void setSecondText(String secondText) {
    this.secondText = secondText;
  }
}