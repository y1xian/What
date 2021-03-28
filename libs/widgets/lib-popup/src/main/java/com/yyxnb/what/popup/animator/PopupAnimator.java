package com.yyxnb.what.popup.animator;

import android.view.View;

import com.yyxnb.what.popup.PopupManager;


/**
 * Description: 弹窗动画执行器
 */
public abstract class PopupAnimator {
    public View targetView;
    public PopupAnimation popupAnimation; // 内置的动画
    public PopupAnimator(){}
    public PopupAnimator(View target){
        this(target, null);
    }

    public PopupAnimator(View target, PopupAnimation popupAnimation){
        this.targetView = target;
        this.popupAnimation = popupAnimation;
    }

    public abstract void initAnimator();
    public abstract void animateShow();
    public abstract void animateDismiss();
    public int getDuration(){
        return PopupManager.getAnimationDuration();
    }
}
