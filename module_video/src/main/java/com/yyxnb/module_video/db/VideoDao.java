package com.yyxnb.module_video.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.yyxnb.network.db.BaseDao;
import com.yyxnb.module_video.bean.TikTokBean;

import java.util.List;

@Dao
public interface VideoDao extends BaseDao<TikTokBean> {

    @Query("SELECT * FROM video limit 0,20")
    LiveData<List<TikTokBean>> getVideos();

    //删全部
    @Query("DELETE FROM video")
    void deleteAll();

}
