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

class WanProjectViewModel : BaseViewModel() {

    private val mApi = Http.create(WanService::class.java)

    @JvmField
    val projecTypes = MutableLiveData<List<WanClassifyBean>>()

    @JvmField
    val projecDataByType = MutableLiveData<WanStatus<WanAriticleBean>>()

    @JvmField
    val projecNewData = MutableLiveData<List<WanAriticleBean>>()

    fun getProjecTypes() {
        launchOnlyResult(
                block = { mApi.projecTypes() },
                reTry = { getProjecTypes() },
                success = { projecTypes.postValue(it) },
                error = { LogUtils.e(it.message) }
        )
    }

    fun getProjecDataByType(page: Int, cid: Int) {
        launchOnlyResult(
                block = { mApi.getProjecDataByType(page, cid) },
                reTry = { getProjecDataByType(page, cid) },
                success = { projecDataByType.postValue(it) },
                error = { LogUtils.e(it.message) }
        )
    }

    fun getProjecNewData(page: Int) {
        launchOnlyResult(
                block = { mApi.getProjecNewData(page) },
                reTry = { getProjecNewData(page) },
                success = { projecNewData.postValue(it) },
                error = { LogUtils.e(it.message) }
        )
    }
}