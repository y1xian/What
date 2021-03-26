package com.yyxnb.what.task.task;

import android.os.Process;

import com.yyxnb.what.task.utils.DispatcherExecutor;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public abstract class Task implements ITask {

    // 当前Task依赖的Task数量（需要等待被依赖的Task执行完毕才能执行自己），默认没有依赖
    private CountDownLatch taskCountDownLatch = new CountDownLatch(dependentArr() == null ? 0 : dependentArr().size());
    private int sequence;

    @Override
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public int getSequence() {
        return sequence;
    }

    /**
     * 当前Task等待，让依赖的Task先执行
     */
    @Override
    public void startLock() {
        try {
            taskCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 依赖的Task执行完一个
     */
    @Override
    public void unlock() {
        taskCountDownLatch.countDown();
    }

    /**
     * Task的优先级，运行在主线程则不要去改优先级(值越大，进程的优先级就越低，获得CPU调用的机会越少,一个值为-20的进程优先级最高，值为19的进程优先级最低)
     * THREAD_PRIORITY_DEFAULT，默认的线程优先级，值为0。
     * THREAD_PRIORITY_LOWEST，最低的线程级别，值为19。
     * THREAD_PRIORITY_BACKGROUND 后台线程建议设置这个优先级，值为10。
     */
    @Override
    public int priority() {
        return Process.THREAD_PRIORITY_BACKGROUND;
    }

    /**
     * Task执行在哪个线程池，默认在IO的线程池；
     */
    @Override
    public ExecutorService runOnExecutor() {
        return DispatcherExecutor.getIOExecutor();
    }

    /**
     * 异步线程执行的Task是否需要在被调用await的时候等待，默认不需要
     */
    @Override
    public boolean needWait() {
        return false;
    }

    /**
     * 当前Task依赖的Task集合（需要等待被依赖的Task执行完毕才能执行自己），默认没有依赖
     */
    @Override
    public List<Class<? extends ITask>> dependentArr() {
        return null;
    }

    /**
     * 是否在主线程执行
     */
    @Override
    public boolean runOnMainThread() {
        return false;
    }

    /**
     * Task主任务执行完成之后需要执行的任务
     */
    @Override
    public Runnable getTailRunnable() {
        return null;
    }

    /**
     * 需要提升自己优先级的，先执行（这个先是相对于没有依赖的先）
     */
    public boolean needRunAsSoon() {
        return false;
    }

    @Override
    public int compareTo(ITask o) {
        final int me = this.priority();
        final int it = o.priority();
        return me == it ? this.getSequence() - o.getSequence() : me - it;
    }
}
