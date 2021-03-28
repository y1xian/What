package com.yyxnb.module_server;

import com.yyxnb.common_base.module.ModuleInitImpl;

public class ModuleInit extends ModuleInitImpl {

    @Override
    public void onCreate() {
        ServerManager.getInstance().register();
        ServerManager.getInstance().startServer();
    }

    @Override
    public void onDestroy() {
        ServerManager.getInstance().unRegister();
        ServerManager.getInstance().stopServer();
    }

}
