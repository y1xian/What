package com.yyxnb.what.download.download;

import android.text.TextUtils;

import com.yyxnb.what.app.AppUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 下载管理器，断点续传
 */
public class DownloadManager {

    //默认下载目录
//    private final String DEFAULT_FILE_DIR = Environment.getDownloadCacheDirectory().getAbsolutePath()
    // /storage/emulated/0/Android/data/package/files/download/
    private final String DEFAULT_FILE_DIR = AppUtils.getApp().getExternalFilesDir(null).getAbsolutePath()
            + File.separator + "download" + File.separator;
    //文件下载任务索引，String为url,用来唯一区别并操作下载的文件
    private Map<String, DownloadTask> mDownloadTasks;
    private static final String TAG = "DownloadManager";

    private static volatile DownloadManager mInstance = null;

    private DownloadManager() {
        mDownloadTasks = new HashMap<>();
    }

    public static DownloadManager getInstance() {
        if (null == mInstance) {
            synchronized (DownloadManager.class) {
                if (null == mInstance) {
                    mInstance = new DownloadManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 下载文件
     */
    public void download(String... urls) {
        //单任务开启下载或多任务开启下载
        for (int i = 0, length = urls.length; i < length; i++) {
            String url = urls[i];
            if (mDownloadTasks.containsKey(url)) {
                mDownloadTasks.get(url).start();
            }
        }
    }


    // 获取下载文件的名称
    public String getFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 暂停
     */
    public void pause(String... urls) {
        //单任务暂停或多任务暂停下载
        for (int i = 0, length = urls.length; i < length; i++) {
            String url = urls[i];
            if (mDownloadTasks.containsKey(url)) {
                mDownloadTasks.get(url).pause();
            }
        }
    }

    /**
     * 取消下载
     */
    public void cancel(String... urls) {
        //单任务取消或多任务取消下载
        for (int i = 0, length = urls.length; i < length; i++) {
            String url = urls[i];
            if (mDownloadTasks.containsKey(url)) {
                mDownloadTasks.get(url).cancel();
            }
        }
    }

    /**
     * 添加下载任务
     */
    public void add(String url, DownloadListner l) {
        add(url, null, null, l);
    }

    /**
     * 添加下载任务
     */
    public void add(String url, String filePath, DownloadListner l) {
        add(url, filePath, null, l);
    }

    /**
     * 添加下载任务
     */
    public void add(String url, String filePath, String fileName, DownloadListner l) {
        //没有指定下载目录,使用默认目录
        if (TextUtils.isEmpty(filePath)) {
            filePath = DEFAULT_FILE_DIR;
        }
        if (TextUtils.isEmpty(fileName)) {
            fileName = getFileName(url);
        }
        mDownloadTasks.put(url, new DownloadTask(new FilePoint(url, filePath, fileName), l));
        mDownloadTasks.get(url).start();
    }

    /**
     * 取消下载
     */
    public boolean isDownloading(String... urls) {
        //这里传一个url就是判断一个下载任务
        //多个url数组适合下载管理器判断是否作操作全部下载或全部取消下载
        boolean result = false;
        for (int i = 0, length = urls.length; i < length; i++) {
            String url = urls[i];
            if (mDownloadTasks.containsKey(url)) {
                result = mDownloadTasks.get(url).isDownloading();
            }
        }
        return result;
    }
}