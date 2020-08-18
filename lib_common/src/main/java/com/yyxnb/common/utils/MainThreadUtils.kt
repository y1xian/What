package com.yyxnb.common.utils

import android.os.Handler
import android.os.Looper
import java.io.Serializable

/**
 * 方便地将 Runnable post 到主线程执行
 */
object MainThreadUtils : Serializable {

    private val HANDLER = Handler(Looper.getMainLooper())
    @JvmStatic
    fun post(runnable: Runnable) {
        if (isMainThread) {
            runnable.run()
        } else {
            HANDLER.post(runnable)
        }
    }

    fun postDelayed(runnable: Runnable, delayMillis: Long) {
        HANDLER.postDelayed(runnable, delayMillis)
    }

    val isMainThread: Boolean
        get() = Looper.getMainLooper() == Looper.myLooper()

}