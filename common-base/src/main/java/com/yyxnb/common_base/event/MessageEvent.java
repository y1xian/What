package com.yyxnb.common_base.event;

import androidx.lifecycle.LifecycleOwner;

import cn.hutool.core.util.ObjectUtil;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/19
 * 描    述：提供观察消息事件
 * ================================================
 */
public class MessageEvent extends SingleLiveEvent<String> {

    public void observe(LifecycleOwner owner, final MessageObserver observer) {
        super.observe(owner, t -> {
            if (ObjectUtil.isNotNull(t)) {
                observer.onMessage(t);
            }
        });
    }

    public interface MessageObserver {
        void onMessage(String message);
    }
}
