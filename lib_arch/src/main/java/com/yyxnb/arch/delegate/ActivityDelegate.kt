package com.yyxnb.arch.delegate

import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.yyxnb.arch.annotations.BarStyle
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.base.IActivity
import com.yyxnb.arch.common.ArchConfig
import com.yyxnb.arch.common.Bus.post
import com.yyxnb.arch.common.MsgEvent
import com.yyxnb.common.utils.MainThreadUtils.post
import com.yyxnb.common.utils.StatusBarUtils.setStatusBarColor
import com.yyxnb.common.utils.StatusBarUtils.setStatusBarStyle
import com.yyxnb.common.utils.StatusBarUtils.setStatusBarTranslucent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import java.util.*

/**
 * Activity 代理
 *
 * @author yyx
 */
class ActivityDelegate(private var iActivity: IActivity?) : DefaultLifecycleObserver, CoroutineScope by MainScope() {

    private var mActivity: FragmentActivity? = iActivity as FragmentActivity
    private var layoutRes = 0
    private var statusBarTranslucent = ArchConfig.statusBarTranslucent
    private var fitsSystemWindows = ArchConfig.fitsSystemWindows
    private var statusBarColor = ArchConfig.statusBarColor
    private var statusBarDarkTheme = ArchConfig.statusBarStyle
    private var needLogin = false
    private var isContainer = false

    /**
     * 是否第一次加载
     */
    private var mIsFirstVisible = true

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(savedInstanceState: Bundle?) {
        initAttributes()
        if (layoutRes != 0 || iActivity!!.initLayoutResId() != 0) {
            mActivity!!.setContentView(if (layoutRes == 0) iActivity!!.initLayoutResId() else layoutRes)
        }
        initView()
    }

    private fun initView() {
        if (!isContainer) {
            // 不留空间 则透明
            if (!fitsSystemWindows) {
                setStatusBarColor(window, Color.TRANSPARENT)
            } else {
                setStatusBarColor(window, statusBarColor)
            }
            setStatusBarStyle(window, statusBarDarkTheme == BarStyle.DARK_CONTENT)
            setStatusBarTranslucent(window, statusBarTranslucent, fitsSystemWindows)
        }
    }

    private val window: Window
        private get() = mActivity!!.window

    fun onWindowFocusChanged(hasFocus: Boolean) {
        if (mIsFirstVisible && hasFocus) {
            mIsFirstVisible = false
            iActivity!!.initViewData()
            iActivity!!.initObservable()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        mIsFirstVisible = true
        iActivity = null
        mActivity = null
        // 取消协程
        if (isActive) {
            cancel()
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return (event.x <= left || event.x >= right
                    || event.y <= top || event.y >= bottom)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false
    }

    /**
     * 加载注解设置
     */
    fun initAttributes() {
        post(Runnable {
            val bindRes = iActivity?.javaClass!!.getAnnotation(BindRes::class.java)
            if (bindRes != null) {
                layoutRes = bindRes.layoutRes
                fitsSystemWindows = bindRes.fitsSystemWindows
                statusBarTranslucent = bindRes.statusBarTranslucent
                if (bindRes.statusBarStyle != BarStyle.NONE) {
                    statusBarDarkTheme = bindRes.statusBarStyle
                }
                if (bindRes.statusBarColor != 0) {
                    statusBarColor = bindRes.statusBarColor
                }
                needLogin = bindRes.needLogin
                isContainer = bindRes.isContainer
                // 如果需要登录，并且处于未登录状态下，发送通知
                if (needLogin && !ArchConfig.needLogin) {
                    post(MsgEvent(ArchConfig.NEED_LOGIN_CODE))
                }
            }
        })
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val that = o as ActivityDelegate
        return iActivity == that.iActivity
    }

    override fun hashCode(): Int {
        return Objects.hash(iActivity)
    }
}