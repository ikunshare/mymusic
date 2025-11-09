package com.mylrc.mymusic.network;


import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

public class MvDownloader {

  public MvDownloader() {
  }

  public void downloadMv(Context context, String url, String fileName) {
    Uri uri = Uri.parse(url);

    DownloadManager downloadManager = (DownloadManager) context.getSystemService(
        Context.DOWNLOAD_SERVICE);

    DownloadManager.Request request = new DownloadManager.Request(uri);
    request.setAllowedNetworkTypes(3);
    request.setDestinationInExternalPublicDir("MusicDownloader/MV", fileName);
    request.setVisibleInDownloadsUi(true);
    request.allowScanningByMediaScanner();
    request.setTitle(fileName);

    downloadManager.enqueue(request);
  }
}