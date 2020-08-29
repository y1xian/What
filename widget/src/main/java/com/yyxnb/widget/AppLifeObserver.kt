package com.yyxnb.widget

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * 监听整个应用应用状态，与Activity数量无关
 */
object AppLifeObserver : LifecycleObserver {
    /**
     * ON_CREATE 在应用程序的整个生命周期中只会被调用一次
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.w("-AppLifeObserver-", "应用首次创建")
    }

    /**
     * 应用程序出现到前台时调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.w("-AppLifeObserver-", "应用进入前台")
    }

    /**
     * 应用程序退出到后台时调用
     * 会有一定的延后，因为系统需要为屏幕旋转，配置发生变化导致Activity重新创建预留一些时间
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.e("-AppLifeObserver-", "应用进入后台")
    }

    /**
     * 永远不会被调用到，系统不会分发调用ON_DESTROY事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.e("-AppLifeObserver-", "ON_DESTROY")
    }
}