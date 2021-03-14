package com.yyxnb.module_server.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.yyxnb.lib_room.BaseDao;
import com.yyxnb.module_server.bean.response.CodeVo;

@Dao
public interface CodeDao extends BaseDao<CodeVo> {

    @Query("SELECT * FROM code WHERE phone=:phone and code=:code")
    boolean findCode(String phone, String code);

    @Query("DELETE FROM code WHERE phone=:phone")
    void deleteCode(String phone);
}
