package com.yyxnb.common_base.module;

/**
 * 组件生命周期反射类名管理，在这里注册需要初始化的组件，通过反射动态调用各个组件的初始化方法
 * 注意：以下模块中初始化的Module类不能被混淆
 */
public class ModuleLifecycleReflexs {

    private static final String APP_ID = "com.yyxnb";

    // 主业务模块
    private static final String MAIN_INIT = APP_ID + ".module_main.ModuleInit";
    // 登录注册模块
    private static final String LOGIN_INIT = APP_ID + ".module_login.ModuleInit";
    // 用户业务模块
    private static final String USER_INIT = APP_ID + ".module_user.ModuleInit";
    // 视频模块
    private static final String VIDEO_INIT = APP_ID + ".module_video.ModuleInit";
    // 音乐模块
    private static final String MUSIC_INIT = APP_ID + ".module_music.ModuleInit";
    // 小说模块
    private static final String NOVEL_INIT = APP_ID + ".module_novel.ModuleInit";
    // 消息模块
    private static final String CHAT_INIT = APP_ID + ".module_chat.ModuleInit";
    // 笑话模块
    private static final String JOKE_INIT = APP_ID + ".module_joke.ModuleInit";
    // 玩安卓模块
    private static final String WAN_INIT = APP_ID + ".module_wanandroid.ModuleInit";
    // 新闻模块
    private static final String NEWS_INIT = APP_ID + ".module_news.ModuleInit";
    // 商城模块
    private static final String MALL_INIT = APP_ID + ".module_mall.ModuleInit";
    // 直播模块
    private static final String LIVE_INIT = APP_ID + ".module_live.ModuleInit";
    // 功能测试模块
    private static final String WIDGET_INIT = APP_ID + ".module_widget.ModuleInit";
    // http api
    private static final String SERVER_INIT = APP_ID + ".module_server.ModuleInit";

    public static String[] initModuleNames = {
            MAIN_INIT
            , LOGIN_INIT
            , USER_INIT
            , VIDEO_INIT
            , MUSIC_INIT
            , NOVEL_INIT
            , CHAT_INIT
            , JOKE_INIT
            , WAN_INIT
            , NEWS_INIT
            , MALL_INIT
            , LIVE_INIT
            , WIDGET_INIT
            , SERVER_INIT
    };
}
