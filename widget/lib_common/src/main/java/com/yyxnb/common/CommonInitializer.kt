package com.yyxnb.common

import android.content.Context
import androidx.multidex.MultiDex
import androidx.startup.Initializer
import com.yyxnb.common.utils.log.LogUtils
import com.yyxnb.widget.AppUtils
import com.yyxnb.widget.WidgetInitializer
import com.yyxnb.widget.WidgetManager
import java.util.*

/**
 * 使用ContentProvider初始化三方库
 *
 * @author yyx
 */
class CommonInitializer : Initializer<CommonManager> {
    override fun create(context: Context): CommonManager {

        LogUtils.init()
                //是否显示日志，默认true，发布时最好关闭
                .setShowThreadInfo(AppUtils.isDebug).isDebug = AppUtils.isDebug

        return CommonManager
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(WidgetInitializer::class.java)
    }
}