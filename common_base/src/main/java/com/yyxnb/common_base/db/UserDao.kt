package com.yyxnb.common_base.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.yyxnb.common_base.bean.UserBean;

import java.util.List;

@Dao
public interface UserDao extends BaseDao<UserBean> {

    @Query("SELECT * FROM user WHERE userId=:userId")
    LiveData<UserBean> getUser(int userId);

    @Query("SELECT * FROM user WHERE userId=:userId")
    UserBean getUserMainThread(int userId);

    @Query("SELECT * FROM user")
    LiveData<List<UserBean>> getUserAll();
}
