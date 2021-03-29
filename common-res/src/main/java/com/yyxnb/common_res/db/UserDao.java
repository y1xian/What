package com.yyxnb.common_res.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.yyxnb.common_res.bean.UserVo;
import com.yyxnb.what.room.BaseDao;

import java.util.List;

@Dao
public interface UserDao extends BaseDao<UserVo> {

    @Query("SELECT * FROM user WHERE token=:token")
    LiveData<UserVo> getUser(String token);

    @Query("SELECT * FROM user WHERE userId=:userId")
    LiveData<UserVo> getUserById(String userId);

    @Query("SELECT * FROM user")
    LiveData<List<UserVo>> getUserAll();

    @Query("DELETE FROM user WHERE userLevel = '0'")
    int delAllVisitor();
}
