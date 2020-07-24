package com.yyxnb.module_music.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.yyxnb.common_base.db.BaseDao;
import com.yyxnb.module_music.bean.MusicRecordBean;

import java.util.List;

/**
 * 历史记录
 */
@Dao
public interface RecordDao extends BaseDao<MusicRecordBean> {

    // 最后一条记录
    @Query("SELECT * FROM record ORDER BY updateTime DESC limit 1")
    LiveData<MusicRecordBean> liveLastRecord();

    // 最后一条记录
    @Query("SELECT * FROM record ORDER BY updateTime DESC limit 1")
    MusicRecordBean getLastRecord();

    // 全部记录
    @Query("SELECT * FROM record ORDER BY updateTime DESC")
    LiveData<List<MusicRecordBean>> liveRecordAll();

    // 全部记录
    @Query("SELECT * FROM record ORDER BY updateTime DESC")
    List<MusicRecordBean> getRecordAll();

}
