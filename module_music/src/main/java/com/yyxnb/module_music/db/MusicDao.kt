package com.yyxnb.module_music.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.yyxnb.common_base.db.BaseDao
import com.yyxnb.module_music.bean.MusicBean

/**
 * 进行播放的音乐数据源
 */
@Dao
interface MusicDao : BaseDao<MusicBean> {
    @Query("SELECT * FROM music")
    fun liveMusicList(): LiveData<List<MusicBean>>

    @get:Query("SELECT * FROM music")
    val musicList: List<MusicBean>

    //删全部
    @Query("DELETE FROM music")
    fun deleteAll() //    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //    void insertItems(ArrayList<IAudio> mLists);
}