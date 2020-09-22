package com.yyxnb.arch.delegate

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.util.LruCache
import androidx.fragment.app.FragmentActivity
import com.yyxnb.arch.base.IActivity
import com.yyxnb.arch.utils.AppManager.addActivity
import com.yyxnb.arch.utils.AppManager.removeActivity
import com.yyxnb.widget.AppUtils

/**
 * Activity 注册监听生命周期
 *
 * @author yyx
 */
object ActivityLifecycle : AppUtils.ActivityLifecycleCallbacks() {

    private val cache = LruCache<String, IActivityDelegate?>(100)

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        activity?.let { addActivity(it) }
        if (activity is IActivity) {
            var activityDelegate: IActivityDelegate? = null
            activityDelegate = if (fetchActivityDelegate(activity) != null) {
                fetchActivityDelegate(activity)
            } else {
                newDelegate(activity)
            }
            cache.put(getKey(activity), activityDelegate)
            activityDelegate!!.onCreate(savedInstanceState)
            registerFragmentCallback(activity)
        }
    }

    private fun registerFragmentCallback(activity: Activity?) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentLifecycle, true)
        }
    }

    override fun onActivityStarted(activity: Activity?) {
        fetchActivityDelegate(activity)?.apply { onStart() }
    }

    override fun onActivityResumed(activity: Activity?) {
        fetchActivityDelegate(activity)?.apply { onResume() }
    }

    override fun onActivityPaused(activity: Activity?) {
        fetchActivityDelegate(activity)?.apply { onPause() }
    }

    override fun onActivityStopped(activity: Activity?) {
        fetchActivityDelegate(activity)?.apply { onStop() }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        fetchActivityDelegate(activity)?.apply { onSaveInstanceState(activity, outState) }
    }

    override fun onActivityDestroyed(activity: Activity?) {
        activity?.let { removeActivity(it) }
        fetchActivityDelegate(activity)?.apply { onDestroy() }
    }

    private fun fetchActivityDelegate(activity: Activity?): IActivityDelegate? {
        return if (activity is IActivity) {
            cache[getKey(activity)]
        } else null
    }

    private fun newDelegate(activity: Activity): IActivityDelegate {
        return ActivityDelegateImpl(activity as FragmentActivity)
    }

    private fun getKey(activity: Activity): String {
        return activity.javaClass.name + activity.hashCode()
    }

}