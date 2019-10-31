package com.yyxnb.arch.annotations

import android.support.annotation.LayoutRes

/**
 * 布局id
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class LayoutResId(@LayoutRes val value: Int)
