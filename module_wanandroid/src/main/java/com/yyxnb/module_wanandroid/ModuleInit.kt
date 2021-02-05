package com.yyxnb.module_wanandroid

import android.app.Application
import com.yyxnb.common_base.module.IModuleInit
import com.yyxnb.util_okhttp.interceptor.weaknetwork.WeakNetworkManager

class ModuleInit : IModuleInit {
    override fun onCreate(application: Application) {
        WeakNetworkManager.getInstance()
                .setActive(false)
                .setParameter(5000, 5, 5)
                .type = WeakNetworkManager.TYPE_TIMEOUT
    }
}