package com.yyxnb.lib_skinloader.skinInterface;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

/**
 * 换肤功能，替换资源管理接口
 */

public interface ISkinResourceManager {

    String getPkgName();

    Resources getPluginResource();

    void setPluginResourcesAndPkgName(Resources resources, String pkgName);

    int getColor(@ColorRes int resId) throws Resources.NotFoundException;

    ColorStateList getColorStateList(@ColorRes int resId) throws Resources.NotFoundException;

    Drawable getDrawable(@DrawableRes int resId) throws Resources.NotFoundException;

    Drawable getDrawableForMapmip(int attrValueRefId) throws Resources.NotFoundException;

    ;
}
