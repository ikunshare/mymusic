package com.mylrc.mymusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class HeadsetButtonReceiver extends BroadcastReceiver {

  private static final int CONTROL_PLAY_PAUSE = 291;
  private static final int CONTROL_NEXT = 292;
  private static final int CONTROL_PREVIOUS = 295;
  private static final int CONTROL_TRIPLE_CLICK = 294;

  private static final int MSG_SINGLE_CLICK = 1;
  private static final int MSG_DOUBLE_CLICK = 2;
  private static final int MSG_TRIPLE_CLICK = 3;

  private static final long CLICK_DELAY = 800L;

  private static int clickCount = 0;
  private static int currentPlayState = CONTROL_PLAY_PAUSE;
  private final Handler handler;
  private final Runnable clickCheckRunnable;
  private Context context;

  public HeadsetButtonReceiver() {
    this.handler = new ClickHandler(this, Looper.getMainLooper());
    this.clickCheckRunnable = new ClickCheckRunnable(this);
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    this.context = context;
    clickCount++;

    if (clickCount == 2) {
      handler.postDelayed(clickCheckRunnable, CLICK_DELAY);
    }
  }

  private void sendControlBroadcast(int controlCode) {
    Intent intent = new Intent();
    intent.setAction("com.mylrc.mymusic.ac");
    intent.putExtra("control", controlCode);
    context.sendBroadcast(intent);
  }

  private static class ClickCheckRunnable implements Runnable {

    private final HeadsetButtonReceiver receiver;

    ClickCheckRunnable(HeadsetButtonReceiver receiver) {
      this.receiver = receiver;
    }

    @Override
    public void run() {
      Message message = new Message();

      switch (clickCount) {
        case 2:
        case 4:
          message.what = MSG_DOUBLE_CLICK;
          break;
        case 6:
          message.what = MSG_TRIPLE_CLICK;
          break;
        default:
          message.what = MSG_SINGLE_CLICK;
          break;
      }

      receiver.handler.sendMessage(message);
      clickCount = 0;
    }
  }

  private static class ClickHandler extends Handler {

    private final HeadsetButtonReceiver receiver;

    ClickHandler(HeadsetButtonReceiver receiver, Looper looper) {
      super(looper);
      this.receiver = receiver;
    }

    @Override
    public void handleMessage(Message message) {
      int controlCode;

      switch (message.what) {
        case MSG_SINGLE_CLICK:
          if (currentPlayState == CONTROL_PLAY_PAUSE) {
            controlCode = CONTROL_NEXT;
            currentPlayState = CONTROL_NEXT;
          } else {
            controlCode = CONTROL_PLAY_PAUSE;
            currentPlayState = CONTROL_PLAY_PAUSE;
          }
          break;

        case MSG_DOUBLE_CLICK:
          controlCode = CONTROL_PREVIOUS;
          break;

        case MSG_TRIPLE_CLICK:
          controlCode = CONTROL_TRIPLE_CLICK;
          break;

        default:
          super.handleMessage(message);
          return;
      }

      receiver.sendControlBroadcast(controlCode);
      super.handleMessage(message);
    }
  }
}