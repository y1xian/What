package com.yyxnb.module_login.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_res.utils.ARouterUtils;
import com.yyxnb.common_res.service.LoginService;
import com.yyxnb.common_res.constants.LoginRouterPath;
import com.yyxnb.what.arch.base.IFragment;
import com.yyxnb.module_login.config.LoginManager;


/**
 * 登陆模块对外接口功能实现
 */
@Route(path = LoginRouterPath.SERVICE)
public class LoginServiceImpl implements LoginService {

    @Override
    public void loginOut() {
        LoginManager.getInstance().loginOut();
    }

    @Override
    public boolean isLogin() {
        return LoginManager.getInstance().isLogin();
    }

    @Override
    public void start(Context context) {
        ARouterUtils.navActivity(LoginRouterPath.MAIN_ACTIVITY);
    }

    @Override
    public IFragment mainPage(Context context) {
        return ARouterUtils.navFragment(LoginRouterPath.MAIN_FRAGMENT);
    }

    @Override
    public IFragment showPage(Context context) {
        return ARouterUtils.navFragment(LoginRouterPath.SHOW_FRAGMENT);
    }

    @Override
    public void init(Context context) {

    }
}
