package com.yyxnb.arch.delegate

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.yyxnb.common.interfaces.ILifecycle

/**
 * @author yyx
 */
interface IActivityDelegate : ILifecycle {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(savedInstanceState: Bundle?)

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop()

    fun onSaveInstanceState(activity: Activity?, outState: Bundle?)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()
}