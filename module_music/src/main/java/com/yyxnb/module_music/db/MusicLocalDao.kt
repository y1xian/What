package com.yyxnb.module_music.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.yyxnb.common_base.db.BaseDao
import com.yyxnb.module_music.bean.MusicLocalBean

/**
 * 本地音乐
 */
@Dao
interface MusicLocalDao : BaseDao<MusicLocalBean> {
    @Query("SELECT * FROM local")
    fun liveMusicList(): LiveData<List<MusicLocalBean>>

    @get:Query("SELECT * FROM local")
    val musicList: List<MusicLocalBean>

    //删全部
    @Query("DELETE FROM local")
    fun deleteAll() //    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //    void insertItems(ArrayList<IAudio> mLists);
}