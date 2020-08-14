package com.yyxnb.module_wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yyxnb.common.utils.log.LogUtils
import com.yyxnb.common_base.config.Http
import com.yyxnb.module_wanandroid.bean.WanAriticleBean
import com.yyxnb.module_wanandroid.bean.WanStatus
import com.yyxnb.module_wanandroid.config.WanService
import com.yyxnb.network.BaseViewModel

class WanHomeViewModel : BaseViewModel() {

    private val mApi = Http.create(WanService::class.java)

    @JvmField
    val bannerData = MutableLiveData<List<WanAriticleBean>>()

    @JvmField
    val topArticleData = MutableLiveData<List<WanAriticleBean>>()

    @JvmField
    val homeListData = MutableLiveData<WanStatus<WanAriticleBean>>()

    fun getAritrilList(page: Int) {
        launchOnlyResult(
                block = { mApi.getAritrilList(page) },
                reTry = { getAritrilList(page) },
                success = { homeListData.postValue(it) },
                error = { LogUtils.e(it.message) }
        )
    }

    fun getTopAritrilList() {
        launchOnlyResult(
                block = { mApi.topAritrilList() },
                reTry = { getTopAritrilList() },
                success = { topArticleData.postValue(it) },
                error = { LogUtils.e(it.message) }
        )
    }


    fun getBanner() {
        launchOnlyResult(
                block = { mApi.banner() },
                reTry = { getBanner() },
                success = { bannerData.postValue(it) },
                error = { LogUtils.e(it.message) }
        )
    }

}