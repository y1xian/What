package com.yyxnb.arch.interfaces

/**
 * 状态栏隐藏
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class StatusBarHidden(val value: Boolean = false)
