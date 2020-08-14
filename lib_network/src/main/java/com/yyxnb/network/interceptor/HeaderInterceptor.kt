package com.yyxnb.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 请求拦截器  统一添加请求头使用
 */
class HeaderInterceptor(private val map: MutableMap<String, Any>) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        map["User-Agent"] = "Mozilla/5.0 (Android)"
        map["Accept-Encoding"] = "gzip"
        map["Accept"] = "application/json"
        map["Content-Type"] = "application/json; charset=utf-8"
        for ((key, value) in map) {
            requestBuilder.addHeader(key, value as String)
        }
        requestBuilder.method(originalRequest.method, originalRequest.body)
        return chain.proceed(requestBuilder.build())
    }

}