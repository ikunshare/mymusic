package com.mylrc.mymusic.network;

/**
 * MvDownloader
 * <p>
 * 此文件由自动重命名脚本生成 原始文件: v.java
 *
 * @author Auto Renamer
 * @version 1.0
 */


import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

public class MvDownloader {

  public void downloadMv(Context context, String str, String str2) {
    Uri uri = Uri.parse(str);
    DownloadManager downloadManager = (DownloadManager) context.getSystemService(
        Context.DOWNLOAD_SERVICE);
    DownloadManager.Request request = new DownloadManager.Request(uri);
    request.setAllowedNetworkTypes(3);
    request.setDestinationInExternalPublicDir("MusicDownloader/MV", str2);
    request.setVisibleInDownloadsUi(true);
    request.allowScanningByMediaScanner();
    request.setTitle(str2);
    downloadManager.enqueue(request);
  }
}