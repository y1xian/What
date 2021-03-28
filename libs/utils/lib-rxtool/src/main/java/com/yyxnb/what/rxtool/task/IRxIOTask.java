package com.yyxnb.what.rxtool.task;

/**
 * 在IO线程中执行的任务
 */
public interface IRxIOTask<T, R> {

    /**
     * 在IO线程中执行
     *
     * @param t 任务执行的入参
     * @return R 任务执行的出参
     */
    R doInIOThread(T t);
}