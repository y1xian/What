package com.yyxnb.widget.action

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

/**
 * 组件通用意图
 *
 * @author yyx
 */
interface WidgetAction {

    /**
     * 获取 Context
     */
    fun getContext(): Context

    /**
     * 获取 Activity
     */
    val getActivity: Activity
        get() {
            var context = getContext()
            do {
                context = if (context is Activity) {
                    return context
                } else if (context is ContextWrapper) {
                    context.baseContext
                } else {
                    return null!!
                }
            } while (context != null)
            return null!!
        }
}