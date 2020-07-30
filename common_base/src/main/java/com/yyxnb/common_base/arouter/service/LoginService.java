package com.yyxnb.common_base.arouter.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.yyxnb.common_base.bean.UserBean;

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
