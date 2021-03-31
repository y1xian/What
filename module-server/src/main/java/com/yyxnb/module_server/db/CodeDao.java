package com.yyxnb.module_server.db;

import androidx.room.Dao;
import androidx.room.Query;

import com.yyxnb.module_server.bean.response.CodeVo;
import com.yyxnb.what.room.BaseDao;

@Dao
public interface CodeDao extends BaseDao<CodeVo> {

    @Query("SELECT * FROM code WHERE phone=:phone and code=:code")
    boolean findCode(String phone, String code);

    @Query("DELETE FROM code WHERE phone=:phone")
    void deleteCode(String phone);
}
