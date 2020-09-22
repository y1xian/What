package com.yyxnb.module_wanandroid

import android.app.Application
import com.yyxnb.common_base.module.IModuleInit
import com.yyxnb.network.interceptor.weaknetwork.WeakNetworkManager

class ModuleInit : IModuleInit {
    override fun onCreate(application: Application) {
        WeakNetworkManager.isActive = false
        WeakNetworkManager.type = WeakNetworkManager.TYPE_SPEED_LIMIT
    }
}