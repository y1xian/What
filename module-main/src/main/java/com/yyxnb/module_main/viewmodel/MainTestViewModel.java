package com.yyxnb.module_main.viewmodel;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.yyxnb.common_base.base.BasePagedViewModel;
import com.yyxnb.module_main.bean.MainHomeBean;
import com.yyxnb.module_main.config.DataConfig;

import java.util.Collections;

public class MainTestViewModel extends BasePagedViewModel<MainHomeBean> {
    @Override
    public DataSource createDataSource() {
        return new PageKeyedDataSource<Integer, MainHomeBean>() {

            @Override
            public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, MainHomeBean> callback) {
                callback.onResult(DataConfig.getMainBeans(), null, null);
            }

            @Override
            public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MainHomeBean> callback) {
                callback.onResult(Collections.emptyList(), null);
            }

            @Override
            public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MainHomeBean> callback) {
                callback.onResult(Collections.emptyList(), null);
            }
        };
    }
}
