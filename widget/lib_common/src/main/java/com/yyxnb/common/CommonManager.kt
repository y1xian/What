package com.yyxnb.common

import com.yyxnb.common.utils.ToastUtils
import com.yyxnb.common.utils.log.LogUtils
import com.yyxnb.widget.WidgetManager.isDebug

/**
 * app 管理
 *
 * @author yyx
 */
object CommonManager {


    fun toast(s: String) {
        ToastUtils.normal(s)
    }

    fun log(tag: String, s: String) {
        if (isDebug) {
            LogUtils.w(s, tag)
        }
    }

    fun log(s: String) {
        if (isDebug) {
            log("----CommonManager----", s)
        }
    }


}