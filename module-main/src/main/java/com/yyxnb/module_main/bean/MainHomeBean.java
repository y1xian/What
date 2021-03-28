package com.yyxnb.module_main.bean;

import com.yyxnb.what.core.interfaces.IData;

public class MainHomeBean implements IData<Long> {
    public int id;
    public int type;
    public String title;
    public String des;
    public String url;
    public String color;
    public int span;

    @Override
    public int id() {
        return id;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMsg() {
        return null;
    }

    @Override
    public Long getResult() {
        return null;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}
