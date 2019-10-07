package com.yyxnb.arch.base.nav
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.yyxnb.arch.base.BaseFragment


object FragmentHelper {

    private val TAG = "Navigation"

    fun executePendingTransactionsSafe(fragmentManager: FragmentManager) {
        try {
            fragmentManager.executePendingTransactions()
        } catch (e: IllegalStateException) {
            Log.wtf(TAG, e)
        }

    }

//    @JvmOverloads
//    fun addFragment(fragmentManager: FragmentManager, containerId: Int, thatFragment: BaseFragment, animation: PresentAnimation = PresentAnimation.None) {
//        if (fragmentManager.isDestroyed) {
//            return
//        }
//        executePendingTransactionsSafe(fragmentManager)
//
//        val transaction = fragmentManager.beginTransaction()
//
//        transaction.setReorderingAllowed(true)
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//        val topFragment = fragmentManager.findFragmentById(containerId) as BaseFragment?
//        if (topFragment != null && topFragment.isAdded) {
//            topFragment.onHiddenChanged(true)
//            topFragment.setAnimation(animation)
//            transaction.hide(topFragment)
//        }
//        thatFragment.setAnimation(animation)
//        transaction.add(containerId, thatFragment, thatFragment.getSceneId())
//        transaction.commit()
//    }

    @JvmOverloads
    fun addFragmentToBackStack(fragmentManager: FragmentManager, containerId: Int, thatFragment: BaseFragment, animation: PresentAnimation = PresentAnimation.Push) {
        if (fragmentManager.isDestroyed) {
            return
        }
        executePendingTransactionsSafe(fragmentManager)

        val transaction = fragmentManager.beginTransaction()

//        transaction.setReorderingAllowed(true)
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        val topFragment = fragmentManager.findFragmentById(containerId) as BaseFragment?
        if (topFragment != null && topFragment.isAdded) {
            topFragment.userVisibleHint = false
            topFragment.onHiddenChanged(true)
            topFragment.setAnimation(animation)
            transaction.hide(topFragment)
        }
        thatFragment.setAnimation(animation)
        transaction.add(containerId, thatFragment, thatFragment.getSceneId())
        transaction.addToBackStack(thatFragment.getSceneId())
        transaction.commit()
    }

    @JvmOverloads
    fun addFragmentToAddedList(fragmentManager: FragmentManager, containerId: Int, fragment: BaseFragment, primary: Boolean = true) {
        if (fragmentManager.isDestroyed) {
            return
        }
        executePendingTransactionsSafe(fragmentManager)

        val transaction = fragmentManager.beginTransaction()
        transaction.add(containerId, fragment, fragment.getSceneId())
        if (primary) {
            transaction.setPrimaryNavigationFragment(fragment) // primary
        }
        transaction.commit()
    }

    //后一个
    fun getLatterFragment(fragmentManager: FragmentManager, fragment: BaseFragment): BaseFragment? {
        val count = fragmentManager.backStackEntryCount
        val index = findIndexAtBackStack(fragmentManager, fragment)
        if (index > -1 && index < count - 1) {
            val backStackEntry = fragmentManager.getBackStackEntryAt(index + 1)
            val latter = fragmentManager.findFragmentByTag(backStackEntry.name) as BaseFragment?
            if (latter != null && latter.isAdded) {
                return latter
            }
        }
        return null
    }

    //前一个
    fun getAheadFragment(fragmentManager: FragmentManager, fragment: BaseFragment): BaseFragment? {
        val count = fragmentManager.backStackEntryCount
        val index = findIndexAtBackStack(fragmentManager, fragment)
        if (index > 0 && index < count) {
            val backStackEntry = fragmentManager.getBackStackEntryAt(index - 1)
            val ahead = fragmentManager.findFragmentByTag(backStackEntry.name) as BaseFragment?
            if (ahead != null && ahead.isAdded) {
                return ahead
            }
        }
        return null
    }

    //栈内
    fun findIndexAtBackStack(fragmentManager: FragmentManager, fragment: BaseFragment): Int {
        val count = fragmentManager.backStackEntryCount
        var index = -1
        for (i in 0 until count) {
            val backStackEntry = fragmentManager.getBackStackEntryAt(i)
            val tag = fragment.tag
            if (tag != null && tag == backStackEntry.name) {
                index = i
            }
        }
        return index
    }

    //最后
    fun findDescendantFragment(fragmentManager: FragmentManager, tag: String): Fragment? {
        var target = fragmentManager.findFragmentByTag(tag)
        if (target == null) {
            val fragments = fragmentManager.fragments
            val count = fragments.size
            for (i in count - 1 downTo -1 + 1) {
                val f = fragments[i]
                if (f.isAdded) {
                    if (f is BaseFragment) {
                        if (f.getSceneId() == tag) {
                            target = f
                        }
                    }

                    if (target == null) {
                        target = findDescendantFragment(f.childFragmentManager, tag)
                    }

                    if (target != null) {
                        break
                    }
                }
            }
        }
        return target
    }

}
