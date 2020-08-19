package com.yyxnb.module_wanandroid.bean

data class WanSystemBean(
        /**
         * children : [{"children":[],"courseId":13,"id":10,"name":"Activity","order":10000,"parentChapterId":9,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":15,"name":"Service","order":10001,"parentChapterId":9,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":16,"name":"BroadcastReceiver","order":10002,"parentChapterId":9,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":17,"name":"ContentProvider","order":10003,"parentChapterId":9,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":19,"name":"Intent","order":10004,"parentChapterId":9,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":40,"name":"Context","order":10005,"parentChapterId":9,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":267,"name":"handler","order":10006,"parentChapterId":9,"userControlSetTop":false,"visible":1}]
         * courseId : 13
         * id : 9
         * name : 四大组件
         * order : 10
         * parentChapterId : 0
         * userControlSetTop : false
         * visible : 1
         */
        var courseId: Int = 0,
        var id: Int = 0,
        @JvmField
        var name: String? = null,
        var order: Int = 0,
        var parentChapterId: Int = 0,
        var userControlSetTop: Boolean = false,
        var visible: Int = 0,

        @JvmField
        var children: List<WanClassifyBean>? = null
)