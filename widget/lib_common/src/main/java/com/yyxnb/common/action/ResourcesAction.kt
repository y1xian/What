package com.yyxnb.common.action

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.ContextCompat

/**
 * Context 意图处理（扩展非 Context 类的方法，禁止 Context 类实现此接口）
 *
 * @author yyx
 */
interface ResourcesAction {
    /**
     * 获取 Context
     */
    val context: Context

    /**
     * 获取资源对象（仅供子类调用）
     */
    val resources: Resources
        get() = context.resources

    /**
     * 根据 id 获取一个文本
     */
    fun getString(@StringRes id: Int): String? {
        return context.getString(id)
    }

    fun getString(@StringRes id: Int, vararg formatArgs: Any?): String? {
        return resources.getString(id, *formatArgs)
    }

    /**
     * 根据 id 获取一个 Drawable
     */
    fun getDrawable(@DrawableRes id: Int): Drawable? {
        return ContextCompat.getDrawable(context, id)
    }

    /**
     * 根据 id 获取一个颜色
     */
    @ColorInt
    fun getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(context, id)
    }

    /**
     * 获取系统服务
     */
    fun <S> getSystemService(@NonNull serviceClass: Class<S>): S? {
        return ContextCompat.getSystemService(context, serviceClass)
    }
}