package com.yyxnb.arch.base

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
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.yyxnb.arch.Arch
import com.yyxnb.arch.ContainerActivity
import com.yyxnb.arch.annotations.*
import com.yyxnb.arch.common.AppConfig
import com.yyxnb.arch.common.AppConfig.statusBarColor
import com.yyxnb.arch.interfaces.*
import com.yyxnb.arch.jetpack.LifecycleDelegate
import com.yyxnb.arch.utils.MainThreadUtils
import com.yyxnb.arch.utils.StatusBarUtils
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
abstract class BaseFragment : Fragment(), ILazyOwner, CoroutineScope by MainScope() {

    protected lateinit var mActivity: AppCompatActivity
    protected lateinit var mContext: Context

    protected val TAG = javaClass.canonicalName

    protected var mRootView: View? = null

    private var statusBarTranslucent = AppConfig.statusBarTranslucent
    private var fitsSystemWindows = AppConfig.fitsSystemWindows
    private var statusBarHidden = AppConfig.statusBarHidden
    private var statusBarDarkTheme = AppConfig.statusBarStyle
    private var swipeBack = AppConfig.swipeBack

    private val lifecycleDelegate by lazy { LifecycleDelegate(this) }

    private var sceneId = UUID.randomUUID().toString()

    fun getSceneId(): String = sceneId

    /**
     * 懒加载代理类
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
            javaClass.getAnnotation(TagValue::class.java)?.let { sceneId = it.value }
            javaClass.getAnnotation(FitsSystemWindows::class.java)?.let { fitsSystemWindows = it.value }
            javaClass.getAnnotation(SwipeBack::class.java)?.let { swipeBack = it.value }

            setStatusBarTranslucent(statusBarTranslucent, fitsSystemWindows)
            setStatusBarStyle(statusBarDarkTheme)
            setStatusBarHidden(statusBarHidden)
            setSwipeBack(swipeBack)
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
        onHiddenChanged(true)
        scheduleTaskAtStarted(Runnable {
            val intent = Intent(mActivity, ContainerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(AppConfig.FRAGMENT, targetFragment.javaClass.canonicalName)
            intent.putExtra(AppConfig.BUNDLE, initArguments())
            startActivityForResult(intent, requestCode)
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

    fun setSwipeBack(mSwipeBack: Int = 0) = (mActivity as? BaseActivity)?.setSwipeBack(mSwipeBack)


    //更新状态栏样式
    fun setNeedsStatusBarAppearanceUpdate() {

        // 隐藏
        val hidden = statusBarHidden
        setStatusBarHidden(hidden)

        // 文字颜色
        val statusBarStyle = statusBarDarkTheme
        setStatusBarStyle(statusBarStyle)

        // 隐藏 、 不留空间 则透明
        if (hidden || !fitsSystemWindows) {
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

}
