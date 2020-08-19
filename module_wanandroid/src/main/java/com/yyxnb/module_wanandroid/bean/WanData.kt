package com.yyxnb.module_wanandroid.bean

import com.yyxnb.common.interfaces.IData
import java.io.Serializable

/**
 * 玩安卓 基类
 */
data class WanData<T>(
        var errorCode: Int,
        var errorMsg: String,
        var data: T

) : IData<T>, Serializable {
    /*
    errorCode = 0 代表执行成功，不建议依赖任何非0的 errorCode.
    errorCode = -1001 代表登录失效，需要重新登录。
     */

    override fun id(): Int {
        return hashCode()
    }

    override fun getCode(): String = errorCode.toString()

    override fun getMsg(): String? = errorMsg

    override fun getResult(): T? = data

    override fun isSuccess(): Boolean = errorCode == 0
}