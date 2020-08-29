package com.yyxnb.common.action

import com.yyxnb.common.CommonManager
import com.yyxnb.common.utils.log.LogUtils
import com.yyxnb.common.utils.log.LogUtils.e
import com.yyxnb.widget.action.WidgetAction

/**
 * 通用意图
 *
 * @author yyx
 */
interface CommonAction : WidgetAction {
    /**
     * toast
     *
     * @param text
     */
    fun toast(text: CharSequence) {
        CommonManager.toast(text.toString())
    }

    /**
     * log
     *
     * @param tag
     * @param text
     */
    fun log(tag: String, text: CharSequence) {
        CommonManager.log(tag, text.toString())
    }

    /**
     * log
     *
     * @param text
     */
    fun log(text: CharSequence) {
        CommonManager.log(text.toString())
    }

    /**
     * log e
     *
     * @param text
     */
    fun loge(text: CharSequence) {
        e(text.toString())
    }

    /**
     * log list
     *
     * @param list 集合
     */
    fun list(list: List<*>?) {
        LogUtils.list(list)
    }
}