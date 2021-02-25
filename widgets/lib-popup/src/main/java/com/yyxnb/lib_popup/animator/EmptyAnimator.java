package com.yyxnb.lib_popup.animator;

import android.view.View;

import com.yyxnb.lib_popup.PopupManager;


/**
 * Description: 没有动画效果的动画器
 */
public class EmptyAnimator extends PopupAnimator {
    public EmptyAnimator(View target){
        super(target, null);
    }
    @Override
    public void initAnimator() {
        targetView.setAlpha(0);
    }

    @Override
    public void animateShow() {
        targetView.animate().alpha(1f).setDuration(PopupManager.getAnimationDuration()).withLayer().start();
    }

    @Override
    public void animateDismiss() {
        targetView.animate().alpha(0f).setDuration(PopupManager.getAnimationDuration()).withLayer().start();
    }
}
