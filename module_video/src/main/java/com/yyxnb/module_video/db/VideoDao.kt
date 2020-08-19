package com.yyxnb.module_video.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.yyxnb.common_base.db.BaseDao
import com.yyxnb.module_video.bean.TikTokBean

@Dao
interface VideoDao : BaseDao<TikTokBean> {

    @get:Query("SELECT * FROM video limit 0,20")
    val videos: LiveData<List<TikTokBean>>

    //删全部
    @Query("DELETE FROM video")
    fun deleteAll()
}