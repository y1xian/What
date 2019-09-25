package com.yyxnb.http.interceptor


import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.io.InterruptedIOException

/**
 * 失败重连 次数、间隔
 */

class RetryInterceptor internal constructor(builder: Builder) : Interceptor {
    var executionCount: Int = 0//最大重试次数
    /**
     * retry间隔时间
     */
    val retryInterval: Long//重试的间隔

    init {
        this.executionCount = builder.executionCount
        this.retryInterval = builder.retryInterval
    }


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = doRequest(chain, request)
        var retryNum = 0
        while ((response == null || !response.isSuccessful) && retryNum <= executionCount) {
            Log.e("--- $retryNum", "intercept Request is not successful - {}")
            val nextInterval = retryInterval
            try {
                Thread.sleep(nextInterval)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
                throw InterruptedIOException()
            }

            retryNum++
            // retry the request
            response = doRequest(chain, request)
        }
        return response!!
    }

    private fun doRequest(chain: Interceptor.Chain, request: Request): Response? {
        var response: Response? = null
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return response
    }

    class Builder {
        var executionCount: Int = 3
        var retryInterval: Long = 2000

        fun executionCount(executionCount: Int): Builder {
            this.executionCount = executionCount
            return this
        }

        fun retryInterval(retryInterval: Long): Builder {
            this.retryInterval = retryInterval
            return this
        }

        fun build(): RetryInterceptor {
            return RetryInterceptor(this)
        }
    }

}