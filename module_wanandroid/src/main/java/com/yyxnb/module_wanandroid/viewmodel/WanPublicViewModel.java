package com.yyxnb.module_wanandroid.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.yyxnb.common_base.config.Http;
import com.yyxnb.network.BaseViewModel;
import com.yyxnb.module_wanandroid.bean.WanAriticleBean;
import com.yyxnb.module_wanandroid.bean.WanClassifyBean;
import com.yyxnb.common_base.bean.WanData;
import com.yyxnb.module_wanandroid.bean.WanStatus;
import com.yyxnb.module_wanandroid.config.WanService;

import java.util.List;

public class WanPublicViewModel extends BaseViewModel {

    private final WanService mApi = Http.getInstance().create(WanService.class);

    public MutableLiveData<List<WanClassifyBean>> publicTypes = new MutableLiveData<>();
    public MutableLiveData<WanStatus<WanAriticleBean>> publicData = new MutableLiveData<>();

    public void getPublicTypes() {

        launchOnlyResult(mApi.getPublicTypes(), new OnHandleException<WanData<List<WanClassifyBean>>>() {
            @Override
            public void success(WanData<List<WanClassifyBean>> data) {
                publicTypes.postValue(data.getResult());
            }

            @Override
            public void error(String msg) {
                loge(msg);
            }
        });
    }

    public void getPublicData(int page, int id) {

        launchOnlyResult(mApi.getPublicData(page, id), new OnHandleException<WanData<WanStatus<WanAriticleBean>>>() {
            @Override
            public void success(WanData<WanStatus<WanAriticleBean>> data) {
                publicData.postValue(data.getResult());
            }

            @Override
            public void error(String msg) {
                loge(msg);
            }
        });
    }

}
