package com.yyxnb.what.okhttp;

import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.yyxnb.what.app.AppUtils;
import com.yyxnb.what.okhttp.annotation.CacheType;
import com.yyxnb.what.okhttp.interceptor.CacheInterceptor;
import com.yyxnb.what.okhttp.interceptor.GzipRequestInterceptor;
import com.yyxnb.what.okhttp.interceptor.HeaderInterceptor;
import com.yyxnb.what.okhttp.interceptor.LogInterceptor;
import com.yyxnb.what.okhttp.interceptor.NetworkInterceptor;
import com.yyxnb.what.okhttp.interceptor.weaknetwork.WeakNetworkInterceptor;
import com.yyxnb.what.okhttp.utils.SSLUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/01/12
 * 描    述：AbsOkHttp
 * ================================================
 */
public abstract class AbsOkHttp {

    /**
     * baseUrl
     */
    protected abstract String baseUrl();

    /**
     * 缓存类型
     */
    protected int cacheType() {
        return CacheType.FORCE_NETWORK;
    }

    /**
     * 缓存目录
     */
    protected File cachedDir() {
        return new File(AppUtils.getApp().getExternalCacheDir().getAbsolutePath() + "/okHttp_cache");
    }

    /**
     * 缓存大小
     */
    protected int maxCacheSize() {
        return 10 * 1024 * 1024;
    }

    /**
     * 持久化缓存
     */
    protected CookieJar cookieJar() {
        return null;
    }

    /**
     * Headers 请求头
     */
    protected Map<String, String> header() {
        return new HashMap<>(16);
    }

    /**
     * cookies存放
     */
    protected HashMap<String, List<Cookie>> cookieStore = new HashMap<>(16);

    /**
     * OkHttp的拦截器
     */
    protected Iterable<Interceptor> interceptors() {
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
     * Gzip压缩，需要服务端支持
     */
    protected boolean isGzip() {
        return false;
    }

    /**
     * OkHttpClient
     */
    protected OkHttpClient okHttpClient() {
        return defaultOkHttpClient();
    }

    private OkHttpClient defaultOkHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();

        final SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory(keyStore(), keyStorePassword(), certificates());

        builder
                // 请求头
                .addInterceptor(new HeaderInterceptor(header()))
                // 缓存处理
                .addInterceptor(new CacheInterceptor(cacheType()))
                // 网络请求
                .addInterceptor(new NetworkInterceptor())
                // 弱网
                .addInterceptor(new WeakNetworkInterceptor())
                // 超时
                .readTimeout(readTimeout(), TimeUnit.SECONDS)
                .writeTimeout(writeTimeout(), TimeUnit.SECONDS)
                .connectTimeout(connectTimeout(), TimeUnit.SECONDS)
                // 重连
                .retryOnConnectionFailure(true)
                // 证书
                .hostnameVerifier(SSLUtils.UnSafeHostnameVerifier)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        if (openLog()) {
            final HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new LogInterceptor());
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logInterceptor);
        }

        if (isGzip()) {
            builder.addInterceptor(new GzipRequestInterceptor());
        }

        //检测是否有写的权限
        int permission = ActivityCompat.checkSelfPermission(AppUtils.getApp(),
                "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission == PackageManager.PERMISSION_GRANTED && null != cachedDir()) {
            builder.cache(new Cache(cachedDir(), maxCacheSize()));
        }

        if (null != cookieJar()) {
            builder.cookieJar(cookieJar());
        }

        for (Interceptor it : interceptors()) {
            builder.addInterceptor(it);
        }

        return builder.build();
    }

}
