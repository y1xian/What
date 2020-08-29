package com.yyxnb.widget

import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import androidx.startup.Initializer
import java.util.*

/**
 * 使用ContentProvider初始化三方库
 *
 * @author yyx
 */
class WidgetInitializer : Initializer<WidgetManager> {

    override fun create(context: Context): WidgetManager {
        // 初始化
        val context: Context = WidgetManager.getContext()
        if (context != null) {

            // 突破65535的限制
            MultiDex.install(context)

            // 应用监听
            ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifeObserver)

        }
        return WidgetManager
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return Collections.emptyList()
    }
}