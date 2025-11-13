package com.mylrc.mymusic.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;
import com.mylrc.mymusic.R;

/**
 * Activity for playing music videos (MV).
 * Displays video in full screen landscape mode.
 */
public class MVActivity extends Activity {

  private static final String TAG = "MVActivity";
  private VideoView videoView;

  private void playVideo(String videoUrl) {
    try {
      if (videoUrl == null || videoUrl.isEmpty()) {
        Log.e(TAG, "Video URL is null or empty");
        finish();
        return;
      }

      videoView.setMediaController(new MediaController(this));
      videoView.setVideoPath(Uri.parse(videoUrl).toString());
      videoView.start();
      videoView.requestFocus();

      // Pause background music if playing
      pauseBackgroundMusic();

    } catch (IllegalStateException e) {
      Log.e(TAG, "Error playing video: " + e.getMessage());
      finish();
    }
  }

  private void pauseBackgroundMusic() {
    try {
      MediaPlayer mediaPlayer = MainActivity.mediaPlayer;
      if (mediaPlayer != null && mediaPlayer.isPlaying()) {
        mediaPlayer.pause();
      }
    } catch (IllegalStateException e) {
      Log.e(TAG, "Error pausing background music: " + e.getMessage());
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Set full screen and landscape mode
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    getWindow().setFormat(-3);

    setContentView(R.layout.ts);
    videoView = findViewById(R.id.tsVideoView);

    String videoUrl = getIntent().getStringExtra("url");
    playVideo(videoUrl);
  }
}