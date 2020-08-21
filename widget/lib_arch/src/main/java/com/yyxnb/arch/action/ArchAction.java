package com.yyxnb.arch.action;


import com.yyxnb.common.action.CommonAction;

/**
 * 框架常用意图
 *
 * @author yyx
 */
public interface ArchAction extends CommonAction {

    /**
     * 是否使用侧滑
     */
    default boolean isSwipeEnable() {
        // 默认开启
        return true;
    }
}
