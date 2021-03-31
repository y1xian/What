package com.yyxnb.module_widget.ui.function;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_widget.R;
import com.yyxnb.module_widget.databinding.FragmentWidgetQueueBinding;
import com.yyxnb.module_widget.widget.PrintTask;
import com.yyxnb.what.task.IdleTaskManager;
import com.yyxnb.what.task.OrderTaskManager;
import com.yyxnb.what.task.TaskManager;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/12/18
 * 描    述：有序、无序、闲置队列
 * ================================================
 */
public class WidgetQueueFragment extends BaseFragment {

    private FragmentWidgetQueueBinding binding;
    private int type;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_widget_queue;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
    }

    @SuppressLint("NewApi")
    @Override
    public void initViewData() {
        type = initArguments().getInt("type", 1);
        log("" + type);
        switch (type) {
            case 1:
                // 优先级队列
                initPriorityQueue();
                break;
            case 2:
                // 普通队列
                initQueue();
                break;
            default:
                // 闲置队列
                initIdleQueue();
                break;
        }

    }

    private void initIdleQueue() {
        IdleTaskManager manager = new IdleTaskManager();
        for (int i = 0; i < 10; i++) {
            PrintTask task = new PrintTask(i);
            manager.addTask(task);
        }
        manager.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initQueue() {
        TaskManager manager = new TaskManager(getContext());
        for (int i = 0; i < 10; i++) {
            PrintTask task = new PrintTask(i);
            manager.addTask(task);
        }
        manager.start();
//        manager.startLock();
    }

    private void initPriorityQueue() {
        OrderTaskManager manager = new OrderTaskManager(1);
        for (int i = 0; i < 10; i++) {
            PrintTask task = new PrintTask(i);
            manager.add(task);
        }
        manager.start();
    }

    @Override
    public void onDestroy() {
        TaskManager.getInstance(getContext()).cancel();
        super.onDestroy();
    }
}