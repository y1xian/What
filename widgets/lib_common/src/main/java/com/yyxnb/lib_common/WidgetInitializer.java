package com.yyxnb.lib_common;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDex;
import androidx.startup.Initializer;

import com.yyxnb.util_app.AppUtils;

import java.util.Collections;
import java.util.List;


/**
 * 使用ContentProvider初始化三方库
 *
 * @author yyx
 */
public class WidgetInitializer implements Initializer<Object> {

    @NonNull
    @Override
    public Object create(@NonNull Context context) {


        Log.e("WidgetInitializer", "第一个初始化的存在");

        AppUtils.init((Application) context);

        WidgetManager.getInstance().unInit(AppUtils.getApp());
        WidgetManager.getInstance().init(AppUtils.getApp());

        // 应用监听
        ProcessLifecycleOwner.get().getLifecycle().addObserver(AppLifeObserver.getInstance());

        // 突破65535的限制
        MultiDex.install(AppUtils.getApp());

        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
