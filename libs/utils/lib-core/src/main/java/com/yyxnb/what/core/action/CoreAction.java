package com.yyxnb.what.core.action;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

/**
 * 组件通用意图
 *
 * @author yyx
 */
public interface CoreAction {

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

}
