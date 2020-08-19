package com.yyxnb.common_base.arouter.service.impl

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.yyxnb.common_base.arouter.ARouterConstant
import com.yyxnb.common_base.arouter.service.LoginService
import com.yyxnb.common_base.bean.UserBean

/**
 * 对LoginService包装，业务方直接调用，无需再自己初始化service类
 */
class LoginImpl public constructor() {

    init {
        //初始化
        ARouter.getInstance().inject(this)
    }

    @Autowired(name = ARouterConstant.LOGIN_SERVICE)
    lateinit var mLoginService: LoginService

    /**
     * 跳转登录 ，建议直接路由跳转
     */
    fun login(context: Context?) {
        mLoginService!!.login(context)
    }

    /**
     * 是否登录
     */
    val isLogin: Boolean
        get() = mLoginService!!.isLogin

    /**
     * 退出
     */
    fun loginOut() {
        mLoginService!!.loginOut()
    }

    /**
     * 获取用户信息
     */
    val userInfo: UserBean?
        get() = mLoginService!!.userInfo

    /**
     * 更新用户信息
     */
    fun updateUserInfo(userBean: UserBean?) {
        mLoginService!!.updateUserInfo(userBean)
    }

    companion object {
        var mLoginImpl: LoginImpl? = null
        @JvmStatic
        val instance: LoginImpl?
            get() {
                if (mLoginImpl == null) {
                    synchronized(LoginImpl::class.java) {
                        if (mLoginImpl == null) {
                            mLoginImpl = LoginImpl()
                        }
                        return mLoginImpl
                    }
                }
                return mLoginImpl
            }
    }

}