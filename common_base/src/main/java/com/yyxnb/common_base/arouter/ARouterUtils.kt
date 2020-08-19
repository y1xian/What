package com.yyxnb.common_base.arouter

import com.alibaba.android.arouter.launcher.ARouter
import com.yyxnb.arch.base.IFragment

/**
 * ARouter路由跳转工具类
 */
object ARouterUtils {
    /**
     * 获取fragment
     */
    @JvmStatic
    fun navFragment(path: String?): IFragment {
        return ARouter.getInstance().build(path).navigation() as IFragment
    }

    @JvmStatic
    fun navActivity(path: String?) {
        ARouter.getInstance().build(path).navigation()
    }

    fun startFragment(aClass: Class<*>?) {}
}