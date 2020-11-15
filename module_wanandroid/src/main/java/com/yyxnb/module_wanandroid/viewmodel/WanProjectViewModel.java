package com.yyxnb.module_wanandroid.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.yyxnb.common_base.bean.WanData;
import com.yyxnb.common_base.config.Http;
import com.yyxnb.module_wanandroid.bean.WanAriticleBean;
import com.yyxnb.module_wanandroid.bean.WanClassifyBean;
import com.yyxnb.module_wanandroid.bean.WanStatus;
import com.yyxnb.module_wanandroid.config.WanService;
import com.yyxnb.lib_network.BaseViewModel;

import java.util.List;

public class WanProjectViewModel extends BaseViewModel {

    private final WanService mApi = Http.getInstance().create(WanService.class);

    public MutableLiveData<List<WanClassifyBean>> projecTypes = new MutableLiveData<>();
    public MutableLiveData<WanStatus<WanAriticleBean>> projecDataByType = new MutableLiveData<>();
    public MutableLiveData<List<WanAriticleBean>> projecNewData = new MutableLiveData<>();

    public void getProjecTypes() {

        launchOnlyResult(mApi.getProjecTypes(), new OnHandleException<WanData<List<WanClassifyBean>>>() {
            @Override
            public void success(WanData<List<WanClassifyBean>> data) {
                projecTypes.postValue(data.getResult());
            }

            @Override
            public void error(String msg) {
                loge(msg);
            }
        });
    }

    public void getProjecDataByType(int page, int cid) {

        launchOnlyResult(mApi.getProjecDataByType(page, cid), new OnHandleException<WanData<WanStatus<WanAriticleBean>>>() {
            @Override
            public void success(WanData<WanStatus<WanAriticleBean>> data) {
                projecDataByType.postValue(data.getResult());
            }

            @Override
            public void error(String msg) {
                loge(msg);
            }
        });
    }

    public void getProjecNewData(int page) {

        launchOnlyResult(mApi.getProjecNewData(page), new OnHandleException<WanData<List<WanAriticleBean>>>() {
            @Override
            public void success(WanData<List<WanAriticleBean>> data) {
                projecNewData.postValue(data.getResult());
            }

            @Override
            public void error(String msg) {
                loge(msg);
            }
        });
    }
}
