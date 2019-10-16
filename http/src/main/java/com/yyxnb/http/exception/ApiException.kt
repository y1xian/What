package com.yyxnb.http.exception

import com.google.gson.JsonParseException
import com.google.gson.JsonSerializer
import com.google.gson.JsonSyntaxException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.io.NotSerializableException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * 统一处理了API异常错误
 */
class ApiException : Exception() {

    override var message: String = "unknown error"

    companion object {

        fun handleException(e: Throwable): ApiException {
            val ex = ApiException()
            when (e) {
                is HttpException -> {
                    try {
                        ex.message = "网络异常 ${e.message}"
                    } catch (e1: IOException) {
                        e1.printStackTrace()
                        ex.message = "IOException ${e1.message}"
                    }
                }
                is SocketTimeoutException
                    , is ConnectException
                    , is ConnectTimeoutException -> {
                    ex.message = "网络连接超时，请检查您的网络状态，稍后重试！ ${e.message}"
                }
                is NullPointerException -> {
                    ex.message = "空指针异常  ${e.message}"
                }
                is javax.net.ssl.SSLHandshakeException -> {
                    ex.message = "证书验证失败  ${e.message}"
                }
                is ClassCastException -> {
                    ex.message = "数据类型转换异常  ${e.message}"
                }
                is ArrayIndexOutOfBoundsException -> {
                    ex.message = "数组越界  ${e.message}"
                }
                is JsonParseException
                    , is JSONException
                    , is JsonSyntaxException
                    , is JsonSerializer<*>
                    , is NotSerializableException
                    , is ParseException -> {
                    ex.message = "解析错误  ${e.message}"
                }
                is UnknownHostException -> {
                    ex.message = "无法解析该域名 ${e.message}"
                }
                is IllegalStateException -> {
                    ex.message = "违法的状态异常  ${e.message}"
                }
                is TypeNotPresentException -> {
                    ex.message = "类型不存在异常  ${e.message}"
                }
                else -> ex.message = "未知错误 ${e.message}"
            }
            return ex
        }
    }
}
