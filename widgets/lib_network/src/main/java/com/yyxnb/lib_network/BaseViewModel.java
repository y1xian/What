package com.yyxnb.lib_network;

import android.content.Context;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yyxnb.lib_common.action.CommonAction;
import com.yyxnb.lib_common.interfaces.IData;
import com.yyxnb.lib_network.rx.BaseHttpSubscriber;
import com.yyxnb.lib_network.rx.RetryWithDelay;
import com.yyxnb.util_app.AppUtils;
import com.yyxnb.util_rxtool.DisposablePool;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("rawtypes")
@Deprecated
public abstract class BaseViewModel extends ViewModel implements DefaultLifecycleObserver, CommonAction {

    public final MutableLiveData<Status> status = new MutableLiveData<>();
    private final List<Integer> mDisposable = new ArrayList<>();

    @Override
    public Context getContext() {
        return AppUtils.getApp();
    }

    public <T extends IData> BaseHttpSubscriber<T> request(Flowable<T> flowable) {
        final BaseHttpSubscriber<T> baseHttpSubscriber = new BaseHttpSubscriber<T>();
        //RxJava Subscriber回调
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseHttpSubscriber);
        return baseHttpSubscriber;
    }

    public <T extends IData> LiveData<T> request(Observable<T> observable) {
        return new ObservableLiveData<>(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).retryWhen(new RetryWithDelay()));
    }

    public <T extends IData> void launchOnlyResult(
            Observable<T> call,
            OnHandleException<T> onHandleException
    ) {
        status.postValue(Status.LOADING);
        call.subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(call.hashCode());
                        DisposablePool.get().add(d, call.hashCode());
                    }

                    @Override
                    public void onNext(T t) {
                        if (t.isSuccess()) {
                            status.postValue(Status.SUCCESS);
                            onHandleException.success(t);
                        } else {
                            status.postValue(Status.ERROR);
                            onHandleException.error(t.getCode());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        status.postValue(Status.ERROR);
                        onHandleException.error(e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        status.postValue(Status.COMPLETE);
                    }
                });
    }

    public interface OnHandleException<T> {

        void success(T data);

        void error(String msg);

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        for (Integer i : mDisposable) {
            DisposablePool.get().remove(i);
        }
        mDisposable.clear();
    }
}
