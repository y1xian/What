package com.yyxnb.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.yyxnb.common.R
import com.yyxnb.widget.WidgetManager.getContext
import java.io.Serializable

/**
 * 自定义Toast
 *
 * @author yyx
 */
object ToastUtils : Serializable {

    @ColorInt
    private val DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")
    private const val TOAST_TYPEFACE = "sans-serif-condensed"
    private val context = getContext()

    /**
     * Toast对象
     */
    private var mToast: Toast? = null

    fun normal(message: String, duration: Int = Toast.LENGTH_SHORT,
               icon: Drawable? = null): Toast? {
        return custom(message, icon, -1, duration)
    }

    fun custom(message: String, icon: Drawable?, @DrawableRes bgRes: Int, duration: Int): Toast? {
        return custom(message, icon, DEFAULT_TEXT_COLOR, bgRes, duration)
    }

    fun custom(message: String, @DrawableRes iconRes: Int,
               @ColorInt textColor: Int, @DrawableRes bgRes: Int, duration: Int): Toast? {
        return custom(message, getDrawable(context, iconRes), textColor, bgRes, duration)
    }

    /**
     * 自定义toast方法
     *
     * @param message   提示消息文本
     * @param icon      提示消息的icon,传入null代表不显示
     * @param textColor 提示消息文本颜色
     * @param bgRes     提示背景颜色
     * @param duration  显示时长
     * @return
     */
    fun custom(message: String, icon: Drawable?, @ColorInt textColor: Int, @DrawableRes bgRes: Int, duration: Int): Toast? {
        if (mToast == null) {
            mToast = Toast(context)
        } else {
            mToast!!.cancel()
            mToast = null
            mToast = Toast(context)
        }
        @SuppressLint("InflateParams") val toastLayout = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout._toast_layout, null)
        val toastIcon = toastLayout.findViewById<ImageView>(R.id.toast_icon)
        val toastText = toastLayout.findViewById<TextView>(R.id.toast_text)
        if (bgRes != -1) {
            setBackground(toastLayout, getDrawable(context, bgRes))
        }
        if (icon == null) {
            toastIcon.visibility = View.GONE
        } else {
            setBackground(toastIcon, icon)
        }
        toastText.setTextColor(textColor)
        toastText.text = message
        toastText.typeface = Typeface.create(TOAST_TYPEFACE, Typeface.NORMAL)
        mToast!!.view = toastLayout
        mToast!!.duration = duration
        mToast!!.show()
        return mToast
    }

    @SuppressLint("ObsoleteSdkInt")
    fun setBackground(view: View, drawable: Drawable?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.background = drawable
        } else {
            view.setBackgroundDrawable(drawable)
        }
    }

    fun getDrawable(context: Context, @DrawableRes id: Int): Drawable? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getDrawable(id)
        } else {
            context.resources.getDrawable(id)
        }
    }
}