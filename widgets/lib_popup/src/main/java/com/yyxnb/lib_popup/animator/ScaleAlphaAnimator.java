package com.yyxnb.lib_popup.animator;

import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.yyxnb.lib_popup.PopupManager;


/**
 * Description: 缩放透明
 */
public class ScaleAlphaAnimator extends PopupAnimator {
    public ScaleAlphaAnimator(View target, PopupAnimation popupAnimation) {
        super(target, popupAnimation);
    }

    @Override
    public void initAnimator() {
        targetView.setScaleX(0f);
        targetView.setScaleY(0f);
        targetView.setAlpha(0);

        // 设置动画参考点
        targetView.post(new Runnable() {
            @Override
            public void run() {
                applyPivot();
            }
        });
    }

    /**
     * 根据不同的PopupAnimation来设定对应的pivot
     */
    private void applyPivot() {
        switch (popupAnimation) {
            case ScaleAlphaFromCenter:
                targetView.setPivotX(targetView.getMeasuredWidth() / 2);
                targetView.setPivotY(targetView.getMeasuredHeight() / 2);
                break;
            case ScaleAlphaFromLeftTop:
                targetView.setPivotX(0);
                targetView.setPivotY(0);
                break;
            case ScaleAlphaFromRightTop:
                targetView.setPivotX(targetView.getMeasuredWidth());
                targetView.setPivotY(0f);
                break;
            case ScaleAlphaFromLeftBottom:
                targetView.setPivotX(0f);
                targetView.setPivotY(targetView.getMeasuredHeight());
                break;
            case ScaleAlphaFromRightBottom:
                targetView.setPivotX(targetView.getMeasuredWidth());
                targetView.setPivotY(targetView.getMeasuredHeight());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + popupAnimation);
        }

    }

    @Override
    public void animateShow() {
        targetView.animate().scaleX(1f).scaleY(1f).alpha(1f)
                .setDuration(PopupManager.getAnimationDuration())
                .setInterpolator(new OvershootInterpolator(1f))
                .withLayer()
                .start();
    }

    @Override
    public void animateDismiss() {
        targetView.animate().scaleX(0f).scaleY(0f).alpha(0f).setDuration(PopupManager.getAnimationDuration())
                .setInterpolator(new FastOutSlowInInterpolator())
                .withLayer()
                .start();
    }

}
