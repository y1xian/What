package com.yyxnb.common_base.event;

import androidx.lifecycle.LifecycleOwner;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/19
 * 描    述：提供观察状态事件
 * ================================================
 */
public class StatusEvent extends SingleLiveEvent<StatusEvent.HttpStatus> {

    public void observe(LifecycleOwner owner, final StatusEvent.StatusObserver observer) {
        super.observe(owner, t -> {
            if (t != null) {
                observer.onStatusChanged(t);
            }
        });
    }

    public interface StatusObserver {
        void onStatusChanged(HttpStatus status);
    }

    /**
     * 状态
     */
    public enum HttpStatus {
        LOADING,
        SUCCESS,
        FAILURE,
        ERROR
    }
}
