package com.yyxnb.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.yyxnb.common.interfaces.IData;

public class ItemDiffCallback<T extends IData> extends DiffUtil.ItemCallback<T> {
    @Override
    public boolean areItemsTheSame(@NonNull T t, @NonNull T t1) {
        return t.id() == t1.id();
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull T t, @NonNull T t1) {
        return t.equals(t1);
    }
}
