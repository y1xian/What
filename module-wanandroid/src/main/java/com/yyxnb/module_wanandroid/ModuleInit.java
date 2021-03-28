package com.yyxnb.module_wanandroid;

import com.yyxnb.common_base.module.ModuleInitImpl;
import com.yyxnb.what.okhttp.interceptor.weaknetwork.WeakNetworkManager;

public class ModuleInit extends ModuleInitImpl {

    @Override
    public void onCreate() {
        WeakNetworkManager.getInstance()
                .setActive(false)
                .setParameter(5000, 5, 5)
                .setType(WeakNetworkManager.TYPE_TIMEOUT);
    }
}
