package com.yyxnb.lib_file.download;

/**
 * 下载监听
 */
public interface DownloadListner {
    void onFinished();

    void onProgress(float progress);

    void onPause();

    void onCancel();
}