package com.yyxnb.module_wanandroid.bean

data class WanAriticleBean(
        /**
         * apkLink :
         * audit : 1
         * author :
         * canEdit : false
         * chapterId : 502
         * chapterName : 自助
         * collect : false
         * courseId : 13
         * desc :
         * descMd :
         * envelopePic :
         * fresh : false
         * id : 14091
         * link : https://juejin.im/post/5ef9327e6fb9a07e716acac4
         * niceDate : 2020-06-30 08:24
         * niceShareDate : 2020-06-30 08:24
         * origin :
         * prefix :
         * projectLink :
         * publishTime : 1593476689000
         * realSuperChapterId : 493
         * selfVisible : 0
         * shareDate : 1593476689000
         * shareUser : JsonChao
         * superChapterId : 494
         * superChapterName : 广场Tab
         * tags : []
         * title : 深入探索 Gradle 自动化构建技术（九、Gradle 插件平台化框架 ByteX 探秘之旅）
         * type : 0
         * userId : 611
         * visible : 1
         * zan : 0
         */
        var apkLink: String? = null,
        var audit: Int = 0,
        @JvmField
        var author: String? = null,
        var canEdit: Boolean = false,
        var chapterId: Int = 0,
        @JvmField
        var chapterName: String? = null,
        var collect: Boolean = false,
        var courseId: Int = 0,
        var desc: String? = null,
        var descMd: String? = null,
        @JvmField
        var envelopePic: String? = null,
        @JvmField
        var fresh: Boolean = false,
        var id: Int = 0,
        var link: String? = null,
        @JvmField
        var niceDate: String? = null,
        var niceShareDate: String? = null,
        var origin: String? = null,
        var prefix: String? = null,
        var projectLink: String? = null,
        var publishTime: Long = 0,
        var realSuperChapterId: Int = 0,
        var selfVisible: Int = 0,
        var shareDate: Long = 0,
        @JvmField
        var shareUser: String? = null,
        var superChapterId: Int = 0,
        @JvmField
        var superChapterName: String? = null,
        @JvmField
        var title: String? = null,
        @JvmField
        var type: Int = 0,
        var userId: Int = 0,
        var visible: Int = 0,
        var zan: Int = 0,
        var tags: List<DataBeans>? = null,

        // 轮播的
        var imagePath: String? = null,
        var url: String? = null,
//    var isVisible: Int = 0
        var order: Int = 0


)

data class DataBeans(
        var name: String? = null,
        var url: String? = null
)