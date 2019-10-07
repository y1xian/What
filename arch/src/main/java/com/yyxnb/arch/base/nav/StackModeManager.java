package com.yyxnb.arch.base.nav;

import android.support.annotation.IntDef;

/**
 * 压栈类型
 */
public class StackModeManager {
    /**
     * 对应activity的standard压栈操作（default）
     */
    public static final int STANDARD = 0x01;
    /**
     * 对应activity的singleTop压栈操作，在栈顶的不再压栈，否则继续压入
     */
    public static final int SINGLE_TOP = 0x02;
    /**
     * 对应activity的singleTop压栈操作，在栈中的不再压栈，弹出其上的所有对象
     */
    public static final int SINGLE_TASK = 0x03;

    @IntDef({STANDARD, SINGLE_TOP, SINGLE_TASK})
    public @interface StackMode {

    }
}