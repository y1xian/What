package com.yyxnb.util_rxtool;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.AdapterViewItemClickEvent;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.yyxnb.util_rxtool.subsciber.SimpleThrowableAction;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * RxBinding工具
 */
public final class RxBindingUtils {

    private final static String TAG = "RxBindingUtils";

    private RxBindingUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    //========================点击事件=============================//

    /**
     * 自定义控件监听
     *
     * @param v 监听控件
     * @return
     */
    public static Observable<Object> setViewClicks(View v) {
        return setViewClicks(v, 1, TimeUnit.SECONDS);
    }

    /**
     * 自定义控件监听
     *
     * @param v        监听控件
     * @param duration 点击时间间隔
     * @param unit     时间间隔单位
     * @return
     */
    public static Observable<Object> setViewClicks(View v, long duration, TimeUnit unit) {
        return RxView.clicks(v)
                .compose(RxOperationUtils._throttleFirst(duration, unit))
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 简单的控件点击监听
     *
     * @param v       监听控件
     * @param action1 监听事件
     * @return
     */
    public static Disposable setViewClicks(View v, Consumer<Object> action1) {
        return setViewClicks(v, 1, TimeUnit.SECONDS, action1, new SimpleThrowableAction(TAG));
    }

    /**
     * 简单的控件点击监听
     *
     * @param v        监听控件
     * @param duration 点击时间间隔
     * @param unit     时间间隔单位
     * @param action1  监听事件
     * @return
     */
    public static Disposable setViewClicks(View v, long duration, TimeUnit unit, Consumer<Object> action1) {
        return setViewClicks(v, duration, unit, action1, new SimpleThrowableAction(TAG));
    }

    /**
     * 简单的控件点击监听
     *
     * @param v           监听控件
     * @param duration    点击时间间隔
     * @param unit        时间间隔单位
     * @param action1     监听事件
     * @param errorAction 出错的事件
     * @return
     */
    public static Disposable setViewClicks(View v, long duration, TimeUnit unit, Consumer<Object> action1, Consumer<Throwable> errorAction) {
        return RxView.clicks(v)
                .compose(RxOperationUtils._throttleFirst(duration, unit))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, errorAction);
    }

    /**
     * AdapterView控件点击监听
     *
     * @param view    监听控件
     * @param action1 条目点击监听事件
     */
    public static Disposable setItemClicks(AdapterView<?> view, Consumer<AdapterViewItemClickEvent> action1) {
        return setItemClicks(view, 1, TimeUnit.SECONDS, action1, new SimpleThrowableAction(TAG));
    }

    /**
     * AdapterView控件点击监听
     *
     * @param view        监听控件
     * @param action1     条目点击监听事件
     * @param errorAction 出错的事件
     */
    public static Disposable setItemClicks(AdapterView<?> view, long duration, TimeUnit unit, Consumer<AdapterViewItemClickEvent> action1, Consumer<Throwable> errorAction) {
        return RxAdapterView.itemClickEvents(view)
                .compose(RxOperationUtils.<AdapterViewItemClickEvent>_throttleFirst(duration, unit))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, errorAction);
    }

    //========================变化事件=============================//

    /**
     * 简单的文字变化监听
     *
     * @param textView 监听控件
     * @return
     */
    public static Observable<CharSequence> textChanges(TextView textView) {
        return RxTextView.textChanges(textView);
    }

    /**
     * 简单的文字变化监听
     *
     * @param textView 监听控件
     * @param timeout  响应的间隔
     * @param unit     时间间隔单位
     * @return
     */
    public static Observable<CharSequence> textChanges(TextView textView, long timeout, TimeUnit unit) {
        return RxTextView.textChanges(textView)
                .compose(RxOperationUtils.<CharSequence>_debounce(timeout, unit))
                .skip(1) //跳过第1次数据发射 = 初始输入框的空字符状态
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 简单的文字变化监听
     *
     * @param textView 监听控件
     * @param timeout  响应的间隔
     * @param unit     时间间隔单位
     * @param action1  响应的动作
     * @return
     */
    public static Disposable textChanges(TextView textView, long timeout, TimeUnit unit, Consumer<CharSequence> action1) {
        return textChanges(textView, timeout, unit)
                .subscribe(action1, new SimpleThrowableAction(TAG));
    }

}