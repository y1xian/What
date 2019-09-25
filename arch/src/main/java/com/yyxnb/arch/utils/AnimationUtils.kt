package com.yyxnb.arch.utils

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import java.io.Serializable

/**
 * Description: 动画工具
 *
 * @author : yyx
 * @date : 2018/7/16
 */
object AnimationUtils : Serializable {

    //从下面显示隐藏view动画
    val hiddenBottomAction: TranslateAnimation
        get() {
            val mAction = TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                    1.0f)
            mAction.duration = 300
            return mAction
        }
    val showBottomAction: TranslateAnimation
        get() {
            val mAction = TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                    0f)
            mAction.duration = 300
            return mAction
        }

    //从左边显示隐藏view动画
    val hiddenLeftAction: TranslateAnimation
        get() {
            val mAction = TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                    0f)
            mAction.duration = 300
            return mAction
        }
    val showLeftAction: TranslateAnimation
        get() {
            val mAction = TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    -1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                    0f)
            mAction.duration = 300
            return mAction
        }

    //从右边显示隐藏view动画
    val hiddenRightAction: TranslateAnimation
        get() {
            val mAction = TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                    0f)
            mAction.duration = 300
            return mAction
        }
    val showRightAction: TranslateAnimation
        get() {
            val mAction = TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                    0f)
            mAction.duration = 300
            return mAction
        }

    //显示隐藏动画 淡入淡出
    val hiddenAnimation: AlphaAnimation
        get() {

            val mAction = AlphaAnimation(1.0f, 0f)
            mAction.duration = 300
            return mAction
        }
    val showAnimation: AlphaAnimation
        get() {

            val mAction = AlphaAnimation(0f, 1.0f)
            mAction.duration = 300
            return mAction
        }

    //旋转动画 顺时针
    val clockwiseAnimator: ObjectAnimator
        get() {
            @SuppressLint("ObjectAnimatorBinding")
            val mAction = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)
            mAction.duration = 300
            return mAction
        }

    //旋转动画 逆时针
    val antiClockwiseAnimator: ObjectAnimator
        get() {
            @SuppressLint("ObjectAnimatorBinding")
            val mAction = ObjectAnimator.ofFloat(this, "rotation", 3600f, 0f)
            mAction.duration = 300
            return mAction
        }

    //从上面显示隐藏view动画
    val hiddenTopAction: TranslateAnimation
        get() {
            val mAction = TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                    -1.0f)
            mAction.duration = 300
            return mAction
        }
    val showTopAction: TranslateAnimation
        get() {
            val mAction = TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF,
                    0f)
            mAction.duration = 300
            return mAction
        }

}
