package com.yyxnb.module_wanandroid.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.yyxnb.common_base.core.CommonViewModel;
import com.yyxnb.common_res.bean.WanData;
import com.yyxnb.common_res.config.Http;
import com.yyxnb.util_okhttp.OkHttpUtils;
import com.yyxnb.util_okhttp.utils.HttpCallBack;
import com.yyxnb.module_wanandroid.bean.WanAriticleBean;
import com.yyxnb.module_wanandroid.bean.WanStatus;
import com.yyxnb.module_wanandroid.config.WanService;

import java.util.HashMap;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class WanHomeViewModel extends CommonViewModel {

    private final WanService mApi = Http.getInstance().create(WanService.class);

    public MutableLiveData<List<WanAriticleBean>> bannerData = new MutableLiveData<>();
    public MutableLiveData<List<WanAriticleBean>> topArticleData = new MutableLiveData<>();
    public MutableLiveData<WanStatus<WanAriticleBean>> homeListData = new MutableLiveData<>();

    public void getAritrilList(int page) {

        launchOnlyResult(mApi.getAritrilList(page), new HttpResponseCallback<WanData<WanStatus<WanAriticleBean>>>() {
            @Override
            public void success(WanData<WanStatus<WanAriticleBean>> data) {
                homeListData.postValue(data.getResult());
            }

            @Override
            public void error(String msg) {
            }
        });
    }

    public void getTopAritrilList() {

        launchOnlyResult(mApi.getTopAritrilList(), new HttpResponseCallback<WanData<List<WanAriticleBean>>>() {
            @Override
            public void success(WanData<List<WanAriticleBean>> data) {
                topArticleData.postValue(data.getResult());
            }

            @Override
            public void error(String msg) {
            }
        });
    }

    public void getBanner() {

//        launchOnlyResult(mApi.getBanner(), new OnHandleException<WanData<List<WanAriticleBean>>>() {
//            @Override
//            public void success(WanData<List<WanAriticleBean>> data) {
//                bannerData.postValue(data.getResult());
//            }
//
//            @Override
//            public void error(String msg) {
//                loge(msg);
//            }
//        });

        OkHttpUtils.getInstance().post("https://www.wanandroid.com/banner/json", new HashMap<>(),
                new HttpCallBack<WanData<List<WanAriticleBean>>>() {
                    @Override
                    public void onLoadingBefore(Request request) {

                    }

                    @Override
                    public void onSuccess(Response response, WanData<List<WanAriticleBean>> result) {
                        bannerData.postValue(result.getResult());
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onError(Response response, Exception e) {
                        e.printStackTrace();
                    }
                });
    }

}
