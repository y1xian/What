package com.yyxnb.module_main.bean

import com.yyxnb.common.interfaces.IData

data class MainHomeBean(
        @JvmField
        var id: Int = 0,
        var type: Int = 0,
        var title: String? = null,
        var des: String? = null,
        var url: String? = null,
        var color: String? = null,
        @JvmField
        var span: Int = 0
) : IData<Long> {

    override fun id(): Int {
        return id
    }

    override fun getCode(): String {
        return id.toString()
    }

    override fun getMsg(): String? {
        return null
    }

    override fun getResult(): Long? {
        return null
    }

    override fun isSuccess(): Boolean {
        return false
    }
}