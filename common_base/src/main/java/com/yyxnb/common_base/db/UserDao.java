package com.yyxnb.common_base.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.yyxnb.common_base.bean.UserBean;
import com.yyxnb.lib_room.BaseDao;

import java.util.List;

@Dao
public interface UserDao extends BaseDao<UserBean> {

    @Query("SELECT * FROM user WHERE userId=:userId")
    LiveData<UserBean> getUser(int userId);

    @Query("SELECT * FROM user WHERE userId=:userId")
    UserBean getUserMainThread(int userId);

    @Query("SELECT * FROM user")
    LiveData<List<UserBean>> getUserAll();

    @Query("DELETE FROM user WHERE loginStatus = -1")
    int delAllVisitor();
}
