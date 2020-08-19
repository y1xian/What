package com.yyxnb.common_base.module

/**
 * 组件生命周期反射类名管理，在这里注册需要初始化的组件，通过反射动态调用各个组件的初始化方法
 * 注意：以下模块中初始化的Module类不能被混淆
 */
object ModuleLifecycleReflexs {
    //主业务模块
    private const val MAIN_INIT = "com.yyxnb.module_main.ModuleInit"

    //登录注册模块
    private const val LOGIN_INIT = "com.yyxnb.module_login.ModuleInit"

    //用户业务模块
    private const val USER_INIT = "com.yyxnb.module_user.ModuleInit"

    //视频模块
    private const val VIDEO_INIT = "com.yyxnb.module_video.ModuleInit"

    //音乐模块
    private const val MUSIC_INIT = "com.yyxnb.module_music.ModuleInit"

    //小说模块
    private const val NOVEL_INIT = "com.yyxnb.module_novel.ModuleInit"

    //消息模块
    private const val NEWS_INIT = "com.yyxnb.module_chat.ModuleInit"

    //笑话模块
    private const val JOKE_INIT = "com.yyxnb.module_joke.ModuleInit"

    //玩安卓模块
    private const val WAN_INIT = "com.yyxnb.module_wanandroid.ModuleInit"

    //功能测试模块
    private const val WIDGET_INIT = "com.yyxnb.module_widget.ModuleInit"
    var initModuleNames = arrayOf(
            MAIN_INIT, LOGIN_INIT, USER_INIT, VIDEO_INIT, MUSIC_INIT, NOVEL_INIT, NEWS_INIT, JOKE_INIT, WAN_INIT, WIDGET_INIT
    )
}