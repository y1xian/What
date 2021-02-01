package com.yyxnb.lib_adapter;


import android.annotation.SuppressLint;
import android.arch.core.executor.ArchTaskExecutor;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 具体原理见 {@link MutableItemKeyedDataSource}
 *
 * @param <Value>
 */
public class MutablePageKeyedDataSource<Value> extends PageKeyedDataSource<Integer, Value> {
    public List<Value> data = new ArrayList<>();

    public PagedList<Value> buildNewPagedList(PagedList.Config config) {
        @SuppressLint("RestrictedApi") PagedList<Value> pagedList = new PagedList.Builder<Integer, Value>(this, config)
                .setFetchExecutor(ArchTaskExecutor.getIOThreadExecutor())
                .setNotifyExecutor(ArchTaskExecutor.getMainThreadExecutor())
                .build();

        return pagedList;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Value> callback) {
        callback.onResult(data, null, null);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Value> callback) {
        callback.onResult(Collections.emptyList(), null);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Value> callback) {
        callback.onResult(Collections.emptyList(), null);
    }
}
