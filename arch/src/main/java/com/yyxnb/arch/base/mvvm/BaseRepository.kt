package com.yyxnb.arch.base.mvvm

import android.arch.lifecycle.DefaultLifecycleObserver


/**
 * 数据仓库
 *
 * 即使Repository模块看起来没有必要，它也有着重要的作用。它从应用程序的其余部分提取数据源。
 *
 * 一个数据仓库负责获取同类型的数据来源。比如图书数据仓库能够获取各种条件筛选的图书数据，
 * 这份数据可以来自网络（Retrofit + OKHttp），本地Database（Room），缓存 （HashMap）等等，
 * ViewModel在从Repository获取数据时，不需关注数据具体是怎么来的。
 * @author yyx
 * @date ：2018/6/13
 */
abstract class BaseRepository<T : Any> : DefaultLifecycleObserver {

    protected lateinit var mApi: T

//    init {
//        mApi = initApiServer(Arch.getInstance<Class<T>>(this, 0)!!)
//    }
//
//    private fun initApiServer(modelClass: Class<T>): T {
//        return RetrofitManager.createApi(modelClass)
//    }

}
