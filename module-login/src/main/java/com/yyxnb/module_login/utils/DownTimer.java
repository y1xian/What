package com.yyxnb.module_login.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * DownTimer
 * 倒计时工具类
 * <b>1.实例化后必须设置倒计时的总时间（totalTime）和每隔多久时间（intervalTime）回调</b><br>
 * <b>2.有start()、 cancel()、 pause()、resume() 四个方法看方法就知道它的意思了 </b>
 */
public class DownTimer implements LifecycleObserver {
    // 总时长
    private long totalTime = 60 * 1000;
    // 间隔
    private long intervalTime = 1000;
    // 记录
    private long remainTime;
    private long systemAddTotalTime;
    private static final int TIME = 1;
    private TimeListener listener;
    private long curReminTime;
    private boolean isPause = false;

    public DownTimer(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    public void setIntervalTime(long intervalTime) {
        this.intervalTime = intervalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public long getIntervalTime() {
        return intervalTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void start() {
        if (totalTime <= 0 && intervalTime <= 0) {
            throw new RuntimeException("you must set the totalTime > 0 or intervalTime >0");
        }

        systemAddTotalTime = SystemClock.elapsedRealtime() + totalTime;

        mHandler.sendEmptyMessage(TIME);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void cancel() {
        mHandler.removeMessages(TIME);
        mHandler.removeCallbacksAndMessages(this);
    }

    //    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void pause() {
        mHandler.removeMessages(TIME);
        isPause = true;
        curReminTime = remainTime;

    }

    //    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume() {
        if (isPause) {
            isPause = false;
            totalTime = curReminTime;
            start();
        }

    }

    private final Handler mHandler = new Handler() {

        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME:
                    if (!isPause) {
                        soloveTime();
                    }
                    break;
                case 2:
                    isPause = true;
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * SystemClock.elapsedRealtime()计算某个时间经历了多长时间有意义，例如通话经历了多长时间，这个值是系统设置无关
     */
    private void soloveTime() {
        remainTime = systemAddTotalTime - SystemClock.elapsedRealtime();
        if (remainTime <= 0) {
            if (listener != null) {
                listener.onFinish();
                cancel();
            }
        } else if (remainTime < intervalTime) {
            mHandler.sendEmptyMessageDelayed(TIME, remainTime);
        } else {
            long curSystemTime = SystemClock.elapsedRealtime();
            if (listener != null) {
                listener.onInterval(remainTime);
            }

            long delay = curSystemTime + intervalTime - SystemClock.elapsedRealtime();

            while (delay < 0) {
                delay += intervalTime;
            }

            mHandler.sendEmptyMessageDelayed(TIME, delay);
        }
    }

    public interface TimeListener {
        public void onFinish();

        public void onInterval(long remainTime);
    }

    public void setTimerListener(TimeListener listener) {
        this.listener = listener;
    }

}