package com.yyxnb.module_video.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yyxnb.common_base.base.CommonViewModel;
import com.yyxnb.common_res.bean.StateData;
import com.yyxnb.common_res.config.Http;
import com.yyxnb.module_video.bean.TikTokBean;
import com.yyxnb.module_video.config.DataConfig;
import com.yyxnb.module_video.config.VideoService;
import com.yyxnb.module_video.db.VideoDao;
import com.yyxnb.module_video.db.VideoDatabase;

import java.util.List;

public class VideoViewModel extends CommonViewModel {

    private VideoService mApi = Http.getInstance().create(VideoService.class);
    private VideoDao mVideoDao = VideoDatabase.getInstance().videoDao();

    public MutableLiveData<StateData<TikTokBean>> result = new MutableLiveData();
    public LiveData<List<TikTokBean>> result1(){
        return mVideoDao.getVideos();
    };

    public void reqVideoList(){
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
        mVideoDao.insertItems(DataConfig.getTikTokBeans());


    }

}
