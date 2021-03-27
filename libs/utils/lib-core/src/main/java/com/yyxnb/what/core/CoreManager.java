package com.yyxnb.what.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.yyxnb.what.app.AppUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * app 管理
 *
 * @author yyx
 */
@SuppressWarnings("rawtypes")
public final class CoreManager implements Application.ActivityLifecycleCallbacks, Serializable {

    @SuppressLint("StaticFieldLeak")
    private volatile static CoreManager coreManager;

    public boolean mIsBackground = false;

    private CoreManager() {
    }

    public static CoreManager getInstance() {
        if (coreManager == null) {
            synchronized (CoreManager.class) {
                if (coreManager == null) {
                    coreManager = new CoreManager();
                }
            }
        }
        return coreManager;
    }

    /**
     * 避免序列化破坏单例模式
     */
    private Object readResolve() {
        return coreManager;
    }

    public void init(Application app) {
        app.registerActivityLifecycleCallbacks(this);
    }

    public void unInit(Application app) {
        mActivityList.clear();
        app.unregisterActivityLifecycleCallbacks(this);
    }

    public Activity getTopActivity() {
        List<Activity> activityList = getActivityList();
        for (Activity activity : activityList) {
            if (!AppUtils.isActivityAlive(activity)) {
                continue;
            }
            return activity;
        }
        return null;
    }

    public List<Activity> getActivityList() {
        if (!mActivityList.isEmpty()) {
            return mActivityList;
        }
        List<Activity> reflectActivities = getActivitiesByReflect();
        mActivityList.addAll(reflectActivities);
        return mActivityList;
    }

    private void setTopActivity(final Activity activity) {
        if (mActivityList.contains(activity)) {
            if (!mActivityList.getFirst().equals(activity)) {
                mActivityList.remove(activity);
                mActivityList.addFirst(activity);
            }
        } else {
            mActivityList.addFirst(activity);
        }
    }

    // === 生命周期

    private final LinkedList<Activity> mActivityList = new LinkedList<>();
    private AppUtils.ActivityLifecycleCallbacks lifecycleCallbacks = new AppUtils.ActivityLifecycleCallbacks();

    public void setLifecycleCallbacks(AppUtils.ActivityLifecycleCallbacks lifecycleCallbacks) {
        this.lifecycleCallbacks = lifecycleCallbacks;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setTopActivity(activity);
        lifecycleCallbacks.onActivityCreated(activity, savedInstanceState);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (!mIsBackground) {
            setTopActivity(activity);
        }
        lifecycleCallbacks.onActivityStarted(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        setTopActivity(activity);
        if (mIsBackground) {
            mIsBackground = false;
        }
        lifecycleCallbacks.onActivityResumed(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        lifecycleCallbacks.onActivityPaused(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        lifecycleCallbacks.onActivityStopped(activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        lifecycleCallbacks.onActivitySaveInstanceState(activity, outState);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mActivityList.remove(activity);
        lifecycleCallbacks.onActivityDestroyed(activity);
    }

    /**
     * @return the activities which topActivity is first position
     */
    private List<Activity> getActivitiesByReflect() {
        LinkedList<Activity> list = new LinkedList<>();
        Activity topActivity = null;
        try {
            Object activityThread = getActivityThread();
            Field mActivitiesField = activityThread.getClass().getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            Object mActivities = mActivitiesField.get(activityThread);
            if (!(mActivities instanceof Map)) {
                return list;
            }
            Map<Object, Object> binder_activityClientRecord_map = (Map<Object, Object>) mActivities;
            for (Object activityRecord : binder_activityClientRecord_map.values()) {
                Class activityClientRecordClass = activityRecord.getClass();
                Field activityField = activityClientRecordClass.getDeclaredField("activity");
                activityField.setAccessible(true);
                Activity activity = (Activity) activityField.get(activityRecord);
                if (topActivity == null) {
                    Field pausedField = activityClientRecordClass.getDeclaredField("paused");
                    pausedField.setAccessible(true);
                    if (!pausedField.getBoolean(activityRecord)) {
                        topActivity = activity;
                    } else {
                        list.add(activity);
                    }
                } else {
                    list.add(activity);
                }
            }
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivitiesByReflect: " + e.getMessage());
        }
        if (topActivity != null) {
            list.addFirst(topActivity);
        }
        return list;
    }


    private Object getActivityThread() {
        Object activityThread = getActivityThreadInActivityThreadStaticField();
        if (activityThread != null) {
            return activityThread;
        }
        activityThread = getActivityThreadInActivityThreadStaticMethod();
        if (activityThread != null) {
            return activityThread;
        }
        return getActivityThreadInLoadedApkField();
    }

    private Object getActivityThreadInActivityThreadStaticField() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            return sCurrentActivityThreadField.get(null);
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticField: " + e.getMessage());
            return null;
        }
    }

    private Object getActivityThreadInActivityThreadStaticMethod() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            return activityThreadClass.getMethod("currentActivityThread").invoke(null);
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticMethod: " + e.getMessage());
            return null;
        }
    }

    private Object getActivityThreadInLoadedApkField() {
        try {
            Field mLoadedApkField = Application.class.getDeclaredField("mLoadedApk");
            mLoadedApkField.setAccessible(true);
            Object mLoadedApk = mLoadedApkField.get(AppUtils.getApp());
            Field mActivityThreadField = mLoadedApk.getClass().getDeclaredField("mActivityThread");
            mActivityThreadField.setAccessible(true);
            return mActivityThreadField.get(mLoadedApk);
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInLoadedApkField: " + e.getMessage());
            return null;
        }
    }
}
