package com.yyxnb.module_music.db

import androidx.room.Dao
import com.yyxnb.room.BaseDao
import com.yyxnb.module_music.bean.MusicFavouriteBean

/**
 * 喜欢/收藏
 */
@Dao
interface FavouriteDao : BaseDao<MusicFavouriteBean> { //    @Query("SELECT * FROM favourite WHERE mid = :mid limit 1")
    //    LiveData<MusicFavouriteBean> getFavouriteLive(String mid);
    //
    //    @Query("SELECT * FROM favourite WHERE mid = :mid  limit 1")
    //    MusicFavouriteBean getFavourite(String mid);
}