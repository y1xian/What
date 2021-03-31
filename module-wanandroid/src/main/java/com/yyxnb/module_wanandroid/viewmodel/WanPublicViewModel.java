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

public class WanPublicViewModel extends CommonViewModel {

    private final WanService mApi = Http.getInstance().create(WanService.class);

    public MutableLiveData<List<WanClassifyBean>> publicTypes = new MutableLiveData<>();
    public MutableLiveData<WanStatus<WanAriticleBean>> publicData = new MutableLiveData<>();

    public void getPublicTypes() {

        launchOnlyResult(mApi.getPublicTypes(), new HttpResponseCallback<WanData<List<WanClassifyBean>>>() {
            @Override
            public void onSuccess(WanData<List<WanClassifyBean>> data) {
                publicTypes.postValue(data.getResult());
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    public void getPublicData(int page, int id) {

        launchOnlyResult(mApi.getPublicData(page, id), new HttpResponseCallback<WanData<WanStatus<WanAriticleBean>>>() {
            @Override
            public void onSuccess(WanData<WanStatus<WanAriticleBean>> data) {
                publicData.postValue(data.getResult());
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

}
