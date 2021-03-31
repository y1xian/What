package com.yyxnb.what.room.cache;

import androidx.room.Dao;
import androidx.room.Query;

import com.yyxnb.what.room.BaseDao;

import java.util.List;

@Dao
public interface CacheDao extends BaseDao<Cache> {

    @Query("SELECT * FROM cache WHERE `key`=:key limit 1")
    Cache getCache(String key);

    //如果是一对多,这里可以写List<Cache>
    @Query("SELECT * FROM cache WHERE `key`=:key")
    List<Cache> getCacheList(String key);

}
