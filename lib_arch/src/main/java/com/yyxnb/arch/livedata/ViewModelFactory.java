package com.yyxnb.arch.livedata;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.yyxnb.common.AppConfig;

import java.io.Serializable;
import java.lang.reflect.Field;

public class ViewModelFactory implements Serializable {

    /**
     * 创建 对应的 ViewModel, 并且 添加 通用 (LiveData) 到 ViewModel中
     */
    public static ViewModel createViewModel(Fragment fragment, Field field) {
        Class<ViewModel> viewModelClass = AppConfig.getInstance().getFiledClazz(field);
        return ViewModelProviders.of(fragment).get(viewModelClass);
    }

    /**
     * 创建 对应的 ViewModel, 并且 添加 通用 (LiveData) 到 ViewModel中
     */
    public static ViewModel createViewModel(FragmentActivity activity, Field field) {
        Class<ViewModel> viewModelClass = AppConfig.getInstance().getFiledClazz(field);
        return ViewModelProviders.of(activity).get(viewModelClass);
    }
}
