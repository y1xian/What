package com.yyxnb.module_login.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;


import com.yyxnb.arch.base.mvvm.BaseViewModel;
import com.yyxnb.module_login.BaseDatas;
import com.yyxnb.module_login.TestData;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestViewModel extends BaseViewModel {

    private TestRepository mRepository = new TestRepository();

    private MutableLiveData<Map<String, String>> reqTeam = new MutableLiveData();

    public MutableLiveData<BaseDatas<List<TestData>>> reqTest = new MutableLiveData();
//

    public LiveData<BaseDatas<List<TestData>>> getTest(){
        return Transformations.switchMap(reqTeam, input -> mRepository.getTest());
    }


//
    public void reqTest(){
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", "李白");
        reqTeam.postValue(map);
    }

}
