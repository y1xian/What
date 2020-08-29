package com.yyxnb.arch.annotations

import androidx.annotation.IntDef

/**
 * 状态栏文字颜色
 *
 * @author yyx
 */
@IntDef(BarStyle.DARK_CONTENT, BarStyle.LIGHT_CONTENT, BarStyle.NONE)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class BarStyle {
    companion object {
        /**
         * 深色
         */
        const val DARK_CONTENT = 1

        /**
         * 浅色
         */
        const val LIGHT_CONTENT = 2

        /**
         * none
         */
        const val NONE = 0
    }
}