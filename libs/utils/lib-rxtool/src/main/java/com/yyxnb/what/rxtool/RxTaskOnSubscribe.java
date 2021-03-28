package com.yyxnb.what.rxtool;

import io.reactivex.FlowableOnSubscribe;

/**
 * 在订阅时执行的回调
 */
public abstract class RxTaskOnSubscribe<T> implements FlowableOnSubscribe<T> {
    /**
     * 在订阅时执行的任务
     */
    private T mTask;

    public RxTaskOnSubscribe(T task) {
        mTask = task;
    }

    public T getTask() {
        return mTask;
    }

    public RxTaskOnSubscribe setTask(T task) {
        mTask = task;
        return this;
    }

}