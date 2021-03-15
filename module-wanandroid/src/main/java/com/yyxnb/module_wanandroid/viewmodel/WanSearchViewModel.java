package com.yyxnb.module_wanandroid.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.yyxnb.common_base.core.CommonViewModel;
import com.yyxnb.common_res.bean.WanData;
import com.yyxnb.common_res.config.Http;
import com.yyxnb.module_wanandroid.bean.WanAriticleBean;
import com.yyxnb.module_wanandroid.bean.WanClassifyBean;
import com.yyxnb.module_wanandroid.bean.WanStatus;
import com.yyxnb.module_wanandroid.config.WanService;

import java.util.List;

public class WanSearchViewModel extends CommonViewModel {

    private final WanService mApi = Http.getInstance().create(WanService.class);

    public MutableLiveData<List<WanClassifyBean>> searchData = new MutableLiveData<>();
    public MutableLiveData<WanStatus<WanAriticleBean>> searchDataByKey = new MutableLiveData<>();

    public void getSearchData() {

        launchOnlyResult(mApi.getSearchData(), new HttpResponseCallback<WanData<List<WanClassifyBean>>>() {
            @Override
            public void onSuccess(WanData<List<WanClassifyBean>> data) {
                searchData.postValue(data.getResult());
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    public void getSearchDataByKey(int page, String key) {
        launchOnlyResult(mApi.getSearchDataByKey(page, key), new HttpResponseCallback<WanData<WanStatus<WanAriticleBean>>>() {
            @Override
            public void onSuccess(WanData<WanStatus<WanAriticleBean>> data) {
                searchDataByKey.postValue(data.getResult());
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

}
