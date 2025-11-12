package com.mylrc.mymusic.tool;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Looper;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import com.mylrc.mymusic.R;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;

@SuppressLint({"StaticFieldLeak"})
public class LrcView extends View {

  private static final String TAG = "LrcView";
  private static final long TIMELINE_HIDE_DELAY = 4000L;
  private static final long ADJUST_DURATION = 100L;

  private final GestureDetector.SimpleOnGestureListener gestureListener;
  private final Runnable hideTimelineRunnable;
  private final List<LrcEntry> lrcEntryList;
  private final TextPaint lrcPaint;
  private final TextPaint timePaint;
  private GestureDetector gestureDetector;
  private Scroller scroller;
  private ValueAnimator animator;
  private int currentLine;
  private Object flag;
  private Paint.FontMetrics timeFontMetrics;
  private Drawable playDrawable;

  private String label;
  private int textAlign;
  private float dividerHeight;
  private long animationDuration;
  private int normalTextColor;
  private float normalTextSize;
  private int currentTextColor;
  private float currentTextSize;
  private int timelineTextColor;
  private int timelineColor;
  private int timeTextColor;
  private int drawableWidth;
  private int timeWidth;
  private float lrcPadding;

  private float offset;
  private boolean isTouching;
  private boolean isFling;
  private boolean isDragging;

  private OnPlayClickListener onPlayClickListener;

  public LrcView(Context context) {
    this(context, null);
  }

  public LrcView(Context context, AttributeSet attributeSet) {
    this(context, attributeSet, 0);
  }

  public LrcView(Context context, AttributeSet attributeSet, int defStyleAttr) {
    super(context, attributeSet, defStyleAttr);
    this.lrcEntryList = new ArrayList<>();
    this.lrcPaint = new TextPaint();
    this.timePaint = new TextPaint();
    this.gestureListener = new LrcGestureListener(this);
    this.hideTimelineRunnable = new HideTimelineRunnable(this);
    initAttributes(attributeSet);
  }

  @SuppressLint("ResourceType")
  private void initAttributes(AttributeSet attributeSet) {
    TypedArray ta = getContext().obtainStyledAttributes(attributeSet, new int[]{
        R.attr.lrcAnimationDuration,
        R.attr.lrcCurrentTextColor,
        R.attr.lrcDividerHeight,
        R.attr.lrcLabel,
        R.attr.lrcNormalTextColor,
        R.attr.lrcNormalTextSize,
        R.attr.lrcPadding,
        R.attr.lrcPlayDrawable,
        R.attr.lrcTextGravity,
        R.attr.lrcTextSize,
        R.attr.lrcTimeTextColor,
        R.attr.lrcTimeTextSize,
        R.attr.lrcTimelineColor,
        R.attr.lrcTimelineHeight,
        R.attr.lrcTimelineTextColor
    });

    float timelineHeight;
    float timeTextSize;
    try {
      this.currentTextSize = ta.getDimension(9, getResources().getDimension(R.dimen.lrc_text_size));
      float dimension = ta.getDimension(5, getResources().getDimension(R.dimen.lrc_notext_size));
      this.normalTextSize = dimension;
      if (dimension == 0.0f) {
        this.normalTextSize = this.currentTextSize;
      }

      this.dividerHeight = ta.getDimension(2,
          getResources().getDimension(R.dimen.lrc_divider_height));
      int defaultDuration = getResources().getInteger(R.integer.lrc_animation_duration);
      long duration = ta.getInt(0, defaultDuration);
      this.animationDuration = duration < 0 ? defaultDuration : duration;

      this.normalTextColor = ta.getColor(4, getResources().getColor(R.color.lrc_normal_text));
      this.currentTextColor = ta.getColor(1, getResources().getColor(R.color.lrc_current_text));
      this.timelineTextColor = ta.getColor(14, getResources().getColor(R.color.lrc_timeline_text));
      this.timelineColor = ta.getColor(12, getResources().getColor(R.color.lrc_timeline));
      this.timeTextColor = ta.getColor(10, getResources().getColor(R.color.lrc_time_text));

      String string = ta.getString(3);
      this.label = TextUtils.isEmpty(string) ? getContext().getString(R.string.lrc_label) : string;

      this.lrcPadding = ta.getDimension(6, 0.0f);
      timelineHeight = ta.getDimension(13,
          getResources().getDimension(R.dimen.lrc_timeline_height));
      timeTextSize = ta.getDimension(11, getResources().getDimension(R.dimen.lrc_time_text_size));
      this.textAlign = ta.getInteger(8, 0);

      Drawable drawable = ta.getDrawable(7);
      this.playDrawable = drawable != null ? drawable : getResources().getDrawable(R.drawable.play);
    } finally {
      ta.recycle();
    }

    this.drawableWidth = (int) getResources().getDimension(R.dimen.lrc_drawable_width);
    this.timeWidth = (int) getResources().getDimension(R.dimen.lrc_time_width);

    this.lrcPaint.setAntiAlias(true);
    this.lrcPaint.setTextSize(this.currentTextSize);
    this.lrcPaint.setTextAlign(Paint.Align.LEFT);

    this.timePaint.setAntiAlias(true);
    this.timePaint.setTextSize(timeTextSize);
    this.timePaint.setTextAlign(Paint.Align.CENTER);
    this.timePaint.setStrokeWidth(timelineHeight);
    this.timePaint.setStrokeCap(Paint.Cap.ROUND);
    this.timeFontMetrics = this.timePaint.getFontMetrics();

    this.gestureDetector = new GestureDetector(getContext(), this.gestureListener);
    this.gestureDetector.setIsLongpressEnabled(false);
    this.scroller = new Scroller(getContext());
  }

  private void initLrcEntryList() {
    if (!hasLrc() || getWidth() == 0) {
      return;
    }
    Iterator<LrcEntry> it = this.lrcEntryList.iterator();
    while (it.hasNext()) {
      it.next().initStaticLayout(this.lrcPaint, (int) getLrcWidth(), this.textAlign);
    }
    this.offset = getHeight() / 2.0f;
  }

  private void initPlayDrawable() {
    int left = (this.drawableWidth - this.timeWidth) / 2;
    int centerY = getHeight() / 2;
    int halfHeight = this.drawableWidth / 2;
    int top = centerY - halfHeight;
    this.playDrawable.setBounds(left, top, left + this.drawableWidth, this.drawableWidth + top);
  }

  private float getOffset(int line) {
    if (this.lrcEntryList.get(line).getOffset() == Float.MIN_VALUE) {
      float height = getHeight() / 2.0f;
      for (int i = 1; i <= line; i++) {
        height -= ((this.lrcEntryList.get(i).getHeight() +
            this.lrcEntryList.get(i - 1).getHeight()) >> 1) + this.dividerHeight;
      }
      this.lrcEntryList.get(line).setOffset(height);
    }
    return this.lrcEntryList.get(line).getOffset();
  }

  private float getLrcWidth() {
    return getWidth() - (this.lrcPadding * 2.0f);
  }

  private int getCenterLine() {
    int centerLine = 0;
    float minDistance = Float.MAX_VALUE;
    for (int i = 0; i < this.lrcEntryList.size(); i++) {
      float distance = Math.abs(this.offset - getOffset(i));
      if (distance < minDistance) {
        minDistance = distance;
        centerLine = i;
      }
    }
    return centerLine;
  }

  private void smoothScrollTo(int line) {
    smoothScrollTo(line, this.animationDuration);
  }

  private void smoothScrollTo(int line, long duration) {
    float targetOffset = getOffset(line);
    endAnimation();

    ValueAnimator animator = ValueAnimator.ofFloat(this.offset, targetOffset);
    this.animator = animator;
    animator.setDuration(duration);
    this.animator.setInterpolator(new LinearInterpolator());
    this.animator.addUpdateListener(new AnimatorUpdateListener(this));

    try {
      LrcParser.fixAnimationScale();
    } catch (Exception e) {
      Log.w(TAG, "Failed to fix animation scale", e);
    }

    this.animator.start();
  }

  private void adjustCenter() {
    smoothScrollTo(getCenterLine(), ADJUST_DURATION);
  }

  private void drawText(Canvas canvas, StaticLayout staticLayout, float y) {
    canvas.save();
    canvas.translate(this.lrcPadding, y - (staticLayout.getHeight() >> 1));
    staticLayout.draw(canvas);
    canvas.restore();
  }

  private void endAnimation() {
    if (this.animator != null && this.animator.isRunning()) {
      this.animator.end();
    }
  }

  private void reset() {
    endAnimation();
    this.scroller.forceFinished(true);
    this.isTouching = false;
    this.isFling = false;
    this.isDragging = false;
    removeCallbacks(this.hideTimelineRunnable);
    this.lrcEntryList.clear();
    this.offset = 0.0f;
    this.currentLine = 0;
    invalidate();
  }

  private void runOnUi(Runnable runnable) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      runnable.run();
    } else {
      post(runnable);
    }
  }

  private void onLrcLoaded(List<LrcEntry> list) {
    if (list != null && !list.isEmpty()) {
      this.lrcEntryList.addAll(list);
    }
    Collections.sort(this.lrcEntryList);
    initLrcEntryList();
    invalidate();
  }

  private Object getFlag() {
    return this.flag;
  }

  private void setFlag(Object obj) {
    this.flag = obj;
  }

  public boolean hasLrc() {
    return !this.lrcEntryList.isEmpty();
  }

  public int findShowLine(long time) {
    int high = this.lrcEntryList.size() - 1;
    int low = 0;

    while (low <= high) {
      int mid = (low + high) / 2;
      long midTime = this.lrcEntryList.get(mid).getTimestamp();

      if (time < midTime) {
        high = mid - 1;
      } else {
        low = mid + 1;
        if (low >= this.lrcEntryList.size() ||
            time < this.lrcEntryList.get(low).getTimestamp()) {
          return mid;
        }
      }
    }
    return 0;
  }

  @Deprecated
  public int A(long time) {
    return findShowLine(time);
  }

  public void loadLrc(File file) {
    loadLrc(file, null);
  }

  public void loadLrc(File lrcFile, File secondLrcFile) {
    runOnUi(new LoadLrcFileRunnable(this, lrcFile, secondLrcFile));
  }

  public void loadLrc(String lrcText, String secondLrcText) {
    runOnUi(new LoadLrcTextRunnable(this, lrcText, secondLrcText));
  }

  public String getLrcText(int line) {
    if (this.lrcEntryList.isEmpty() || line > this.lrcEntryList.size() - 1) {
      return FrameBodyCOMM.DEFAULT;
    }
    return this.lrcEntryList.get(line).getText();
  }

  public void updateTime(long time) {
    runOnUi(new UpdateTimeRunnable(this, time));
  }

  public void setDraggable(boolean draggable, OnPlayClickListener listener) {
    if (!draggable) {
      this.onPlayClickListener = null;
    } else {
      if (listener == null) {
        throw new IllegalArgumentException("if draggable == true, listener must not be null");
      }
      this.onPlayClickListener = listener;
    }
  }

  public void setLabel(String label) {
    runOnUi(new SetLabelRunnable(this, label));
  }

  public void setCurrentColor(int color) {
    this.currentTextColor = color;
    postInvalidate();
  }

  public void setCurrentTextSize(float size) {
    this.currentTextSize = size;
  }

  public void setNormalColor(int color) {
    this.normalTextColor = color;
    postInvalidate();
  }

  public void setNormalTextSize(float size) {
    this.normalTextSize = size;
  }

  public void setTimeTextColor(int color) {
    this.timeTextColor = color;
    postInvalidate();
  }

  public void setTimelineColor(int color) {
    this.timelineColor = color;
    postInvalidate();
  }

  public void setTimelineTextColor(int color) {
    this.timelineTextColor = color;
    postInvalidate();
  }

  @Deprecated
  public void setOnPlayClickListener(OnPlayClickListener listener) {
    this.onPlayClickListener = listener;
  }

  @Deprecated
  public void G(File file) {
    loadLrc(file);
  }

  @Deprecated
  public void H(File lrcFile, File secondLrcFile) {
    loadLrc(lrcFile, secondLrcFile);
  }

  @Deprecated
  public void I(String lrcText, String secondLrcText) {
    loadLrc(lrcText, secondLrcText);
  }

  @Deprecated
  public void M(boolean draggable, OnPlayClickListener listener) {
    setDraggable(draggable, listener);
  }

  @Deprecated
  public String P(int line) {
    return getLrcText(line);
  }

  @Deprecated
  public void Q(long time) {
    updateTime(time);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    if (changed) {
      initPlayDrawable();
      initLrcEntryList();
      if (hasLrc()) {
        smoothScrollTo(this.currentLine, 0L);
      }
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    int centerY = getHeight() / 2;

    if (!hasLrc()) {
      this.lrcPaint.setColor(this.normalTextColor);
      drawText(canvas, new StaticLayout(this.label, this.lrcPaint,
          (int) getLrcWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false), centerY);
      return;
    }

    int centerLine = getCenterLine();

    if (this.isDragging) {
      this.playDrawable.draw(canvas);
      this.timePaint.setColor(this.timelineColor);
      float y = centerY;
      canvas.drawLine(this.drawableWidth, y, getWidth() - this.timeWidth, y, this.timePaint);

      this.timePaint.setColor(this.timelineTextColor);
      String timeStr = LrcParser.formatTime(this.lrcEntryList.get(centerLine).getTimestamp());
      float x = getWidth() - (this.timeWidth / 2.0f);
      Paint.FontMetrics fontMetrics = this.timeFontMetrics;
      canvas.drawText(timeStr, x, y - ((fontMetrics.ascent + fontMetrics.descent) / 2.0f),
          this.timePaint);
    }

    canvas.translate(0.0f, this.offset);
    float offsetY = 0.0f;

    for (int i = 0; i < this.lrcEntryList.size(); i++) {
      if (i > 0) {
        offsetY += ((this.lrcEntryList.get(i).getHeight() +
            this.lrcEntryList.get(i - 1).getHeight()) >> 1) + this.dividerHeight;
      }

      if (i == this.currentLine) {
        this.lrcPaint.setTextSize(this.currentTextSize);
        this.lrcPaint.setColor(this.currentTextColor);
      } else if (this.isDragging && i == centerLine) {
        this.lrcPaint.setColor(this.timeTextColor);
      } else {
        this.lrcPaint.setTextSize(this.normalTextSize);
        this.lrcPaint.setColor(this.normalTextColor);
      }

      drawText(canvas, this.lrcEntryList.get(i).getStaticLayout(), offsetY);
    }
  }

  @Override
  public void computeScroll() {
    if (this.scroller.computeScrollOffset()) {
      this.offset = this.scroller.getCurrY();
      invalidate();
    }

    if (this.isFling && this.scroller.isFinished()) {
      this.isFling = false;
      if (hasLrc() && !this.isTouching) {
        adjustCenter();
        postDelayed(this.hideTimelineRunnable, TIMELINE_HIDE_DELAY);
      }
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_UP ||
        event.getAction() == MotionEvent.ACTION_CANCEL) {
      this.isTouching = false;
      if (hasLrc() && !this.isFling) {
        adjustCenter();
        postDelayed(this.hideTimelineRunnable, TIMELINE_HIDE_DELAY);
      }
    }
    return this.gestureDetector.onTouchEvent(event);
  }

  @Override
  protected void onDetachedFromWindow() {
    removeCallbacks(this.hideTimelineRunnable);
    super.onDetachedFromWindow();
  }

  public interface OnPlayClickListener {

    boolean onPlayClick(long time);
  }

  @Deprecated
  public interface h extends OnPlayClickListener {

    @Override
    default boolean onPlayClick(long time) {
      return a(time);
    }

    boolean a(long time);
  }

  static class SetLabelRunnable implements Runnable {

    final String text;
    final LrcView view;

    SetLabelRunnable(LrcView view, String text) {
      this.view = view;
      this.text = text;
    }

    @Override
    public void run() {
      this.view.label = this.text;
      this.view.invalidate();
    }
  }

  static class LoadLrcFileRunnable implements Runnable {

    final File lrcFile;
    final File secondLrcFile;
    final LrcView view;

    LoadLrcFileRunnable(LrcView view, File lrcFile, File secondLrcFile) {
      this.view = view;
      this.lrcFile = lrcFile;
      this.secondLrcFile = secondLrcFile;
    }

    @Override
    public void run() {
      this.view.reset();
      StringBuilder sb = new StringBuilder("file://");
      sb.append(this.lrcFile.getPath());
      if (this.secondLrcFile != null) {
        sb.append("#");
        sb.append(this.secondLrcFile.getPath());
      }
      String flagStr = sb.toString();
      this.view.setFlag(flagStr);
      new LoadLrcFileTask(this, flagStr).execute(this.lrcFile, this.secondLrcFile);
    }

    static class LoadLrcFileTask extends AsyncTask<File, Integer, List<LrcEntry>> {

      final String flagStr;
      final LoadLrcFileRunnable context;

      LoadLrcFileTask(LoadLrcFileRunnable context, String flagStr) {
        this.context = context;
        this.flagStr = flagStr;
      }

      @Override
      protected List<LrcEntry> doInBackground(File... files) {
        try {
          return LrcParser.parseDualLrcFiles(files);
        } catch (IOException e) {
          Log.e(TAG, "Failed to load lrc file", e);
          return null;
        }
      }

      @Override
      protected void onPostExecute(List<LrcEntry> list) {
        if (this.context.view.getFlag() == this.flagStr) {
          this.context.view.onLrcLoaded(list);
          this.context.view.setFlag(null);
        }
      }
    }
  }

  static class LoadLrcTextRunnable implements Runnable {

    final String lrcText;
    final String secondLrcText;
    final LrcView view;

    LoadLrcTextRunnable(LrcView view, String lrcText, String secondLrcText) {
      this.view = view;
      this.lrcText = lrcText;
      this.secondLrcText = secondLrcText;
    }

    @Override
    public void run() {
      this.view.reset();
      StringBuilder sb = new StringBuilder("text://");
      sb.append(this.lrcText);
      if (this.secondLrcText != null) {
        sb.append("#");
        sb.append(this.secondLrcText);
      }
      String flagStr = sb.toString();
      this.view.setFlag(flagStr);
      new LoadLrcTextTask(this, flagStr).execute(this.lrcText, this.secondLrcText);
    }

    static class LoadLrcTextTask extends AsyncTask<String, Integer, List<LrcEntry>> {

      final String flagStr;
      final LoadLrcTextRunnable context;

      LoadLrcTextTask(LoadLrcTextRunnable context, String flagStr) {
        this.context = context;
        this.flagStr = flagStr;
      }

      @Override
      protected List<LrcEntry> doInBackground(String... strings) {
        return LrcParser.parseDualLrcStrings(strings);
      }

      @Override
      protected void onPostExecute(List<LrcEntry> list) {
        if (this.context.view.getFlag() == this.flagStr) {
          this.context.view.onLrcLoaded(list);
          this.context.view.setFlag(null);
        }
      }
    }
  }

  static class UpdateTimeRunnable implements Runnable {

    final long time;
    final LrcView view;

    UpdateTimeRunnable(LrcView view, long time) {
      this.view = view;
      this.time = time;
    }

    @Override
    public void run() {
      if (!this.view.hasLrc()) {
        return;
      }

      int line = this.view.findShowLine(this.time);
      if (line != this.view.currentLine) {
        this.view.currentLine = line;
        if (this.view.isTouching) {
          this.view.invalidate();
        } else {
          this.view.smoothScrollTo(line);
        }
      }
    }
  }

  static class LrcGestureListener extends GestureDetector.SimpleOnGestureListener {

    final LrcView view;

    LrcGestureListener(LrcView view) {
      this.view = view;
    }

    @Override
    public boolean onDown(MotionEvent e) {
      if (!this.view.hasLrc() || this.view.onPlayClickListener == null) {
        return super.onDown(e);
      }
      this.view.scroller.forceFinished(true);
      this.view.removeCallbacks(this.view.hideTimelineRunnable);
      this.view.isDragging = true;
      this.view.isTouching = true;
      this.view.invalidate();
      return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
      if (!this.view.hasLrc()) {
        return super.onScroll(e1, e2, distanceX, distanceY);
      }
      this.view.offset += -distanceY;
      this.view.offset = Math.min(this.view.offset, this.view.getOffset(0));
      this.view.offset = Math.max(this.view.offset,
          this.view.getOffset(this.view.lrcEntryList.size() - 1));
      this.view.invalidate();
      return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
      if (!this.view.hasLrc()) {
        return super.onFling(e1, e2, velocityX, velocityY);
      }
      this.view.scroller.fling(
          0, (int) this.view.offset,
          0, (int) velocityY,
          0, 0,
          (int) this.view.getOffset(this.view.lrcEntryList.size() - 1),
          (int) this.view.getOffset(0)
      );
      this.view.isFling = true;
      return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
      if (!this.view.hasLrc() || !this.view.isDragging) {
        return super.onSingleTapConfirmed(e);
      }

      if (this.view.playDrawable.getBounds().contains((int) e.getX(), (int) e.getY())) {
        int centerLine = this.view.getCenterLine();
        long time = this.view.lrcEntryList.get(centerLine).getTimestamp();

        if (this.view.onPlayClickListener != null) {
          boolean handled = this.view.onPlayClickListener.onPlayClick(time);
          if (handled) {
            this.view.isTouching = false;
            this.view.removeCallbacks(this.view.hideTimelineRunnable);
            this.view.currentLine = centerLine;
            this.view.invalidate();
            return true;
          }
        }
      }
      return super.onSingleTapConfirmed(e);
    }
  }

  static class HideTimelineRunnable implements Runnable {

    final LrcView view;

    HideTimelineRunnable(LrcView view) {
      this.view = view;
    }

    @Override
    public void run() {
      if (this.view.hasLrc() && this.view.isDragging) {
        this.view.isDragging = false;
        this.view.smoothScrollTo(this.view.currentLine);
      }
    }
  }

  static class AnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {

    final LrcView view;

    AnimatorUpdateListener(LrcView view) {
      this.view = view;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
      this.view.offset = ((Float) animation.getAnimatedValue()).floatValue();
      this.view.invalidate();
    }
  }
}