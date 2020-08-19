package com.yyxnb.common_base.base

import com.yyxnb.common.interfaces.IData

data class BaseData<T>(
        var status: String = "",
        var message: String = "",
        var data: T? = null
) : IData<T> {

    override var code: String? = status

    override var msg: String? = message

    override var result: T? = data

    override val isSuccess: Boolean
        get() = "200" == code

    override fun id(): Int {
        return hashCode()
    }
}