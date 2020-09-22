package com.yyxnb.module_music.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.yyxnb.room.BaseDao
import com.yyxnb.module_music.bean.MusicRecordBean

/**
 * 历史记录
 */
@Dao
interface RecordDao : BaseDao<MusicRecordBean> {
    // 最后一条记录
    @Query("SELECT * FROM record ORDER BY updateTime DESC limit 1")
    fun liveLastRecord(): LiveData<MusicRecordBean>

    // 最后一条记录
    @get:Query("SELECT * FROM record ORDER BY updateTime DESC limit 1")
    val lastRecord: MusicRecordBean

    // 全部记录
    @Query("SELECT * FROM record ORDER BY updateTime DESC")
    fun liveRecordAll(): LiveData<List<MusicRecordBean>>

    // 全部记录
    @get:Query("SELECT * FROM record ORDER BY updateTime DESC")
    val recordAll: List<MusicRecordBean>
}