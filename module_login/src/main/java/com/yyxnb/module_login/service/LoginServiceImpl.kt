package com.yyxnb.module_login.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.arouter.ARouterUtils;
import com.yyxnb.common_base.arouter.service.LoginService;
import com.yyxnb.common_base.bean.UserBean;
import com.yyxnb.module_login.config.UserManager;

import static com.yyxnb.common_base.arouter.ARouterConstant.LOGIN_ACTIVITY;
import static com.yyxnb.common_base.arouter.ARouterConstant.LOGIN_SERVICE;

/**
 * 登陆模块对外接口功能实现
 */
@Route(path = LOGIN_SERVICE)
public class LoginServiceImpl implements LoginService {

    @Override
    public UserBean getUserInfo() {
        return UserManager.getInstance().getUserBean();
    }

    @Override
    public void updateUserInfo(UserBean userBean) {
        UserManager.getInstance().setUserBean(userBean);
    }

    @Override
    public void loginOut() {
        UserManager.getInstance().loginOut();
    }

    @Override
    public boolean isLogin() {
        return UserManager.getInstance().isLogin();
    }

    @Override
    public void login(Context context) {
        ARouterUtils.navActivity(LOGIN_ACTIVITY);
    }

    @Override
    public void init(Context context) {

    }
}
