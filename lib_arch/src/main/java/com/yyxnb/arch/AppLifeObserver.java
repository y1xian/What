package com.yyxnb.arch;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.yyxnb.common.AppConfig;

/**
 * 监听整个应用应用状态，与Activity数量无关
 */
public class AppLifeObserver implements LifecycleObserver {

    /**
     * ON_CREATE 在应用程序的整个生命周期中只会被调用一次
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        AppConfig.getInstance().log("应用首次创建");
    }

    /**
     * 应用程序出现到前台时调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart() {
        AppConfig.getInstance().log("应用进入前台");
    }

    /**
     * 应用程序退出到后台时调用
     * 会有一定的延后，因为系统需要为屏幕旋转，配置发生变化导致Activity重新创建预留一些时间
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
        AppConfig.getInstance().log("应用进入后台");
    }

    /**
     * 永远不会被调用到，系统不会分发调用ON_DESTROY事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        AppConfig.getInstance().log("ON_DESTROY");
    }
}
