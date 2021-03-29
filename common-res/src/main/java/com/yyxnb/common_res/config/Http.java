package com.yyxnb.common_res.config;

import com.yyxnb.common_res.utils.UrlInterceptor;
import com.yyxnb.what.network.AbsRetrofit;

import java.util.List;

import cn.hutool.core.collection.ListUtil;
import okhttp3.Interceptor;

import static com.yyxnb.common_res.config.BaseAPI.URL_APIOPEN;
import static com.yyxnb.common_res.config.BaseAPI.URL_JISU;
import static com.yyxnb.common_res.config.BaseAPI.URL_LOCAL;
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
        return URL_LOCAL;
    }

    @Override
    protected Iterable<Interceptor> interceptors() {

        final List<String> urlBucket = ListUtil.list(false,
                URL_MOCKY, URL_WAN_ANDROID, URL_APIOPEN, URL_JISU
        );
        return ListUtil.list(false,
                new UrlInterceptor(urlBucket)
        );
    }
}
