package com.yyxnb.arch.interfaces

import android.os.Bundle

/**
 * 懒加载接口
 * @author yyx
 */
interface ILazyOwner {

    /**
     * 初始化控件
     */
    fun initView(savedInstanceState: Bundle?)

    /**
     * 初始化复杂数据 懒加载
     */
    fun initViewData()

    /**
     * 用户可见时候调用
     */
    fun onVisible()

    /**
     * 用户不可见时候调用
     */
    fun onInVisible()

}