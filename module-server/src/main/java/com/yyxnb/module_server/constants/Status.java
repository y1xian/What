package com.yyxnb.module_server.constants;

public enum Status {

    TOKEN_INVALID(401,"token不存在"),
    ;

    private int code;
    private String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
