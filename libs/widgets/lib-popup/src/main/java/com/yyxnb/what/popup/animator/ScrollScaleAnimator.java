package com.yyxnb.what.popup.animator;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.view.View;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.yyxnb.what.popup.PopupManager;


/**
 * Description: 像系统的PopupMenu那样的动画
 */
public class ScrollScaleAnimator extends PopupAnimator{

    private IntEvaluator intEvaluator = new IntEvaluator();
    private int startScrollX, startScrollY;
    private float startAlpha = 0f;
    private float startScale = 0f;

    public boolean isOnlyScaleX = false;
    public ScrollScaleAnimator(View target, PopupAnimation popupAnimation) {
        super(target, popupAnimation);
    }

    @Override
    public void initAnimator() {
        targetView.setAlpha(startAlpha);
        targetView.setScaleX(startScale);
        if(!isOnlyScaleX){
            targetView.setScaleY(startScale);
        }

        targetView.post(new Runnable() {
            @Override
            public void run() {
                // 设置参考点
                applyPivot();
                targetView.scrollTo(startScrollX, startScrollY);
            }
        });
    }

    private void applyPivot(){
        switch (popupAnimation){
            case ScrollAlphaFromLeft:
                targetView.setPivotX(0f);
                targetView.setPivotY(targetView.getMeasuredHeight()/2);

                startScrollX = targetView.getMeasuredWidth();
                startScrollY = 0;
                break;
            case ScrollAlphaFromLeftTop:
                targetView.setPivotX(0f);
                targetView.setPivotY(0f);
                startScrollX =  targetView.getMeasuredWidth();
                startScrollY =  targetView.getMeasuredHeight();
                break;
            case ScrollAlphaFromTop:
                targetView.setPivotX(targetView.getMeasuredWidth()/2);
                targetView.setPivotY(0f);

                startScrollY =  targetView.getMeasuredHeight();
                break;
            case ScrollAlphaFromRightTop:
                targetView.setPivotX(targetView.getMeasuredWidth());
                targetView.setPivotY(0f);
                startScrollX =  -targetView.getMeasuredWidth();
                startScrollY =  targetView.getMeasuredHeight();
                break;
            case ScrollAlphaFromRight:
                targetView.setPivotX(targetView.getMeasuredWidth());
                targetView.setPivotY(targetView.getMeasuredHeight()/2);

                startScrollX =  -targetView.getMeasuredWidth();
                break;
            case ScrollAlphaFromRightBottom:
                targetView.setPivotX(targetView.getMeasuredWidth());
                targetView.setPivotY(targetView.getMeasuredHeight());

                startScrollX =  -targetView.getMeasuredWidth();
                startScrollY =  -targetView.getMeasuredHeight();
                break;
            case ScrollAlphaFromBottom:
                targetView.setPivotX(targetView.getMeasuredWidth()/2);
                targetView.setPivotY(targetView.getMeasuredHeight());

                startScrollY =  -targetView.getMeasuredHeight();
                break;
            case ScrollAlphaFromLeftBottom:
                targetView.setPivotX(0);
                targetView.setPivotY(targetView.getMeasuredHeight());

                startScrollX =  targetView.getMeasuredWidth();
                startScrollY =  -targetView.getMeasuredHeight();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + popupAnimation);
        }
    }

    @Override
    public void animateShow() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                targetView.setAlpha(fraction);
                targetView.scrollTo(intEvaluator.evaluate(fraction, startScrollX, 0),
                        intEvaluator.evaluate(fraction, startScrollY, 0));
                targetView.setScaleX(fraction);
                if(!isOnlyScaleX) {
                    targetView.setScaleY(fraction);
                }
            }
        });
        animator.setDuration(PopupManager.getAnimationDuration()).setInterpolator(new FastOutSlowInInterpolator());
        animator.start();
    }

    @Override
    public void animateDismiss() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                targetView.setAlpha(1-fraction);
                targetView.scrollTo(intEvaluator.evaluate(fraction, 0, startScrollX),
                        intEvaluator.evaluate(fraction, 0, startScrollY));
                targetView.setScaleX(1-fraction);
                if(!isOnlyScaleX) {
                    targetView.setScaleY(1-fraction);
                }
            }
        });
        animator.setDuration(PopupManager.getAnimationDuration())
                .setInterpolator(new FastOutSlowInInterpolator());
        animator.start();
    }

}
