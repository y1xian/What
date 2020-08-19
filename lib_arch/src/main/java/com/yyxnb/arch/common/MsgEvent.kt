package com.yyxnb.arch.common

import java.io.Serializable

data class MsgEvent(
        @JvmField
        var code: Int = 0,
        @JvmField
        var msg: String = "",
        @JvmField
        var data: Any? = null
) : Serializable