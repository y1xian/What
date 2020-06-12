package com.yyxnb.module_base.base;

import com.yyxnb.common.interfaces.IData;

public class BaseData<T> implements IData<T> {

    public String status;
    public String message;
    public T data;

    @Override
    public String getCode() {
        return status;
    }

    @Override
    public String getMsg() {
        return message;
    }

    @Override
    public T getResult() {
        return data;
    }

    @Override
    public boolean isSuccess() {
        return "200".equals(status);
    }
}
