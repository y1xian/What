package com.yyxnb.module_wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yyxnb.common.utils.log.LogUtils
import com.yyxnb.common_base.config.Http
import com.yyxnb.module_wanandroid.bean.*
import com.yyxnb.module_wanandroid.config.WanService
import com.yyxnb.network.BaseViewModel

class WanTreeViewModel : BaseViewModel() {

    private val mApi = Http.create(WanService::class.java)

    @JvmField
    val squareData = MutableLiveData<WanStatus<WanAriticleBean>>()

    @JvmField
    val systemData = MutableLiveData<List<WanSystemBean>>()

    @JvmField
    val navigationData = MutableLiveData<List<WanNavigationBean>>()

    fun getSquareData(page: Int) {
        launchOnlyResult(
                block = { mApi.getSquareData(page) },
                reTry = { getSquareData(page) },
                success = { squareData.postValue(it) },
                error = { LogUtils.e(it.message) }
        )
    }

    fun getSystemData() {
        launchOnlyResult(
                block = { mApi.systemData() },
                reTry = { getSystemData() },
                success = { systemData.postValue(it) },
                error = { LogUtils.e(it.message) }
        )
    }

    fun getNavigationData() {
        launchOnlyResult(
                block = { mApi.navigationData() },
                reTry = { getNavigationData() },
                success = { navigationData.postValue(it) },
                error = { LogUtils.e(it.message) }
        )
    }
}