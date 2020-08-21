package com.yyxnb.network.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求拦截器  统一添加请求头使用
 */
public class HeaderInterceptor implements Interceptor {

    private Map<String, String> map;

    public HeaderInterceptor(Map<String, String> header) {
        this.map = header;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder();
        map.put("User-Agent", "Mozilla/5.0 (Android)");
        map.put("Accept-Encoding", "gzip");
        map.put("Accept", "application/json");
        map.put("Content-Type", "application/json; charset=utf-8");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }
        requestBuilder.method(originalRequest.method(), originalRequest.body());
        return chain.proceed(requestBuilder.build());
    }

}