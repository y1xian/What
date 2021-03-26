package com.yyxnb.what.okhttp.interceptor.weaknetwork;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/01/13
 * 描    述：用于模拟弱网的拦截器
 * ================================================
 */
public class WeakNetworkInterceptor implements Interceptor {

    @SuppressWarnings("NullableProblems")
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!WeakNetworkManager.getInstance().isActive()) {
            Request originalRequest = chain.request();
            return chain.proceed(originalRequest);
        }
        final int type = WeakNetworkManager.getInstance().getType();
        switch (type) {
            case WeakNetworkManager.TYPE_TIMEOUT:
                //超时
                return WeakNetworkManager.getInstance().simulateTimeOut(chain);
            case WeakNetworkManager.TYPE_SPEED_LIMIT:
                //限速
                return WeakNetworkManager.getInstance().simulateSpeedLimit(chain);
            default:
                //断网
                return WeakNetworkManager.getInstance().simulateOffNetwork(chain);
        }
    }
}
