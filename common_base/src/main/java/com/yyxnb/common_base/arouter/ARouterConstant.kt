package com.yyxnb.common_base.arouter

/**
 * 路由路径
 */
object ARouterConstant {

    // == 服务
    const val LOGIN_SERVICE = "/login/login_service"

    //====== login
    //跳转到登陆页面
    const val LOGIN_ACTIVITY = "/login/LoginActivity"
    const val LOGIN_FRAGMENT = "/login/LoginFragment"

    //跳转到注册页面
    const val REGISTER_ACTIVITY = "/login/RegisterActivity"

    //====== user
    //跳转到用户页面
    const val USER_FRAGMENT = "/user/UserFragment"

    //========== video
    //跳转到视频页面
    const val VIDEO_VIDEO = "/video/VideoActivity"
    const val VIDEO_MAIN_FRAGMENT = "/video/VideoMainFragment"

    //======= message
    //消息列表
    const val MESSAGE_MAIN = "/message/MessageActivity"
    const val MESSAGE_LIST_FRAGMENT = "/message/MessageListFragment"

    //======== joke
    const val JOKE_MAIN = "/joke/JokeActivity"
    const val JOKE_MAIN_FRAGMENT = "/joke/JokeMainFragment"
    const val JOKE_HOME_FRAGMENT = "/joke/JokeHomeFragment"

    // ====== wanandroid
    const val WAN_MAIN = "/wan/WanActivity"
    const val WAN_MAIN_FRAGMENT = "/wan/WanMainFragment"

    // ====== 音乐
    const val MUSIC_MAIN = "/music/MusicActivity"
    const val MUSIC_HOME_FRAGMENT = "/music/MusicHomeFragment"

    // ====== 小说
    const val NOVEL_MAIN = "/novel/NovelActivity"

    //==========其他
    const val WIDGET_MAIN = "/widget/WidgetMainActivity"

    //跳转到关于项目更多页面
    const val OTHER_ABOUT_ME = "/other/AboutMeActivity"

    //跳转到webView详情页面
    const val LIBRARY_WEB_VIEW = "/library/WebViewActivity"

    //跳转到意见反馈页面
    const val OTHER_FEEDBACK = "/other/MeFeedBackActivity"
}