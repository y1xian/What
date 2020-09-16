package com.yyxnb.module_wanandroid;

import android.app.Application;

import com.yyxnb.common_base.module.IModuleInit;
import com.yyxnb.network.interceptor.weaknetwork.WeakNetworkManager;

public class ModuleInit implements IModuleInit {

    @Override
    public void onCreate(Application application) {
        WeakNetworkManager.get().setActive(false);
        WeakNetworkManager.get().setParameter(5000, 1, 1);
        WeakNetworkManager.get().setType(WeakNetworkManager.TYPE_SPEED_LIMIT);
    }
}
