package com.yyxnb.module_wanandroid.bean

data class WanStatus<T>(
        var curPage: Int = 0,
        var offset: Int = 0,
        var over: Boolean = false,
        var pageCount: Int = 0,
        var size: Int = 0,
        var total: Int = 0,
        var datas: List<T> = listOf()
)