package com.yyxnb.module_server.bean;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/13
 * 描    述：统一返回对象
 * ================================================
 */
public class JsonResultVo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;
    // 请求成功
    public static final int SUCCESS = 200;
    // 请求失败，一般是参数错误或数据不存在
    public static final int FAILURE = 0;
    // 请求异常
    public static final int ERROR = 400;
    public static final String SUCCESS_MESSAGE = "Success";
    public static final String FAILURE_MESSAGE = "Failure";

    public JsonResultVo() {
    }

    public JsonResultVo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResultVo(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> JsonResultVo<T> success(String message) {
        return new JsonResultVo<>(SUCCESS, message);
    }

    public static <T> JsonResultVo<T> success() {
        return new JsonResultVo<>(SUCCESS, SUCCESS_MESSAGE);
    }

    public static <T> JsonResultVo<T> success(T data) {
        return new JsonResultVo<>(SUCCESS, SUCCESS_MESSAGE, data);
    }

    public static <T> JsonResultVo<T> successData(T data) {
        return new JsonResultVo<>(SUCCESS, SUCCESS_MESSAGE, data);
    }

    public static <T> JsonResultVo<T> success(String message, T data) {
        return new JsonResultVo<>(SUCCESS, message, data);
    }

    public static <T> JsonResultVo<T> failure() {
        return new JsonResultVo<>(FAILURE, FAILURE_MESSAGE);
    }

    public static <T> JsonResultVo<T> failure(String message) {
        return new JsonResultVo<>(FAILURE, message);
    }

    public static <T> JsonResultVo<T> error(String message) {
        return new JsonResultVo<>(ERROR, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
