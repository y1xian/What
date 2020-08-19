package com.yyxnb.common_base.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.yyxnb.common_base.bean.UserBean

@Dao
interface UserDao : BaseDao<UserBean> {

    @Query("SELECT * FROM user WHERE userId=:userId")
    fun getUser(userId: Int): LiveData<UserBean>

    @Query("SELECT * FROM user WHERE userId=:userId")
    fun getUserMainThread(userId: Int): UserBean

    @Query("SELECT * FROM user")
    fun getUserAll(): LiveData<List<UserBean>>
}