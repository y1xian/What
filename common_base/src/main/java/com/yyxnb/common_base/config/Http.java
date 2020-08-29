package com.yyxnb.common_base.config;

import com.yyxnb.network.AbstractHttp;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

import static com.yyxnb.common_base.config.BaseAPI.URL_APIOPEN;
import static com.yyxnb.common_base.config.BaseAPI.URL_JISU;
import static com.yyxnb.common_base.config.BaseAPI.URL_MOCKY;
import static com.yyxnb.common_base.config.BaseAPI.URL_WAN_ANDROID;


public class Http extends AbstractHttp {

    private volatile static Http http;

    public static Http getInstance() {
        if (http == null) {
            synchronized (Http.class) {
                if (http == null) {
                    http = new Http();
                }
            }
        }
        return http;
    }

    @Override
    protected String baseUrl() {
        return URL_MOCKY;
    }

    @Override
    protected Iterable<Interceptor> interceptors() {

        final List<String> urlBucket = new ArrayList<>();
        urlBucket.add(URL_WAN_ANDROID);
        urlBucket.add(URL_APIOPEN);
        urlBucket.add(URL_JISU);

        final List<Interceptor> interceptorList = new ArrayList<>();
        interceptorList.add(new UrlInterceptor(urlBucket));
//        interceptorList.add(new BaseUrlInterceptor());
        return interceptorList;
    }
}
