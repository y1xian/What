package com.yyxnb.what.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/01/23
 * 描    述：AppUtils
 * ================================================
 */
public final class AppUtils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApp;
    private static boolean sFirstRun = true;

    public static void init(final Application app) {
        if (app == null) {
            Log.e("AppUtils", "app is null.");
            return;
        }
        sFirstRun = false;
        if (sApp == null) {
            sApp = app;
            return;
        }
        if (sApp.equals(app)) {
            return;
        }
        sApp = app;
    }

    public static Application getApp() {

        if (sApp != null) {
            return sApp;
        }
        sApp = AppGlobals.getApplication();
        if (sApp == null) {
            throw new NullPointerException("application反射获取失败");
        }
        return sApp;
    }


    public static boolean isActivityAlive(final Activity activity) {
        return activity != null && !activity.isFinishing() && !activity.isDestroyed();
    }

    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isDebug() {
        try {
            ApplicationInfo info = getApp().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 返回实例的泛型类型
     */
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static <T> T getInstance(Object t, int i) {
        if (t != null) {
            try {
                return (T) ((ParameterizedType) t.getClass().getGenericSuperclass())
                        .getActualTypeArguments()[i];

            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static <T> Class<T> getClass(Object t) {
        // 通过反射 获取当前类的父类的泛型 (T) 对应 Class类
        return (Class<T>) ((ParameterizedType) t.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getFiledClazz(Field field) {
        return (Class<T>) field.getGenericType();
    }

    public static boolean isFirstRun() {
        return sFirstRun;
    }

    public static class ActivityLifecycleCallbacks {

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {/**/}

        public void onActivityStarted(Activity activity) {/**/}

        public void onActivityResumed(Activity activity) {/**/}

        public void onActivityPaused(Activity activity) {/**/}

        public void onActivityStopped(Activity activity) {/**/}

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {/**/}

        public void onActivityDestroyed(Activity activity) {/**/}

    }
}
