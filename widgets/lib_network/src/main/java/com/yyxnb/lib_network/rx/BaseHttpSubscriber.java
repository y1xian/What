package com.yyxnb.lib_network.rx;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.yyxnb.lib_common.interfaces.IData;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * https://blog.csdn.net/longlonghaohao/java/article/details/103975307
 *
 * @param <T>
 */
public class BaseHttpSubscriber<T extends IData> implements Subscriber<T> {

    //异常类
    private ApiException ex;

    private Subscription subscription;

    public BaseHttpSubscriber() {
        data = new MediatorLiveData<>();
    }

    private MediatorLiveData<T> data;

    public MutableLiveData<T> get() {
        return data;
    }

    public void set(T t) {
        this.data.setValue(t);
    }

    public void onFinish(T t) {
        if (t != null){
            set(t);
        }
    }

    @Override
    public void onSubscribe(Subscription s) {
        subscription = s;
        // 观察者接收事件 = 1个
        subscription.request(1);
    }

    @Override
    public void onNext(T t) {
        if (t.isSuccess()) {
            onFinish(t);
        } else {
            ex = ExceptionEngine.handleException(new ServerException(t.getCode(), t.getMsg()));
            getErrorDto(t, ex);
        }

    }

    @Override
    public void onError(Throwable t) {
        ex = ExceptionEngine.handleException(t);
        getErrorDto(null, ex);
        Log.d("BaseHttpSubscriber", "onError " + ex.getStatusDesc());
    }

    /**
     * 初始化错误的dto
     *
     * @param ex
     */
    private void getErrorDto(T t, ApiException ex) {
        try {
            onFinish(null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            subscription.cancel();
            Log.d("BaseHttpSubscriber", "getErrorDto " + ex.getStatusDesc());
        }
    }

    @Override
    public void onComplete() {
        subscription.cancel();
    }

}