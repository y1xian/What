package com.yyxnb.common_base.bean;

public class LiveEvent {
    public MsgType type = MsgType.TOAST;
    public Object value;
    public String key;

    public LiveEvent(MsgType type) {
        this.type = type;
    }

    public LiveEvent(Object value) {
        this.value = value;
    }

    public LiveEvent(MsgType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public LiveEvent(MsgType type, Object value, String key) {
        this.type = type;
        this.value = value;
        this.key = key;
    }

    public enum MsgType {
        TOAST, MSG, NUMBER, LOADING, HIDE_LOADING, VALUE
    }
}
