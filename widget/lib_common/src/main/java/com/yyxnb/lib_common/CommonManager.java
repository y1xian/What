package com.yyxnb.lib_common;

import android.annotation.SuppressLint;

import com.yyxnb.lib_common.utils.ToastUtils;
import com.yyxnb.lib_common.utils.log.LogUtils;
import com.yyxnb.lib_widget.AppUtils;
import com.yyxnb.lib_widget.WidgetManager;

/**
 * 常用管理
 *
 * @author yyx
 */
public class CommonManager {

    @SuppressLint("StaticFieldLeak")
    private volatile static CommonManager commonManager;

    private CommonManager() {
    }

    public static CommonManager getInstance() {
        if (commonManager == null) {
            synchronized (WidgetManager.class) {
                if (commonManager == null) {
                    commonManager = new CommonManager();
                }
            }
        }
        return commonManager;
    }


    public void toast(String s) {
        ToastUtils.normal(s);
    }

    public void log(String tag, String s) {
        if (AppUtils.isDebug()) {
            LogUtils.w(s, tag);
        }
    }

    public void log(String s) {
        if (AppUtils.isDebug()) {
            log("------AppConfig------", s);
        }
    }


}
