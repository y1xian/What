package com.yyxnb.module_music.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.yyxnb.module_music.bean.MusicBean;
import com.yyxnb.lib_room.BaseDao;

import java.util.List;

/**
 * 进行播放的音乐数据源
 */
@Dao
public interface MusicDao extends BaseDao<MusicBean> {

    @Query("SELECT * FROM music")
    LiveData<List<MusicBean>> liveMusicList();

    @Query("SELECT * FROM music")
    List<MusicBean> getMusicList();

    //删全部
    @Query("DELETE FROM music")
    void deleteAll();

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertItems(ArrayList<IAudio> mLists);
}
