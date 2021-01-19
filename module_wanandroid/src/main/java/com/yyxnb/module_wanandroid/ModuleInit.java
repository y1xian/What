package com.yyxnb.module_wanandroid;

import android.app.Application;

import com.yyxnb.common_base.module.IModuleInit;
import com.yyxnb.lib_okhttp.interceptor.weaknetwork.WeakNetworkManager;

public class ModuleInit implements IModuleInit {

    @Override
    public void onCreate(Application application) {
        WeakNetworkManager.getInstance()
                .setActive(false)
                .setParameter(5000, 5, 5)
                .setType(WeakNetworkManager.TYPE_TIMEOUT);
    }
}
