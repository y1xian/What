package com.yyxnb.what.core.action;

import android.view.View;

import androidx.annotation.IdRes;

import com.yyxnb.what.core.ClickUtils;

/**
 * 点击事件意图
 *
 * @author yyx
 */
public interface ClickAction extends View.OnClickListener {

    <V extends View> V findViewById(@IdRes int id);

    @Override
    default void onClick(View v) {
        // 默认不实现，让子类实现
        if (ClickUtils.isFastClick()) {
            onClickEvent(v);
        }
    }

    /**
     * 防止过快点击
     */
    default void onClickEvent(View v) {
    }

    default void setOnClickListener(@IdRes int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
    }

    default void setOnClickListener(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }
}