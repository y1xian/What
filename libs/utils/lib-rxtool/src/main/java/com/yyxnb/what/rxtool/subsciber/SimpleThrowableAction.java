package com.yyxnb.what.rxtool.subsciber;

import android.util.Log;

import io.reactivex.functions.Consumer;

/**
 * 简单的出错处理（把错误打印出来）
 */
public final class SimpleThrowableAction implements Consumer<Throwable> {

    private String mTag;

    public SimpleThrowableAction(String tag) {
        mTag = tag;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        Log.e(mTag, "订阅发生错误！", throwable);
    }
}