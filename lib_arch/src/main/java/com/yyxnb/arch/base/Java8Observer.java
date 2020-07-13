package com.yyxnb.arch.base;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.util.Log;

import com.yyxnb.common.AppConfig;

import java.io.Serializable;

public class Java8Observer implements DefaultLifecycleObserver, Serializable {

    private String tag;

    public Java8Observer(String tag) {
        this.tag = tag;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Log.e(tag, owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        Log.w(tag, owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Log.w(tag, owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Log.w(tag, owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        Log.w(tag, owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Log.e(tag, owner.getLifecycle().getCurrentState().name());
    }
}
