package com.yyxnb.what.arch.base;

import android.os.Bundle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.yyxnb.what.arch.config.AppManager;
import com.yyxnb.what.arch.delegate.FragmentDelegate;

import java.util.UUID;

/**
 * Fragment 需实现
 *
 * @author yyx
 */
public interface IFragment extends IView {

    /**
     * Fragment 代理
     *
     * @return FragmentDelegate
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    default FragmentDelegate getBaseDelegate() {
        return AppManager.getInstance().getFragmentDelegate(this, hashCode());
    }

    /**
     * 用户可见时候调用
     */
    default void onVisible() {
    }

    /**
     * 用户不可见时候调用
     */
    default void onInVisible() {
    }

    /**
     * 传值
     *
     * @return Bundle
     */
    default Bundle initArguments() {
        return null;
    }

    /**
     * id
     *
     * @return id
     */
    default String sceneId() {
        return UUID.randomUUID().toString();
    }

}
