package com.yyxnb.module_wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yyxnb.common.utils.log.LogUtils
import com.yyxnb.common_base.config.Http
import com.yyxnb.module_wanandroid.bean.WanAriticleBean
import com.yyxnb.module_wanandroid.bean.WanClassifyBean
import com.yyxnb.module_wanandroid.bean.WanData
import com.yyxnb.module_wanandroid.bean.WanStatus
import com.yyxnb.module_wanandroid.config.WanService
import com.yyxnb.network.BaseViewModel

class WanSearchViewModel : BaseViewModel() {

    private val mApi = Http.create(WanService::class.java)

    @JvmField
    val searchData = MutableLiveData<List<WanClassifyBean>>()

    @JvmField
    val searchDataByKey = MutableLiveData<WanStatus<WanAriticleBean>>()

    fun getSearchData() {
        launchOnlyResult(
                block = { mApi.searchData() },
                reTry = { getSearchData() },
                success = { searchData.postValue(it) },
                error = { LogUtils.e(it.message) }
        )
    }

    fun getSearchDataByKey(page: Int, key: String?) {
        launchOnlyResult(
                block = { mApi.getSearchDataByKey(page, key) },
                reTry = { getSearchDataByKey(page, key) },
                success = { searchDataByKey.postValue(it) },
                error = { LogUtils.e(it.message) }
        )
    }
}