package com.yyxnb.module_wanandroid.bean;

import com.yyxnb.common.interfaces.IData;

public class WanData<T> implements IData<T> {

    /*
    errorCode = 0 代表执行成功，不建议依赖任何非0的 errorCode.
    errorCode = -1001 代表登录失效，需要重新登录。
     */

    public int errorCode;
    public String errorMsg;
    public T data;

    @Override
    public String getCode() {
        return String.valueOf(errorCode);
    }

    @Override
    public String getMsg() {
        return errorMsg;
    }

    @Override
    public T getResult() {
        return data;
    }

    @Override
    public boolean isSuccess() {
        return errorCode == 0;
    }

    @Override
    public int id() {
        return hashCode();
    }
}
