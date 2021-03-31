package com.yyxnb.module_wanandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.yyxnb.common_base.base.CommonViewModel;
import com.yyxnb.common_res.bean.WanData;
import com.yyxnb.common_res.config.Http;
import com.yyxnb.module_wanandroid.bean.WanAriticleBean;
import com.yyxnb.module_wanandroid.bean.WanClassifyBean;
import com.yyxnb.module_wanandroid.bean.WanStatus;
import com.yyxnb.module_wanandroid.config.WanService;

import java.util.List;

public class WanProjectViewModel extends CommonViewModel {

    private final WanService mApi = Http.getInstance().create(WanService.class);

    public MutableLiveData<List<WanClassifyBean>> projecTypes = new MutableLiveData<>();
    public MutableLiveData<WanStatus<WanAriticleBean>> projecDataByType = new MutableLiveData<>();
    public MutableLiveData<List<WanAriticleBean>> projecNewData = new MutableLiveData<>();

    public void getProjecTypes() {

        launchOnlyResult(mApi.getProjecTypes(), new HttpResponseCallback<WanData<List<WanClassifyBean>>>() {
            @Override
            public void onSuccess(WanData<List<WanClassifyBean>> data) {
                projecTypes.postValue(data.getResult());
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    public void getProjecDataByType(int page, int cid) {

        launchOnlyResult(mApi.getProjecDataByType(page, cid), new HttpResponseCallback<WanData<WanStatus<WanAriticleBean>>>() {
            @Override
            public void onSuccess(WanData<WanStatus<WanAriticleBean>> data) {
                projecDataByType.postValue(data.getResult());
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    public void getProjecNewData(int page) {

        launchOnlyResult(mApi.getProjecNewData(page), new HttpResponseCallback<WanData<List<WanAriticleBean>>>() {
            @Override
            public void onSuccess(WanData<List<WanAriticleBean>> data) {
                projecNewData.postValue(data.getResult());
            }

            @Override
            public void onError(String msg) {
            }
        });
    }
}
