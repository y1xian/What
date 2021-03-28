package com.yyxnb.what.rxtool.exception;

/**
 * 错误信息处理者
 */
public interface IExceptionHandler {

    /**
     * 处理过滤错误信息
     * @param e
     * @return
     */
    RxException handleException(Throwable e);
}