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
        // 通过这个标 识，用户所访问的网站可以显示不同的排版从而为用户提供更好的体验或者进行信息统计
        map["User-Agent"] = "Mozilla/5.0 (Android)"
        // 告知服务器采用何种压缩方式,Okhttp不会帮你处理Gzip的解压，需要你自己去处理
//        map["Accept-Encoding"] = "gzip"
        // 告知服务器发送何种媒体类型
        map["Accept"] = "application/json"
        // 将内容指定为JSON格式，以UTF-8字符编码进行编码
        map["Content-Type"] = "application/json; charset=utf-8"
        for ((key, value) in map) {
            requestBuilder.addHeader(key, value as String)
        }
        requestBuilder.method(originalRequest.method, originalRequest.body)
        return chain.proceed(requestBuilder.build())
    }

}