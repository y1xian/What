package com.yyxnb.common_base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.yyxnb.common_base.core.ContainerActivity;
import com.yyxnb.common_base.module.ModuleLifecycleConfig;
import com.yyxnb.lib_arch.annotations.SwipeStyle;
import com.yyxnb.lib_arch.config.ArchConfig;
import com.yyxnb.lib_arch.config.ArchManager;
import com.yyxnb.lib_image_loader.ImageManager;
import com.yyxnb.util_app.AppUtils;
import com.yyxnb.util_core.UITask;

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

        //初始化组件
        ModuleLifecycleConfig.getInstance().initModule(this);
    }
}
