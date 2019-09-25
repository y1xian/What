package com.yyxnb.http.interceptor


import com.yyxnb.http.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Description: 网络缓存
 *
 * @author : yyx
 * @date ：2018/6/16
 */
class NetCacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        if (NetworkUtils.isAvailable || NetworkUtils.isConnected) {
            //如果有网络，缓存60s
            val response = chain.proceed(request)
            val maxTime = 60
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=$maxTime")
                    .build()
        }
        //如果没有网络，不做处理，直接返回
        return chain.proceed(request)
    }

}