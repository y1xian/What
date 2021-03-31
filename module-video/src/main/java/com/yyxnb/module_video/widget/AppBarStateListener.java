package com.yyxnb.module_video.widget;

import com.google.android.material.appbar.AppBarLayout;

/**
 * Created by cxf on 2018/7/9.
 */

public abstract class AppBarStateListener implements AppBarLayout.OnOffsetChangedListener {

    public static final int EXPANDED = 1;//展开
    public static final int MIDDLE = 2;//展开和折叠的中间状态
    public static final int COLLAPSED = 3;//折叠

    private int mCurrentState;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            if (mCurrentState != EXPANDED) {
                onStateChanged(appBarLayout, EXPANDED);
            }
            mCurrentState = EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != COLLAPSED) {
                onStateChanged(appBarLayout, COLLAPSED);
            }
            mCurrentState = COLLAPSED;
        } else {
            if (mCurrentState != MIDDLE) {
                onStateChanged(appBarLayout, MIDDLE);
            }
            mCurrentState = MIDDLE;
        }

    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, int state);
}
