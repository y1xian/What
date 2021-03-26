package com.yyxnb.what.okhttp.interceptor;

import com.yyxnb.what.core.NetworkUtils;
import com.yyxnb.what.okhttp.annotation.CacheType;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/01/13
 * 描    述：缓存应用拦截器
 * ================================================
 */
public class CacheInterceptor implements Interceptor {

    int count = 0;
    int cacheType;

    public CacheInterceptor(int cacheType) {
        this.cacheType = cacheType;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Response originalResponse;
        switch (cacheType) {
            case CacheType.FORCE_CACHE:
                originalRequest = originalRequest.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                originalResponse = chain.proceed(originalRequest);
                break;
            case CacheType.FORCE_NETWORK:
                originalRequest = originalRequest.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
                originalResponse = chain.proceed(originalRequest);
                break;
            case CacheType.NETWORK_THEN_CACHE:
                if (!NetworkUtils.isAvailable()) {
                    originalRequest = originalRequest.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                    originalResponse = chain.proceed(originalRequest);
                } else {
                    originalRequest = originalRequest.newBuilder()
                            .cacheControl(CacheControl.FORCE_NETWORK)
                            .build();
                    //网络请求失败后自动读取缓存并响应
                    originalResponse = aopChain(chain, originalRequest, CacheControl.FORCE_CACHE);
                }
                break;
            case CacheType.CACHE_THEN_NETWORK:
                if (!NetworkUtils.isAvailable()) {
                    originalRequest = originalRequest.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                    originalResponse = chain.proceed(originalRequest);
                } else {
                    originalResponse = chain.proceed(originalRequest);
                }
                break;
            default:
                originalResponse = chain.proceed(originalRequest);
                break;
        }
        return originalResponse;
    }

    private Response aopChain(Chain chain, Request request, CacheControl cacheControl) {
        Response originalResponse = null;
        count++;
        try {
            //责任链模式处理拦截器
            originalResponse = chain.proceed(request);
            if (count >= 4) {
                return originalResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request = request.newBuilder().cacheControl(cacheControl).build();
            originalResponse = aopChain(chain, request, cacheControl);
        }
        return originalResponse;
    }
}
