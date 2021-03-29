package com.yyxnb.what.core;


import com.yyxnb.what.core.log.LogUtils;
import com.yyxnb.what.app.AppUtils;

/**
 * 常用管理
 *
 * @author yyx
 */
public class CommonManager {

    private static volatile CommonManager mInstance = null;

    private CommonManager() {
    }

    public static CommonManager getInstance() {
        if (null == mInstance) {
            synchronized (CommonManager.class) {
                if (null == mInstance) {
                    mInstance = new CommonManager();
                }
            }
        }
        return mInstance;
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
