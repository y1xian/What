package com.yyxnb.what.popup.impl;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.yyxnb.what.popup.PopupManager;
import com.yyxnb.what.popup.animator.PopupAnimation;
import com.yyxnb.what.popup.animator.PopupAnimator;
import com.yyxnb.what.popup.animator.TranslateAnimator;
import com.yyxnb.what.popup.code.CenterPopupView;
import com.yyxnb.what.popup.util.PopupUtils;


/**
 * Description: 宽高撑满的全屏弹窗
 */
public class FullScreenPopupView extends CenterPopupView {
    public ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    public FullScreenPopupView(@NonNull Context context) {
        super(context);
        addInnerContent();
    }
    @Override
    protected int getMaxWidth() {
        return 0;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        popupInfo.hasShadowBg = false;
    }

    @Override
    public void onNavigationBarChange(boolean show) {
        if(!show){
            applyFull();
            getPopupContentView().setPadding(0,0,0,0);
        }else {
            applySize(true);
        }
    }

    @Override
    protected void applySize(boolean isShowNavBar) {
        View contentView = getPopupContentView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) contentView.getLayoutParams();
        params.gravity = Gravity.TOP;
        contentView.setLayoutParams(params);
        contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop(), contentView.getPaddingRight(), 0);
    }

    Paint paint = new Paint();
    Rect shadowRect;

    int currColor = Color.TRANSPARENT;
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (popupInfo.hasStatusBarShadow) {
            paint.setColor(currColor);
            shadowRect = new Rect(0, 0, getMeasuredWidth(), PopupUtils.getStatusBarHeight());
            canvas.drawRect(shadowRect, paint);
        }
    }

    @Override
    protected void doShowAnimation() {
        super.doShowAnimation();
        doStatusBarColorTransform(true);
    }

    @Override
    protected void doDismissAnimation() {
        super.doDismissAnimation();
        doStatusBarColorTransform(false);
    }

    public void doStatusBarColorTransform(boolean isShow){
        if (popupInfo.hasStatusBarShadow) {
            //状态栏渐变动画
            ValueAnimator animator = ValueAnimator.ofObject(argbEvaluator,
                    isShow ? Color.TRANSPARENT : PopupManager.statusBarShadowColor,
                    isShow ? PopupManager.statusBarShadowColor : Color.TRANSPARENT);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currColor = (Integer) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animator.setDuration(PopupManager.getAnimationDuration()).start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        paint = null;
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        return new TranslateAnimator(getPopupContentView(), PopupAnimation.TranslateFromBottom);
    }
}
