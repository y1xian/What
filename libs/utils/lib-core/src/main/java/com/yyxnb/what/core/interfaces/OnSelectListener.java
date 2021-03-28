package com.yyxnb.what.core.interfaces;

import android.view.View;

/**
 * 选择回调
 *
 * @author yyx
 */
public interface OnSelectListener {

    /**
     * 点击回调
     *
     * @param v        view
     * @param position 当前位置
     * @param text     内容
     */
    void onClick(View v, int position, String text);
}
