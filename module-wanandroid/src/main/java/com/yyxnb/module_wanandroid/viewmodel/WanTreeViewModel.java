package com.yyxnb.module_wanandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.yyxnb.common_base.base.CommonViewModel;
import com.yyxnb.common_res.bean.WanData;
import com.yyxnb.common_res.config.Http;
import com.yyxnb.module_wanandroid.bean.WanAriticleBean;
import com.yyxnb.module_wanandroid.bean.WanNavigationBean;
import com.yyxnb.module_wanandroid.bean.WanStatus;
import com.yyxnb.module_wanandroid.bean.WanSystemBean;
import com.yyxnb.module_wanandroid.config.WanService;

import java.util.List;

public class WanTreeViewModel extends CommonViewModel {

    private final WanService mApi = Http.getInstance().create(WanService.class);

    public MutableLiveData<WanStatus<WanAriticleBean>> squareData = new MutableLiveData<>();
    public MutableLiveData<List<WanSystemBean>> systemData = new MutableLiveData<>();
    public MutableLiveData<List<WanNavigationBean>> navigationData = new MutableLiveData<>();

    public void getSquareData(int page){

        launchOnlyResult(mApi.getSquareData(page), new HttpResponseCallback<WanData<WanStatus<WanAriticleBean>>>() {
            @Override
            public void onSuccess(WanData<WanStatus<WanAriticleBean>> data) {
                squareData.postValue(data.getResult());
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    public void getSystemData(){

        launchOnlyResult(mApi.getSystemData(), new HttpResponseCallback<WanData<List<WanSystemBean>>>() {
            @Override
            public void onSuccess(WanData<List<WanSystemBean>> data) {
                systemData.postValue(data.getResult());
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    public void getNavigationData(){

        launchOnlyResult(mApi.getNavigationData(), new HttpResponseCallback<WanData<List<WanNavigationBean>>>() {
            @Override
            public void onSuccess(WanData<List<WanNavigationBean>> data) {
                navigationData.postValue(data.getResult());
            }

            @Override
            public void onError(String msg) {
            }
        });
    }
}
