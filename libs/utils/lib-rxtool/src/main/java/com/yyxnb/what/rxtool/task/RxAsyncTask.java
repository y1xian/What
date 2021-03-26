package com.yyxnb.what.rxtool.task;

/**
 * 通用的Rx异步执行任务，在io线程中进行数据处理，在ui线程中刷新ui
 */
public abstract class RxAsyncTask<T, R> implements IRxIOTask<T, R>, IRxUITask<R> {

    /**
     * IO执行任务的入参
     */
    private T InData;

    /**
     * IO执行任务的出参,UI执行任务的入参
     */
    private R OutData;

    public RxAsyncTask(T inData) {
        InData = inData;
    }

    public T getInData() {
        return InData;
    }

    public RxAsyncTask setInData(T inData) {
        InData = inData;
        return this;
    }

    public R getOutData() {
        return OutData;
    }

    public RxAsyncTask setOutData(R outData) {
        OutData = outData;
        return this;
    }
}