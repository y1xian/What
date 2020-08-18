package com.yyxnb.arch.delegate

import android.content.Context
import android.os.Bundle
import android.util.LruCache
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.yyxnb.arch.base.IFragment
import com.yyxnb.arch.utils.AppManager.addFragment
import com.yyxnb.arch.utils.AppManager.removeFragment

/**
 * Fragment 注册生命周期监听
 *
 * @author yyx
 */
object FragmentLifecycle : FragmentManager.FragmentLifecycleCallbacks() {

    private val cache = LruCache<String, IFragmentDelegate?>(100)

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        f?.let { addFragment(it) }
        if (f is IFragment) {
            val fragmentDelegate = fetchFragmentDelegate(f, fm)
            fragmentDelegate!!.onAttached(context)
        }
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f)!!.onCreated(savedInstanceState)
        }
    }

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f)!!.onViewCreated(v, savedInstanceState)
        }
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f)!!.onActivityCreated(savedInstanceState)
        }
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f)!!.onStarted()
        }
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f)!!.onResumed()
        }
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f)!!.onPaused()
        }
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f)!!.onStopped()
        }
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f)!!.onSaveInstanceState(outState)
        }
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f)!!.onViewDestroyed()
        }
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        f?.let { removeFragment(it) }
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f)!!.onDestroyed()
        }
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f)!!.onDetached()
        }
    }

    private fun fetchFragmentDelegate(fragment: Fragment, manager: FragmentManager): IFragmentDelegate? {
        var fragmentDelegate: IFragmentDelegate? = null
        fragmentDelegate = if (fetchFragmentDelegateFromCache(fragment) != null) {
            fetchFragmentDelegateFromCache(fragment)
        } else {
            newDelegate(manager, fragment)
        }
        cache.put(getKey(fragment), fragmentDelegate)
        return fragmentDelegate
    }

    private fun fetchFragmentDelegateFromCache(fragment: Fragment): IFragmentDelegate? {
        return if (fragment is IFragment) {
            cache[getKey(fragment)]
        } else null
    }

    private fun newDelegate(manager: FragmentManager, fragment: Fragment): IFragmentDelegate {
        return FragmentDelegateImpl(manager, fragment)
    }

    private fun getKey(fragment: Fragment): String {
        return fragment.javaClass.name + fragment.hashCode()
    }

}