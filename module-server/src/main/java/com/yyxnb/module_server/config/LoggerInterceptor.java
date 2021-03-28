package com.yyxnb.module_server.config;

import com.yanzhenjie.andserver.annotation.Interceptor;
import com.yanzhenjie.andserver.framework.HandlerInterceptor;
import com.yanzhenjie.andserver.framework.handler.RequestHandler;
import com.yanzhenjie.andserver.http.HttpMethod;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.util.MultiValueMap;
import com.yyxnb.what.core.log.LogUtils;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/02/27
 * 描    述：日志打印
 * ================================================
 */
@Interceptor
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean onIntercept(HttpRequest request, HttpResponse respons, RequestHandler handler) {
        String httpPath = request.getPath();
        HttpMethod method = request.getMethod();
        MultiValueMap<String, String> valueMap = request.getParameter();

        LogUtils.i(String.format("Path: %s%n Method: %s%n Param: %s%n", httpPath, method.value(), valueMap));
        return false;
    }
}