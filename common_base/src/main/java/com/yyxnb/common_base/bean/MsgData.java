package com.yyxnb.common_base.bean;

public class MsgData {
    public String key;
    public MsgType type = MsgType.TOAST;
    public Object value;

    public MsgData(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public MsgData(String key, MsgType type) {
        this.key = key;
        this.type = type;
    }

    public MsgData(String key, MsgType type, Object value) {
        this.key = key;
        this.type = type;
        this.value = value;
    }

    public enum MsgType {
        TOAST, MSG, NUMBER, LOADING, HIDE_LOADING
    }
}
