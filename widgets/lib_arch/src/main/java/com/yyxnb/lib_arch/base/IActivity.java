package com.yyxnb.lib_arch.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.yyxnb.lib_arch.annotations.SwipeStyle;
import com.yyxnb.lib_arch.common.AppManager;
import com.yyxnb.lib_arch.delegate.ActivityDelegate;


/**
 * Activity 需实现
 *
 * @author yyx
 */
public interface IActivity extends IView {

    /**
     * Activity代理
     *
     * @return ActivityDelegate
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    default ActivityDelegate getBaseDelegate() {
        return AppManager.getInstance().getActivityDelegate(this, hashCode());
    }

    /**
     * 初始化窗口
     */
    default void initWindows() {
    }

    /**
     * 侧滑返回
     *
     * @param mSwipeBack 是否返回 {@link SwipeStyle}
     */
    default void setSwipeBack(@SwipeStyle int mSwipeBack) {

    }

}
