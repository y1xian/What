package com.yyxnb.module_login;

import java.io.Serializable;

/**
 * Description:
 *
 * @author : yyx
 * @date : 2018/11/21
 */
public class BaseDatas<T> implements Serializable {

    private int code;
    private String message;
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
