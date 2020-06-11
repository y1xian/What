package com.yyxnb.module_base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.squareup.leakcanary.LeakCanary;
import com.yyxnb.common.AppConfig;
import com.yyxnb.module_base.module.ModuleLifecycleConfig;

/**
 * debug包下的代码不参与编译，仅作为独立模块运行时初始化数据
 *
 * @author yyx
 */

public class DebugApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化阿里路由框架
        if (AppConfig.getInstance().isDebug()) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);

        //初始化组件
        ModuleLifecycleConfig.getInstance().initModule(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

}
