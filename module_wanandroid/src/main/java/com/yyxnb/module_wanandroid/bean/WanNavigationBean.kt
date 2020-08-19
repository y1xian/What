package com.yyxnb.module_wanandroid.bean

data class WanNavigationBean(
        var cid: Int = 0,
        @JvmField
        var name: String? = null,
        @JvmField
        var articles: List<WanAriticleBean>? = null
)