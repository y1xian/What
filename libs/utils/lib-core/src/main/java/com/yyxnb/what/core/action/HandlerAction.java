package com.yyxnb.what.core.action;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

/**
 * Handler 意图处理
 *
 * @author yyx
 */
public interface HandlerAction {

    Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * 获取 Handler
     */
    default Handler getHandler() {
        return HANDLER;
    }

    /**
     * 是否在主线程
     */
    default boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 延迟执行
     */
    default void post(Runnable r) {
        if (isMainThread()) {
            r.run();
        } else {
            postDelayed(r, 0);
        }
    }

    /**
     * 延迟一段时间执行
     */
    default void postDelayed(Runnable r, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        postAtTime(r, SystemClock.uptimeMillis() + delayMillis);
    }

    /**
     * 在指定的时间执行
     */
    default void postAtTime(Runnable r, long uptimeMillis) {
        // 发送和这个 Activity 相关的消息回调
        HANDLER.postAtTime(r, this, uptimeMillis);
    }

    /**
     * 移除单个消息回调
     */
    default void removeCallbacks(Runnable r) {
        HANDLER.removeCallbacks(r);
    }

    /**
     * 移除全部消息回调
     */
    default void removeCallbacks() {
        HANDLER.removeCallbacksAndMessages(this);
    }
}