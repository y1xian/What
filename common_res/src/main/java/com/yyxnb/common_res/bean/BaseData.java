package com.yyxnb.common_res.bean;

import com.yyxnb.lib_widget.interfaces.IData;

/**
 * 常用的数据结构
 *
 * @param <T>
 */
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
