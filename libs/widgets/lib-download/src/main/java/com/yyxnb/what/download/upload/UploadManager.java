package com.yyxnb.what.download.upload;

public class UploadManager {

    private static volatile UploadManager mInstance = null;

    private UploadManager() {
    }

    public static UploadManager getInstance() {
        if (null == mInstance) {
            synchronized (UploadManager.class) {
                if (null == mInstance) {
                    mInstance = new UploadManager();
                }
            }
        }
        return mInstance;
    }



}
