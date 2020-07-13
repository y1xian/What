package com.yyxnb.arch.annotations;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({SwipeStyle.Full, SwipeStyle.Edge, SwipeStyle.None})
@Retention(RetentionPolicy.SOURCE)
public @interface SwipeStyle {
    // 全屏
    int Full = 1;
    // 边缘
    int Edge = 2;
    // none
    int None = 0;
}
