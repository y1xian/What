package com.yyxnb.module_server.config;

import androidx.annotation.NonNull;

import com.yanzhenjie.andserver.annotation.Resolver;
import com.yanzhenjie.andserver.error.HttpException;
import com.yanzhenjie.andserver.framework.ExceptionResolver;
import com.yanzhenjie.andserver.framework.body.JsonBody;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.http.StatusCode;
import com.yyxnb.module_server.bean.JsonResultVo;
import com.yyxnb.what.okhttp.utils.GsonUtils;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/14
 * 描    述：处理所有请求Http Api时发生的异常
 * ================================================
 */
@Resolver
public class AppExceptionResolver implements ExceptionResolver {

    @Override
    public void onResolve(@NonNull HttpRequest request, @NonNull HttpResponse response, @NonNull Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException exception = (HttpException) e;
            response.setStatus(exception.getStatusCode());
        } else {
            response.setStatus(StatusCode.SC_INTERNAL_SERVER_ERROR);
        }
        JsonResultVo<String> vo = new JsonResultVo<>();
        vo.setCode(response.getStatus());
        vo.setMessage(e.getMessage());
        String body = GsonUtils.getGson().toJson(vo);
        response.setBody(new JsonBody(body));
    }
}