package com.mylrc.mymusic.tool;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

  boolean isMeasured;
  private int horizontalSpacing;
  private int verticalSpacing;
  private List<List<View>> allLines;
  private List<Integer> lineHeights;

  public FlowLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.horizontalSpacing = dpToPx(8);
    this.verticalSpacing = dpToPx(13);
    this.lineHeights = new ArrayList<>();
    this.isMeasured = false;
  }

  public static int dpToPx(int dp) {
    return (int) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        Resources.getSystem().getDisplayMetrics()
    );
  }

  private void clearLines() {
    this.allLines = new ArrayList<>();
    this.lineHeights = new ArrayList<>();
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    int lineCount = this.allLines.size();
    int currentY = 0;

    for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
      List<View> lineViews = this.allLines.get(lineIndex);
      int lineHeight = this.lineHeights.get(lineIndex);

      int currentX = 0;

      for (int viewIndex = 0; viewIndex < lineViews.size(); viewIndex++) {
        View child = lineViews.get(viewIndex);
        int childWidth = child.getMeasuredWidth();
        int childHeight = child.getMeasuredHeight();

        child.layout(
            currentX,
            currentY,
            currentX + childWidth,
            currentY + childHeight
        );

        currentX += childWidth + this.horizontalSpacing;
      }

      currentY += lineHeight + this.verticalSpacing;
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    clearLines();

    int childCount = getChildCount();
    int paddingLeft = getPaddingLeft();
    int paddingRight = getPaddingRight();
    int paddingTop = getPaddingTop();
    int paddingBottom = getPaddingBottom();

    int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
    int parentHeight = MeasureSpec.getSize(heightMeasureSpec);

    List<View> currentLine = new ArrayList<>();

    int currentLineWidth = 0;
    int currentLineHeight = 0;
    int maxLineWidth = 0;
    int totalHeight = 0;

    for (int i = 0; i < childCount; i++) {
      View child = getChildAt(i);
      ViewGroup.LayoutParams layoutParams = child.getLayoutParams();

      child.measure(
          getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, layoutParams.width),
          getChildMeasureSpec(heightMeasureSpec, paddingBottom + paddingTop, layoutParams.height)
      );

      int childWidth = child.getMeasuredWidth();
      int childHeight = child.getMeasuredHeight();

      if (currentLineWidth + childWidth + this.horizontalSpacing > parentWidth) {
        this.allLines.add(currentLine);
        this.lineHeights.add(currentLineHeight);

        maxLineWidth = Math.max(maxLineWidth, currentLineWidth + this.horizontalSpacing);
        totalHeight += currentLineHeight + this.verticalSpacing;

        currentLine = new ArrayList<>();
        currentLineWidth = 0;
        currentLineHeight = 0;
      }

      currentLine.add(child);
      currentLineWidth += childWidth + this.horizontalSpacing;
      currentLineHeight = Math.max(currentLineHeight, childHeight);

      if (i == childCount - 1) {
        this.lineHeights.add(currentLineHeight);
        this.allLines.add(currentLine);
        maxLineWidth = Math.max(maxLineWidth, currentLineWidth);
        totalHeight += currentLineHeight;
      }
    }

    int finalWidth = MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY
        ? parentWidth
        : maxLineWidth;

    int finalHeight = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY
        ? parentHeight
        : totalHeight;

    setMeasuredDimension(finalWidth, finalHeight);
    this.isMeasured = true;
  }

  public void setHorizontalSpacing(int spacing) {
    this.horizontalSpacing = spacing;
    requestLayout();
  }

  public void setVerticalSpacing(int spacing) {
    this.verticalSpacing = spacing;
    requestLayout();
  }
}