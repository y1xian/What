package com.yyxnb.module_server.db;

import androidx.room.Dao;
import androidx.room.Query;

import com.yyxnb.module_server.bean.response.UserVo;
import com.yyxnb.what.room.BaseDao;

import java.util.List;

@Dao
public interface UserDao extends BaseDao<UserVo> {

    @Query("SELECT * FROM user")
    List<UserVo> getUserList();

    @Query("SELECT * FROM user WHERE token=:token")
    UserVo getUser(String token);

    @Query("SELECT * FROM user WHERE phone=:phone")
    UserVo getUserByPhone(String phone);

    @Query("SELECT * FROM user WHERE userId=:userId")
    UserVo getUserById(String userId);

    /**
     * 清空所以游客记录
     */
    @Query("DELETE FROM user WHERE userLevel='0'")
    void deleteAllVisitor();

}
