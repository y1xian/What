package com.yyxnb.what.core.action;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;


/**
 * Context 意图处理（扩展非 Context 类的方法，禁止 Context 类实现此接口）
 *
 * @author yyx
 */
public interface ResourcesAction {

    /**
     * 获取 Context
     */
    Context getContext();

    /**
     * 获取资源对象（仅供子类调用）
     */
    default Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 根据 id 获取一个文本
     */
    default String getString(@StringRes int id) {
        return getContext().getString(id);
    }

    default String getString(@StringRes int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    /**
     * 根据 id 获取一个 Drawable
     */
    default Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(getContext(), id);
    }

    /**
     * 根据 id 获取一个颜色
     */
    @ColorInt
    default int getColor(@ColorRes int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    /**
     * 获取系统服务
     */
    default <S> S getSystemService(@NonNull Class<S> serviceClass) {
        return ContextCompat.getSystemService(getContext(), serviceClass);
    }
}