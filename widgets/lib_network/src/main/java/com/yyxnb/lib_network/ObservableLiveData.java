package com.yyxnb.lib_network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.yyxnb.lib_common.interfaces.IData;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@SuppressWarnings("ALL")
class ObservableLiveData<T extends IData> extends LiveData<T> {
    private WeakReference<Disposable> mDisposableRef;
    private final Observable<T> mObservable;
    private final Object mLock = new Object();

    ObservableLiveData(@NonNull final Observable<T> observable) {
        mObservable = observable;
    }

    @Override
    protected void onActive() {
        super.onActive();

        mObservable.subscribe(new Observer<T>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                // Don't worry about backpressure. If the stream is too noisy then
                // backpressure can be handled upstream.
                synchronized (mLock) {
                    mDisposableRef = new WeakReference<>(d);
                }
            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull T t) {
                if (t.isSuccess()) {
                    postValue(t);
                }
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable t) {
                synchronized (mLock) {
                    mDisposableRef = null;
                }
                // Errors should be handled upstream, so propagate as a crash.
//                throw new RuntimeException(t);
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                synchronized (mLock) {
                    mDisposableRef = null;
                }
            }
        });

    }

    @Override
    protected void onInactive() {
        super.onInactive();
        synchronized (mLock) {
            WeakReference<Disposable> subscriptionRef = mDisposableRef;
            if (subscriptionRef != null) {
                Disposable subscription = subscriptionRef.get();
                if (subscription != null) {
                    subscription.dispose();
                }
                mDisposableRef = null;
            }
        }
    }
}