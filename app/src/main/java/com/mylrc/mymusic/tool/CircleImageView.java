package com.mylrc.mymusic.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class CircleImageView extends androidx.appcompat.widget.AppCompatImageView {

  private int radius;

  public CircleImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  @SuppressLint({"DrawAllocation"})
  protected void onDraw(Canvas canvas) {
    Paint paint = new Paint();
    Drawable drawable = getDrawable();

    if (drawable == null) {
      super.onDraw(canvas);
      return;
    }

    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
    if (bitmap == null) {
      super.onDraw(canvas);
      return;
    }

    Shader.TileMode tileMode = Shader.TileMode.CLAMP;
    BitmapShader bitmapShader = new BitmapShader(bitmap, tileMode, tileMode);

    int minSize = Math.min(bitmap.getHeight(), bitmap.getWidth());
    float scale = (this.radius * 2.0f) / minSize;

    Matrix matrix = new Matrix();
    matrix.setScale(scale, scale);
    bitmapShader.setLocalMatrix(matrix);

    paint.setShader(bitmapShader);

    canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));

    canvas.drawCircle(this.radius, this.radius, this.radius, paint);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    int minSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
    this.radius = minSize / 2;

    setMeasuredDimension(minSize, minSize);
  }
}