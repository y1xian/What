package com.yyxnb.arch.delegate;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.LruCache;

import com.yyxnb.arch.base.IActivity;
import com.yyxnb.arch.utils.AppManager;

public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private volatile static ActivityLifecycle lifecycle = null;

    public static ActivityLifecycle getInstance() {
        if (lifecycle == null) {
            synchronized (ActivityLifecycle.class) {
                if (lifecycle == null) {
                    lifecycle = new ActivityLifecycle();
                }
            }
        }
        return lifecycle;
    }

    private LruCache<String, IActivityDelegate> cache = new LruCache<>(100);

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity != null) {
            AppManager.getInstance().addActivity(activity);
        }

        if (activity instanceof IActivity) {

            IActivityDelegate activityDelegate = null;

            if (fetchActivityDelegate(activity) != null) {
                activityDelegate = fetchActivityDelegate(activity);
            } else {
                activityDelegate = newDelegate(activity);
            }

            cache.put(getKey(activity), activityDelegate);

            activityDelegate.onCreate(savedInstanceState);

            registerFragmentCallback(activity);
        }

    }

    private void registerFragmentCallback(Activity activity) {

        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(FragmentLifecycle.getInstance(), true);
        }

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (fetchActivityDelegate(activity) != null) {
            fetchActivityDelegate(activity).onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (fetchActivityDelegate(activity) != null) {
            fetchActivityDelegate(activity).onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (fetchActivityDelegate(activity) != null) {
            fetchActivityDelegate(activity).onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (fetchActivityDelegate(activity) != null) {
            fetchActivityDelegate(activity).onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if (fetchActivityDelegate(activity) != null) {
            fetchActivityDelegate(activity).onSaveInstanceState(activity, outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity != null) {
            AppManager.getInstance().removeActivity(activity);
        }
        if (fetchActivityDelegate(activity) != null) {
            fetchActivityDelegate(activity).onDestroy();
        }
    }

    private IActivityDelegate fetchActivityDelegate(Activity activity) {

        if (activity instanceof IActivity) {
            return cache.get(getKey(activity));
        }
        return null;
    }

    private IActivityDelegate newDelegate(Activity activity) {

        return new ActivityDelegateImpl((FragmentActivity) activity);
    }

    private String getKey(Activity activity) {
        return activity.getClass().getName() + activity.hashCode();
    }
}
