package com.yyxnb.arch.interfaces

/**
 * 侧滑边距 0为全屏滑动
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class SwipeEdgeSize(val value: Int = 0)
