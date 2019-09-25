package com.yyxnb.arch.interfaces

import android.graphics.Color

/**
 * 底栏颜色
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class NavigationBarColor(val color: Int = Color.TRANSPARENT)
