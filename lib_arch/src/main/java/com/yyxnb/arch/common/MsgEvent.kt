package com.yyxnb.arch.common

import java.io.Serializable

data class MsgEvent(
        var code: Int = 0,
        var msg: String? = null,
        var data: Any? = null
) : Serializable