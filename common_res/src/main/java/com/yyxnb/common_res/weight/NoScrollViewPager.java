package com.yyxnb.common_res.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/24
 * 历    史：
 * 描    述：处理viewpager 滑动 ，动画
 * ================================================
 */
public class NoScrollViewPager extends ViewPager {

    // 是否可以滑动，默认false 可以滑动
    private boolean noScroll = false;
    // 夸多个切换是否有滑动，默认false 无动画
    private boolean animation = false;


    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    public void setAnimation(boolean animation) {
        this.animation = animation;
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }


    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (noScroll) {
            return false;
        } else {
            return super.onTouchEvent(arg0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll) {
            return false;
        } else {
            return super.onInterceptTouchEvent(arg0);
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, animation);
    }

}