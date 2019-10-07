package com.yyxnb.arch.base

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.SmartSwipeWrapper
import com.billy.android.swipe.SwipeConsumer
import com.billy.android.swipe.consumer.ActivitySlidingBackConsumer
import com.billy.android.swipe.listener.SimpleSwipeListener
import com.yyxnb.arch.Arch
import com.yyxnb.arch.ContainerActivity
import com.yyxnb.arch.R
import com.yyxnb.arch.base.nav.*
import com.yyxnb.arch.common.AppConfig
import com.yyxnb.arch.common.AppConfig.statusBarColor
import com.yyxnb.arch.ext.wrap
import com.yyxnb.arch.interfaces.*
import com.yyxnb.arch.jetpack.LifecycleDelegate
import com.yyxnb.arch.utils.MainThreadUtils
import com.yyxnb.arch.utils.StatusBarUtils
import com.yyxnb.arch.utils.ToastUtils
import com.yyxnb.arch.utils.log.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import java.util.*

/**
 * Description: 处理懒加载、状态栏 BaseFragment
 *
 * @author : yyx
 * @date ：2016/10
 */
abstract class BaseFragment : Fragment(), ILazyOwner, CoroutineScope by MainScope()/*, NewIntentCallback, FragmentLifecycleDelegate*/ {

    protected lateinit var mActivity: AppCompatActivity
    protected lateinit var mContext: Context

    protected val TAG = javaClass.canonicalName

    protected var mRootView: View? = null

    private var statusBarTranslucent = AppConfig.statusBarTranslucent
    private var fitsSystemWindows = AppConfig.fitsSystemWindows
    private var statusBarHidden = AppConfig.statusBarHidden
    private var statusBarDarkTheme = AppConfig.statusBarStyle
    private var swipeBack = AppConfig.swipeBackEnabled
    private var edgeSize = AppConfig.swipeEdgeSize

    private val lifecycleDelegate by lazy { LifecycleDelegate(this) }

    private var sceneId = UUID.randomUUID().toString()

    fun getSceneId(): String = sceneId

    /**
     * ImmersionBar代理类
     */
    private val mLazyProxy by lazy { LazyProxy(this) }

    init {
        lifecycle.addObserver(Java8Observer(TAG))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLazyProxy.onCreate(savedInstanceState)
        val bundle = initArguments()
        if (bundle.size() > 0) {
            initVariables(bundle)
        }
    }

    fun initArguments(): Bundle {
        var args = arguments
        if (args == null) {
            args = Bundle()
            arguments = args
        }
        return args
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        initAttributes()
        if (null == mRootView) {
            mRootView = inflater.inflate(initLayoutResId(), container, false)
        } else {
            //  二次加载删除上一个子view
            val viewGroup = mRootView?.parent as ViewGroup
            viewGroup.removeView(mRootView)
        }
        mRootView!!.setOnTouchListener { _, event ->
            mActivity.onTouchEvent(event)
            return@setOnTouchListener false
        }
//        FragmentManagerUtils.createFragment(this, mRootView!!)
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //当设备旋转时，fragment会随托管activity一起销毁并重建。
//        retainInstance = true
        mLazyProxy.onActivityCreated(savedInstanceState)
    }

    /**
     * 加载注解设置
     */
    private fun initAttributes() {
        MainThreadUtils.post(Runnable {
            javaClass.getAnnotation(StatusBarTranslucent::class.java)?.let { statusBarTranslucent = it.value }
            javaClass.getAnnotation(StatusBarHidden::class.java)?.let { statusBarHidden = it.value }
            javaClass.getAnnotation(StatusBarDarkTheme::class.java)?.let { statusBarDarkTheme = it.value }
            javaClass.getAnnotation(SwipeBack::class.java)?.let { swipeBack = it.value }
            javaClass.getAnnotation(SwipeEdgeSize::class.java)?.let { edgeSize = it.value }
            javaClass.getAnnotation(TagValue::class.java)?.let { sceneId = it.value }
            javaClass.getAnnotation(FitsSystemWindows::class.java)?.let { fitsSystemWindows = it.value }

            setStatusBarTranslucent(statusBarTranslucent, fitsSystemWindows)
            setStatusBarStyle(statusBarDarkTheme)
            setStatusBarHidden(statusBarHidden)
            setSwipeBack(swipeBack, edgeSize)
        })
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mLazyProxy.setUserVisibleHint(isVisibleToUser)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mLazyProxy.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mLazyProxy.onConfigurationChanged(newConfig)
    }

    override fun onResume() {
        super.onResume()
        mLazyProxy.onResume()
    }

    override fun onPause() {
        super.onPause()
        mLazyProxy.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mLazyProxy.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        FragmentManagerUtils.destroyFragment(this)
        cancel() // 关闭页面后，结束所有协程任务
        mRootView = null
    }

    /**
     * 被ViewPager移出的Fragment 下次显示时会从getArguments()中重新获取数据
     * 所以若需要刷新被移除Fragment内的数据需要重新put数据
     */
    open fun initVariables(bundle: Bundle) {}

    /**
     * 当界面可见时的操作
     */
    override fun onVisible() {
        setNeedsStatusBarAppearanceUpdate()
        Arch.debugLog("---onVisible $TAG")
    }

    /**
     * 当界面不可见时的操作
     */
    override fun onInVisible() {
        Arch.debugLog("---onInVisible $TAG")
    }

    /**
     * 初始化根布局
     */
    @LayoutRes
    abstract fun initLayoutResId(): Int

    /**
     * 状态栏颜色
     */
    open fun initStatusBarColor(): Int = statusBarColor

    /**
     * 初始化控件
     */
    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 初始化复杂数据 懒加载
     */
    override fun initViewData() {
        Arch.debugLog("--- 懒加载 initViewData $TAG")
    }

    fun <T> findViewById(@IdRes resId: Int): T {
        return mRootView!!.findViewById<View>(resId) as T
    }

    /**
     * 返回.
     */
    fun finish() {
        mActivity.onBackPressed()
    }


    // ------ lifecycle arch -------

    @JvmOverloads
    fun scheduleTaskAtStarted(runnable: Runnable, interval: Long = 1L) {
        lifecycleDelegate.scheduleTaskAtStarted(runnable, interval)
    }

    fun getWindow(): Window {
        return mActivity.window
    }

    /**
     * 使用给定的类名创建Fragment的新实例。 这与调用其空构造函数相同。
     *
     * @param targetFragment 目标fragment.
     * @param bundle        argument.
     * @param T           [BaseFragment].
     * @return new instance.
     */
    @JvmOverloads
    fun <T : BaseFragment> fragment(targetFragment: T, bundle: Bundle? = null): T {
        return instantiate(context, targetFragment.javaClass.canonicalName, bundle) as T
    }

    /**
     * 跳转 activity.
     *
     * @param clazz 目标activity class.
     * @param <T>   [Activity].
    </T> */
    protected fun <T : Activity> startActivity(clazz: Class<T>) {
        startActivity(Intent(mActivity, clazz))
    }

    /**
     * 跳转 fragment.
     *
     * @param targetFragment 目标fragment.
     * @param requestCode    请求码.
     * @param T          [BaseFragment].
     */
    @JvmOverloads
    fun <T : BaseFragment> startFragment(targetFragment: T, requestCode: Int = 0) {

//        kv.encode(ARGS_REQUEST_CODE, requestCode)
        userVisibleHint = false
        onHiddenChanged(true)
        scheduleTaskAtStarted(Runnable {
            if (mActivity is BaseActivity && getNavigationFragment() != null) {
                getNavigationFragment()?.pushFragment(targetFragment)
            } else {
                startActivityRootFragment(targetFragment)
            }
        })
    }

    fun rootFragment(targetFragment: BaseFragment): BaseFragment {
        val navigationFragment = NavigationFragment()
        navigationFragment.setRootFragment(targetFragment)
        return navigationFragment
    }

    private fun <T : BaseFragment> startActivityRootFragment(rootFragment: T) {
        scheduleTaskAtStarted(Runnable {
            if (mActivity is BaseActivity && getNavigationFragment() != null) {
//                ToastUtils.debug("1")
                (mActivity as BaseActivity).startActivityRootFragment(rootFragment(rootFragment))
            } else {
//                ToastUtils.debug("2")
                val intent = Intent(mActivity, ContainerActivity::class.java)
                intent.putExtra(AppConfig.FRAGMENT, rootFragment.javaClass.canonicalName)
                intent.putExtra(AppConfig.BUNDLE, initArguments())
                mActivity.startActivity(intent)
            }
        })
    }

    // ------- statusBar --------

    fun setStatusBarTranslucent(translucent: Boolean, fitsSystemWindows: Boolean) = (mActivity as? BaseActivity)?.setStatusBarTranslucent(translucent, fitsSystemWindows)
            ?: let { StatusBarUtils.setStatusBarTranslucent(getWindow(), translucent, fitsSystemWindows) }

    fun setStatusBarColor(color: Int) = (mActivity as? BaseActivity)?.setStatusBarColor(color)
            ?: let { StatusBarUtils.setStatusBarColor(getWindow(), color) }

    fun setStatusBarStyle(barStyle: BarStyle) = (mActivity as? BaseActivity)?.setStatusBarStyle(barStyle)
            ?: let { StatusBarUtils.setStatusBarStyle(getWindow(), barStyle === BarStyle.DarkContent) }

    fun setStatusBarHidden(hidden: Boolean) = (mActivity as? BaseActivity)?.setStatusBarHidden(hidden)
            ?: let { StatusBarUtils.setStatusBarHidden(getWindow(), hidden) }

    fun setSwipeBack(mSwipeBack: Boolean = swipeBack, mEdgeSize: Int = edgeSize) = (mActivity as? BaseActivity)?.setSwipeBack(mSwipeBack, mEdgeSize)


    //更新状态栏样式
    fun setNeedsStatusBarAppearanceUpdate() {

        // statusBarHidden
        val hidden = statusBarHidden
        setStatusBarHidden(hidden)

        // statusBarStyle
        val statusBarStyle = statusBarDarkTheme
        setStatusBarStyle(statusBarStyle)

        // statusBarColor
        if (hidden) {
            setStatusBarColor(Color.TRANSPARENT)
        } else {
            var statusBarColor = initStatusBarColor()

            //不为深色
            var shouldAdjustForWhiteStatusBar = !StatusBarUtils.isBlackColor(statusBarColor, 176)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                shouldAdjustForWhiteStatusBar = shouldAdjustForWhiteStatusBar && statusBarStyle === BarStyle.LightContent
            }
            //如果状态栏处于白色且状态栏文字也处于白色，避免看不见
            if (shouldAdjustForWhiteStatusBar) {
                statusBarColor = AppConfig.shouldAdjustForWhiteStatusBar
            }

            setStatusBarColor(statusBarColor)
        }
        setStatusBarTranslucent(statusBarTranslucent, fitsSystemWindows)
    }

    //======================nav===

    private var animation: PresentAnimation? = null

    fun setAnimation(animation: PresentAnimation) {
        this.animation = animation
    }

    fun getAnimation(): PresentAnimation {
        if (this.animation == null) {
            this.animation = PresentAnimation.None
        }
        return animation!!
    }

    private var definesPresentationContext: Boolean = false

    fun definesPresentationContext(): Boolean {
        return definesPresentationContext
    }

    fun setDefinesPresentationContext(defines: Boolean) {
        definesPresentationContext = defines
    }

    fun dispatchBackPressed(): Boolean {
        val fragmentManager = childFragmentManager
        val count = fragmentManager.backStackEntryCount
        val fragment = fragmentManager.primaryNavigationFragment

        if (fragment is BaseFragment && definesPresentationContext() && count > 0) {
            val backStackEntry = fragmentManager.getBackStackEntryAt(count - 1)
            val child = fragmentManager.findFragmentByTag(backStackEntry.name) as BaseFragment?
            if (child != null) {
                val processed = child.dispatchBackPressed() || onBackPressed()
                if (!processed) {
                    child.dismissFragment()
                }
                return true
            }
        }

        return when {
            fragment is BaseFragment -> {
                val child = fragment as BaseFragment?
                child!!.dispatchBackPressed() || onBackPressed()
            }
            count > 0 -> {
                val backStackEntry = fragmentManager.getBackStackEntryAt(count - 1)
                val child = fragmentManager.findFragmentByTag(backStackEntry.name) as BaseFragment?
                child != null && child.dispatchBackPressed() || onBackPressed()
            }
            else -> onBackPressed()
        }
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    fun presentFragment(targetFragment: BaseFragment) {
        scheduleTaskAtStarted(Runnable {
            val parent = getParentBaseFragment()
            if (parent != null) {
                if (definesPresentationContext()) {
                    presentFragmentInternal(this, targetFragment)
                } else {
                    parent.presentFragment(targetFragment)
                }
                return@Runnable
            }

            (mActivity as? BaseActivity)?.presentFragment(targetFragment)
        })
    }

    private fun presentFragmentInternal(target: BaseFragment, fragment: BaseFragment) {
        fragment.setTargetFragment(target, requestCode)
        fragment.setDefinesPresentationContext(true)
        FragmentHelper.addFragmentToBackStack(target.requireFragmentManager(), target.id, fragment, PresentAnimation.Push)
    }

    fun dismissFragment() {
        scheduleTaskAtStarted(Runnable {
            val parent = getParentBaseFragment()
            if (parent != null) {
                if (definesPresentationContext()) {
                    val presented = getPresentedFragment()
                    if (presented != null) {
                        dismissFragmentInternal(null)
                        return@Runnable
                    }
                    val target = targetFragment as BaseFragment?
                    if (target != null) {
                        dismissFragmentInternal(target)
                    }
                } else {
                    parent.dismissFragment()
                }
                return@Runnable
            }

            (mActivity as? BaseActivity)?.dismissFragment(this)
        })

    }

    private fun dismissFragmentInternal(target: BaseFragment?) {
        if (target == null) {
            val presented = getPresentedFragment()
            val count = requireFragmentManager().backStackEntryCount
            val backStackEntry = requireFragmentManager().getBackStackEntryAt(count - 1)
            val top = requireFragmentManager().findFragmentByTag(backStackEntry.name) as BaseFragment?
            if (top == null || presented == null) {
                return
            }
            setAnimation(PresentAnimation.Push)
            top.setAnimation(PresentAnimation.Push)
            top.userVisibleHint = false
            top.onHiddenChanged(true)
            requireFragmentManager().popBackStack(presented.sceneId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            FragmentHelper.executePendingTransactionsSafe(requireFragmentManager())
//            onFragmentResult(top.getRequestCode(), top.getResultCode(), top.getResultData())
        } else {
            setAnimation(PresentAnimation.Push)
            target.setAnimation(PresentAnimation.Push)
            target.onHiddenChanged(true)
            requireFragmentManager().popBackStack(getSceneId(), FragmentManager.POP_BACK_STACK_INCLUSIVE)
            FragmentHelper.executePendingTransactionsSafe(requireFragmentManager())
//            target.onFragmentResult(getRequestCode(), getResultCode(), getResultData())
        }
    }

    fun getPresentedFragment(): BaseFragment? {
        val parent = getParentBaseFragment()
        if (parent != null) {
            if (definesPresentationContext()) {
                return if (FragmentHelper.findIndexAtBackStack(requireFragmentManager(), this) == -1) {
                    if (parent.getChildFragmentCountAtBackStack() == 0) {
                        null
                    } else {
                        val backStackEntry = requireFragmentManager().getBackStackEntryAt(0)
                        val presented = requireFragmentManager().findFragmentByTag(backStackEntry.name) as BaseFragment?
                        if (presented != null && presented.isAdded) {
                            presented
                        } else null
                    }
                } else {
                    FragmentHelper.getLatterFragment(requireFragmentManager(), this)
                }
            } else {
                return parent.getPresentedFragment()
            }
        }

        return (mActivity as? BaseActivity)?.getPresentedFragment(this)

    }

    fun getPresentingFragment(): BaseFragment? {
        val parent = getParentBaseFragment()
        if (parent != null) {
            if (definesPresentationContext()) {
                val target = targetFragment as BaseFragment?
                return if (target != null && target.isAdded) {
                    target
                } else null
            } else {
                return parent.getPresentingFragment()
            }
        }

        return (mActivity as? BaseActivity)?.getPresentingFragment(this)

    }

    fun getDebugTag(): String? {
        if (activity == null) {
            return null
        }
        val parent = getParentBaseFragment()
        return if (parent == null) {
            "#" + getIndexAtAddedList() + "-" + javaClass.simpleName
        } else {
            parent.getDebugTag() + "#" + getIndexAtAddedList() + "-" + javaClass.simpleName
        }
    }

    // 可以重写这个方法来指定由那个子 Fragment 来决定系统 UI（状态栏）的样式，否则由容器本身决定
    open fun childFragmentForAppearance(): BaseFragment? {
        return null
    }

    fun getChildFragmentCountAtBackStack(): Int {
        val fragmentManager = childFragmentManager
        return fragmentManager.backStackEntryCount
    }

    fun getIndexAtBackStack(): Int {
        return FragmentHelper.findIndexAtBackStack(requireFragmentManager(), this)
    }

    fun getIndexAtAddedList(): Int {
        val fragments = requireFragmentManager().fragments
        return fragments.indexOf(this)
    }

    fun getChildFragmentsAtAddedList(): List<BaseFragment> {
        val children = ArrayList<BaseFragment>()
        if (isAdded) {
            val fragments = childFragmentManager.fragments
            var i = 0
            val size = fragments.size
            while (i < size) {
                val fragment = fragments[i]
                if (fragment is BaseFragment && fragment.isAdded()) {
                    children.add(fragment)
                }
                i++
            }
        }
        return children
    }

    fun getParentBaseFragment(): BaseFragment? {
        val fragment = parentFragment
        return if (fragment is BaseFragment) {
            fragment
        } else null
    }

    open fun isParentFragment(): Boolean {
        return false
    }

    // ------ NavigationFragment -----
    open fun getNavigationFragment(): NavigationFragment? {
        if (this is NavigationFragment) {
            return this
        }
        val parent = getParentBaseFragment()
        return parent?.getNavigationFragment()
    }

    fun isNavigationRoot(): Boolean {
        val navigationFragment = getNavigationFragment()
        if (navigationFragment != null) {
            val awesomeFragment = navigationFragment.getRootFragment()
            return awesomeFragment === this
        }
        return false
    }

    open fun isSwipeBackEnabled(): Boolean {
        return AppConfig.swipeBackEnabled
    }

    /**
     * 处理返回结果.
     *
     * @param resultCode 结果码.
     * @param result     跳转所携带的信息.
     */
    open fun onFragmentResult(requestCode: Int, resultCode: Int, result: Bundle? = null) {

        if (this is NavigationFragment) {
            val child = this.getTopFragment()
            child?.onFragmentResult(requestCode, resultCode, result)

        } else {
            val fragments = getChildFragmentsAtAddedList()
            for (child in fragments) {
                child.onFragmentResult(requestCode, resultCode, result)
            }
        }
    }

    // ------- statusBar --------

    fun setNeedsNavigationBarAppearanceUpdate() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        val parent = getParentBaseFragment()
        if (parent != null) {
            parent.setNeedsNavigationBarAppearanceUpdate()
        } else {
            val color = preferredNavigationBarColor()
//            if (color != null) {
//                setNavigationBarColor(color)
//            } else {
//                setNavigationBarColor(Color.WHITE)
//            }
        }
    }

    //状态栏颜色
    open fun preferredStatusBarColor(): Int {
        val childFragmentForStatusBarColor = childFragmentForAppearance()
        if (childFragmentForStatusBarColor != null) {
            return childFragmentForStatusBarColor.preferredStatusBarColor()
        }
        return AppConfig.statusBarColor
    }

    //虚拟键颜色
    @TargetApi(26)
    open fun preferredNavigationBarColor(): Int? {
        val childFragmentForAppearance = childFragmentForAppearance()
        return if (childFragmentForAppearance != null) {
            childFragmentForAppearance.preferredNavigationBarColor()
        } else AppConfig.navigationBarColor
    }

    //是否需要对颜色做过渡动画
    open fun preferredStatusBarColorAnimated(): Boolean {
        val childFragmentForStatusBarColor = childFragmentForAppearance()
        return childFragmentForStatusBarColor?.preferredStatusBarColorAnimated()
                ?: (getAnimation() !== PresentAnimation.None)
    }

    //状态栏文字颜色
    open fun preferredStatusBarStyle(): BarStyle {
        val childFragmentForStatusBarStyle = childFragmentForAppearance()
        if (childFragmentForStatusBarStyle != null) {
            return childFragmentForStatusBarStyle.preferredStatusBarStyle()
        }
        return AppConfig.statusBarStyle
    }

    //状态栏是否隐藏
    open fun preferredStatusBarHidden(): Boolean {
        val childFragmentForStatusBarHidden = childFragmentForAppearance()
        if (childFragmentForStatusBarHidden != null) {
            return childFragmentForStatusBarHidden.preferredStatusBarHidden()
        }
        return AppConfig.statusBarHidden
    }

    /**
     * 请求码
     */
    private var requestCode: Int = 0

    /**
     * 获取传入的Bundle数据，对应fragment跳转时的[Fragment.setArguments]
     *
     * @return
     */
    fun getBundle(): Bundle? {
        return arguments
    }


}
