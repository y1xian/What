package com.yyxnb.network.interceptor.weaknetwork

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 用于模拟弱网的拦截器
 *
 * @author yyx
 */
class WeakNetworkInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!WeakNetworkManager.isActive) {
            val request = chain.request()
            return chain.proceed(request)
        }
        return when (WeakNetworkManager.type) {
            WeakNetworkManager.TYPE_TIMEOUT ->
                //超时
                WeakNetworkManager.simulateTimeOut(chain)
            WeakNetworkManager.TYPE_SPEED_LIMIT ->
                //限速
                WeakNetworkManager.simulateSpeedLimit(chain)
            else ->
                //断网
                WeakNetworkManager.simulateOffNetwork(chain)
        }
    }
}