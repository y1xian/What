package com.yyxnb.arch.delegate

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.yyxnb.common.interfaces.ILifecycle

/**
 * @author yyx
 */
interface IFragmentDelegate : ILifecycle {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onAttached(context: Context?)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated(savedInstanceState: Bundle?)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onViewCreated(view: View?, savedInstanceState: Bundle?)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onActivityCreated(savedInstanceState: Bundle?)

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStarted()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumed()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPaused()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopped()

    fun onSaveInstanceState(outState: Bundle?)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onViewDestroyed()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyed()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDetached()

    val isAdd: Boolean
}