package com.yyxnb.what.core;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.startup.Initializer;

import com.yyxnb.what.app.AppUtils;

import java.util.Collections;
import java.util.List;

/**
 * 使用ContentProvider初始化三方库
 *
 * @author yyx
 */
public class CoreInitializer implements Initializer<Void> {

    @NonNull
    @Override
    public Void create(@NonNull Context context) {
        Log.e("CoreInitializer", "第一个初始化的存在");
        AppUtils.init((Application) context);

        CoreManager.getInstance().unInit(AppUtils.getApp());
        CoreManager.getInstance().init(AppUtils.getApp());

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
