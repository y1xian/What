package com.yyxnb.arch.annotations

/**
 * 给系统窗口留出空间
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class FitsSystemWindows(val value: Boolean = true)
