package com.yyxnb.arch.annotations

/**
 * 绑定 [androidx.lifecycle.ViewModel]
 *
 * @author yyx
 */
@Target(AnnotationTarget.FIELD)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class BindViewModel(val isActivity: Boolean = false)