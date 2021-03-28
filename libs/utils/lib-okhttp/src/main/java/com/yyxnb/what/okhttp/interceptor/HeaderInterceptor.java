package com.yyxnb.what.okhttp.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/01/13
 * 描    述：请求拦截器  统一添加请求头使用
 * ================================================
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
        // 通过这个标识，用户所访问的网站可以显示不同的排版从而为用户提供更好的体验或者进行信息统计
        map.put("User-Agent", "Mozilla/5.0");
        // 告知服务器发送何种媒体类型
        map.put("Accept", "application/json");
        // 将内容指定为JSON格式，以UTF-8字符编码进行编码
        map.put("Content-Type", "application/json; charset=utf-8");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }
        requestBuilder.method(originalRequest.method(), originalRequest.body());
        return chain.proceed(requestBuilder.build());
    }

}