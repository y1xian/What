package com.yyxnb.what.lib_paging.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.yyxnb.what.adapter.ItemDelegate;
import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.what.core.interfaces.IData;
import com.yyxnb.what.lib_paging.ItemDiffCallback;

public abstract class BasePagedAdapter<T extends IData> extends MultiItemTypePagedAdapter<T> {

    public BasePagedAdapter(int mLayoutId, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        this.mLayoutId = mLayoutId;
    }

    public BasePagedAdapter(int mLayoutId) {
        super(new ItemDiffCallback<T>());
        this.mLayoutId = mLayoutId;
    }

    private int mLayoutId;

    {
        //noinspection InfiniteRecursion
        addItemDelegate(new ItemDelegate<T>() {
            @Override
            public int layoutId() {
                return mLayoutId;
            }

            @Override
            public boolean isThisType(T item, int position) {
                return true;
            }

            @Override
            public void convert(BaseViewHolder holder, T item, int position) {
                bind(holder, item, position);
            }
        });
    }

    protected abstract void bind(BaseViewHolder holder, T item, int position);

}
