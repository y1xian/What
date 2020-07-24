package com.yyxnb.common;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.yyxnb.common.log.LogUtils;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

@SuppressWarnings("rawtypes")
public final class AppConfig implements Serializable {

    @SuppressLint("StaticFieldLeak")
    private volatile static AppConfig appConfig;

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        if (appConfig == null) {
            synchronized (AppConfig.class) {
                if (appConfig == null) {
                    appConfig = new AppConfig();
                }
            }
        }
        return appConfig;
    }

    /**
     * 避免序列化破坏单例模式
     */
    private Object readResolve() {
        return appConfig;
    }

    private WeakReference<Application> app;

    @SuppressWarnings("unchecked")
    public Application getApp() {
        if (app == null) {
            synchronized (AppConfig.class) {
                if (app == null) {
                    app = new WeakReference(AppGlobals.getApplication());
                }
            }
        }
        return app.get();
    }

    /**
     * 获取ApplicationContext
     */
    private WeakReference<Context> context;

    @SuppressWarnings("unchecked")
    public Context getContext() {
        if (context == null) {
            synchronized (AppConfig.class) {
                if (context == null) {
                    context = new WeakReference(getApp().getApplicationContext());
                }
            }
        }
        return context.get();
    }

    public void toast(String s) {
        ToastUtils.normal(s);
    }

    public void log(String tag, String s) {
        if (isDebug()) {
            LogUtils.w(s, tag);
        }
    }

    public void log(String s) {
        if (isDebug()) {
            log("------AppConfig------", s);
        }
    }

    /**
     * 判断当前应用是否是debug状态
     */
    public boolean isDebug() {
        try {
            ApplicationInfo info = getContext().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 返回实例的泛型类型
     */
    public <T> T getInstance(Object t, int i) {
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

    public <T> Class<T> getClass(Object t) {
        // 通过反射 获取当前类的父类的泛型 (T) 对应 Class类
        return (Class<T>) ((ParameterizedType) t.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public <T> Class<T> getFiledClazz(Field field) {
        return (Class<T>) field.getGenericType();
    }

}
