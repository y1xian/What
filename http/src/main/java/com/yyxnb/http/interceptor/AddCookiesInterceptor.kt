package com.yyxnb.http.interceptor

import android.util.Log
import com.yyxnb.http.utils.ISPKeys
import com.yyxnb.http.utils.SPUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

/**
 * 请求头里边添加cookie
 */

class AddCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val preferences = SPUtils.getParam(ISPKeys.COOKIE, HashSet<String>()) as? HashSet<String>
        if (preferences != null) {
            for (cookie in preferences) {
                builder.addHeader("Cookie", cookie)
                // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
                Log.v("RxHttpUtils", "Adding Header Cookie--->: $cookie")
            }
        }
        return chain.proceed(builder.build())
    }

}
