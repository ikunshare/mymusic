package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import com.mylrc.mymusic.R;

/* loaded from: classes.dex */
public class MVActivity extends Activity {

  VideoView videoView;

  private void playVideo(String str) throws IllegalStateException {
    this.videoView.setMediaController(new MediaController(this));
    this.videoView.setVideoPath(Uri.parse(str).toString());
    this.videoView.start();
    this.videoView.requestFocus();
    // Pause music if playing
    MediaPlayer mediaPlayer = MainActivity.mediaPlayer;
    if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
      return;
    }
    MainActivity.mediaPlayer.pause();
  }

  @Override // android.app.Activity
  protected void onCreate(Bundle bundle) throws IllegalStateException {
    super.onCreate(bundle);
    getWindow().setFlags(1024, 1024);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    getWindow().setFormat(-3);
    setContentView(R.layout.ts);
    this.videoView = findViewById(R.id.tsVideoView);
    playVideo(getIntent().getStringExtra("url"));
  }
}