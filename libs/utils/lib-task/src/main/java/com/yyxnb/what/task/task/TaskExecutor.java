package com.yyxnb.what.task.task;

import java.util.concurrent.BlockingQueue;

// 窗口
public class TaskExecutor extends Thread {

    // 队列。
    private BlockingQueue<ITask> taskQueue;

    // 是否在等待着。
    private boolean isRunning = true;

    public TaskExecutor(BlockingQueue<ITask> taskQueue) {
        this.taskQueue = taskQueue;
    }

    // 中断此线程。
    public void quit() {
        isRunning = false;
        interrupt();
    }

    @Override
    public void run() {
        // 等待状态就待着。
        while (isRunning) {
            ITask iTask;
            try {
                // 叫下一个，没有就等着。
                iTask = taskQueue.take();
            } catch (InterruptedException e) {
                if (!isRunning) {
                    // 发生意外了，关闭。
                    interrupt();
                    break; // 如果执行到break，后面的代码就无效了。
                }
                // 不是中断状态，那么继续等待。
                continue;
            }

            // 执行。
            iTask.run();
        }
    }
}