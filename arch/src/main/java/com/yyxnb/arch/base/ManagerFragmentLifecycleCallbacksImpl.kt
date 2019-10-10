package com.yyxnb.arch.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * 管理Fragment生命周期
 */
class ManagerFragmentLifecycleCallbacksImpl : FragmentManager.FragmentLifecycleCallbacks() {
    companion object {
        private val handler = Handler(Looper.getMainLooper())
    }

    override fun onFragmentCreated(
            fm: FragmentManager,
            f: Fragment,
            savedInstanceState: Bundle?
    ) {

    }

    override fun onFragmentActivityCreated(
            fm: FragmentManager,
            f: Fragment,
            savedInstanceState: Bundle?
    ) {
        handler.post {

        }
    }

    override fun onFragmentDestroyed(
            fm: FragmentManager,
            f: Fragment
    ) {

    }

}