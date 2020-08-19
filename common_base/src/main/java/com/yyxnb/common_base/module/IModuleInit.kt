package com.yyxnb.common_base.module

import android.app.Application

/**
 * 有些特殊功能的初始化需要在 Application 中去做，但是这些功能并非全部业务组件都用到的东西，放到 BaseApplication 不合适
 * 动态配置Application，有需要初始化的组件实现该接口，统一在主app的Application中初始化
 *
 * @author yyx
 */
interface IModuleInit {
    /**
     * //Module类的APP初始化
     *
     * @param application
     */
    fun onCreate(application: Application?)
}