package com.yyxnb.what.rxtool.task;

/**
 * 在UI线程中操作的任务
 */
public interface IRxUITask<T> {

    /**
     * 在UI线程中执行
     *
     * @param t 任务执行的入参
     */
    void doInUIThread(T t);
}