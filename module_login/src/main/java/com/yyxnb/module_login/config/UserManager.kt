package com.yyxnb.module_login.config

import com.tencent.mmkv.MMKV
import com.yyxnb.common_base.bean.UserBean
import com.yyxnb.common_base.config.Constants.USER_ID
import com.yyxnb.common_base.db.AppDatabase

object UserManager {

    val kv = MMKV.defaultMMKV()

    private var userBean: UserBean? = null

    fun getUserBean(): UserBean {
        if (userBean == null) {
            val uid = kv.decodeInt(USER_ID, 0)
            if (uid != 0) {
                userBean = AppDatabase.instance!!.userDao()!!.getUserMainThread(uid)
                if (userBean == null) {
                    userBean = UserBean()
                }
            } else {
                userBean = UserBean()
            }
        }
        return userBean!!
    }

    fun setUserBean(userBean: UserBean) {
        kv.encode(USER_ID, userBean.userId)
        this.userBean = userBean
    }

    // 是否登录
    val isLogin: Boolean
        get() = getUserBean().isLogin

    // token
    fun token(): String? {
        return getUserBean().token
    }

    // 退出登录
    fun loginOut() {
        AppDatabase.instance!!.userDao()!!.deleteItem(userBean!!)
        MMKV.defaultMMKV().removeValueForKey(USER_ID)
        userBean = null
    }

}