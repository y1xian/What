package com.yyxnb.common_res.arouter.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.yyxnb.common_res.bean.UserBean;

/**
 * Login模块对外提供的所有功能
 */
public interface LoginService extends IProvider {

    UserBean getUserInfo();

    void updateUserInfo(UserBean userBean);

    void loginOut();

    boolean isLogin();

    @Deprecated
    void login(Context context);

}
