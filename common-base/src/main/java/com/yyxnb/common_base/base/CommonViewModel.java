package com.yyxnb.common_base.base;

import androidx.lifecycle.LiveData;

import com.yyxnb.common_base.event.MessageEvent;
import com.yyxnb.common_base.event.StatusEvent;
import com.yyxnb.common_base.event.TypeEvent;
import com.yyxnb.what.arch.viewmodel.BaseViewModel;
import com.yyxnb.what.core.interfaces.IData;

import cn.hutool.core.util.ObjectUtil;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/12/03
 * 描    述：自定义网络请求
 * ================================================
 */
public class CommonViewModel extends BaseViewModel {

    /**
     * 普通消息事件
     */
    private final MessageEvent mMessageEvent = new MessageEvent();
    /**
     * 网络请求状态事件
     */
    private final StatusEvent mStatusEvent = new StatusEvent();

    /**
     * 自定义状态事件
     */
    private final TypeEvent mTypeEvent = new TypeEvent();

    @Override
    protected void onCreate() {
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public <T extends IData> void launchOnlyResult(
            LiveData<T> call,
            HttpResponseCallback<T> callback
    ) {
        mStatusEvent.setValue(StatusEvent.HttpStatus.LOADING);
        call.observe(this, t -> {
            if (ObjectUtil.isNotNull(t)) {
                if (t.isSuccess()) {
                    mStatusEvent.postValue(StatusEvent.HttpStatus.SUCCESS);
                    callback.onSuccess(t);
                } else {
                    mStatusEvent.postValue(StatusEvent.HttpStatus.FAILURE);
                    callback.onError(t.getMsg());
                }
            } else {
                mStatusEvent.postValue(StatusEvent.HttpStatus.ERROR);
                callback.onError("500");
            }
        });
    }

    public MessageEvent getMessageEvent() {
        return mMessageEvent;
    }

    public StatusEvent getStatusEvent() {
        return mStatusEvent;
    }

    public TypeEvent getTypeEvent() {
        return mTypeEvent;
    }

    public interface HttpResponseCallback<T> {

        void onSuccess(T data);

        void onError(String msg);

    }

}
