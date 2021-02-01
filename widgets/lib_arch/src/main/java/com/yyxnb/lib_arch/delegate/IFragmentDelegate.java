package com.yyxnb.lib_arch.delegate;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.yyxnb.lib_common.interfaces.ILifecycle;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/21
 * 历    史：
 * 描    述：IFragmentDelegate
 * ================================================
 */
public interface IFragmentDelegate extends ILifecycle {

    void onAttached(Context context);

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreated(Bundle savedInstanceState);

    void onViewCreated(View view, Bundle savedInstanceState);

    void onActivityCreated(Bundle savedInstanceState);

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStarted();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResumed();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPaused();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStopped();

    void onSaveInstanceState(Bundle outState);

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onViewDestroyed();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroyed();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDetached();

    boolean isAdd();

}
