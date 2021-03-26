package com.yyxnb.what.task;

import android.os.Looper;
import android.os.MessageQueue;

import com.yyxnb.what.task.task.Task;
import com.yyxnb.what.task.task.TaskRunnable;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/12/21
 * 描    述：CPU空闲时间进行处理耗时操作,可以在CPU空闲时间进行处理耗时操作，加入的任务是谁有空执行谁
 * ================================================
 */
public class IdleTaskManager {

    private Queue<Task> ildeTaskQueue = new LinkedList<>();

    private MessageQueue.IdleHandler idleHandler = () -> {

        if (ildeTaskQueue.size() > 0) {
            // 如果CPU空闲了，
            Task idleTask = ildeTaskQueue.poll();
            new TaskRunnable(idleTask).run();
        }
        // 如果返回false，则移除该 IldeHandler
        return !ildeTaskQueue.isEmpty();
    };

    public IdleTaskManager addTask(Task task) {
        ildeTaskQueue.add(task);
        return this;
    }

    /**
     * 执行空闲方法，因为用了DispatchRunnable，所以会优先处理需要依赖的task，再处理本次需要处理的task，顺序执行
     */
    public void start() {
        Looper.myQueue().addIdleHandler(idleHandler);
    }
}
