package com.yyxnb.module_server.component;

import android.support.annotation.NonNull;

import com.yanzhenjie.andserver.annotation.Interceptor;
import com.yanzhenjie.andserver.error.HttpException;
import com.yanzhenjie.andserver.framework.HandlerInterceptor;
import com.yanzhenjie.andserver.framework.handler.MethodHandler;
import com.yanzhenjie.andserver.framework.handler.RequestHandler;
import com.yanzhenjie.andserver.framework.mapping.Addition;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.http.session.Session;

import cn.hutool.core.util.ArrayUtil;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/02/27
 * 描    述：拦截用户登录
 * ================================================
 */
@Interceptor
public class LoginInterceptor implements HandlerInterceptor {

    public static final String LOGIN_ATTRIBUTE = "USER.LOGIN.SIGN";
    public static final String LOGIN_TYPE = "LOGIN";

    @Override
    public boolean onIntercept(@NonNull HttpRequest request, @NonNull HttpResponse response,
                               @NonNull RequestHandler handler) {
        if (handler instanceof MethodHandler) {
            MethodHandler methodHandler = (MethodHandler) handler;
            Addition addition = methodHandler.getAddition();
            if (!isLogin(request, addition)) {
                throw new HttpException(401, "You are not logged in yet.");
            }
        }
        return false;
    }

    private boolean isNeedLogin(Addition addition) {
        if (addition == null) {
            return false;
        }

        String[] stringType = addition.getStringType();
        if (ArrayUtil.isEmpty(stringType)) {
            return false;
        }

        boolean[] booleanType = addition.getBooleanType();
        if (ArrayUtil.isEmpty(booleanType)) {
            return false;
        }
        return stringType[0].equalsIgnoreCase(LOGIN_TYPE) && booleanType[0];
    }

    private boolean isLogin(HttpRequest request, Addition addition) {
        if (isNeedLogin(addition)) {
            Session session = request.getSession();
            if (session != null) {
                Object o = session.getAttribute(LOGIN_ATTRIBUTE);
                return o instanceof Boolean && (boolean) o;
            }
            return false;
        }
        return true;
    }
}