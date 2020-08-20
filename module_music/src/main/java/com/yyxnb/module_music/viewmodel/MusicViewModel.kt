package com.yyxnb.module_music.viewmodel

import androidx.lifecycle.LiveData
import com.yyxnb.module_music.bean.MusicBean
import com.yyxnb.module_music.bean.MusicLocalBean
import com.yyxnb.module_music.bean.MusicRecordBean
import com.yyxnb.module_music.config.DataConfig.musicBeans
import com.yyxnb.module_music.db.MusicDatabase.Companion.instance
import com.yyxnb.network.BaseViewModel

class MusicViewModel : BaseViewModel() {

    private val musicDao = instance!!.musicDao()
    private val localDao = instance!!.musicLocalDao()
    private val recordDao = instance!!.recordDao()

    // 列表
    val musicData: LiveData<List<MusicBean>>
        get() = musicDao.liveMusicList()

    //本地
    val localMusicData: LiveData<List<MusicLocalBean>>
        get() = localDao.liveMusicList()

    //最近一条历史记录/播放着的
    val recordData: LiveData<MusicRecordBean>
        get() = recordDao.liveLastRecord()

    //历史记录
    val recordListData: LiveData<List<MusicRecordBean>>
        get() = recordDao.liveRecordAll()

    fun reqMusicData() {
        if (null == musicDao.liveMusicList()) {
        }
        musicDao.deleteAll()
        musicDao.insertItems(musicBeans!!)
        //        mMusicData = musicDao.liveMusicList();
    }
}