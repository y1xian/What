package com.yyxnb.what.rxtool.task;

import androidx.annotation.NonNull;

/**
 * 通用的遍历数组或者集合的异步任务，在io线程中进行数据处理，在ui线程中刷新ui
 */
public abstract class RxIteratorTask<T, R> implements IRxIOTask<T, R>, IRxUITask<R> {

    /**
     * 遍历的是否是数组
     */
    private boolean mIsArray;
    /**
     * IO执行任务的入参, 遍历的集合
     */
    private Iterable<T> Iterable;

    /**
     * IO执行任务的入参, 遍历的数组
     */
    private T[] Array;

    public RxIteratorTask(@NonNull Iterable<T> iterable) {
        Iterable = iterable;
        mIsArray = false;
    }

    public RxIteratorTask(@NonNull T[] array) {
        Array = array;
        mIsArray = true;
    }


    public Iterable<T> getIterable() {
        return Iterable;
    }

    public RxIteratorTask setIterable(Iterable<T> iterable) {
        Iterable = iterable;
        return this;
    }

    public T[] getArray() {
        return Array;
    }

    public RxIteratorTask setArray(T[] array) {
        Array = array;
        return this;
    }

    public boolean isArray() {
        return mIsArray;
    }
}