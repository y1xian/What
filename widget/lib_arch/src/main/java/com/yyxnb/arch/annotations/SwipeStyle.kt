package com.yyxnb.arch.annotations

import androidx.annotation.IntDef

/**
 * 侧滑 注解
 *
 * @author yyx
 */
@IntDef(SwipeStyle.FULL, SwipeStyle.EDGE, SwipeStyle.NONE)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class SwipeStyle {
    companion object {
        /**
         * 全屏
         */
        const val FULL = 1

        /**
         * 边缘
         */
        const val EDGE = 2

        /**
         * none
         */
        const val NONE = 0
    }
}