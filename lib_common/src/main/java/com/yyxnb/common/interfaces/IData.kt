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

    val code: String?
    val msg: String?
    val result: T?
    val isSuccess: Boolean
}