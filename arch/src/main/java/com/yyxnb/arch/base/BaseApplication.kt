package com.yyxnb.arch.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.jeremyliao.liveeventbus.LiveEventBus
import com.yyxnb.arch.AppUtils
import com.yyxnb.arch.utils.log.LogUtils


/**
 * Description: BaseApplication
 *
 * @author : yyx
 * @date ：2018/6/11
 */
open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //突破65535的限制
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        AppUtils.init(this)

        LiveEventBus
                .config()
                .supportBroadcast(applicationContext)
                .lifecycleObserverAlwaysActive(true)
                .autoClear(false)
        LogUtils.init()
                .setTag("Test")//设置全局tag
                .setShowThreadInfo(true).setDebug(AppUtils.isDebug) //是否显示日志，默认true，发布时最好关闭

    }

}
