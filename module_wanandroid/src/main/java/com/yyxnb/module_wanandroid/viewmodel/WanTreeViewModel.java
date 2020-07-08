package com.yyxnb.module_wanandroid.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.yyxnb.common_base.config.Http;
import com.yyxnb.http.BaseViewModel;
import com.yyxnb.module_wanandroid.bean.WanAriticleBean;
import com.yyxnb.module_wanandroid.bean.WanData;
import com.yyxnb.module_wanandroid.bean.WanNavigationBean;
import com.yyxnb.module_wanandroid.bean.WanStatus;
import com.yyxnb.module_wanandroid.bean.WanSystemBean;
import com.yyxnb.module_wanandroid.config.WanService;

import java.util.List;

public class WanTreeViewModel extends BaseViewModel {

    private final WanService mApi = Http.getInstance().create(WanService.class);

    public MutableLiveData<WanStatus<WanAriticleBean>> squareData = new MutableLiveData<>();
    public MutableLiveData<List<WanSystemBean>> systemData = new MutableLiveData<>();
    public MutableLiveData<List<WanNavigationBean>> navigationData = new MutableLiveData<>();

    public void getSquareData(int page){

        launchOnlyResult(mApi.getSquareData(page), new OnHandleException<WanData<WanStatus<WanAriticleBean>>>() {
            @Override
            public void success(WanData<WanStatus<WanAriticleBean>> data) {
                squareData.postValue(data.getResult());
            }

            @Override
            public void error(String msg) {

            }
        });
    }

    public void getSystemData(){

        launchOnlyResult(mApi.getSystemData(), new OnHandleException<WanData<List<WanSystemBean>>>() {
            @Override
            public void success(WanData<List<WanSystemBean>> data) {
                systemData.postValue(data.getResult());
            }

            @Override
            public void error(String msg) {

            }
        });
    }

    public void getNavigationData(){

        launchOnlyResult(mApi.getNavigationData(), new OnHandleException<WanData<List<WanNavigationBean>>>() {
            @Override
            public void success(WanData<List<WanNavigationBean>> data) {
                navigationData.postValue(data.getResult());
            }

            @Override
            public void error(String msg) {

            }
        });
    }
}
