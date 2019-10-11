package com.yyxnb.module_login.vm;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.yyxnb.arch.utils.log.LogUtils;
import com.yyxnb.http.network.BaseRepository;
import com.yyxnb.http.network.NetworkBoundResource;
import com.yyxnb.http.network.Resource;
import com.yyxnb.module_login.BaseDatas;
import com.yyxnb.module_login.LoginApi;
import com.yyxnb.module_login.TestData;

import java.util.List;

public class TestRepository extends BaseRepository<LoginApi> {

//    private LoginApi mApi = RetrofitManager.INSTANCE.createApi(LoginApi.class);

    public LiveData<BaseDatas<List<TestData>>> getTest(){
        return mApi.getTest();
    }

    public LiveData<Resource<BaseDatas<List<TestData>>>> getTest2(){
        return new NetworkBoundResource<BaseDatas<List<TestData>>>(){

            @Override
            protected void saveCallResult(@NonNull BaseDatas<List<TestData>> item) {
                LogUtils.INSTANCE.w("saveCallResult " + item.getResult().toString());
            }

            @NonNull
            @Override
            protected LiveData<BaseDatas<List<TestData>>> createCall() {
                return mApi.getTest();
            }

            @Override
            protected void onFetchFailed() {
                super.onFetchFailed();
                LogUtils.INSTANCE.e("onFetchFailed ");
            }
        }.getAsLiveData();
    }

}
