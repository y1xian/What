package com.yyxnb.lib_widget;

import android.app.Application;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.yyxnb.util_app.AppUtils;

import java.util.Objects;


/**
 * 使用ContentProvider初始化三方库
 *
 * @author yyx
 */
public class WidgetInitializer extends ContentProvider {
    @Override
    public boolean onCreate() {

        Log.e("WidgetInitializer", "第一个初始化的存在");

        AppUtils.init((Application) Objects.requireNonNull(getContext()).getApplicationContext());

        WidgetManager.getInstance().unInit(AppUtils.getApp());
        WidgetManager.getInstance().init(AppUtils.getApp());

        // 应用监听
        ProcessLifecycleOwner.get().getLifecycle().addObserver(AppLifeObserver.getInstance());

        // 突破65535的限制
        MultiDex.install(AppUtils.getApp());


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
