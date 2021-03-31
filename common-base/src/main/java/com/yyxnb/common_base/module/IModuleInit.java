package com.yyxnb.common_base.module;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * 代理{@link Application} 的生命周期
 * 有些特殊功能的初始化需要在 Application 中去做，但是这些功能并非全部业务组件都用到的东西，放到 BaseApplication 不合适
 * 动态配置Application，有需要初始化的组件实现该接口，统一在主app的Application中初始化
 *
 * @author yyx
 */
public interface IModuleInit {

    /**
     * 在{@link Application#attachBaseContext(Context)} 中执行
     *
     * @param base
     */
    void attachBaseContext(@NonNull Context base);

    /**
     * 在{@link Application#onCreate()} 中执行
     */
    void onCreate();

    /**
     * 在{@link Application#onTerminate()} 中执行
     * 注意点：该方法不会在真机上执行
     */
    void onTerminate();

    /**
     * 在{@link Application#onLowMemory()} 中执行
     */
    void onLowMemory();

    /**
     * 在{@link Application#onTrimMemory(int)} 中执行
     */
    void onTrimMemory(int level);

    /**
     * 应用退出
     */
    void onDestroy();

}
