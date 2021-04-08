package com.yyxnb.what.arch;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.startup.Initializer;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.yyxnb.what.app.AppUtils;
import com.yyxnb.what.arch.delegate.ActivityLifecycle;
import com.yyxnb.what.core.CoreInitializer;
import com.yyxnb.what.core.CoreManager;

import java.util.List;

import cn.hutool.core.collection.ListUtil;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/21
 * 历    史：
 * 描    述：使用ContentProvider初始化三方库
 * ================================================
 */
public class ArchInitializer implements Initializer<Void> {

    @NonNull
    @Override
    public Void create(@NonNull Context context) {
        Log.e("ArchInitializer", "第二个初始化的存在");

        // 系统会在每个 Activity 执行完对应的生命周期后都调用这个实现类中对应的方法
        CoreManager.getInstance().setLifecycleCallbacks(ActivityLifecycle.getInstance());

        // 应用监听
        ProcessLifecycleOwner.get().getLifecycle().addObserver(AppLifeObserver.getInstance());

        LiveEventBus
                .config()
                .enableLogger(AppUtils.isDebug());
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return ListUtil.list(true, CoreInitializer.class);
    }
}
