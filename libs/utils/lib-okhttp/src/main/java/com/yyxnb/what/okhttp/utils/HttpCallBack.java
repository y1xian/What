package com.yyxnb.what.okhttp.utils;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

public abstract class HttpCallBack<T> {

    public Type mType;

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public HttpCallBack() {
        mType = getSuperclassTypeParameter(getClass());
    }

    /**
     * 开始请求
     */
    public abstract void onLoadingBefore(Request request);

    /**
     * 第二个参数为传入的泛型，也就是说返回的结果直接就是我们需要的类型
     * @param response
     * @param result
     */
    public abstract void onSuccess(Response response, T result);

    /**
     * 请求错误
     * @param request
     * @param e
     */
    public abstract void onFailure(Request request, Exception e);

    /**
     * 解析异常
     * @param response
     * @param e
     */
    public abstract void onError(Response response, Exception e);
}