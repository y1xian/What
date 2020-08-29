package com.yyxnb.rxtool.subsciber;

import com.yyxnb.rxtool.exception.RxException;

/**
 * 简单的订阅者
 */
public abstract class SimpleSubscriber<T> extends BaseSubscriber<T> {
    /**
     * 出错
     *
     * @param e
     */
    @Override
    public void onError(RxException e) {
//        RxLog.e(e);
    }
}