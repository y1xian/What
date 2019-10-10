package com.yyxnb.arch.base

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.FragmentActivity
import com.yyxnb.arch.interfaces.IDefaultActivityLifecycleCallbacks
import com.yyxnb.arch.utils.ActivityManagerUtils

/**
 * 管理Activity生命周期
 */
class ManagerActivityLifecycleCallbacksImplI :
        IDefaultActivityLifecycleCallbacks {

    companion object {
        private val handler = Handler(Looper.getMainLooper())
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        activity?.let {
            handler.post {
                ActivityManagerUtils.pushActivity(it)
                (it as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(
                        ManagerFragmentLifecycleCallbacksImpl(),
                        true
                )
            }
        }

    }

    override fun onActivityDestroyed(activity: Activity?) {
        activity?.let {
            handler.post {
                ActivityManagerUtils.deleteActivity(it)
            }
        }
    }
}