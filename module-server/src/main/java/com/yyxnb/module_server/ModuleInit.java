package com.yyxnb.module_server;

import android.app.Application;

import com.yyxnb.common_base.module.IModuleInit;
import com.yyxnb.module_server.service.ServerManager;

public class ModuleInit implements IModuleInit {

    @Override
    public void onCreate(Application application) {
        ServerManager.getInstance().register();
        ServerManager.getInstance().startServer();
    }
}
