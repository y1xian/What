package com.yyxnb.module_novel.bean;

import com.yyxnb.what.core.interfaces.IData;

public class NovelData<T> implements IData<T> {

    /*
     0 代表执行成功
     */

    public int status;
    public String msg;
    public T result;

    @Override
    public String getCode() {
        return String.valueOf(status);
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public T getResult() {
        return result;
    }

    @Override
    public boolean isSuccess() {
        return status == 0;
    }
}
