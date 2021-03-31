package com.yyxnb.what.arch.annotations;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 状态栏文字颜色
 *
 * @author yyx
 */
@IntDef({BarStyle.DARK_CONTENT, BarStyle.LIGHT_CONTENT, BarStyle.NONE})
@Retention(RetentionPolicy.SOURCE)
public @interface BarStyle {

    /**
     * 深色
     */
    int DARK_CONTENT = 1;
    /**
     * 浅色
     */
    int LIGHT_CONTENT = 2;
    /**
     * none
     */
    int NONE = 0;
}
