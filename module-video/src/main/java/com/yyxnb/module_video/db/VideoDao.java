package com.yyxnb.module_video.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.yyxnb.module_video.bean.TikTokBean;
import com.yyxnb.what.room.BaseDao;

import java.util.List;

@Dao
public interface VideoDao extends BaseDao<TikTokBean> {

    @Query("SELECT * FROM video limit 0,20")
    LiveData<List<TikTokBean>> getVideos();

    //删全部
    @Query("DELETE FROM video")
    void deleteAll();

}
