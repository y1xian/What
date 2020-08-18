package com.yyxnb.arch;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.yyxnb.arch.common.ArchConfig;
import com.yyxnb.arch.delegate.ActivityLifecycle;
import com.yyxnb.common.CommonManager;
import com.yyxnb.common.CommonInitializer;

import java.util.Arrays;
import java.util.List;

/**
 * 使用ContentProvider初始化三方库
 *
 * @author yyx
 */
public class ArchInitializer implements Initializer<ArchConfig> {
    @NonNull
    @Override
    public ArchConfig create(@NonNull Context context) {

        // 系统会在每个 Activity 执行完对应的生命周期后都调用这个实现类中对应的方法
        CommonManager.INSTANCE.getApp().registerActivityLifecycleCallbacks(ActivityLifecycle.getInstance());

        LiveEventBus
                .config()
                .enableLogger(CommonManager.INSTANCE.isDebug());

        return new ArchConfig();
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Arrays.asList(CommonInitializer.class);
    }
}
