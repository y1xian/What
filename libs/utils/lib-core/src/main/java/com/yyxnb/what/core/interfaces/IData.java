package com.yyxnb.what.core.interfaces;

/**
 * 接口返回结构封装类
 *
 * @param <T>
 * @author yyx
 */
public interface IData<T> {

    default int id() {
        return hashCode();
    }

    /**
     * 状态码
     *
     * @return 判断数据是否请求成功
     */
    String getCode();

    /**
     * 提示语
     *
     * @return 提示用户
     */
    String getMsg();

    /**
     * 数据
     *
     * @return 可为空
     */
    T getResult();

    /**
     * 判断数据的请求是否成功
     *
     * @return true or false
     */
    boolean isSuccess();
}
