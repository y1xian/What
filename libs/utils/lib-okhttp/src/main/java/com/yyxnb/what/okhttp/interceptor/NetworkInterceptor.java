package com.yyxnb.what.okhttp.interceptor;


import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/01/13
 * 描    述：网络请求拦截器
 * ================================================
 */
public class NetworkInterceptor implements Interceptor {

    @SuppressWarnings("NullableProblems")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Response originalResponse = chain.proceed(originalRequest);
        // 默认缓存7天
        int maxAge = 60 * 60 * 24 * 7;
        return originalResponse.newBuilder()
                .header("Cache-Control", String.format(Locale.getDefault(), "max-age=%d", maxAge))
                .build();

    }
}
