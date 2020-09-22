package com.yyxnb.network

import com.yyxnb.network.interceptor.CacheInterceptor
import com.yyxnb.network.utils.GsonUtils.gson
import com.yyxnb.network.utils.SSLUtils
import com.yyxnb.network.utils.SSLUtils.getSslSocketFactory
import com.yyxnb.network.interceptor.HeaderInterceptor
import com.yyxnb.network.interceptor.LoggingInterceptor
import com.yyxnb.network.interceptor.weaknetwork.WeakNetworkInterceptor
import com.yyxnb.widget.AppUtils
import com.yyxnb.widget.WidgetManager
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.InputStream
import java.util.*
import java.util.concurrent.TimeUnit

abstract class AbstractHttp {

    /**
     * baseUrl
     */
    abstract fun baseUrl(): String

    /**
     * Headers
     */
    open fun header(): HashMap<String, Any> = hashMapOf()

    /**
     * OkHttp的拦截器
     */
    open fun interceptors(): Iterable<Interceptor> = arrayListOf()

    /**
     * CallAdapter转换器
     */
    open fun callAdapterFactories(): Iterable<CallAdapter.Factory> = arrayListOf()

    /**
     * Converter转换器
     */
    open fun convertersFactories(): Iterable<Converter.Factory> = arrayListOf()

    /**
     * Https证书
     */
    open fun certificates(): Array<InputStream>? = arrayOf()

    /**
     * Https密钥
     */
    open fun keyStore(): InputStream? = null

    /**
     * Https密码
     */
    open fun keyStorePassword(): String? = null

    /**
     * 缓存
     */
    open fun saveCache(): Boolean = true

    /**
     * 开启打印
     */
    open fun openLog(): Boolean = true

    /**
     * 读
     */
    open fun readTimeout(): Long = 10L

    /**
     * 写
     */
    open fun writeTimeout(): Long = 10L

    /**
     * 请求
     */
    open fun connectTimeout(): Long = 10L

    /**
     * Retrofit
     */
    open fun retrofit(): Retrofit = defaultRetrofit()

    /**
     * OkHttpClient
     */
    open fun okHttpClient(): OkHttpClient = defaultOkHttpClient()

    private fun defaultRetrofit(): Retrofit {
        val builder = Retrofit.Builder()
        for (it in callAdapterFactories()) {
            builder.addCallAdapterFactory(it)
        }
        for (it in convertersFactories()) {
            builder.addConverterFactory(it)
        }
        builder.baseUrl(baseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient())
        return builder.build()
    }

    private fun defaultOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        for (it in interceptors()) {
            builder.addInterceptor(it)
        }
        if (openLog()) {
            val logInterceptor = HttpLoggingInterceptor(LoggingInterceptor())
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logInterceptor)
        }
        if (saveCache()) {
            val externalCacheDir = AppUtils.app.externalCacheDir
            if (null != externalCacheDir) {
                builder.cache(Cache(File(externalCacheDir.path + "/HttpCacheData"), 20 * 1024 * 1024))
                builder.addInterceptor(CacheInterceptor())
            }
        }
        val sslParams = getSslSocketFactory(keyStore(), keyStorePassword(), *certificates()!!)
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        builder
                // 请求头
                .addInterceptor(HeaderInterceptor(header()))
                // 弱网
                .addInterceptor(WeakNetworkInterceptor())
                // 超时
                .readTimeout(readTimeout(), TimeUnit.SECONDS)
                .writeTimeout(writeTimeout(), TimeUnit.SECONDS)
                .connectTimeout(connectTimeout(), TimeUnit.SECONDS)
                // 重连
                .retryOnConnectionFailure(true)
                // 证书
                .sslSocketFactory(sslParams.sSLSocketFactory!!, sslParams.trustManager!!)
                .hostnameVerifier(SSLUtils.UnSafeHostnameVerifier)

        return builder.build()
    }

    fun <T> create(clz: Class<T>): T {
        return retrofit().create(clz)
    }

}