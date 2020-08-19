package com.yyxnb.module_login.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.yyxnb.common_base.arouter.ARouterConstant.LOGIN_ACTIVITY
import com.yyxnb.common_base.arouter.ARouterConstant.LOGIN_SERVICE
import com.yyxnb.common_base.arouter.ARouterUtils.navActivity
import com.yyxnb.common_base.arouter.service.LoginService
import com.yyxnb.common_base.bean.UserBean
import com.yyxnb.module_login.config.UserManager
import com.yyxnb.module_login.config.UserManager.getUserBean
import com.yyxnb.module_login.config.UserManager.setUserBean

/**
 * 登陆模块对外接口功能实现
 */
@Route(path = LOGIN_SERVICE)
class LoginServiceImpl : LoginService {

    override val userInfo: UserBean
        get() = getUserBean()

    override fun updateUserInfo(userBean: UserBean?) {
        setUserBean(userBean!!)
    }

    override fun loginOut() {
        UserManager.loginOut()
    }

    override val isLogin: Boolean
        get() = UserManager.isLogin

    override fun login(context: Context?) {
        navActivity(LOGIN_ACTIVITY)
    }

    override fun init(context: Context) {}
}