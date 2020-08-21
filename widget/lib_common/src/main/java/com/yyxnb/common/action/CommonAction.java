package com.yyxnb.common.action;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

import com.yyxnb.common.CommonManager;

/**
 * 通用意图
 */
public interface CommonAction {

    /**
     * 获取 Context
     */
    Context getContext();

    /**
     * 获取 Activity
     */
    default Activity getActivity() {
        Context context = getContext();
        do {
            if (context instanceof Activity) {
                return (Activity) context;
            } else if (context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
            } else {
                return null;
            }
        } while (context != null);
        return null;
    }

    /**
     * toast
     *
     * @param text
     */
    default void toast(CharSequence text) {
        CommonManager.getInstance().toast(text.toString());
    }

    /**
     * log
     *
     * @param tag
     * @param text
     */
    default void log(String tag, CharSequence text) {
        CommonManager.getInstance().log(tag, text.toString());
    }

    /**
     * log
     *
     * @param text
     */
    default void log(CharSequence text) {
        CommonManager.getInstance().log(text.toString());
    }
}
