package com.yyxnb.module_video.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yyxnb.common_base.base.StateData
import com.yyxnb.common_base.config.Http
import com.yyxnb.module_video.bean.TikTokBean
import com.yyxnb.module_video.config.DataConfig
import com.yyxnb.module_video.config.VideoService
import com.yyxnb.module_video.db.VideoDatabase
import com.yyxnb.network.BaseViewModel

class VideoViewModel : BaseViewModel() {

    private val mApi = Http.create(VideoService::class.java)
    private val mVideoDao = VideoDatabase.instance.videoDao()
    var result: MutableLiveData<StateData<TikTokBean>> = MutableLiveData()
    fun result1(): LiveData<List<TikTokBean>> {
        return mVideoDao.videos
    }

    fun reqVideoList() {
//        Map<String,String> map = new HashMap<>();
//
//        launchOnlyResult(mApi.getVideoList4(map), new OnHandleException<BaseData<StateData<TikTokBean>>>() {
//            @Override
//            public void success(BaseData<StateData<TikTokBean>> data) {
//                result.postValue(data.getResult());
//            }
//
//            @Override
//            public void error(String msg) {
//                AppConfig.getInstance().log(msg);
//            }
//        });
        mVideoDao.insertItems(DataConfig.tikTokBeans!!)
    }
}