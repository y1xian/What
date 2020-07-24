package com.yyxnb.common;

import android.os.Handler;
import android.os.Looper;

import java.io.Serializable;

/**
 * 方便地将 Runnable post 到主线程执行
 */
public class MainThreadUtils implements Serializable {

    private static Handler HANDLER = new Handler(Looper.getMainLooper());

    public static void post(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            HANDLER.post(runnable);
        }
    }

    public static void postDelayed(Runnable runnable, Long delayMillis) {
        HANDLER.postDelayed(runnable, delayMillis);
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
