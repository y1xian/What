package com.yyxnb.module_user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.yyxnb.common_base.bean.UserBean
import com.yyxnb.common_base.db.AppDatabase.Companion.instance
import com.yyxnb.network.BaseViewModel

class UserViewModel : BaseViewModel() {

    private val userDao = instance!!.userDao()
    @JvmField
    var reqUserId = MutableLiveData<Int>()

    val user: LiveData<UserBean>
        get() = Transformations.switchMap(reqUserId) { input: Int? -> userDao!!.getUser(input!!) }

    fun getUser(userId: Int): LiveData<UserBean> {
        return userDao!!.getUser(userId)
    }
}