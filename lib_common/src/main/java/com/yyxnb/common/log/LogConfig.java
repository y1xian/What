package com.yyxnb.common.log;

import android.text.TextUtils;

import com.yyxnb.common.AppConfig;


public class LogConfig {

    private boolean showThreadInfo = true;
    private boolean debug = AppConfig.getInstance().isDebug();
    private String tag = "----arch----";


    public LogConfig setTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            this.tag = tag;
        }
        return this;
    }

    public LogConfig setShowThreadInfo(boolean showThreadInfo) {
        this.showThreadInfo = showThreadInfo;
        return this;
    }


    public LogConfig setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isShowThreadInfo() {
        return showThreadInfo;
    }
}