package com.yyxnb.common.interfaces;

/**
 * 接口返回封装类
 *
 * @param <T>
 */
public interface IData<T> {

    default int id() {
        return hashCode();
    }

    String getCode();

    String getMsg();

    T getResult();

    boolean isSuccess();
}
