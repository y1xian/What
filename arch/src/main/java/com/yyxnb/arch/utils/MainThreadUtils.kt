package com.yyxnb.arch.utils

import android.os.Handler
import android.os.Looper
import java.io.Serializable

/**
 * 方便地将 Runnable post 到主线程执行
 */
object MainThreadUtils : Serializable {

    private val HANDLER = Handler(Looper.getMainLooper())

    fun post(runnable: Runnable) {
        if (isMainThread) {
            runnable.run()
        } else {
            HANDLER.post(runnable)
        }
    }

    @JvmOverloads
    fun postDelayed(runnable: Runnable, delayMillis: Long = 1L) {
        HANDLER.postDelayed(runnable, delayMillis)
    }

    val isMainThread: Boolean
        get() = Looper.getMainLooper() == Looper.myLooper()

}