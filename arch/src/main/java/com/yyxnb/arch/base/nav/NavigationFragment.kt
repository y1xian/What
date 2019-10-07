package com.yyxnb.arch.base.nav


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.yyxnb.arch.R
import com.yyxnb.arch.base.BaseFragment
import com.yyxnb.arch.utils.StatusBarUtils
import com.yyxnb.arch.utils.log.LogUtils

/**
 * NavigationFragment 支持 push、pop、replace
 *
 * @author : yyx
 * @date ：2019/4/15
 */
@Suppress("DEPRECATED_IDENTITY_EQUALS")
class NavigationFragment : BaseFragment(), SwipeBackLayout.SwipeListener {

    override fun initViewData() {
        super.initViewData()
        LogUtils.e("-----")
    }

    private var rootFragment: BaseFragment? = null

    override fun initLayoutResId(): Int {
        return if (isSwipeBackEnabled()) {
            R.layout.nav_fragment_navigation_swipe_back
        } else {
            R.layout.nav_fragment_navigation
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        if (isSwipeBackEnabled()) {
            swipeBackLayout = mRootView!!.findViewById(R.id.navigation_content)
            swipeBackLayout!!.setSwipeListener(this)
        }
    }

    var swipeBackLayout: SwipeBackLayout? = null
        private set

    fun getTopFragment(): BaseFragment? {
        return if (isAdded) {
            childFragmentManager.findFragmentById(R.id.navigation_content) as BaseFragment?
        } else null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            if (rootFragment == null) {
                throw IllegalArgumentException("必须通过 `setRootFragment` 指定 rootFragment")
            } else {
                setRootFragmentInternal(rootFragment!!)
                rootFragment = null
            }
        }
    }

    override fun isParentFragment(): Boolean {
        return true
    }

    override fun childFragmentForAppearance(): BaseFragment? {
        return getTopFragment()
    }

    override fun onBackPressed(): Boolean {
        val fragmentManager = childFragmentManager
        val count = fragmentManager.backStackEntryCount
        return if (count > 1) {
            val topFragment = getTopFragment()
            if (topFragment != null) {
                popFragment()
            }
            true
        } else {
            super.onBackPressed()
        }
    }

    fun setRootFragment(fragment: BaseFragment): BaseFragment {
        if (isAdded) {
            throw IllegalStateException("NavigationFragment 已经出于 added 状态，不可以再设置 rootFragment")
        }
        this.rootFragment = fragment
        return rootFragment!!
    }

    fun getRootFragment(): BaseFragment? {
        if (isAdded) {
            val fragmentManager = childFragmentManager
            val count = fragmentManager.backStackEntryCount
            if (count > 0) {
                val backStackEntry = fragmentManager.getBackStackEntryAt(0)
                return fragmentManager.findFragmentByTag(backStackEntry.name) as BaseFragment?
            }
        }
        return rootFragment
    }

    private fun setRootFragmentInternal(fragment: BaseFragment) {
        FragmentHelper.addFragmentToBackStack(childFragmentManager, R.id.navigation_content, fragment, PresentAnimation.Push)
    }

    @JvmOverloads
    fun pushFragment(fragment: BaseFragment, animated: Boolean = true) {
        scheduleTaskAtStarted(Runnable { pushFragmentInternal(fragment, animated) })
    }

    private fun pushFragmentInternal(fragment: BaseFragment, animated: Boolean) {
        FragmentHelper.addFragmentToBackStack(childFragmentManager, R.id.navigation_content, fragment, if (animated) PresentAnimation.Push else PresentAnimation.None)
    }

    @JvmOverloads
    fun popToFragment(fragment: BaseFragment, animated: Boolean = true) {
        scheduleTaskAtStarted(Runnable { popToFragmentInternal(fragment, animated) })
    }

    private fun popToFragmentInternal(fragment: BaseFragment?, animated: Boolean) {
        val fragmentManager = childFragmentManager
        FragmentHelper.executePendingTransactionsSafe(fragmentManager)

        val topFragment = getTopFragment()
        if (topFragment == null || topFragment === fragment) {
            return
        }
        topFragment.setAnimation(if (animated) PresentAnimation.Push else PresentAnimation.None)
        fragment!!.setAnimation(if (animated) PresentAnimation.Push else PresentAnimation.None)
        topFragment.onHiddenChanged(true)
        fragmentManager.popBackStack(fragment.getSceneId(), 0)
        FragmentHelper.executePendingTransactionsSafe(fragmentManager)

//        fragment.onFragmentResult(topFragment.getRequestCode(), topFragment.getResultCode(), topFragment.getResultData())
    }

    @JvmOverloads
    fun popFragment(animated: Boolean = true) {
        scheduleTaskAtStarted(Runnable { popFragmentInternal(animated) })
    }

    private fun popFragmentInternal(animated: Boolean) {
        val top = getTopFragment() ?: return

        val latter = FragmentHelper.getLatterFragment(childFragmentManager, top)
        if (latter != null) {
            popToFragmentInternal(this, animated)
            return
        }

        val ahead = FragmentHelper.getAheadFragment(childFragmentManager, top)
        if (ahead != null) {
            popToFragmentInternal(ahead, animated)
        }
    }

    @JvmOverloads
    fun popToRootFragment(animated: Boolean = true) {
        scheduleTaskAtStarted(Runnable { popToRootFragmentInternal(animated) })
    }

    private fun popToRootFragmentInternal(animated: Boolean) {
        val awesomeFragment = getRootFragment()
        if (awesomeFragment != null) {
            popToFragmentInternal(getRootFragment(), animated)
        }
    }

    fun replaceFragment(substitution: BaseFragment) {
        scheduleTaskAtStarted(Runnable { replaceFragmentInternal(substitution, null) })
    }

    fun replaceFragment(substitution: BaseFragment, target: BaseFragment) {
        scheduleTaskAtStarted(Runnable { replaceFragmentInternal(substitution, target) })
    }

    private fun replaceFragmentInternal(fragment: BaseFragment, target: BaseFragment?) {
        var target = target
        val fragmentManager = childFragmentManager
        FragmentHelper.executePendingTransactionsSafe(fragmentManager)

        val topFragment = getTopFragment() ?: return

        if (target == null) {
            target = topFragment
        }

        val aheadFragment = FragmentHelper.getAheadFragment(fragmentManager, target)

        topFragment.setAnimation(PresentAnimation.Push)
        topFragment.onHiddenChanged(true)
        aheadFragment?.setAnimation(PresentAnimation.Push)
        fragmentManager.popBackStack(target.getSceneId(), FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val transaction = fragmentManager.beginTransaction()
//        transaction.setReorderingAllowed(true)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (aheadFragment != null && aheadFragment.isAdded) {
            transaction.hide(aheadFragment)
        }
        fragment.setAnimation(PresentAnimation.Push)
        transaction.add(R.id.navigation_content, fragment, fragment.getSceneId())
        transaction.addToBackStack(fragment.getSceneId())
        transaction.commit()
    }

    fun replaceToRootFragment(fragment: BaseFragment) {
        scheduleTaskAtStarted(Runnable { replaceToRootFragmentInternal(fragment) })
    }

    private fun replaceToRootFragmentInternal(fragment: BaseFragment) {
        val fragmentManager = childFragmentManager
        FragmentHelper.executePendingTransactionsSafe(fragmentManager)

        val topFragment = getTopFragment()
        val rootFragment = getRootFragment()
        if (topFragment == null || rootFragment == null) {
            return
        }

        topFragment.setAnimation(PresentAnimation.Push)
        rootFragment.setAnimation(PresentAnimation.Push)
        topFragment.userVisibleHint = false
        topFragment.onHiddenChanged(true)
        fragmentManager.popBackStack(rootFragment.getSceneId(), FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val transaction = fragmentManager.beginTransaction()
//        transaction.setReorderingAllowed(true)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragment.setAnimation(PresentAnimation.Push)
        transaction.add(R.id.navigation_content, fragment, fragment.getSceneId())
        transaction.addToBackStack(fragment.getSceneId())
        transaction.commit()
    }

    override fun getNavigationFragment(): NavigationFragment? {
        val navF = super.getNavigationFragment()
        if (navF != null) {
            var parent = navF.getParentBaseFragment()
            while (parent != null) {
                if (parent is NavigationFragment && parent.getWindow() === navF.getWindow()) {
                    throw IllegalStateException("should not nest NavigationFragment in the same window.")
                }
                parent = parent.getParentBaseFragment()
            }
        }
        return navF
    }


    override fun onViewDragStateChanged(state: Int, scrollPercent: Float) {
        val topFragment = getTopFragment() ?: return

        if (state == SwipeBackLayout.STATE_DRAGGING) {
            val aheadFragment = FragmentHelper.getAheadFragment(childFragmentManager, topFragment)

            if (aheadFragment != null /*&& shouldTransitionWithStatusBar(aheadFragment, topFragment)*/) {
                StatusBarUtils.setStatusBarColor(getWindow(), topFragment.preferredStatusBarColor(), false)
            }

            if (aheadFragment != null && aheadFragment.view != null) {
                aheadFragment.view!!.visibility = View.VISIBLE
            }

            if (aheadFragment != null && aheadFragment === getRootFragment()) {

            }

        } else if (state == SwipeBackLayout.STATE_IDLE) {
            val aheadFragment = FragmentHelper.getAheadFragment(childFragmentManager, topFragment)

            if (aheadFragment != null && aheadFragment.view != null) {
                aheadFragment.view!!.visibility = View.GONE
            }

            if (aheadFragment != null && scrollPercent >= 1.0f) {
                val fragmentManager = childFragmentManager
                FragmentHelper.executePendingTransactionsSafe(fragmentManager)
                topFragment.setAnimation(PresentAnimation.Push)
                aheadFragment.setAnimation(PresentAnimation.Push)
                topFragment.userVisibleHint = false
                topFragment.onHiddenChanged(true)
                fragmentManager.popBackStack(aheadFragment.getSceneId(), 0)
                FragmentHelper.executePendingTransactionsSafe(fragmentManager)

//                aheadFragment.onFragmentResult(topFragment.getRequestCode(), topFragment.getResultCode(), topFragment.getResultData())
            }

//            if (aheadFragment != null && shouldTransitionWithStatusBar(aheadFragment, topFragment)) {
//                setNeedsStatusBarAppearanceUpdate(false)
//            }

            swipeBackLayout!!.setTabBar(null)
        }
    }

    override fun shouldSwipeBack(): Boolean {
        val top = getTopFragment() ?: return false
        val isSwipeBack = (getChildFragmentCountAtBackStack() > 1
                && top.isSwipeBackEnabled())
//        if (isSwipeBack) ParallaxHelper.disableParallaxBack(mActivity) else ParallaxHelper.enableParallaxBack(mActivity)
        return isSwipeBack
    }

//    private fun shouldTransitionWithStatusBar(aheadFragment: BaseFragment, topFragment: BaseFragment): Boolean {
//        val shouldAdjustForWhiteStatusBar = StatusBarUtils.shouldAdjustStatusBarColor(this)
//
//        return (isStatusBarTranslucent()
//                && !shouldAdjustForWhiteStatusBar
//                && aheadFragment.preferredStatusBarColor() !== Color.TRANSPARENT
//                && aheadFragment.preferredStatusBarColor() === topFragment.preferredStatusBarColor())
//    }


}
