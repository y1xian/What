package com.yyxnb.module_base.module;

/**
 * 组件生命周期反射类名管理，在这里注册需要初始化的组件，通过反射动态调用各个组件的初始化方法
 * 注意：以下模块中初始化的Module类不能被混淆
 */

public class ModuleLifecycleReflexs {

    //主业务模块
    private static final String MAIN_INIT = "com.yyxnb.module_main.ModuleInit";
    //登录注册模块
    private static final String LOGIN_INIT = "com.yyxnb.module_login.ModuleInit";
    //用户业务模块
    private static final String USER_INIT = "com.yyxnb.module_user.ModuleInit";

    public static String[] initModuleNames = {MAIN_INIT, LOGIN_INIT, USER_INIT};
}
