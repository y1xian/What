package com.yyxnb.arch.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.Px
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import com.yyxnb.arch.ext.ViewClickDelay.DELAY_TIME
import com.yyxnb.arch.ext.ViewClickDelay.hash
import com.yyxnb.arch.ext.ViewClickDelay.lastClickTime

/**
 * @author TuFei
 * @date 18-10-10.
 */

object ViewClickDelay {
    var hash: Int = 0
    var lastClickTime: Long = 0
    var DELAY_TIME: Long = 500L
}

/**
 * 防止多次点击
 */
fun View.clickDelay(delay: Long = DELAY_TIME, clickAction: () -> Unit) {
    this.setOnClickListener {
        if (this.hashCode() != hash) {
            hash = this.hashCode()
            lastClickTime = System.currentTimeMillis()
            clickAction()
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > delay) {
                lastClickTime = System.currentTimeMillis()
                clickAction()
            }
        }
    }
}

/**
 * 1.activity、fragment等要先实现View.OnClickListener
 * 2.在activity、fragment里面直接调用该扩展方法，给多个控件设置点击事件
 *
 * 多个控件设置setOnClickListener
 */
fun View.OnClickListener.setOnClickListener(vararg ids: View?) {
    ids.filterNotNull().forEach {
        it.setOnClickListener(this)
    }
}

/**
 * 设置多个View的显示和隐藏
 *
 * @param visibility 显示或隐藏
 * @param ids View
 */
fun View.setVisibility(visibility: Int, vararg ids: View) {
    ids.forEach {
        it.visibility = visibility
    }
}

/**
 * 多个CheckBox设置setOnCheckedChangeListener
 */
fun CompoundButton.OnCheckedChangeListener.setOnCheckedChangeListener(vararg ids: CheckBox?) {
    ids.filterNotNull().forEach {
        it.setOnCheckedChangeListener(this)
    }
}

/**
 * 多个CheckBox设置setOnCheckedChangeListener
 */
fun CompoundButton.OnCheckedChangeListener.setOnCheckedChangeListener(ids: List<CheckBox?>) {
    ids.filterNotNull().forEach {
        it.setOnCheckedChangeListener(this)
    }
}

/**
 * 手动显示软键盘
 */
fun View.showKeyboard() {
    if (requestFocus()) {
        val imm = context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

/**
 * 手动隐藏软键盘
 */
fun View.hideKeyBoard() {
    val imm = context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.inflate(layoutRes: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false): View =
        LayoutInflater.from(this).inflate(layoutRes, parent, attachToRoot)

var View.isVisible: Boolean
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

var View.isHidden: Boolean
    get() = visibility == GONE
    set(value) {
        visibility = if (value) GONE else VISIBLE
    }

var View.isInvisible: Boolean
    get() = visibility == INVISIBLE
    set(value) {
        visibility = if (value) INVISIBLE else VISIBLE
    }

fun View.show() {
    visibility = VISIBLE
}

fun View.hide() {
    visibility = GONE
}

fun View.invisible() {
    visibility = INVISIBLE
}

fun Context.getColorRef(@ColorRes res: Int): Int {
    return ContextCompat.getColor(this, res)
}

fun Context.getDrawableRef(@DrawableRes res: Int): Drawable? {
    return ContextCompat.getDrawable(this, res)
}

fun View.getColorRef(@ColorRes res: Int): Int {
    return ContextCompat.getColor(this.context, res)
}

fun View.getDrawableRef(@DrawableRes res: Int): Drawable? {
    return ContextCompat.getDrawable(this.context, res)
}

fun View.setPadding(@Px size: Int) {
    setPadding(size, size, size, size)
}

inline fun View.postDelayed(delayInMillis: Long, crossinline action: () -> Unit): Runnable {
    val runnable = Runnable { action() }
    postDelayed(runnable, delayInMillis)
    return runnable
}

fun View.toBitmap(scale: Float = 1f, config: Bitmap.Config = Bitmap.Config.ARGB_8888): Bitmap? {
    if (this is ImageView) {
        if (drawable is BitmapDrawable) return (drawable as BitmapDrawable).bitmap
    }
    this.clearFocus()
    val bitmap = createBitmapSafely((width * scale).toInt(), (height * scale).toInt(), config, 1)
    if (bitmap != null) {
        Canvas().run {
            setBitmap(bitmap)
            save()
            drawColor(Color.WHITE)
            scale(scale, scale)
            this@toBitmap.draw(this)
            restore()
            setBitmap(null)
        }
    }
    return bitmap
}

fun createBitmapSafely(width: Int, height: Int, config: Bitmap.Config, retryCount: Int): Bitmap? {
    try {
        return Bitmap.createBitmap(width, height, config)
    } catch (e: OutOfMemoryError) {
        e.printStackTrace()
        if (retryCount > 0) {
            System.gc()
            return createBitmapSafely(width, height, config, retryCount - 1)
        }
        return null
    }

}


inline fun View.onGlobalLayout(crossinline callback: () -> Unit) = with(viewTreeObserver) {
    addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun onGlobalLayout() {
            removeOnGlobalLayoutListener(this)
            callback()
        }
    })
}

inline fun View.afterMeasured(crossinline callback: View.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                callback()
            }
        }
    })
}