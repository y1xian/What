package com.yyxnb.what.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import androidx.annotation.NonNull;

import com.yyxnb.what.app.AppUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 服务
 */
public final class ServiceUtils {

    private ServiceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取所有运行的服务
     */
    public static Set getAllRunningServices() {
        ActivityManager am = (ActivityManager) AppUtils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
        Set<String> names = new HashSet<>();
        if (info == null || info.size() == 0) {
            return null;
        }
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            names.add(aInfo.service.getClassName());
        }
        return names;
    }

    /**
     * 启动服务
     */
    public static void startService(@NonNull final String className) {
        try {
            startService(Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动服务
     */
    public static void startService(@NonNull final Class<?> cls) {
        Intent intent = new Intent(AppUtils.getApp(), cls);
        AppUtils.getApp().startService(intent);
    }

    /**
     * 停止服务
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean stopService(@NonNull final String className) {
        try {
            return stopService(Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 停止服务
     */
    public static boolean stopService(@NonNull final Class<?> cls) {
        Intent intent = new Intent(AppUtils.getApp(), cls);
        return AppUtils.getApp().stopService(intent);
    }

    /**
     * 绑定服务
     * <ul>
     * <li>0</li>
     * <li>{@link Context#BIND_AUTO_CREATE}</li>
     * <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     * <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     * <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     * <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     * <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     * </ul>
     */
    public static void bindService(@NonNull final String className,
                                   @NonNull final ServiceConnection conn,
                                   final int flags) {
        try {
            bindService(Class.forName(className), conn, flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定服务
     * <ul>
     * <li>0</li>
     * <li>{@link Context#BIND_AUTO_CREATE}</li>
     * <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     * <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     * <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     * <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     * <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     * </ul>
     */
    public static void bindService(@NonNull final Class<?> cls,
                                   @NonNull final ServiceConnection conn,
                                   final int flags) {
        Intent intent = new Intent(AppUtils.getApp(), cls);
        AppUtils.getApp().bindService(intent, conn, flags);
    }

    /**
     * 解绑服务
     */
    public static void unbindService(@NonNull final ServiceConnection conn) {
        AppUtils.getApp().unbindService(conn);
    }

    /**
     * 判断服务是否运行
     */
    public static boolean isServiceRunning(@NonNull final Class<?> cls) {
        return isServiceRunning(cls.getName());
    }

    /**
     * 判断服务是否运行
     */
    public static boolean isServiceRunning(@NonNull final String className) {
        ActivityManager am = (ActivityManager) AppUtils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
        if (info == null || info.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}