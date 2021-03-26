package com.yyxnb.what.core.callback;

public interface ResultCallback<T> {

    void onStarted();

    void onSuccess(T result);

    void onError(Throwable ex);
}
