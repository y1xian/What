package com.yyxnb.common_res.weight;

import android.content.Context;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

public class ScaleTransitionPagerTitleView extends ColorTransitionPagerTitleView {

    private float minScale = 0.8f;

    public ScaleTransitionPagerTitleView(Context context) {
        super(context);
    }

    public ScaleTransitionPagerTitleView(Context context, float minScale) {
        super(context);
        this.minScale = minScale;
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        super.onEnter(index, totalCount, enterPercent, leftToRight);
        setScaleX(minScale + (1.0f - minScale) * enterPercent);
        setScaleY(minScale + (1.0f - minScale) * enterPercent);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        super.onLeave(index, totalCount, leavePercent, leftToRight);
        setScaleX(1.0f + (minScale - 1.0f) * leavePercent);
        setScaleY(1.0f + (minScale - 1.0f) * leavePercent);
    }
}
