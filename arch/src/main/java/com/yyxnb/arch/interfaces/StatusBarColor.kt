package com.yyxnb.arch.interfaces

import android.graphics.Color

/**
 * 状态栏颜色
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class StatusBarColor(val color: Int = Color.TRANSPARENT)
