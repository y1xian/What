package com.yyxnb.module_base.config;

import com.yyxnb.http.AbstractHttp;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

import static com.yyxnb.module_base.config.BaseAPI.URL_APIOPEN;


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
        return BaseAPI.URL_MOCKY;
    }

    @Override
    protected Iterable<Interceptor> interceptors() {

        List<String> urlBucket = new ArrayList<>();
        urlBucket.add(URL_APIOPEN);

        List<Interceptor> interceptorList = new ArrayList<>();
        interceptorList.add(new UrlInterceptor(urlBucket));
        return interceptorList;
    }
}
