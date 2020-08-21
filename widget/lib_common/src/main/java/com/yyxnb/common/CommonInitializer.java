package com.yyxnb.common;

import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;

import com.yyxnb.common.utils.log.LogUtils;

/**
 * 使用ContentProvider初始化三方库
 *
 * @author yyx
 */
public class CommonInitializer extends ContentProvider {
    @Override
    public boolean onCreate() {
        // 初始化
        Context context = CommonManager.getInstance().getContext();

        if (context != null) {

            // 突破65535的限制
            MultiDex.install(context);

            // 应用监听
            ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppLifeObserver());

            LogUtils.init()
                    //设置全局tag
                    .setTag("---What---")
                    //是否显示日志，默认true，发布时最好关闭
                    .setShowThreadInfo(CommonManager.getInstance().isDebug())
                    .setDebug(CommonManager.getInstance().isDebug());

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
