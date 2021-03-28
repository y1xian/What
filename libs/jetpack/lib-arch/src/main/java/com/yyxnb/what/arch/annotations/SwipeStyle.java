package com.yyxnb.what.arch.annotations;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 侧滑 注解
 *
 * @author yyx
 */
@IntDef({SwipeStyle.FULL, SwipeStyle.EDGE, SwipeStyle.NONE})
@Retention(RetentionPolicy.SOURCE)
public @interface SwipeStyle {
    /**
     * 全屏
     */
    int FULL = 1;
    /**
     * 边缘
     */
    int EDGE = 2;
    /**
     * none
     */
    int NONE = 0;
}
