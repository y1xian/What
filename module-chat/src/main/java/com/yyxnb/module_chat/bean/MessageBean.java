package com.yyxnb.module_chat.bean;

import com.yyxnb.what.core.interfaces.IData;

public class MessageBean implements IData<Long> {
    public int id;
    public int type;
    public String text;
    public String avatar;
    public String name;

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
