package com.yyxnb.what.rxtool.exception;

import android.util.Log;

/**
 * 自定义错误
 */
public class RxException extends Exception {
    /**
     * 默认错误码
     */
    public static final int DEFAULT_ERROR = -1;

    /**
     * 自定义的错误码
     */
    private int mCode;

    public RxException(String message, int code) {
        super(message);
        mCode = code;
    }

    public RxException(Throwable e, int code) {
        super(e);
        mCode = code;
    }

    /**
     * 获取自定义的错误码
     *
     * @return
     */
    public int getCode() {
        return mCode;
    }

    @Override
    public String getMessage() {
        return "Code:" + mCode + ", Message:" + getDetailMessage();
    }

    /**
     * 获取详情信息
     *
     * @return
     */
    public String getDetailMessage() {
        return super.getMessage();
    }

    /**
     * 获取错误堆栈信息
     *
     * @return
     */
    public String getExceptionStackTraceInfo() {
        return Log.getStackTraceString(this);
    }
}