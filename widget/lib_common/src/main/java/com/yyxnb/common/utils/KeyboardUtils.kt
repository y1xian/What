package com.yyxnb.common.utils

import android.R
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import java.util.*

/**
 * 软键盘工具
 */
object KeyboardUtils {

    interface OnSoftInputChangedListener {
        fun onSoftInputChanged(height: Int)
    }

    var sDecorViewInvisibleHeightPre = 0
    private var onGlobalLayoutListener: OnGlobalLayoutListener? = null
    private val listenerMap = HashMap<View, OnSoftInputChangedListener>()
    private var sDecorViewDelta = 0
    private fun getDecorViewInvisibleHeight(window: Window): Int {
        val decorView = window.decorView ?: return sDecorViewInvisibleHeightPre
        val outRect = Rect()
        decorView.getWindowVisibleDisplayFrame(outRect)
        val delta = Math.abs(decorView.bottom - outRect.bottom)
        if (delta <= navBarHeight) {
            sDecorViewDelta = delta
            return 0
        }
        return delta - sDecorViewDelta
    }

    /**
     * Register soft input changed listener.
     *
     * @param window   The activity.
     * @param listener The soft input changed listener.
     */
    fun registerSoftInputChangedListener(window: Window, view: View, listener: OnSoftInputChangedListener) {
        val flags = window.attributes.flags
        if (flags and WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS != 0) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        val contentView = window.findViewById<FrameLayout>(R.id.content)
        sDecorViewInvisibleHeightPre = getDecorViewInvisibleHeight(window)
        listenerMap[view] = listener
        val onGlobalLayoutListener = OnGlobalLayoutListener {
            val height = getDecorViewInvisibleHeight(window)
            if (sDecorViewInvisibleHeightPre != height) {
                //通知所有弹窗的监听器输入法高度变化了
                for (changedListener in listenerMap.values) {
                    changedListener.onSoftInputChanged(height)
                }
                sDecorViewInvisibleHeightPre = height
            }
        }
        contentView.viewTreeObserver
                .addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    fun removeLayoutChangeListener(decorView: View?, view: View?) {
        onGlobalLayoutListener = null
        if (decorView == null) {
            return
        }
        val contentView = decorView.findViewById<View>(R.id.content) ?: return
        contentView.viewTreeObserver.removeGlobalOnLayoutListener(onGlobalLayoutListener)
        listenerMap.remove(view)
    }

    private val navBarHeight: Int
        private get() {
            val res = Resources.getSystem()
            val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
            return if (resourceId != 0) {
                res.getDimensionPixelSize(resourceId)
            } else {
                0
            }
        }

    @JvmStatic
    fun showSoftInput(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    @JvmStatic
    fun hideSoftInput(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}