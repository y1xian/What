package com.yyxnb.module_video.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.yyxnb.common.AppConfig;
import com.yyxnb.http.BaseViewModel;
import com.yyxnb.common_base.base.BaseData;
import com.yyxnb.common_base.base.StateData;
import com.yyxnb.common_base.config.Http;
import com.yyxnb.module_video.api.IService;
import com.yyxnb.module_video.bean.TikTokBean;

import java.util.HashMap;
import java.util.Map;

public class VideoViewModel extends BaseViewModel {

    private IService mApi = Http.getInstance().create(IService.class);

    public MutableLiveData<StateData<TikTokBean>> result = new MutableLiveData();

    public void reqVideoList(){
        Map<String,String> map = new HashMap<>();

        launchOnlyResult(mApi.getVideoList4(map), new OnHandleException<BaseData<StateData<TikTokBean>>>() {
            @Override
            public void success(BaseData<StateData<TikTokBean>> data) {
                result.postValue(data.getResult());
            }

            @Override
            public void error(String msg) {
                AppConfig.getInstance().log(msg);
            }
        });


    }
}
