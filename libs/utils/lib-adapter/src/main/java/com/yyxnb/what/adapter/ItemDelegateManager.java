package com.yyxnb.what.adapter;


import androidx.collection.SparseArrayCompat;

import com.yyxnb.what.adapter.base.BaseViewHolder;

public class ItemDelegateManager<T> {

    private SparseArrayCompat<ItemDelegate<T>> delegates = new SparseArrayCompat<>();

    public int itemViewDelegateCount() {
        return delegates.size();
    }

    @SuppressWarnings("UnusedAssignment")
    public ItemDelegateManager<T> addDelegate(ItemDelegate<T> delegate) {
        int viewType = delegates.size();
        delegates.put(viewType, delegate);
        viewType++;
        return this;
    }

    public ItemDelegateManager<T> addDelegate(int viewType, ItemDelegate<T> delegate) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemDelegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }


    public int getItemViewType(T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isThisType(item, position)) {
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemDelegate added that matches position= " + position + " in data source");
    }

    public void convert(BaseViewHolder holder, T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            ItemDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isThisType(item, position)) {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemDelegateManager added that matches position= " + position + " in data source");
    }

    public ItemDelegate<T> getItemViewDelegate(int viewType) {
        return delegates.get(viewType);
    }

    public int getItemLayoutId(int viewType) {
        return getItemViewDelegate(viewType).layoutId();
    }

    public int getItemViewType(ItemDelegate<T> itemViewDelegate) {
        return delegates.indexOfValue(itemViewDelegate);
    }
}
