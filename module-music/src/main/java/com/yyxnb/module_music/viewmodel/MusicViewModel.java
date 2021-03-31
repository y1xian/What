package com.yyxnb.module_music.viewmodel;

import androidx.lifecycle.LiveData;

import com.yyxnb.common_base.base.CommonViewModel;
import com.yyxnb.module_music.bean.MusicBean;
import com.yyxnb.module_music.bean.MusicLocalBean;
import com.yyxnb.module_music.bean.MusicRecordBean;
import com.yyxnb.module_music.config.DataConfig;
import com.yyxnb.module_music.db.MusicDao;
import com.yyxnb.module_music.db.MusicDatabase;
import com.yyxnb.module_music.db.MusicLocalDao;
import com.yyxnb.module_music.db.RecordDao;

import java.util.List;

public class MusicViewModel extends CommonViewModel {

    private MusicDao musicDao = MusicDatabase.getInstance().musicDao();
    private MusicLocalDao localDao = MusicDatabase.getInstance().musicLocalDao();
    private RecordDao recordDao = MusicDatabase.getInstance().recordDao();

    // 列表
    public LiveData<List<MusicBean>> getMusicData() {
        return musicDao.liveMusicList();
    }

    //本地
    public LiveData<List<MusicLocalBean>> getLocalMusicData() {
        return localDao.liveMusicList();
    }

    //最近一条历史记录/播放着的
    public LiveData<MusicRecordBean> getRecordData() {
        return recordDao.liveLastRecord();
    }

    //历史记录
    public LiveData<List<MusicRecordBean>> getRecordListData() {
        return recordDao.liveRecordAll();
    }

    public void reqMusicData() {

        if (null == musicDao.liveMusicList()) {
        }
        musicDao.deleteAll();
        musicDao.insertItems(DataConfig.getMusicBeans());
//        mMusicData = musicDao.liveMusicList();
    }


}
