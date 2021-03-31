package com.yyxnb.common_base;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDex;

import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.yyxnb.common_base.base.ContainerActivity;
import com.yyxnb.common_base.module.ModuleLifecycleConfig;
import com.yyxnb.what.app.AppUtils;
import com.yyxnb.what.arch.annotations.SwipeStyle;
import com.yyxnb.what.arch.config.AppManager;
import com.yyxnb.what.arch.config.ArchConfig;
import com.yyxnb.what.arch.config.ArchManager;
import com.yyxnb.what.core.UITask;
import com.yyxnb.what.image.ImageManager;

import me.jessyan.autosize.AutoSizeConfig;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/12/02
 * 描    述：BaseApplication
 * ================================================
 */
public class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ModuleLifecycleConfig.getInstance().initModule(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UITask.run(() -> {
            // 布局
            AutoSizeConfig.getInstance()
                    //按照宽度适配 默认true
                    .setBaseOnWidth(true)
                    //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
                    //如果没有这个需求建议不开启
                    .setCustomFragment(true);
            // 侧滑监听
            AppUtils.getApp().registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
        });
        UITask.run(() -> {
            // 图片框架 集成glide
            ImageManager.getInstance().init(this.getApplicationContext());

            // 框架配置
            ArchConfig archConfig = new ArchConfig.Builder()
                    .setSwipeBack(SwipeStyle.EDGE)
                    .setContainerActivityClassName(ContainerActivity.class.getCanonicalName())
                    .build();
            ArchManager.getInstance().setConfig(archConfig);
        });

        ModuleLifecycleConfig.getInstance().onCreate();

        ProcessLifecycleOwner.get().getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onStop(@NonNull LifecycleOwner owner) {
                if (AppManager.getInstance().activityCount() == 0) {
                    ModuleLifecycleConfig.getInstance().onDestroy();
                }
            }
        });

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ModuleLifecycleConfig.getInstance().onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ModuleLifecycleConfig.getInstance().onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ModuleLifecycleConfig.getInstance().onTrimMemory(level);
    }
}
