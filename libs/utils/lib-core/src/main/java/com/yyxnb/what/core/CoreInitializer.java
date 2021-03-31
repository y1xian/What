package com.yyxnb.what.core;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.yyxnb.what.app.AppUtils;

import java.util.Objects;

/**
 * 使用ContentProvider初始化三方库
 *
 * @author yyx
 */
public class CoreInitializer extends ContentProvider {
    @Override
    public boolean onCreate() {

        if (AppUtils.isFirstRun()) {
            Log.e("CoreInitializer", "第一个初始化的存在");
            AppUtils.init((Application) Objects.requireNonNull(getContext()).getApplicationContext());

            CoreManager.getInstance().unInit(AppUtils.getApp());
            CoreManager.getInstance().init(AppUtils.getApp());

            // 突破65535的限制
            MultiDex.install(AppUtils.getApp());
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
