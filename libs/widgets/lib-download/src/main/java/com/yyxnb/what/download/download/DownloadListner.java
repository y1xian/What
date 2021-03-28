package com.yyxnb.what.download.download;

/**
 * 下载监听
 */
public interface DownloadListner {
    void onFinished(String path);

    void onProgress(float progress);

    void onPause();

    void onCancel();
}