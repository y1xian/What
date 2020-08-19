package com.yyxnb.module_chat.bean

import com.yyxnb.common.interfaces.IData

data class MessageBean(
        var id: Int = 0,
        var type: Int = 0,
        @JvmField
        var text: String? = null,
        var avatar: String? = null,
        @JvmField
        var name: String? = null

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