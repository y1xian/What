package com.yyxnb.util_rxtool;

import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * RxJava订阅池
 */
public class DisposablePool {

    private static volatile DisposablePool sInstance;

    //===================================订阅管理=======================================//
    /**
     * RxJava订阅池，管理Subscribers订阅，防止内存泄漏
     */
    private ConcurrentHashMap<Object, CompositeDisposable> maps = new ConcurrentHashMap<>();

    private DisposablePool() {

    }

    /**
     * 获取订阅池
     *
     * @return
     */
    public static DisposablePool get() {
        if (sInstance == null) {
            synchronized (DisposablePool.class) {
                if (sInstance == null) {
                    sInstance = new DisposablePool();
                }
            }
        }
        return sInstance;
    }


    /**
     * 根据tagName管理订阅【注册订阅信息】
     *
     * @param tagName    标志
     * @param disposable 订阅信息
     */
    public Disposable add(@NonNull Object tagName, Disposable disposable) {
        return add(disposable, tagName);
    }

    /**
     * 根据tagName管理订阅【注册订阅信息】
     *
     * @param disposable 订阅信息
     * @param tagName    标志
     */
    public Disposable add(Disposable disposable, @NonNull Object tagName) {
        /* 订阅管理 */
        CompositeDisposable compositeDisposable = maps.get(tagName);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            maps.put(tagName, compositeDisposable);
        }
        compositeDisposable.add(disposable);
        return disposable;
    }

    /**
     * 取消订阅【取消标志内所有订阅信息】
     *
     * @param tagName 标志
     */
    public void remove(@NonNull Object tagName) {
        CompositeDisposable compositeDisposable = maps.get(tagName);
        if (compositeDisposable != null) {
            compositeDisposable.dispose(); //取消订阅
            maps.remove(tagName);
        }
    }

    /**
     * 取消订阅【单个订阅取消】
     *
     * @param tagName    标志
     * @param disposable 订阅信息
     */
    public void remove(@NonNull Object tagName, Disposable disposable) {
        CompositeDisposable compositeDisposable = maps.get(tagName);
        if (compositeDisposable != null) {
            compositeDisposable.remove(disposable);
            if (compositeDisposable.size() == 0) {
                maps.remove(tagName);
            }
        }
    }

    /**
     * 取消所有订阅
     */
    public void removeAll() {
        Iterator<Map.Entry<Object, CompositeDisposable>> it = maps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, CompositeDisposable> entry = it.next();
            CompositeDisposable compositeDisposable = entry.getValue();
            if (compositeDisposable != null) {
                compositeDisposable.dispose(); //取消订阅
                it.remove();
            }
        }
        maps.clear();
    }


    /**
     * 取消订阅
     *
     * @param disposable 订阅信息
     */
    public static void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}