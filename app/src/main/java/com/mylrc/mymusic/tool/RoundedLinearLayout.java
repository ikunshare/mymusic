package com.mylrc.mymusic.tool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class RoundedLinearLayout extends LinearLayout {

  private float cornerRadius;
  private int viewWidth;

  private int viewHeight;

  public RoundedLinearLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RoundedLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    this.cornerRadius = 25.0f;
    float topLeftRadius = 20.0f;
    float topRightRadius = 20.0f;
    float bottomRightRadius = 20.0f;
    float bottomLeftRadius = 20.0f;
  }

  @Override
  public void draw(Canvas canvas) {
    canvas.save();

    Path path = new Path();
    RectF rectF = new RectF(0.0f, 0.0f, this.viewWidth, this.viewHeight);

    path.addRoundRect(rectF, this.cornerRadius, this.cornerRadius, Path.Direction.CCW);

    canvas.clipPath(path);

    super.draw(canvas);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    this.viewWidth = getMeasuredWidth();
    this.viewHeight = getMeasuredHeight();

    setMeasuredDimension(this.viewWidth, this.viewHeight);
  }

  public void setCornerRadius(float radius) {
    this.cornerRadius = radius;
    invalidate();
  }
}