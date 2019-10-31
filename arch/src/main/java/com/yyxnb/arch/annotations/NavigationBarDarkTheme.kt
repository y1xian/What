package com.yyxnb.arch.annotations

/**
 * 底栏深色浅色切换
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class NavigationBarDarkTheme(val value: BarStyle = BarStyle.DarkContent)
