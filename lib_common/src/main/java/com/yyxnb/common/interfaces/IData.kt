package com.yyxnb.common.interfaces

/**
 * 接口返回封装类
 *
 * @param <T>
 */
interface IData<T> {

    fun id(): Int {
        return hashCode()
    }

    fun getCode(): String

    fun getMsg(): String?

    fun getResult(): T?

    fun isSuccess(): Boolean
}