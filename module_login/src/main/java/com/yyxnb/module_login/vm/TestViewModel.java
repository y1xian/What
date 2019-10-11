package com.yyxnb.module_login.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.yyxnb.arch.base.mvvm.BaseViewModel;
import com.yyxnb.http.network.Resource;
import com.yyxnb.http.RetrofitManager;
import com.yyxnb.module_login.BaseDatas;
import com.yyxnb.module_login.LoginApi;
import com.yyxnb.module_login.TestData;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestViewModel extends BaseViewModel {

    private TestRepository mRepository = new TestRepository();
    private LoginApi mApi = RetrofitManager.INSTANCE.createApi(LoginApi.class);

    private MutableLiveData<Map<String, String>> reqTeam = new MutableLiveData();

    public LiveData<BaseDatas<List<TestData>>> getTest() {
        return Transformations.switchMap(reqTeam, input -> mApi.getTest());
    }

    public LiveData<Resource<BaseDatas<List<TestData>>>> getTest2() {
        return Transformations.switchMap(reqTeam, input -> mRepository.getTest2());
    }

    public void reqTest() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", "李白");
        reqTeam.postValue(map);
    }

}
