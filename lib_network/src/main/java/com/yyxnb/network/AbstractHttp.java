package com.yyxnb.network;

import com.yyxnb.common.AppGlobals;
import com.yyxnb.network.interceptor.CacheInterceptor;
import com.yyxnb.network.interceptor.HeaderInterceptor;
import com.yyxnb.network.interceptor.LoggingInterceptor;
import com.yyxnb.network.utils.GsonUtils;
import com.yyxnb.network.utils.SSLUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class AbstractHttp {

    /**
     * baseUrl
     */
    protected abstract String baseUrl();

    /**
     * Headers
     */
    protected Map<String, String> header() {
        return new HashMap<>(16);
    }

    /**
     * OkHttp的拦截器
     */
    protected Iterable<Interceptor> interceptors() {
        return new ArrayList<>();
    }

    /**
     * CallAdapter转换器
     */
    protected Iterable<CallAdapter.Factory> callAdapterFactories() {
        return new ArrayList<>();
    }

    /**
     * Converter转换器
     */
    protected Iterable<Converter.Factory> convertersFactories() {
        return new ArrayList<>();
    }

    /**
     * Https证书
     */
    protected InputStream[] certificates() {
        return null;
    }

    /**
     * Https密钥
     */
    protected InputStream keyStore() {
        return null;
    }

    /**
     * Https密码
     */
    protected String keyStorePassword() {
        return null;
    }

    /**
     * 缓存
     */
    protected Boolean saveCache() {
        return true;
    }

    /**
     * 开启打印
     */
    protected Boolean openLog() {
        return true;
    }

    /**
     * 读
     */
    protected Long readTimeout() {
        return 10L;
    }

    /**
     * 写
     */
    protected Long writeTimeout() {
        return 10L;
    }

    /**
     * 请求
     */
    protected Long connectTimeout() {
        return 10L;
    }

    /**
     * Retrofit
     */
    protected Retrofit retrofit() {
        return defaultRetrofit();
    }

    /**
     * OkHttpClient
     */
    protected OkHttpClient okHttpClient() {
        return defaultOkHttpClient();
    }

    private Retrofit defaultRetrofit() {

        Retrofit.Builder builder = new Retrofit.Builder();

        for (CallAdapter.Factory it : callAdapterFactories()) {
            builder.addCallAdapterFactory(it);
        }
        for (Converter.Factory it : convertersFactories()) {
            builder.addConverterFactory(it);
        }

        builder.baseUrl(baseUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson()))
                .client(okHttpClient());

        return builder.build();
    }

    private OkHttpClient defaultOkHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        for (Interceptor it : interceptors()) {
            builder.addInterceptor(it);
        }
        if (openLog()) {
            final HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new LoggingInterceptor());
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logInterceptor);
        }
        if (saveCache()) {
            final File externalCacheDir = AppGlobals.getApplication().getApplicationContext().getExternalCacheDir();
            if (null != externalCacheDir) {
                builder.cache(new Cache(new File(externalCacheDir.getPath() + "/HttpCacheData"), 20 * 1024 * 1024));
                builder.addInterceptor(new CacheInterceptor());
            }
        }
        final SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory(keyStore(), keyStorePassword(), certificates());

        builder.addInterceptor(new HeaderInterceptor(header()))
                .readTimeout(readTimeout(), TimeUnit.SECONDS)
                .writeTimeout(writeTimeout(), TimeUnit.SECONDS)
                .connectTimeout(connectTimeout(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .hostnameVerifier(SSLUtils.UnSafeHostnameVerifier);

        return builder.build();
    }

    public <T> T create(Class<T> clz) {
        return retrofit().create(clz);
    }

}
