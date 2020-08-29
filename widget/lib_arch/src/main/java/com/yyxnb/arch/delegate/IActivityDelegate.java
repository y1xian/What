package com.yyxnb.arch.delegate;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;

import com.yyxnb.widget.interfaces.ILifecycle;

/**
 * @author yyx
 */
public interface IActivityDelegate extends ILifecycle {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(Bundle savedInstanceState);

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop();

    void onSaveInstanceState(Activity activity, Bundle outState);

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

}
