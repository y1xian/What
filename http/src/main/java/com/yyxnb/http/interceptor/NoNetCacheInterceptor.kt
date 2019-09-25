package com.yyxnb.http.interceptor


import com.yyxnb.http.utils.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Description: 网络缓存
 *
 * @author : yyx
 * @date ：2018/6/16
 */
class NoNetCacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        //如果没有网络，则启用 FORCE_CACHE
        if (!NetworkUtils.isAvailable || !NetworkUtils.isConnected) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()

            val response = chain.proceed(request)
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=3600")
                    .removeHeader("Pragma")
                    .build()
        }
        //有网络的时候，这个拦截器不做处理，直接返回
        return chain.proceed(request)
    }

}