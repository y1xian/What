package com.yyxnb.arch.base.mvvm

import android.arch.lifecycle.*
import android.support.annotation.CallSuper
import com.jeremyliao.liveeventbus.LiveEventBus
import com.yyxnb.arch.bean.Lcee
import com.yyxnb.arch.bean.Result
import com.yyxnb.arch.ext.map
import com.yyxnb.arch.ext.switchMap
import com.yyxnb.arch.ext.tryCatch
import kotlinx.coroutines.*


/**
 * 逻辑处理
 *
 * 负责数据处理和View层与Model层的交互。
 * ViewModel通过数据仓库Repository获取数据来源，处理来自View的事件命令，同时更新数据。
 * @author : yyx
 * @date ：2018/6/13
 */
abstract class BaseViewModel : ViewModel(), DefaultLifecycleObserver {

    val mData by lazy { MutableLiveData<Result>() }

    open val mScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }

    fun launchUI(block: suspend CoroutineScope.() -> Unit) {

        mScope.launch {
            tryCatch({
                block()
            }, {
                //                error?.invoke(it) ?: view?.showError(it.toString())
                throw it
            })
        }
    }

    fun <T> reqMap3(map: Map<String, String>, str: (Map<String, String>) -> LiveData<T>) {
//        val req = SingleLiveEvent<Map<String, String>>()
//        req.value = map
//        req.postValue(map)
        val reqTeam: MutableLiveData<Map<String, String>> = MutableLiveData()

        reqTeam.postValue(map)

    }

    fun <T> reqMap(map: Map<String, String>, str: (Map<String, String>) -> LiveData<T>): LiveData<T> {
//        val req = SingleLiveEvent<Map<String, String>>()
//        req.value = map
//        req.postValue(map)
        val reqTeam: MutableLiveData<Map<String, String>> = MutableLiveData()

        reqTeam.postValue(map)

        return reqTeam.switchMap { input ->
            println("map in $input")
            str(input)
        }
    }

    fun <T> reqMap2(map: LiveData<Map<String, String>>, str: (Map<String, String>) -> LiveData<T>): LiveData<T> {
//        val req = SingleLiveEvent<Map<String, String>>()
//        req.value = map
//        req.postValue(map)

        return map.switchMap { input ->
            println("map in $input")
            str(input)
        }
    }

    fun <T> reqMap3(map: LiveData<Map<String, String>>, str: (Map<String, String>) -> LiveData<T>) {
//        val req = SingleLiveEvent<Map<String, String>>()
//        req.value = map
//        req.postValue(map)
        bus("AAA", map.switchMap { input ->
            println("map in $input")
            str(input)
        })

    }


    fun <T> bus(tag: String = "tag", value: LiveData<T>) {
        value.map {
            println("value in $it")

            val data = Result(resultObject = it, tag = tag)

            mData.value = data

            LiveEventBus.get(tag, Result::class.java).post(data)
        }
    }

    fun <T> bus2(tag: String = "tag", value: LiveData<T>) {
//        value.switchMap {
//
//        }
    }

    fun <T> mapPage(tag: String = "tag", source: LiveData<T>): LiveData<T> {
        return Transformations.map(source) {

            println("value in $it")

//            val data = MutableLiveData<Result(0,it,"1")>()

//            if (null === it) {
//
//
//            } else {
//                mData.value = Result(resultCode = 0, resultObject = it, tag = tag)
//            }
            val data = Result(resultObject = it, tag = tag)

            mData.value = data

            LiveEventBus.get(tag, Result::class.java).post(data)

            it
        }
    }

    fun <T> mapPage1(source: LiveData<T>): LiveData<Lcee<T>> {

        return Transformations.map(source) {

            Lcee.loading(it)
            println("value in $it")

            if (null === it) {
                Lcee.error(it)
            } else {
                Lcee.content(it)
            }

        }


    }


    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        mScope.cancel()
    }
}
