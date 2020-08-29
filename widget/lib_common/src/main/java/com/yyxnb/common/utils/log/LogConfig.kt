package com.yyxnb.common.utils.log

import android.text.TextUtils
import com.yyxnb.widget.WidgetManager

object LogConfig {

    var isShowThreadInfo = true

    var isDebug = WidgetManager.isDebug

    var tag = "----what----"

    fun setTag(tag: String): LogConfig {
        if (!TextUtils.isEmpty(tag)) {
            this.tag = tag
        }
        return this
    }

    fun setShowThreadInfo(showThreadInfo: Boolean): LogConfig {
        isShowThreadInfo = showThreadInfo
        return this
    }

    fun setDebug(debug: Boolean): LogConfig {
        this.isDebug = debug
        return this
    }

}