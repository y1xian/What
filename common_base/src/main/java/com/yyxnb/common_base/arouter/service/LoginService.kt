package com.yyxnb.common_base.arouter.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider
import com.yyxnb.common_base.bean.UserBean

/**
 * Login模块对外提供的所有功能
 */
interface LoginService : IProvider {

    val userInfo: UserBean?

    fun updateUserInfo(userBean: UserBean?)

    fun loginOut()

    val isLogin: Boolean

    @Deprecated("")
    fun login(context: Context?)
}