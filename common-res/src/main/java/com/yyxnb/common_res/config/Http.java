package com.yyxnb.common_res.config;

import com.yyxnb.lib_network.AbsRetrofit;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

import static com.yyxnb.common_res.config.BaseAPI.URL_APIOPEN;
import static com.yyxnb.common_res.config.BaseAPI.URL_JISU;
import static com.yyxnb.common_res.config.BaseAPI.URL_MOCKY;
import static com.yyxnb.common_res.config.BaseAPI.URL_WAN_ANDROID;


public class Http extends AbsRetrofit {

    private static volatile Http mInstance = null;

    private Http() {
    }

    public static Http getInstance() {
        if (null == mInstance) {
            synchronized (Http.class) {
                if (null == mInstance) {
                    mInstance = new Http();
                }
            }
        }
        return mInstance;
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
