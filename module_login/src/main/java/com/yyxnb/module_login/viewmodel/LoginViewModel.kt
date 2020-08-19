package com.yyxnb.module_login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.yyxnb.common.utils.log.LogUtils.w
import com.yyxnb.common_base.bean.UserBean
import com.yyxnb.common_base.config.Http
import com.yyxnb.common_base.db.AppDatabase.Companion.instance
import com.yyxnb.module_login.config.LoginApi
import com.yyxnb.network.BaseViewModel
import com.yyxnb.utils.encrypt.MD5Utils

class LoginViewModel : BaseViewModel() {

    private val mApi = Http.create(LoginApi::class.java)
    private val userDao = instance!!.userDao()
    private val reqUser: MutableLiveData<UserBean> = MutableLiveData<UserBean>()

    val user: LiveData<UserBean>
        get() = Transformations.switchMap(reqUser) { input: UserBean? -> userDao!!.getUser(input!!.userId) }
    val userAll: LiveData<List<UserBean>>
        get() = userDao!!.getUserAll()

    fun reqLogin(phone: String) {
        val userBean = UserBean()
        userBean.userId = Math.abs("uid_$phone".hashCode())
        userBean.phone = phone
        userBean.signature = "暂无签名"
        userBean.token = MD5Utils.parseStrToMd5L32(userBean.userId.toString() + "-token-" + userBean.phone)
        userBean.nickname = "游客" + phone.substring(7)
        userBean.isLogin = true
        userDao!!.insertItem(userBean)
        reqUser.value = userBean
        w("user : $userBean")
    }

}