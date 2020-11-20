package com.yyxnb.lib_arch.viewmodel;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.MainThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.yyxnb.lib_widget.AppUtils;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/21
 * 历    史：
 * 描    述：所有的ViewModel在此获取，便于方便管理
 * ================================================
 */
public class ViewModelFactory implements Serializable {

    /**
     * 创建 对应的 BaseViewModel, 并做缓存
     *
     * @param fragment 创建 对应的 ViewModel, 并且 添加 通用 (LiveData) 到 ViewModel中
     * @param field    反射获取到的Field对象
     * @param <T>      BaseViewModel 的实现
     */
    @MainThread
    public static <T extends BaseViewModel> T createViewModel(Fragment fragment, Field field) {
        Class<T> viewModelClass = AppUtils.getFiledClazz(field);
        return createViewModel(fragment, viewModelClass);
    }

    @MainThread
    public static <T extends BaseViewModel> T createViewModel(FragmentActivity activity, Field field) {
        Class<T> viewModel = AppUtils.getFiledClazz(field);
        return createViewModel(activity, viewModel);
    }

    @MainThread
    public static <T extends BaseViewModel> T createViewModel(Fragment fragment, Class<T> viewModel) {
        return ViewModelProviders.of(fragment).get(viewModel).attachLifecycleOwner(fragment);
    }

    @MainThread
    public static <T extends BaseViewModel> T createViewModel(FragmentActivity activity, Class<T> viewModel) {
        return ViewModelProviders.of(activity).get(viewModel).attachLifecycleOwner(activity);
    }
}
