package com.yyxnb.arch.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.yyxnb.arch.annotations.SwipeStyle
import com.yyxnb.arch.delegate.ActivityDelegate
import com.yyxnb.arch.utils.AppManager

/**
 * Activity 需实现
 *
 * @author yyx
 */
interface IActivity : IView {

    /**
     * Activity代理
     *
     * @return ActivityDelegate
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getBaseDelegate(): ActivityDelegate = AppManager.getActivityDelegate(this, hashCode())

    /**
     * 初始化窗口
     */
    fun initWindows() {}

    /**
     * 侧滑返回
     *
     * @param mSwipeBack 是否返回 [SwipeStyle]
     */
    fun setSwipeBack(@SwipeStyle mSwipeBack: Int) {}
}