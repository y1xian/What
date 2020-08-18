package com.yyxnb.arch.delegate

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.yyxnb.arch.annotations.BarStyle
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.SwipeStyle
import com.yyxnb.arch.base.IActivity
import com.yyxnb.arch.base.IFragment
import com.yyxnb.arch.common.ArchConfig
import com.yyxnb.arch.common.Bus.post
import com.yyxnb.arch.common.MsgEvent
import com.yyxnb.common.utils.MainThreadUtils.post
import com.yyxnb.common.utils.StatusBarUtils.isBlackColor
import com.yyxnb.common.utils.StatusBarUtils.setStatusBarColor
import com.yyxnb.common.utils.StatusBarUtils.setStatusBarStyle
import com.yyxnb.common.utils.StatusBarUtils.setStatusBarTranslucent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import java.util.*

/**
 * Fragment 代理
 *
 * @author yyx
 */
class FragmentDelegate(private var iFragment: IFragment?) : DefaultLifecycleObserver, CoroutineScope by MainScope() {

    private var iActivity: IActivity? = null
    private var mRootView: View? = null
    private var mFragment: Fragment?
    private var mActivity: FragmentActivity? = null

    private val mLazyDelegate: FragmentLazyDelegate
    private var layoutRes = 0
    private var statusBarTranslucent = ArchConfig.statusBarTranslucent
    private var fitsSystemWindows = ArchConfig.fitsSystemWindows
    private var statusBarColor = ArchConfig.statusBarColor
    private var statusBarDarkTheme = ArchConfig.statusBarStyle
    private var swipeBack = SwipeStyle.EDGE
    private var subPage = false
    private var needLogin = false

    init {
        mFragment = iFragment as Fragment?
        mLazyDelegate = FragmentLazyDelegate(mFragment)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onAttach(context: Context?) {
        mActivity = context as FragmentActivity?
        require(mActivity is IActivity) { "Activity请实现IActivity接口" }
        iActivity = mActivity as IActivity?
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(savedInstanceState: Bundle?) {
        mLazyDelegate.onCreate(savedInstanceState)
        initAttributes()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @SuppressLint("ClickableViewAccessibility")
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == mRootView) {
            if (layoutRes != 0 || iFragment!!.initLayoutResId() != 0) {
                mRootView = inflater.inflate(if (layoutRes == 0) iFragment!!.initLayoutResId() else layoutRes, container, false)
            }
        } else {
            //  二次加载删除上一个子view
            val viewGroup = mRootView as ViewGroup
            viewGroup.removeView(mRootView)
        }
        mRootView!!.setOnTouchListener { v: View?, event: MotionEvent? ->
            mActivity!!.onTouchEvent(event)
            false
        }
        return mRootView
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onActivityCreated(savedInstanceState: Bundle?) {
        mLazyDelegate.onActivityCreated(savedInstanceState)
        setNeedsStatusBarAppearanceUpdate()
    }

    /**
     * viewpager调用 [BindRes] [为 true][FragmentDelegate.subPage]
     */
    fun setUserVisibleHint(isVisibleToUser: Boolean) {
        mLazyDelegate.setUserVisibleHint(isVisibleToUser)
    }

    /**
     * show/hide调用
     */
    fun onHiddenChanged(hidden: Boolean) {
        mLazyDelegate.onHiddenChanged(hidden)
    }

    /**
     * 屏幕方向发生改变时
     */
    fun onConfigurationChanged(newConfig: Configuration?) {
        mLazyDelegate.onConfigurationChanged(newConfig)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        mLazyDelegate.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        mLazyDelegate.onPause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        mLazyDelegate.onDestroy()
        iFragment = null
        iActivity = null
        mActivity = null
        mFragment = null
        mRootView = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyView() {
        // 取消协程
        if (isActive) {
            cancel()
        }
    }

    /**
     * 加载注解设置
     */
    fun initAttributes() {
        post(Runnable {
            val bindRes = iFragment?.javaClass!!.getAnnotation(BindRes::class.java)
            if (bindRes != null) {
                layoutRes = bindRes.layoutRes
                fitsSystemWindows = bindRes.fitsSystemWindows
                statusBarTranslucent = bindRes.statusBarTranslucent
                swipeBack = bindRes.swipeBack
                subPage = bindRes.subPage
                if (bindRes.statusBarStyle != BarStyle.NONE) {
                    statusBarDarkTheme = bindRes.statusBarStyle
                }
                if (bindRes.statusBarColor != 0) {
                    statusBarColor = bindRes.statusBarColor
                }
                needLogin = bindRes.needLogin
                // 如果需要登录，并且处于未登录状态下，发送通知
                if (needLogin && !ArchConfig.needLogin) {
                    post(MsgEvent(ArchConfig.NEED_LOGIN_CODE))
                }
            }
        })
    }

    /**
     * 更新状态栏样式
     */
    fun setNeedsStatusBarAppearanceUpdate() {

        // 子页面不作做处理
        if (subPage) {
            return
        }
        // 侧滑返回
        iActivity!!.setSwipeBack(swipeBack)

        // 文字颜色
        val statusBarStyle = statusBarDarkTheme
        setStatusBarStyle(window, statusBarStyle == BarStyle.DARK_CONTENT)

        // 隐藏 or 不留空间 则透明
        if (!fitsSystemWindows) {
            setStatusBarColor(window, Color.TRANSPARENT)
        } else {
            var statusBarColor = statusBarColor

            //不为深色
            var shouldAdjustForWhiteStatusBar = !isBlackColor(statusBarColor, 176)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                shouldAdjustForWhiteStatusBar = shouldAdjustForWhiteStatusBar && statusBarStyle == BarStyle.LIGHT_CONTENT
            }
            // 如果状态栏处于白色且状态栏文字也处于白色，避免看不见
            if (shouldAdjustForWhiteStatusBar) {
                statusBarColor = ArchConfig.shouldAdjustForWhiteStatusBar
            }
            setStatusBarColor(window, statusBarColor)
        }
        setStatusBarTranslucent(window, statusBarTranslucent, fitsSystemWindows)
    }

    val window: Window
        get() = mActivity!!.window

    fun initArguments(): Bundle {
        var args = mFragment!!.arguments
        if (args == null) {
            args = Bundle()
            mFragment!!.arguments = args
        }
        return args
    }

    fun finish() {
        mActivity!!.onBackPressed()
    }

    fun getLazyDelegate(): FragmentLazyDelegate = mLazyDelegate

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val that = o as FragmentDelegate
        return iFragment == that.iFragment
    }

    override fun hashCode(): Int {
        return Objects.hash(iFragment)
    }

}