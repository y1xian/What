package com.yyxnb.lib_adapter;

import android.view.View;

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