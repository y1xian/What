package com.yyxnb.common_res.helper;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yyxnb.common_res.constants.LoginRouterPath;
import com.yyxnb.common_res.service.LoginService;
import com.yyxnb.what.arch.base.IFragment;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/16
 * 描    述：对LoginService包装，业务方直接调用，无需再自己初始化service类
 * ================================================
 */
public class LoginHelper {

    private static final LoginService SERVICE =
            (LoginService) ARouter.getInstance().build(LoginRouterPath.SERVICE).navigation();

    public static void start(Context context) {
        SERVICE.start(context);
    }

    public static IFragment mainPage(Context context) {
        return SERVICE.mainPage(context);
    }

    public static IFragment showPage(Context context) {
        return SERVICE.showPage(context);
    }

    /**
     * 是否登录
     */
    public static boolean isLogin() {
        return SERVICE.isLogin();
    }

    /**
     * 退出
     */
    public static void loginOut() {
        SERVICE.loginOut();
    }

}
