package com.yyxnb.network;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.yyxnb.common.action.CommonAction;
import com.yyxnb.widget.WidgetManager;
import com.yyxnb.widget.interfaces.IData;
import com.yyxnb.network.rx.BaseHttpSubscriber;
import com.yyxnb.network.rx.RetryWithDelay;
import com.yyxnb.rxtool.DisposablePool;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("rawtypes")
public abstract class BaseViewModel extends ViewModel implements DefaultLifecycleObserver, CommonAction {

    public final MutableLiveData<Status> status = new MutableLiveData<>();
    private final List<Integer> mDisposable = new ArrayList<>();

    @Override
    public Context getContext() {
        return WidgetManager.getInstance().getContext();
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
