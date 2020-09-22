package com.yyxnb.room.cache

import androidx.room.Dao
import androidx.room.Query
import com.yyxnb.room.BaseDao

@Dao
interface CacheDao : BaseDao<Cache> {

    @Query("SELECT * FROM cache WHERE `key`=:key limit 1")
    fun getCache(key: String?): Cache?

    //如果是一对多,这里可以写List<Cache>
    @Query("SELECT * FROM cache WHERE `key`=:key")
    fun getCacheList(key: String?): List<Cache?>?
}