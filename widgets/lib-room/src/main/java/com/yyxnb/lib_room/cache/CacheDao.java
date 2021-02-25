package com.yyxnb.lib_room.cache;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.yyxnb.lib_room.BaseDao;

import java.util.List;

@Dao
public interface CacheDao extends BaseDao<Cache> {

    @Query("SELECT * FROM cache WHERE `key`=:key limit 1")
    Cache getCache(String key);

    //如果是一对多,这里可以写List<Cache>
    @Query("SELECT * FROM cache WHERE `key`=:key")
    List<Cache> getCacheList(String key);

}
