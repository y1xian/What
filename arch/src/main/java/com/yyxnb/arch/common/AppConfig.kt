package com.yyxnb.arch.common

import android.graphics.Color
import com.yyxnb.arch.Arch
import com.yyxnb.arch.R
import com.yyxnb.arch.annotations.BarStyle
import java.io.Serializable

/**
 * Description: 相关配置属性
 *
 * @author : yyx
 * @date ：2018/6/13
 */
object AppConfig : Serializable {

    const val FRAGMENT = "FRAGMENT"
    const val BUNDLE = "BUNDLE"
    const val REQUEST_CODE = "REQUEST_CODE"

    /**
     * 重连次数
     */
    var retryMaxTime: Int = 3
    /**
     * 重连间隔时间 ms
     */
    var retryDelay: Long = 3000
    /**
     * 侧滑
     */
    var swipeBack: Int = 1
    /**
     * 状态栏透明
     */
    var statusBarTranslucent: Boolean = true
    /**
     * 给系统窗口留出空间
     */
    var fitsSystemWindows: Boolean = false
    /**
     * 状态栏文字颜色
     */
    var statusBarStyle = BarStyle.DarkContent
    /**
     * 状态栏是否隐藏
     */
    var statusBarHidden: Boolean = false
    /**
     * 状态栏颜色
     */
    var statusBarColor: Int = Arch.context.resources.getColor(R.color.statusBar)
    /**
     * 如果状态栏处于白色且状态栏文字也处于白色，避免看不见
     */
    var shouldAdjustForWhiteStatusBar: Int = Color.parseColor("#4A4A4A")
    /**
     * 虚拟键背景颜色
     */
    var navigationBarColor: Int = Color.TRANSPARENT
    /**
     * 虚拟键颜色
     */
    var navigationBarStyle = BarStyle.DarkContent

}
