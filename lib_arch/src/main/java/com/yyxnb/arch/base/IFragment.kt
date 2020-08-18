package com.yyxnb.arch.base

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.yyxnb.arch.delegate.FragmentDelegate
import com.yyxnb.arch.utils.AppManager

/**
 * Fragment 需实现
 *
 * @author yyx
 */
interface IFragment : IView {

    /**
     * Fragment 代理
     *
     * @return FragmentDelegate
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getBaseDelegate(): FragmentDelegate = AppManager.getFragmentDelegate(this, hashCode())

    /**
     * 用户可见时候调用
     */
    fun onVisible() {}

    /**
     * 用户不可见时候调用
     */
    fun onInVisible() {}

    /**
     * 传值
     *
     * @return Bundle
     */
    fun initArguments(): Bundle? = null

    /**
     * id
     *
     * @return id
     */
    fun sceneId(): String? = null
}