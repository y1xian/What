package com.yyxnb.common_base.core;

import android.arch.lifecycle.LiveData;

import com.yyxnb.common_base.bean.LiveEvent;
import com.yyxnb.lib_arch.viewmodel.BaseViewModel;
import com.yyxnb.lib_common.interfaces.IData;
import com.yyxnb.lib_network.NetStatus;
import com.yyxnb.lib_network.SingleLiveEvent;

import cn.hutool.core.util.ObjectUtil;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/12/03
 * 描    述：自定义网络请求
 * ================================================
 */
public class CommonViewModel extends BaseViewModel {

    public final SingleLiveEvent<LiveEvent> defaultMsgEvent = new SingleLiveEvent<>();

    public final SingleLiveEvent<NetStatus> status = new SingleLiveEvent<>();

    @Override
    protected void onCreate() {
    }

    public <T extends IData> void launchOnlyResult(
            LiveData<T> call,
            HttpResponseCallback<T> callback
    ) {
        status.setValue(NetStatus.LOADING);
        call.observe(this, t -> {
            if (ObjectUtil.isNotNull(t)) {
                if (t.isSuccess()) {
                    status.setValue(NetStatus.SUCCESS);
                    callback.onSuccess(t);
                } else {
                    status.setValue(NetStatus.ERROR);
                    callback.onError(t.getMsg());
                }
            } else {
                status.setValue(NetStatus.ERROR);
                callback.onError("500");
            }
            status.setValue(NetStatus.COMPLETE);
        });
    }


    public interface HttpResponseCallback<T> {

        void onSuccess(T data);

        void onError(String msg);

    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
