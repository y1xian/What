package com.yyxnb.what.rxtool.subsciber;

import com.yyxnb.what.rxtool.exception.RxException;
import com.yyxnb.what.rxtool.exception.RxExceptionHandler;

import io.reactivex.observers.DisposableObserver;

/**
 * 基础订阅者
 */
public abstract class BaseSubscriber<T> extends DisposableObserver<T> {

    public BaseSubscriber() {

    }

    @Override
    protected void onStart() {
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onNext(T t) {
        try {
            onSuccess(t);
        } catch (Throwable e) {
            e.printStackTrace();
            onError(e);
        }
    }

    @Override
    public final void onError(Throwable e) {
        try {
            if (e instanceof RxException) {
                onError((RxException) e);
            } else {
                onError(RxExceptionHandler.handleException(e));
            }
        } catch (Throwable throwable) {
            //防止onError中执行又报错导致rx.exceptions.OnErrorFailedException: Error occurred when trying to propagate error to Observer.onError问题
            e.printStackTrace();
        }
    }

    /**
     * 出错
     *
     * @param e
     */
    public abstract void onError(RxException e);

    /**
     * 安全版的{@link #onNext},自动做了try-catch
     *
     * @param t
     */
    public abstract void onSuccess(T t);

}