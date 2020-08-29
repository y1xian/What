package com.yyxnb.common.utils

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.*
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import java.io.Serializable

/**
 * 状态栏工具
 *
 * @author yyx
 */
object StatusBarUtils {
    /**
     * 是否深色
     *
     * @param color
     * @param level 255/2 （>176) 为浅色
     * @return
     */
    @JvmStatic
    fun isBlackColor(color: Int, level: Int): Boolean {
        val grey = toGrey(color)
        return grey < level
    }

    fun toGrey(rgb: Int): Int {
        val blue = rgb and 0x000000FF
        val green = rgb and 0x0000FF00 shr 8
        val red = rgb and 0x00FF0000 shr 16
        return red * 38 + green * 75 + blue * 15 shr 7
    }

    private val BRAND = Build.BRAND.toLowerCase()
    val isHuawei: Boolean
        get() = BRAND.contains("huawei") || BRAND.contains("honor")

    val isXiaomi: Boolean
        get() = "xiaomi" == Build.MANUFACTURER.toLowerCase()

    val isVivo: Boolean
        get() = BRAND.contains("vivo") || BRAND.contains("bbk")

    val isOppo: Boolean
        get() = BRAND.contains("oppo")

    /**
     * 开启沉浸式
     *
     * @param window
     * @param translucent       透明
     * @param fitsSystemWindows 触发View的padding属性来给系统窗口留出空间
     */
    @JvmStatic
    fun setStatusBarTranslucent(window: Window, translucent: Boolean, fitsSystemWindows: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setRenderContentInShortEdgeCutoutAreas(window, translucent)
            val decorView = window.decorView
            if (translucent) {
                decorView.setOnApplyWindowInsetsListener { v: View, insets: WindowInsets? ->
                    val defaultInsets = v.onApplyWindowInsets(insets)
                    defaultInsets.replaceSystemWindowInsets(
                            defaultInsets.systemWindowInsetLeft,  //是否撑开
                            if (fitsSystemWindows) defaultInsets.systemWindowInsetTop else 0,
                            defaultInsets.systemWindowInsetRight,
                            defaultInsets.systemWindowInsetBottom)
                }
            } else {
                decorView.setOnApplyWindowInsetsListener(null)
            }
            ViewCompat.requestApplyInsets(decorView)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (translucent) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
            ViewCompat.requestApplyInsets(window.decorView)
        }
    }
    //    public static boolean shouldAdjustStatusBarColor(@NonNull AwesomeFragment fragment) {
    //        boolean shouldAdjustForWhiteStatusBar = !AppUtils.isBlackColor(fragment.preferredStatusBarColor(), 176);
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    //            shouldAdjustForWhiteStatusBar = shouldAdjustForWhiteStatusBar && fragment.preferredStatusBarStyle() == BarStyle.LightContent;
    //        }
    //        return shouldAdjustForWhiteStatusBar;
    //    }
    /**
     * 虚拟导航栏颜色
     *
     * @param window
     * @param color
     */
    fun setNavigationBarColor(window: Window, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.navigationBarColor = color
        }
    }

    /**
     * 虚拟导航栏文字颜色
     *
     * @param window
     * @param dark   是否深色
     */
    fun setNavigationBarStyle(window: Window, dark: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val decorView = window.decorView
            var systemUi = decorView.systemUiVisibility
            systemUi = if (dark) {
                systemUi or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            } else {
                systemUi and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
            }
            decorView.systemUiVisibility = systemUi
        }
    }

    /**
     * 导航栏文字是否深色
     *
     * @param window
     * @return
     */
    fun isDarNavigationBarStyle(window: Window): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR != 0
        } else false
    }

    /**
     * 隐藏导航栏
     *
     * @param window
     * @param hidden
     */
    fun setNavigationBarHidden(window: Window, hidden: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = window.decorView
            var systemUi = decorView.systemUiVisibility
            if (hidden) {
                systemUi = systemUi or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                systemUi = systemUi or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            } else {
                systemUi = systemUi and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv()
                systemUi = systemUi and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()
            }
            window.decorView.systemUiVisibility = systemUi
        }
    }

    /**
     * 状态栏颜色
     *
     * @param window
     * @param color
     */
    @JvmStatic
    fun setStatusBarColor(window: Window, color: Int) {
        setStatusBarColor(window, color, false)
    }

    /**
     * 状态栏颜色
     *
     * @param window
     * @param color
     * @param animated 是否有动画
     */
    fun setStatusBarColor(window: Window, color: Int, animated: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (animated) {
                val curColor = window.statusBarColor
                val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), curColor, color)
                colorAnimation.addUpdateListener { animator: ValueAnimator -> window.statusBarColor = (animator.animatedValue as Int) }
                colorAnimation.setDuration(200).startDelay = 0
                colorAnimation.start()
            } else {
                window.statusBarColor = color
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val decorViewGroup = window.decorView as ViewGroup
            var statusBarView = decorViewGroup.findViewWithTag<View>("custom_status_bar_tag")
            if (statusBarView == null) {
                statusBarView = View(window.context)
                statusBarView.tag = "custom_status_bar_tag"
                val params = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(window.context))
                params.gravity = Gravity.TOP
                statusBarView.layoutParams = params
                decorViewGroup.addView(statusBarView)
            }
            if (animated) {
                val drawable = statusBarView.background
                var curColor = Int.MAX_VALUE
                if (drawable is ColorDrawable) {
                    curColor = drawable.color
                }
                if (curColor != Int.MAX_VALUE) {
                    val barView = statusBarView
                    val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), curColor, color)
                    colorAnimation.addUpdateListener { animator: ValueAnimator -> barView.background = ColorDrawable((animator.animatedValue as Int)) }
                    colorAnimation.setDuration(200).startDelay = 0
                    colorAnimation.start()
                } else {
                    statusBarView.background = ColorDrawable(color)
                }
            } else {
                statusBarView.background = ColorDrawable(color)
            }
        }
    }

    /**
     * 获取状态栏颜色
     *
     * @param window
     * @return
     */
    fun getStatusBarColor(window: Window): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return window.statusBarColor
        } else {
            val decorViewGroup = window.decorView as ViewGroup
            val statusBarView = decorViewGroup.findViewWithTag<View>("custom_status_bar_tag")
            if (statusBarView != null) {
                val drawable = statusBarView.background
                if (drawable is ColorDrawable) {
                    return drawable.color
                }
            }
        }
        return Color.BLACK
    }

    /**
     * 状态栏字体颜色 6.0
     *
     * @param window
     * @param dark   是否深色
     */
    @JvmStatic
    fun setStatusBarStyle(window: Window, dark: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = window.decorView
            var systemUi = decorView.systemUiVisibility
            systemUi = if (dark) {
                systemUi or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                systemUi and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            decorView.systemUiVisibility = systemUi
        }
    }

    /**
     * 是否深色
     *
     * @param window
     * @return
     */
    fun isDarkStatusBarStyle(window: Window): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR != 0
        } else false
    }

    /**
     * 隐藏状态栏
     *
     * @param window
     * @param hidden
     */
    fun setStatusBarHidden(window: Window, hidden: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val decorView = window.decorView
            var systemUi = decorView.systemUiVisibility
            if (hidden) {
                systemUi = systemUi or View.SYSTEM_UI_FLAG_FULLSCREEN
                systemUi = systemUi or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            } else {
                systemUi = systemUi and View.SYSTEM_UI_FLAG_FULLSCREEN.inv()
                systemUi = systemUi and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()
            }
            window.decorView.systemUiVisibility = systemUi
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            val decorViewGroup = window.decorView as ViewGroup
            val statusBarView = decorViewGroup.findViewWithTag<View>("custom_status_bar_tag")
            if (statusBarView != null) {
                val hiding = statusBarView.isClickable
                if (hiding == hidden) {
                    return
                }
                if (hidden) {
                    statusBarView.isClickable = true
                    val animator = ObjectAnimator.ofFloat(statusBarView, "y", -getStatusBarHeight(window.context).toFloat())
                    animator.duration = 200
                    animator.startDelay = 200
                    animator.start()
                } else {
                    statusBarView.isClickable = false
                    val animator = ObjectAnimator.ofFloat(statusBarView, "y", 0f)
                    animator.duration = 200
                    animator.start()
                }
            }
        }
    }

    /**
     * 状态栏是否隐藏
     *
     * @param window
     * @return
     */
    fun isStatusBarHidden(window: Window): Boolean {
        return window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN != 0
    }

    /**
     * 增加View的paddingTop,增加的值为状态栏高度
     *
     * @param context
     * @param view
     */
    fun appendStatusBarPadding(context: Context, view: View?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (view != null) {
                val statusBarHeight = getStatusBarHeight(context)
                val lp = view.layoutParams
                if (lp != null && lp.height > 0) {
                    lp.height += statusBarHeight //增高
                }
                view.setPadding(view.paddingLeft, view.paddingTop + statusBarHeight,
                        view.paddingRight, view.paddingBottom)
            }
        }
    }

    /**
     * 删除View的paddingTop,增加的值为状态栏高度
     *
     * @param context
     * @param view
     */
    fun removeStatusBarPadding(context: Context, view: View?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (view != null) {
                val statusBarHeight = getStatusBarHeight(context)
                val lp = view.layoutParams
                if (lp != null && lp.height > 0) {
                    lp.height -= statusBarHeight //增高
                }
                view.setPadding(view.paddingLeft, view.paddingTop - statusBarHeight,
                        view.paddingRight, view.paddingBottom)
            }
        }
    }

    fun appendStatusBarMargin(context: Context, view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val lp = view.layoutParams
            if (lp is MarginLayoutParams) {
                lp.topMargin += getStatusBarHeight(context) //增高
            }
            view.layoutParams = lp
        }
    }

    private var statusBarHeight = -1

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        if (statusBarHeight != -1) {
            return statusBarHeight
        }

        //获取status_bar_height资源的ID
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    @Volatile
    private var sHasCheckCutout = false

    @Volatile
    private var sIsCutout = false

    /**
     * 是否刘海屏
     *
     * @param activity
     * @return
     */
    fun isCutout(activity: AppCompatActivity): Boolean {
        if (sHasCheckCutout) {
            return sIsCutout
        }
        sHasCheckCutout = true

        // 低于 API 27 的，都不会是刘海屏、凹凸屏
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O_MR1) {
            sIsCutout = false
            return false
        }
        sIsCutout = isHuaweiCutout(activity) || isOppoCutout(activity) || isVivoCutout(activity) || isXiaomiCutout(activity)
        if (!isGoogleCutoutSupport) {
            return sIsCutout
        }
        if (!sIsCutout) {
            val window = activity.window
                    ?: throw IllegalStateException("activity has not attach to window")
            val decorView = window.decorView
            sIsCutout = attachHasOfficialNotch(decorView)
        }
        return sIsCutout
    }

    @TargetApi(28)
    private fun attachHasOfficialNotch(view: View): Boolean {
        val windowInsets = view.rootWindowInsets
        return if (windowInsets != null) {
            val displayCutout = windowInsets.displayCutout
            displayCutout != null
        } else {
            throw IllegalStateException("activity has not yet attach to window, you must call `isCutout` after `Activity#onAttachedToWindow` is called.")
        }
    }

    fun isHuaweiCutout(context: Context): Boolean {
        if (!isHuawei) {
            return false
        }
        var ret = false
        try {
            val cl = context.classLoader
            val HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil")
            val get = HwNotchSizeUtil.getMethod("hasNotchInScreen")
            ret = get.invoke(HwNotchSizeUtil) as Boolean
        } catch (e: Exception) {
            // ignore
        }
        return ret
    }

    fun isOppoCutout(context: Context): Boolean {
        return if (!isOppo) {
            false
        } else context.packageManager.hasSystemFeature("com.oppo.feature.screen.heteromorphism")
    }

    const val NOTCH_IN_SCREEN_VOIO = 0x00000020 //是否有凹槽
    const val ROUNDED_IN_SCREEN_VOIO = 0x00000008 //是否有圆角
    fun isVivoCutout(context: Context): Boolean {
        if (!isVivo) {
            return false
        }
        var ret = false
        try {
            val cl = context.classLoader
            @SuppressLint("PrivateApi") val ftFeature = cl.loadClass("android.util.FtFeature")
            val methods = ftFeature.declaredMethods
            if (null != methods) {
                for (i in methods.indices) {
                    val method = methods[i]
                    if ("isFeatureSupport".equals(method.name, ignoreCase = true)) {
                        ret = method.invoke(ftFeature, NOTCH_IN_SCREEN_VOIO) as Boolean
                        break
                    }
                }
            }
        } catch (e: Exception) {
            // ignore
        }
        return ret
    }

    private const val MIUI_NOTCH = "ro.miui.notch"

    @SuppressLint("PrivateApi")
    fun isXiaomiCutout(context: Context?): Boolean {
        if (!isXiaomi) {
            return false
        }
        try {
            val spClass = Class.forName("android.os.SystemProperties")
            val getMethod = spClass.getDeclaredMethod("getInt", String::class.java, Int::class.javaPrimitiveType)
            getMethod.isAccessible = true
            val hasNotch = getMethod.invoke(null, MIUI_NOTCH, 0) as Int
            return hasNotch == 1
        } catch (e: Exception) {
            // ignore
        }
        return false
    }

    val isGoogleCutoutSupport: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

    /**
     * 安全区域
     *
     * @param window
     * @param shortEdges
     */
    fun setRenderContentInShortEdgeCutoutAreas(window: Window, shortEdges: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val layoutParams = window.attributes
            if (shortEdges) {
                layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            } else {
                layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
            }
            window.attributes = layoutParams
        }
    }
}