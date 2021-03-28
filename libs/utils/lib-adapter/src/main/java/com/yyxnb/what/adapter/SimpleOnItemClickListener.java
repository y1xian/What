package com.yyxnb.what.adapter;

import android.view.View;

import com.yyxnb.what.adapter.base.BaseViewHolder;

public class SimpleOnItemClickListener implements OnItemClickListener {

    @Override
    public void onItemClick(View view, BaseViewHolder holder, int position) {

    }

    @Override
    public boolean onItemLongClick(View view, BaseViewHolder holder, int position) {
        return false;
    }

    @Override
    public void onItemChildClick(View view, BaseViewHolder holder, int position) {

    }

    @Override
    public boolean onItemChildLongClick(View view, BaseViewHolder holder, int position) {
        return false;
    }
}