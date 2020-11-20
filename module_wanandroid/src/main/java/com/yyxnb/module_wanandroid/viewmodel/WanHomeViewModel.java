package com.yyxnb.module_wanandroid.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.yyxnb.common_base.base.CommonViewModel;
import com.yyxnb.common_base.bean.WanData;
import com.yyxnb.common_base.config.Http;
import com.yyxnb.module_wanandroid.bean.WanAriticleBean;
import com.yyxnb.module_wanandroid.bean.WanStatus;
import com.yyxnb.module_wanandroid.config.WanService;

import java.util.List;

public class WanHomeViewModel extends CommonViewModel {

    private final WanService mApi = Http.getInstance().create(WanService.class);

    public MutableLiveData<List<WanAriticleBean>> bannerData = new MutableLiveData<>();
    public MutableLiveData<List<WanAriticleBean>> topArticleData = new MutableLiveData<>();
    public MutableLiveData<WanStatus<WanAriticleBean>> homeListData = new MutableLiveData<>();

    public void getAritrilList(int page){

        launchOnlyResult(mApi.getAritrilList(page), new OnHandleException<WanData<WanStatus<WanAriticleBean>>>() {
            @Override
            public void success(WanData<WanStatus<WanAriticleBean>> data) {
                homeListData.postValue(data.getResult());
            }

            @Override
            public void error(String msg) {
                loge(msg);
            }
        });
    }

    public void getTopAritrilList(){

        launchOnlyResult(mApi.getTopAritrilList(), new OnHandleException<WanData<List<WanAriticleBean>>>() {
            @Override
            public void success(WanData<List<WanAriticleBean>> data) {
                topArticleData.postValue(data.getResult());
            }

            @Override
            public void error(String msg) {
                loge(msg);
            }
        });
    }

    public void getBanner(){

        launchOnlyResult(mApi.getBanner(), new OnHandleException<WanData<List<WanAriticleBean>>>() {
            @Override
            public void success(WanData<List<WanAriticleBean>> data) {
                bannerData.postValue(data.getResult());
            }

            @Override
            public void error(String msg) {
                loge(msg);
            }
        });
    }

}
