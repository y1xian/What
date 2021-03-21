package com.yyxnb.module_widget.widget;

import android.util.Log;

import com.yyxnb.what.task.task.Task;

public class PrintTask extends Task {

    private int id;

    public PrintTask(int id) {
        this.id = id;
    }

    @Override
    public boolean needWait() {
        return true;
    }

    @Override
    public void run() {
        try {
//           if (id % 2 == 0){
//               Thread.sleep(2000);
//           }else {
            Thread.sleep(500);
//           }
        } catch (Exception ignored) {
        }
        Log.d("PrintTask", "我的id是：" + id);
    }
}
