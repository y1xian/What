package com.yyxnb.arch.annotations

/**
 * 侧滑
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class SwipeBack(val value: Int = 0)