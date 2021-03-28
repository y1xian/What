package com.yyxnb.common_res.service.impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yyxnb.common_res.service.LoginService;
import com.yyxnb.common_res.constants.LoginRouterPath;
import com.yyxnb.what.arch.base.IFragment;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/16
 * 描    述：对LoginService包装，业务方直接调用，无需再自己初始化service类
 * ================================================
 */
public class LoginImpl {

    @Autowired(name = LoginRouterPath.SERVICE)
    protected LoginService service;

    private static volatile LoginImpl mInstance = null;

    private LoginImpl() {
        //初始化
        ARouter.getInstance().inject(this);
    }

    public static LoginImpl getInstance() {
        if (null == mInstance) {
            synchronized (LoginImpl.class) {
                if (null == mInstance) {
                    mInstance = new LoginImpl();
                }
            }
        }
        return mInstance;
    }

    public void start(Context context) {
        service.start(context);
    }

    public IFragment mainPage(Context context) {
        return service.mainPage(context);
    }

    public IFragment showPage(Context context) {
        return service.showPage(context);
    }

    /**
     * 是否登录
     */
    public boolean isLogin() {
        return service.isLogin();
    }

    /**
     * 退出
     */
    public void loginOut() {
        service.loginOut();
    }

}
