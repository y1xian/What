package com.yyxnb.arch

import android.content.Context
import androidx.startup.Initializer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.yyxnb.arch.common.ArchConfig
import com.yyxnb.arch.delegate.ActivityLifecycle
import com.yyxnb.common.CommonInitializer
import com.yyxnb.widget.WidgetInitializer
import com.yyxnb.widget.WidgetManager.getApp
import com.yyxnb.widget.WidgetManager.isDebug

/**
 * 使用ContentProvider初始化三方库
 *
 * @author yyx
 */
class ArchInitializer : Initializer<ArchConfig> {
    override fun create(context: Context): ArchConfig {

        // 系统会在每个 Activity 执行完对应的生命周期后都调用这个实现类中对应的方法
        getApp().registerActivityLifecycleCallbacks(ActivityLifecycle)
        LiveEventBus
                .config()
                .enableLogger(isDebug)
        return ArchConfig
    }

    override fun dependencies(): List<Class<out Initializer<*>?>> {
        return listOf(WidgetInitializer::class.java, CommonInitializer::class.java)
    }
}