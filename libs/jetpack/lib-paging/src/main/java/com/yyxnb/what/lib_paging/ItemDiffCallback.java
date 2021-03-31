package com.yyxnb.what.lib_paging;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.yyxnb.what.core.interfaces.IData;

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
