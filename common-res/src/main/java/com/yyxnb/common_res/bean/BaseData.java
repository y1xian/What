package com.yyxnb.common_res.bean;

import com.yyxnb.what.core.interfaces.IData;

/**
 * 常用的数据结构
 *
 * @param <T>
 */
public class BaseData<T> implements IData<T> {

    public String code;
    public String msg;
    public T data;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public T getResult() {
        return data;
    }

    @Override
    public boolean isSuccess() {
        return "200".equals(code);
    }

}
