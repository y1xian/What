package com.yyxnb.arch.annotations;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({BarStyle.DarkContent, BarStyle.LightContent, BarStyle.None})
@Retention(RetentionPolicy.SOURCE)
public @interface BarStyle {

    // 深色
    int DarkContent = 1;
    // 浅色
    int LightContent = 2;
    // none
    int None = 0;
}
