package com.yyxnb.arch.bean

import java.io.Serializable

data class Result(var resultCode: Int? = 0, var resultObject: Any? = null, var tag: String = "", var success: Boolean = false)
    : Serializable
