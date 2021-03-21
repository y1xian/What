package com.yyxnb.module_music.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.yyxnb.module_music.bean.MusicLocalBean;
import com.yyxnb.what.room.BaseDao;

import java.util.List;

/**
 * 本地音乐
 */
@Dao
public interface MusicLocalDao extends BaseDao<MusicLocalBean> {

    @Query("SELECT * FROM local")
    LiveData<List<MusicLocalBean>> liveMusicList();

    @Query("SELECT * FROM local")
    List<MusicLocalBean> getMusicList();

    //删全部
    @Query("DELETE FROM local")
    void deleteAll();

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertItems(ArrayList<IAudio> mLists);
}
