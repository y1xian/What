package com.yyxnb.widget.action

import android.os.Handler
import android.os.Looper
import android.os.SystemClock

/**
 * Handler 意图处理
 *
 * @author yyx
 */
interface HandlerAction {
    /**
     * 获取 Handler
     */
    val handler: Handler
        get() = HANDLER

    /**
     * 是否在主线程
     */
    val isMainThread: Boolean
        get() = Looper.getMainLooper() == Looper.myLooper()

    /**
     * 延迟执行
     */
    fun post(r: Runnable) {
        if (isMainThread) {
            r.run()
        } else {
            postDelayed(r, 0)
        }
    }

    /**
     * 延迟一段时间执行
     */
    fun postDelayed(r: Runnable, delayMillis: Long) {
        var delayMillis = delayMillis
        if (delayMillis < 0) {
            delayMillis = 0
        }
        postAtTime(r, SystemClock.uptimeMillis() + delayMillis)
    }

    /**
     * 在指定的时间执行
     */
    fun postAtTime(r: Runnable, uptimeMillis: Long) {
        // 发送和这个 Activity 相关的消息回调
        HANDLER.postAtTime(r, this, uptimeMillis)
    }

    /**
     * 移除单个消息回调
     */
    fun removeCallbacks(r: Runnable) {
        HANDLER.removeCallbacks(r)
    }

    /**
     * 移除全部消息回调
     */
    fun removeCallbacks() {
        HANDLER.removeCallbacksAndMessages(this)
    }

    companion object {
        val HANDLER = Handler(Looper.getMainLooper())
    }
}