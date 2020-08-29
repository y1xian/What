package com.yyxnb.common_base.base

import com.yyxnb.widget.interfaces.IData

data class BaseData<T>(
        var status: String = "",
        var message: String = "",
        var data: T
) : IData<T> {

    override fun getCode(): String = status

    override fun getMsg(): String = message

    override fun getResult(): T? = data

    override fun isSuccess(): Boolean = "200" == status

    override fun id(): Int {
        return hashCode()
    }
}