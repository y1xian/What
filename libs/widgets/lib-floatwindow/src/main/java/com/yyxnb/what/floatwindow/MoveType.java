package com.yyxnb.what.floatwindow;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class MoveType {

    /**
     * 固定
     */
    public static final int FIXED = 0;
    /**
     * 不可拖动
     */
    public static final int INACTIVE = 1;
    /**
     * 可拖动
     */
    public static final int ACTIVE = 2;
    /**
     * 可拖动，释放后自动贴边 （默认
     */
    public static final int SLIDE = 3;
    /**
     * 可拖动，释放后自动回到原位置
     */
    public static final int BACK = 4;

    @IntDef({FIXED, INACTIVE, ACTIVE, SLIDE, BACK})
    @Retention(RetentionPolicy.SOURCE)
    @interface MOVE_TYPE {
    }
}
