package com.yyxnb.module_main.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.yyxnb.common_base.core.CommonViewModel;

public class MainViewModel extends CommonViewModel {

    public MutableLiveData<Boolean> isHideBottomBar = new MutableLiveData<>();

}
