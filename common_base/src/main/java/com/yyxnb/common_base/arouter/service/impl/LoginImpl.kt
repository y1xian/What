package com.yyxnb.common_base.arouter.service.impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yyxnb.common_base.arouter.service.LoginService;
import com.yyxnb.common_base.bean.UserBean;

import static com.yyxnb.common_base.arouter.ARouterConstant.LOGIN_SERVICE;

/**
 * 对LoginService包装，业务方直接调用，无需再自己初始化service类
 */
public class LoginImpl {

    @Autowired(name = LOGIN_SERVICE)
    protected LoginService mLoginService;

    private static LoginImpl mLoginImpl = null;

    public static LoginImpl getInstance() {
        if (mLoginImpl == null) {
            synchronized (LoginImpl.class) {
                if (mLoginImpl == null) {
                    mLoginImpl = new LoginImpl();
                }
                return mLoginImpl;
            }
        }
        return mLoginImpl;
    }

    private LoginImpl() {
        //初始化
        ARouter.getInstance().inject(this);
    }

    /**
     * 跳转登录 ，建议直接路由跳转
     */
    public void login(Context context) {
        mLoginService.login(context);
    }

    /**
     * 是否登录
     */
    public boolean isLogin() {
        return mLoginService.isLogin();
    }

    /**
     * 退出
     */
    public void loginOut() {
        mLoginService.loginOut();
    }

    /**
     * 获取用户信息
     */
    public UserBean getUserInfo() {
        return mLoginService.getUserInfo();
    }

    /**
     * 更新用户信息
     */
    public void updateUserInfo(UserBean userBean) {
        mLoginService.updateUserInfo(userBean);
    }

}
