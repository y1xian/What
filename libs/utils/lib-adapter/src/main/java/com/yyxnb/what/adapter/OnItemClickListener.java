package com.yyxnb.what.adapter;

import android.view.View;

import com.yyxnb.what.adapter.base.BaseViewHolder;

public interface OnItemClickListener {

    /**
     * 单击
     *
     * @param view
     * @param holder
     * @param position
     */
    void onItemClick(View view, BaseViewHolder holder, int position);

    /**
     * 长按
     *
     * @param view
     * @param holder
     * @param position
     * @return
     */
    boolean onItemLongClick(View view, BaseViewHolder holder, int position);

    /**
     * 单击子控件
     *
     * @param view
     * @param holder
     * @param position
     */
    void onItemChildClick(View view, BaseViewHolder holder, int position);

    /**
     * 长按子控件
     *
     * @param view
     * @param holder
     * @param position
     * @return
     */
    boolean onItemChildLongClick(View view, BaseViewHolder holder, int position);
}