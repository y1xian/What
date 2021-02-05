package com.yyxnb.what

import android.content.Context
import androidx.multidex.MultiDex
import com.tencent.bugly.beta.Beta
import com.yyxnb.common_res.ModuleApplication

class App : ModuleApplication() {
    override fun onCreate() {
        super.onCreate()
        //        Bugly.init(this, "d2b312bc9b", BuildConfig.DEBUG);
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base)

        // 安装tinker
        Beta.installTinker()
    }
}