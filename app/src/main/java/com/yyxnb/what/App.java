package com.yyxnb.what;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.tencent.bugly.beta.Beta;
import com.yyxnb.module_base.DebugApplication;
import com.yyxnb.module_base.module.ModuleLifecycleConfig;

public class App extends DebugApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化组件
        ModuleLifecycleConfig.getInstance().initModule(this);

//        Bugly.init(this, "d2b312bc9b", BuildConfig.DEBUG);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);


        // 安装tinker
        Beta.installTinker();
    }
}
