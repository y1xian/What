package com.yyxnb.what.core;

import android.view.View;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/11
 * 描    述：防连点
 * ================================================
 */
public class ClickUtils {

    private final static int DELAY_MILLIS = 500;
    private static long lastClickTime;

    public static void click(View v, View.OnClickListener listener) {
        click(v, listener, DELAY_MILLIS);
    }

    public static void click(View v, View.OnClickListener listener, int delayMillis) {
        if (null == v) {
            return;
        }
        v.setOnClickListener(v1 -> {
            listener.onClick(v1);
            v1.setClickable(false);
            v.postDelayed(() -> {
                v.setClickable(true);
            }, delayMillis);
        });
    }

    public static boolean isFastClick() {
        return isFastClick(DELAY_MILLIS);
    }

    public static boolean isFastClick(int delayMillis) {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= delayMillis) {
            lastClickTime = curClickTime;
            flag = true;
        }
        return flag;
    }

}