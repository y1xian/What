package com.yyxnb.lib_popup.animator;

import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;

import com.yyxnb.lib_popup.PopupManager;

/**
 * Description: 平移动画，不带渐变
 */
public class TranslateAnimator extends PopupAnimator {
    //动画起始坐标
    private float startTranslationX, startTranslationY;
    private int oldWidth, oldHeight;
    private float initTranslationX, initTranslationY;
    private boolean hasInitDefTranslation = false;

    public TranslateAnimator(View target, PopupAnimation popupAnimation) {
        super(target, popupAnimation);
    }

    @Override
    public void initAnimator() {
        if (!hasInitDefTranslation) {
            initTranslationX = targetView.getTranslationX();
            initTranslationY = targetView.getTranslationY();
            hasInitDefTranslation = true;
        }
        // 设置起始坐标
        applyTranslation();
        startTranslationX = targetView.getTranslationX();
        startTranslationY = targetView.getTranslationY();

        oldWidth = targetView.getMeasuredWidth();
        oldHeight = targetView.getMeasuredHeight();
    }

    private void applyTranslation() {
        switch (popupAnimation) {
            case TranslateFromLeft:
                targetView.setTranslationX(-targetView.getRight());
                break;
            case TranslateFromTop:
                targetView.setTranslationY(-targetView.getBottom());
                break;
            case TranslateFromRight:
                targetView.setTranslationX(((View) targetView.getParent()).getMeasuredWidth() - targetView.getLeft());
                break;
            case TranslateFromBottom:
                targetView.setTranslationY(((View) targetView.getParent()).getMeasuredHeight() - targetView.getTop());
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + popupAnimation);
        }
    }

    @Override
    public void animateShow() {
        targetView.animate().translationX(initTranslationX).translationY(initTranslationY)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(PopupManager.getAnimationDuration()).withLayer().start();
    }

    @Override
    public void animateDismiss() {
        //执行消失动画的时候，宽高可能改变了，所以需要修正动画的起始值
        switch (popupAnimation) {
            case TranslateFromLeft:
                startTranslationX -= targetView.getMeasuredWidth() - oldWidth;
                break;
            case TranslateFromTop:
                startTranslationY -= targetView.getMeasuredHeight() - oldHeight;
                break;
            case TranslateFromRight:
                startTranslationX += targetView.getMeasuredWidth() - oldWidth;
                break;
            case TranslateFromBottom:
                startTranslationY += targetView.getMeasuredHeight() - oldHeight;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + popupAnimation);
        }
        targetView.animate().translationX(startTranslationX).translationY(startTranslationY)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(PopupManager.getAnimationDuration())
                .withLayer()
                .start();
    }
}
