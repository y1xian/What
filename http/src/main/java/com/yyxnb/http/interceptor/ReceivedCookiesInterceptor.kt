package com.yyxnb.http.interceptor


import android.text.TextUtils
import com.yyxnb.http.utils.ISPKeys
import com.yyxnb.http.utils.SPUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*
import java.util.Calendar.getInstance

/**
 * 接受服务器发的cookie   并保存到本地
 */

class ReceivedCookiesInterceptor : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies = HashSet<String>()

            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }
            SPUtils.saveParam(ISPKeys.COOKIE, cookies)
        }
        //获取服务器相应时间--用于计算倒计时的时间差
        if (!TextUtils.isEmpty(originalResponse.header("Date"))) {
            val date = dateToStamp(originalResponse.header("Date"))
            SPUtils.saveParam(ISPKeys.DATE, date)
        }

        return originalResponse
    }

    companion object {


        /**
         * 将时间转换为时间戳
         *
         * @param s date
         * @return long
         * @throws android.net.ParseException
         */
        @Throws(android.net.ParseException::class)
        fun dateToStamp(s: String?): Long {
            //转换为标准时间对象
            val date = Date(s)
            val calendar = getInstance()
            calendar.time = date
            val mTimeInMillis = calendar.timeInMillis
            return mTimeInMillis
        }
    }
}
