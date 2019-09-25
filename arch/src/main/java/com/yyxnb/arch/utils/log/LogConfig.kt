package com.yyxnb.arch.utils.log


import android.text.TextUtils


class LogConfig {

    private var showThreadInfo = true
    private var debug = isDebug()
    private var tag = "arch"


    fun setTag(tag: String): LogConfig {
        if (!TextUtils.isEmpty(tag)) {
            this.tag = tag
        }
        return this
    }

    fun setShowThreadInfo(showThreadInfo: Boolean): LogConfig {
        this.showThreadInfo = showThreadInfo
        return this
    }


    fun setDebug(debug: Boolean): LogConfig {
        this.debug = debug
        return this
    }

    fun getTag(): String {
        return tag
    }

    fun isDebug(): Boolean {
        return debug
    }

    fun isShowThreadInfo(): Boolean {
        return showThreadInfo
    }
}
