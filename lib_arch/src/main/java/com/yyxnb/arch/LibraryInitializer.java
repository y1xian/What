package com.yyxnb.arch;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.yyxnb.arch.delegate.ActivityLifecycle;
import com.yyxnb.common.AppConfig;

/**
 * 使用ContentProvider初始化三方库
 *
 * @author yyx
 */
public class LibraryInitializer extends ContentProvider {
    @Override
    public boolean onCreate() {
        // 初始化
        Context context = AppConfig.getInstance().getContext();

        if (context != null) {

            // 突破65535的限制
            MultiDex.install(context);

            // 系统会在每个 Activity 执行完对应的生命周期后都调用这个实现类中对应的方法
            AppConfig.getInstance().getApp().registerActivityLifecycleCallbacks(ActivityLifecycle.getInstance());

            LiveEventBus
                    .config()
                    .enableLogger(AppConfig.getInstance().isDebug());

        }


        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
