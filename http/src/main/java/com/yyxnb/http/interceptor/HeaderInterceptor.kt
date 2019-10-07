package com.yyxnb.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

/**
 * 请求拦截器  统一添加请求头使用
 */
class HeaderInterceptor(private val headerMaps: HashMap<String, Any>?) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        //避免某些服务器配置攻击,请求返回403 forbidden 问题
        headerMaps!!["User-Agent"] = "Mozilla/5.0 (Android)"
        if (headerMaps != null && headerMaps.size > 0) {
            for ((key, value) in headerMaps) {
                request.addHeader(key, value as String)
            }
        }
        return chain.proceed(request.build())
    }

}
