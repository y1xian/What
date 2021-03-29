package com.yyxnb.what.popup.animator;

import android.animation.FloatEvaluator;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.yyxnb.what.popup.PopupManager;
import com.yyxnb.what.popup.util.PopupUtils;


/**
 * Description: 背景Shadow动画器，负责执行半透明的渐入渐出动画
 */
public class BlurAnimator extends PopupAnimator {

    private FloatEvaluator evaluate = new FloatEvaluator();

    public BlurAnimator(View target) {
        super(target);
    }

    public Bitmap decorBitmap;
    public boolean hasShadowBg = false;

    public BlurAnimator() {
    }

    @Override
    public void initAnimator() {
        Bitmap blurBmp = PopupUtils.renderScriptBlur(targetView.getContext(), decorBitmap, 25, true);
        BitmapDrawable drawable = new BitmapDrawable(targetView.getResources(), blurBmp);
        if (hasShadowBg) {
            drawable.setColorFilter(PopupManager.getShadowBgColor(), PorterDuff.Mode.SRC_OVER);
        }
        targetView.setBackground(drawable);
    }

    @Override
    public void animateShow() {
//        ValueAnimator animator = ValueAnimator.ofFloat(0,1);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float fraction = animation.getAnimatedFraction();
//                Bitmap blurBmp = ImageUtils.renderScriptBlur(decorBitmap, evaluate.evaluate(0f, 25f, fraction), false);
//                targetView.setBackground(new BitmapDrawable(targetView.getResources(), blurBmp));
//            }
//        });
//        animator.setInterpolator(new LinearInterpolator());
//        animator.setDuration(XPopup.getAnimationDuration()).start();
    }

    @Override
    public void animateDismiss() {
//        ValueAnimator animator = ValueAnimator.ofFloat(1,0);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float fraction = animation.getAnimatedFraction();
//                Bitmap blurBmp = ImageUtils.renderScriptBlur(decorBitmap, evaluate.evaluate(0f, 25f, fraction), false);
//                targetView.setBackground(new BitmapDrawable(targetView.getResources(), blurBmp));
//            }
//        });
//        animator.setInterpolator(new LinearInterpolator());
//        animator.setDuration(XPopup.getAnimationDuration()).start();
    }


}
