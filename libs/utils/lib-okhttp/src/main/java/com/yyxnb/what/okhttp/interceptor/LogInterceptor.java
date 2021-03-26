package com.yyxnb.what.okhttp.interceptor;

import android.util.Log;

import java.net.URLDecoder;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/01/13
 * 描    述：日志拦截器
 * ================================================
 */
public class LogInterceptor implements HttpLoggingInterceptor.Logger {

    private StringBuilder mMessage = new StringBuilder();

    @SuppressWarnings("NullableProblems")
    @Override
    public void log(String text) {
        try {
            String message = text
                    .replaceAll("\\+", "")
                    .replaceAll("%", "%%");
            message = message.replaceAll("%(?![0-9a-fA-F]{2})", "%%");
            message = message.replaceAll("%%", "");
            message = message.replaceAll("\\+", "");
            message = URLDecoder.decode(message, "utf-8");

            if (message.startsWith("--> POST")) {
                mMessage.setLength(0);
                mMessage.append(" ");
                mMessage.append("\r\n");
            }
            if (message.startsWith("--> GET")) {
                mMessage.setLength(0);
                mMessage.append(" ");
                mMessage.append("\r\n");
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            if (message.startsWith("{") && message.endsWith("}") || message.startsWith("[") && message.endsWith("]")) {
//                message = JsonUtils.formatJson(message);
            }
            mMessage.append(message).append("\r\n");
            // 请求或者响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                Log.w("Http", "\n" + mMessage.toString() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
