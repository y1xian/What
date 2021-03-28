package com.yyxnb.module_server.config;

import androidx.annotation.NonNull;

import com.yanzhenjie.andserver.annotation.Interceptor;
import com.yanzhenjie.andserver.error.HttpException;
import com.yanzhenjie.andserver.framework.HandlerInterceptor;
import com.yanzhenjie.andserver.framework.handler.MethodHandler;
import com.yanzhenjie.andserver.framework.handler.RequestHandler;
import com.yanzhenjie.andserver.framework.mapping.Addition;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yyxnb.module_server.constants.HeaderKeys;
import com.yyxnb.module_server.constants.Status;
import com.yyxnb.module_server.db.ServerUserDatabase;
import com.yyxnb.module_server.db.UserDao;

import cn.hutool.core.bean.BeanUtil;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/02/27
 * 描    述：用户操作相关
 * ================================================
 */
@Interceptor
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean onIntercept(@NonNull HttpRequest request, @NonNull HttpResponse response, @NonNull RequestHandler handler) {
        if (handler instanceof MethodHandler) {
            MethodHandler methodHandler = (MethodHandler) handler;
            Addition addition = methodHandler.getAddition();
            if (!isLogin(request)) {
                throw new HttpException(Status.TOKEN_INVALID.getCode(), Status.TOKEN_INVALID.getMessage());
            }
        }
        return false;
    }

    private boolean isLogin(HttpRequest request) {
        if (!request.getHeaderNames().contains(HeaderKeys.TOKEN)) {
            return true;
        }
        final UserDao userDao = ServerUserDatabase.getInstance().userDao();
        return !BeanUtil.isEmpty(userDao.getUser(request.getHeader(HeaderKeys.TOKEN)));
    }
}