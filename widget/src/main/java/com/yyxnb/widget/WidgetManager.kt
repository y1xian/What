package com.yyxnb.widget

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.yyxnb.widget.AppUtils.isActivityAlive
import java.util.*

/**
 * app 管理
 *
 * @author yyx
 */
object WidgetManager : Application.ActivityLifecycleCallbacks {


    var mIsBackground = false

    fun init(app: Application?) {
        app?.registerActivityLifecycleCallbacks(this)
    }

    fun unInit(app: Application?) {
        mActivityList.clear()
        app?.unregisterActivityLifecycleCallbacks(this)
    }

    // === 生命周期
    private val mActivityList = LinkedList<Activity>()
    private var lifecycleCallbacks = AppUtils.ActivityLifecycleCallbacks()

    fun setLifecycleCallbacks(lifecycleCallbacks: AppUtils.ActivityLifecycleCallbacks) {
        this.lifecycleCallbacks = lifecycleCallbacks
    }

    fun getTopActivity(): Activity? {
        val activityList = getActivityList()
        for (activity in activityList) {
            if (!isActivityAlive(activity)) {
                continue
            }
            return activity
        }
        return null
    }

    fun getActivityList(): List<Activity> {
        if (!mActivityList.isEmpty()) {
            return mActivityList
        }
        val reflectActivities: List<Activity> = getActivitiesByReflect()
        mActivityList.addAll(reflectActivities)
        return mActivityList
    }

    private fun setTopActivity(activity: Activity?) {
        if (mActivityList.contains(activity)) {
            if (mActivityList.first != activity) {
                mActivityList.remove(activity)
                mActivityList.addFirst(activity)
            }
        } else {
            mActivityList.addFirst(activity)
        }
    }

    //===== 生命周期

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        setTopActivity(activity)
        lifecycleCallbacks.onActivityCreated(activity, savedInstanceState)
    }

    override fun onActivityStarted(activity: Activity?) {
        if (!mIsBackground) {
            setTopActivity(activity)
        }
        lifecycleCallbacks.onActivityStarted(activity)
    }

    override fun onActivityResumed(activity: Activity?) {
        setTopActivity(activity)
        if (mIsBackground) {
            mIsBackground = false
        }
        lifecycleCallbacks.onActivityResumed(activity)
    }

    override fun onActivityPaused(activity: Activity?) {
        lifecycleCallbacks.onActivityPaused(activity)
    }

    override fun onActivityStopped(activity: Activity?) {
        lifecycleCallbacks.onActivityStopped(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        lifecycleCallbacks.onActivitySaveInstanceState(activity, outState)
    }

    override fun onActivityDestroyed(activity: Activity?) {
        mActivityList.remove(activity)
        lifecycleCallbacks.onActivityDestroyed(activity)
    }


    /**
     * @return the activities which topActivity is first position
     */
    private fun getActivitiesByReflect(): List<Activity> {
        val list = LinkedList<Activity>()
        var topActivity: Activity? = null
        try {
            val activityThread = getActivityThread()
            val mActivitiesField = activityThread!!.javaClass.getDeclaredField("mActivities")
            mActivitiesField.isAccessible = true
            val mActivities = mActivitiesField[activityThread] as? Map<*, *>
                    ?: return list
            val binder_activityClientRecord_map = mActivities as Map<Any, Any>
            for (activityRecord in binder_activityClientRecord_map.values) {
                val activityClientRecordClass: Class<*> = activityRecord.javaClass
                val activityField = activityClientRecordClass.getDeclaredField("activity")
                activityField.isAccessible = true
                val activity = activityField[activityRecord] as Activity
                if (topActivity == null) {
                    val pausedField = activityClientRecordClass.getDeclaredField("paused")
                    pausedField.isAccessible = true
                    if (!pausedField.getBoolean(activityRecord)) {
                        topActivity = activity
                    } else {
                        list.add(activity)
                    }
                } else {
                    list.add(activity)
                }
            }
        } catch (e: java.lang.Exception) {
            Log.e("UtilsActivityLifecycle", "getActivitiesByReflect: " + e.message)
        }
        if (topActivity != null) {
            list.addFirst(topActivity)
        }
        return list
    }


    private fun getActivityThread(): Any? {
        var activityThread = getActivityThreadInActivityThreadStaticField()
        if (activityThread != null) {
            return activityThread
        }
        activityThread = getActivityThreadInActivityThreadStaticMethod()
        return activityThread ?: getActivityThreadInLoadedApkField()
    }

    private fun getActivityThreadInActivityThreadStaticField(): Any? {
        return try {
            val activityThreadClass = Class.forName("android.app.ActivityThread")
            val sCurrentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread")
            sCurrentActivityThreadField.isAccessible = true
            sCurrentActivityThreadField[null]
        } catch (e: java.lang.Exception) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticField: " + e.message)
            null
        }
    }

    private fun getActivityThreadInActivityThreadStaticMethod(): Any? {
        return try {
            val activityThreadClass = Class.forName("android.app.ActivityThread")
            activityThreadClass.getMethod("currentActivityThread").invoke(null)
        } catch (e: java.lang.Exception) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticMethod: " + e.message)
            null
        }
    }

    private fun getActivityThreadInLoadedApkField(): Any? {
        return try {
            val mLoadedApkField = Application::class.java.getDeclaredField("mLoadedApk")
            mLoadedApkField.isAccessible = true
            val mLoadedApk = mLoadedApkField[AppUtils.app]
            val mActivityThreadField = mLoadedApk.javaClass.getDeclaredField("mActivityThread")
            mActivityThreadField.isAccessible = true
            mActivityThreadField[mLoadedApk]
        } catch (e: java.lang.Exception) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInLoadedApkField: " + e.message)
            null
        }
    }

}