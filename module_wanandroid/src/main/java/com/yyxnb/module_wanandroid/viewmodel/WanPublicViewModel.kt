package com.yyxnb.module_wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yyxnb.common.utils.log.LogUtils
import com.yyxnb.common_base.config.Http
import com.yyxnb.module_wanandroid.bean.WanAriticleBean
import com.yyxnb.module_wanandroid.bean.WanClassifyBean
import com.yyxnb.module_wanandroid.bean.WanStatus
import com.yyxnb.module_wanandroid.config.WanService
import com.yyxnb.network.BaseViewModel

class WanPublicViewModel : BaseViewModel() {

    private val mApi = Http.create(WanService::class.java)

    @JvmField
    val publicTypes = MutableLiveData<List<WanClassifyBean>>()

    @JvmField
    val publicData = MutableLiveData<WanStatus<WanAriticleBean>>()

    fun getPublicTypes() {
        launchOnlyResult(
                block = { mApi.publicTypes() },
                reTry = { getPublicTypes() },
                success = { publicTypes.postValue(it) },
                error = { LogUtils.e(it.message) }
        )
    }

    fun getPublicData(page: Int, id: Int) {
        launchOnlyResult(
                block = { mApi.getPublicData(page, id) },
                reTry = { getPublicData(page, id) },
                success = { publicData.postValue(it) },
                error = { LogUtils.e(it.message) }
        )
    }
}