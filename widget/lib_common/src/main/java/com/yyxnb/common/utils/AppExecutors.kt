package com.yyxnb.common.utils

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.concurrent.*

/**
 * 线程池
 */
class AppExecutors @JvmOverloads constructor(
        /**
         * 磁盘IO线程池
         */
        private val diskIO: ExecutorService = diskIoExecutor(),
        /**
         * 网络IO线程池
         */
        private val networkIO: ExecutorService = networkExecutor(),
        /**
         * UI线程
         */
        private val mainThread: Executor = MainThreadExecutor(),
        /**
         * 定时任务线程池
         */
        private val scheduledExecutor: ScheduledExecutorService = scheduledThreadPoolExecutor()) {

    /**
     * 定时(延时)任务线程池
     * 替代Timer,执行定时任务,延时任务
     */
    fun scheduledExecutor(): ScheduledExecutorService {
        return scheduledExecutor
    }

    /**
     * 磁盘IO线程池（单线程）
     * 和磁盘操作有关的进行使用此线程(如读写数据库,读写文件)
     * 禁止延迟,避免等待
     * 此线程不用考虑同步问题
     */
    fun diskIO(): ExecutorService {
        return diskIO
    }

    /**
     * 网络IO线程池
     * 网络请求,异步任务等适用此线程
     * 不建议在这个线程 sleep 或者 wait
     */
    fun networkIO(): ExecutorService {
        return networkIO
    }

    /**
     * UI线程
     * Android 的MainThread
     * UI线程不能做的事情这个都不能做
     */
    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {
        private const val TAG = "AppExecutors"

        @Volatile
        private var appExecutors: AppExecutors? = null
        val instance: AppExecutors?
            get() {
                if (appExecutors == null) {
                    synchronized(AppExecutors::class.java) {
                        if (appExecutors == null) {
                            appExecutors = AppExecutors()
                        }
                    }
                }
                return appExecutors
            }

        private fun scheduledThreadPoolExecutor(): ScheduledExecutorService {
            return ScheduledThreadPoolExecutor(16, ThreadFactory { r: Runnable? -> Thread(r, "scheduled_executor") }, RejectedExecutionHandler { r: Runnable?, executor: ThreadPoolExecutor? -> Log.e(TAG, "rejectedExecution: scheduled executor queue overflow") })
        }

        private fun diskIoExecutor(): ExecutorService {
            return ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, LinkedBlockingQueue(1024), ThreadFactory { r: Runnable? -> Thread(r, "disk_executor") }, RejectedExecutionHandler { r: Runnable?, executor: ThreadPoolExecutor? -> Log.e(TAG, "rejectedExecution: disk io executor queue overflow") })
        }

        private fun networkExecutor(): ExecutorService {
            return ThreadPoolExecutor(3, 6, 1000, TimeUnit.MILLISECONDS, LinkedBlockingQueue(6), ThreadFactory { r: Runnable? -> Thread(r, "network_executor") }, RejectedExecutionHandler { r: Runnable?, executor: ThreadPoolExecutor? -> Log.e(TAG, "rejectedExecution: network executor queue overflow") })
        }
    }

}