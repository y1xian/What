package com.yyxnb.arch.common;

import java.io.Serializable;

public class MsgEvent implements Serializable {
    private int code;
    private String msg;
    private Object data;

    public MsgEvent() {
    }

    public MsgEvent(int code) {
        this.code = code;
    }

    public MsgEvent(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public MsgEvent(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public MsgEvent(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
