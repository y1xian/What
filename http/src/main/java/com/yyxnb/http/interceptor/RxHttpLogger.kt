package com.yyxnb.http.interceptor

import android.util.Log
import com.yyxnb.http.utils.JsonUtils

import okhttp3.logging.HttpLoggingInterceptor

/**
 * Description: 日志打印格式化处理
 *
 * @author : yyx
 * @date ：2018/6/16
 */
class RxHttpLogger : HttpLoggingInterceptor.Logger {
    private val mMessage = StringBuilder()

    override fun log(message: String) {
        var message = message
        // 请求或者响应开始
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0)
            mMessage.append(" ")
            mMessage.append("\r\n")
        }
        if (message.startsWith("--> GET")) {
            mMessage.setLength(0)
            mMessage.append(" ")
            mMessage.append("\r\n")
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if (message.startsWith("{") && message.endsWith("}") || message.startsWith("[") && message.endsWith("]")) {
            message = JsonUtils.formatJson(message)
        }
        mMessage.append(message + "\n")
        // 请求或者响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            Log.d("---", mMessage.toString())
        }
    }
}