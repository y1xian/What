package com.yyxnb.module_wanandroid.bean

data class WanClassifyBean(
        /**
         * children : []
         * courseId : 13
         * id : 434
         * name : Gityuan
         * order : 190013
         * parentChapterId : 407
         * userControlSetTop : false
         * visible : 1
         */
        var courseId: Int = 0,
        var id: Int = 0,
        @JvmField
        var name: String = "",
        var order: Int = 0,
        var parentChapterId: Int = 0,
        var userControlSetTop: Boolean = false,
        var visible: Int = 0,
        var children: List<Any> = listOf()
)