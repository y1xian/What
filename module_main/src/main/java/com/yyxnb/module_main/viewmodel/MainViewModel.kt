package com.yyxnb.module_main.viewmodel

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.yyxnb.module_main.bean.MainHomeBean
import com.yyxnb.module_main.config.DataConfig.mainBeans
import com.yyxnb.network.BasePagedViewModel

class MainViewModel : BasePagedViewModel<MainHomeBean>() {

    override fun createDataSource(): DataSource<*, *> {
        return object : PageKeyedDataSource<Int?, MainHomeBean?>() {
            override fun loadInitial(params: LoadInitialParams<Int?>, callback: LoadInitialCallback<Int?, MainHomeBean?>) {
                callback.onResult(mainBeans!!, null, null)
            }

            override fun loadBefore(params: LoadParams<Int?>, callback: LoadCallback<Int?, MainHomeBean?>) {
                callback.onResult(emptyList(), null)
            }

            override fun loadAfter(params: LoadParams<Int?>, callback: LoadCallback<Int?, MainHomeBean?>) {
                callback.onResult(emptyList(), null)
            }
        }
    }
}