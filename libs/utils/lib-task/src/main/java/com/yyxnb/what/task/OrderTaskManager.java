package com.yyxnb.what.task;


import com.yyxnb.what.task.task.ITask;
import com.yyxnb.what.task.task.TaskExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/12/22
 * 描    述：有序队列
 * ================================================
 */
public class OrderTaskManager {

    private AtomicInteger mAtomicInteger = new AtomicInteger();
    // 队列。
    private BlockingQueue<ITask> mTaskQueue;
    // 好多窗口。
    private TaskExecutor[] mTaskExecutors;

    // 在开发者new队列的时候，要指定数量。
    public OrderTaskManager(int size) {
        mTaskQueue = new PriorityBlockingQueue<>();
        mTaskExecutors = new TaskExecutor[size];
    }

    // 开始。
    public void start() {
        stop();
        for (int i = 0; i < mTaskExecutors.length; i++) {
            mTaskExecutors[i] = new TaskExecutor(mTaskQueue);
            mTaskExecutors[i].start();
        }
    }

    // 统一关闭。
    public void stop() {
        if (mTaskExecutors != null) {
            for (TaskExecutor taskExecutor : mTaskExecutors) {
                if (taskExecutor != null) {
                    taskExecutor.quit();
                }
            }
        }
    }

    // 添加。
    public <T extends ITask> int add(T task) {
        if (!mTaskQueue.contains(task)) {
            task.setSequence(mAtomicInteger.incrementAndGet()); // 注意这行。
            mTaskQueue.add(task);
        }
        // 返回排的队的人数
        return mTaskQueue.size();
    }
}