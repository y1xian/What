package com.yyxnb.arch.interfaces

import android.support.annotation.IntDef

@IntDef(LceeStatus.Loading, LceeStatus.Content, LceeStatus.Empty, LceeStatus.Error)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class LceeStatus {
    companion object {
        const val Loading = 1
        const val Content = 2
        const val Empty = 3
        const val Error = 4
    }
}
