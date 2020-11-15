package com.yyxnb.lib_localservice.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class SpacingDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int spanCount;

    public SpacingDecoration(int spanCount, int space) {
        this.spanCount = spanCount;
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space;
        outRect.bottom = space;
        int position = parent.getChildLayoutPosition(view);
        if (position % spanCount == 0) {
            outRect.left = 0;
        }
    }
}
