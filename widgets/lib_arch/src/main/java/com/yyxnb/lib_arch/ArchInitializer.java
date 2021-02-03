package com.yyxnb.lib_arch;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.yyxnb.lib_arch.delegate.ActivityLifecycle;
import com.yyxnb.lib_common.WidgetInitializer;
import com.yyxnb.lib_common.WidgetManager;
import com.yyxnb.util_app.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/21
 * 历    史：
 * 描    述：使用ContentProvider初始化三方库
 * ================================================
 */
public class ArchInitializer implements Initializer {

    @NonNull
    @Override
    public Object create(@NonNull Context context) {

        Log.e("ArchInitializer", "第二个初始化的存在");

        // 系统会在每个 Activity 执行完对应的生命周期后都调用这个实现类中对应的方法
        WidgetManager.getInstance().setLifecycleCallbacks(ActivityLifecycle.getInstance());

        LiveEventBus
                .config()
                .enableLogger(AppUtils.isDebug());

        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        List<Class<? extends Initializer<?>>> dependencies = new ArrayList<>();
        dependencies.add(WidgetInitializer.class);
        return dependencies;
    }
}
