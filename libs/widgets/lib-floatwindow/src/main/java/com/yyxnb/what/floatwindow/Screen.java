package com.yyxnb.what.floatwindow;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class Screen {
    public static final int WIDTH = 0;
    public static final int HEIGHT = 1;

    @IntDef({WIDTH, HEIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface screenType {
    }
}
