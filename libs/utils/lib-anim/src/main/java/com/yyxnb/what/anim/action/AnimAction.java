package com.yyxnb.what.anim.action;

import com.yyxnb.what.anim.R;

/**
 * 动画样式
 *
 * @author yyx
 */
public interface AnimAction {

    /**
     * 默认动画效果
     */
    int ANIM_DEFAULT = -1;

    /**
     * 没有动画效果
     */
    int ANIM_EMPTY = 0;

    /**
     * 缩放+淡入淡出动画
     */
    int ANIM_SCALE = R.style.Animation_Scale;

    /**
     * 吐司动画
     */
    int ANIM_TOAST = android.R.style.Animation_Toast;

    /**
     * 上进上出
     */
    int ANIM_TOP = R.style.Animation_TopToTop;

    /**
     * 下进下出
     */
    int ANIM_BOTTOM = R.style.Animation_BottomToBottom;

    /**
     * 左进左出
     */
    int ANIM_LEFT = R.style.Animation_LeftToLeft;

    /**
     * 右进右出
     */
    int ANIM_RIGHT = R.style.Animation_RightToRight;

    /**
     * 左进右出
     */
    int ANIM_LEFT_TO_RIGHT = R.style.Animation_LeftToRight;

    /**
     * 右进左出
     */
    int ANIM_RIGHT_TO_LEFT = R.style.Animation_RightToLeft;
}