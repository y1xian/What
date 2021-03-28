package com.yyxnb.what.arch.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/21
 * 历    史：
 * 描    述：实现了LifecycleOwner接口，使其持有生命周期。可在 {@link BaseViewModel#onCreate()} 观察生命周期事件
 * ================================================
 */
public abstract class BaseViewModel extends ViewModel implements LifecycleOwner {

    private LifecycleOwner mLifecycleOwner;

    /**
     * 观察生命周期事件
     */
    protected abstract void onCreate();

    @SuppressWarnings("unchecked")
    public <T extends BaseViewModel> T attachLifecycleOwner(LifecycleOwner lifecycleOwner) {
        T currentModel = (T) this;

        if (null != mLifecycleOwner) {
            return currentModel;
        }

        this.mLifecycleOwner = lifecycleOwner;
        onCreate();
        return currentModel;
    }


    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleOwner.getLifecycle();
    }

    /**
     * onDestroy 调用时调用
     */
    @Override
    protected void onCleared() {
    }
}
