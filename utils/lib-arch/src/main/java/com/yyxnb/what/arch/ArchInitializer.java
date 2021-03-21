package com.yyxnb.what.arch;

import android.arch.lifecycle.ProcessLifecycleOwner;
import android.util.Log;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.yyxnb.what.app.AppUtils;
import com.yyxnb.what.arch.delegate.ActivityLifecycle;
import com.yyxnb.what.core.CoreInitializer;
import com.yyxnb.what.core.WidgetManager;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/21
 * 历    史：
 * 描    述：使用ContentProvider初始化三方库
 * ================================================
 */
public class ArchInitializer extends CoreInitializer {
    @Override
    public boolean onCreate() {
        super.onCreate();

        Log.e("WidgetInitializer", "第二个初始化的存在");

        // 系统会在每个 Activity 执行完对应的生命周期后都调用这个实现类中对应的方法
        WidgetManager.getInstance().setLifecycleCallbacks(ActivityLifecycle.getInstance());

        // 应用监听
        ProcessLifecycleOwner.get().getLifecycle().addObserver(AppLifeObserver.getInstance());

        LiveEventBus
                .config()
                .enableLogger(AppUtils.isDebug());

        return true;
    }

}
