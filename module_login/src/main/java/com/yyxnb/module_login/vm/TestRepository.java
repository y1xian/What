package com.yyxnb.module_login.vm;


import android.arch.lifecycle.LiveData;

import com.yyxnb.http.RetrofitManager;
import com.yyxnb.module_login.BaseDatas;
import com.yyxnb.module_login.TestData;
import com.yyxnb.module_login.LoginApi;

import java.util.List;

public class TestRepository {

    private LoginApi mApi = RetrofitManager.INSTANCE.createApi(LoginApi.class);

//    public LiveData<Lcee<BaseDatas<List<TestData>>>> getTest(){
//        return LiveDataObservableAdapter.INSTANCE.fromDeferredLcee(mApi.getTest());
//    }

    public LiveData<BaseDatas<List<TestData>>> getTest(){
        return mApi.getTest();
    }

}
