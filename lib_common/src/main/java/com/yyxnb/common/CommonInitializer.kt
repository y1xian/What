package com.yyxnb.common

import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import androidx.startup.Initializer
import com.yyxnb.common.utils.log.LogUtils
import java.util.*

/**
 * 使用ContentProvider初始化三方库
 *
 * @author yyx
 */
class CommonInitializer : Initializer<CommonManager> {
    override fun create(context: Context): CommonManager {

        // 突破65535的限制
        MultiDex.install(context)

        LogUtils.init()
                //是否显示日志，默认true，发布时最好关闭
                .setShowThreadInfo(CommonManager.isDebug).isDebug = CommonManager.isDebug

        // 应用监听
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifeObserver)

        return CommonManager
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return Collections.emptyList()
    }
}