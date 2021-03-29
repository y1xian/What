package com.yyxnb.what.arch.base;

import android.os.Bundle;

import com.yyxnb.what.core.interfaces.ILifecycle;

/**
 * Activity 、Fragment
 *
 * @author yyx
 */
public interface IView extends ILifecycle {

    /**
     * 初始化布局
     */
    default int initLayoutResId() {
        return 0;
    }

    /**
     * 初始化控制、监听等轻量级操作
     */
    void initView(Bundle savedInstanceState);

    /**
     * 处理重量级数据、逻辑
     */
    default void initViewData() {
    }

    /**
     * 初始化界面观察者的监听
     * 接收数据结果
     */
    default void initObservable() {
    }
}
