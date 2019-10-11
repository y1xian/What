package com.yyxnb.http.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("LeakingThis")
abstract class NetworkBoundResource<R> @MainThread
constructor() {

    private val result = MediatorLiveData<Resource<R>>()

    // returns a LiveData that represents the resource, implemented
    // in the base class.
    val asLiveData: LiveData<Resource<R>>
        get() = result

    init {
        result.value = Resource.loading(null)

        if (loadFromDb().value == null) {
            val apiResponse = createCall()
            result.addSource(apiResponse) { response ->
                result.removeSource(apiResponse)
                if (response != null) {
                    saveResultAndReInit(response)
                } else {
                    onFetchFailed()
                }
            }
        } else {
            val dbSource = loadFromDb()
            result.addSource(dbSource) { data ->
                result.removeSource(dbSource)
                if (shouldFetch(data)) {
                    fetchFromNetwork(dbSource)
                } else {
                    result.addSource(dbSource
                    ) { newData -> result.postValue(Resource.success<R>(newData)) }
                }
            }
        }


    }

    private fun fetchFromNetwork(dbSource: LiveData<R>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source,
        // it will dispatch its latest value quickly

        result.addSource(dbSource
        ) { newData -> result.setValue(Resource.loading(newData)) }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            if (response != null) {
                saveResultAndReInit(response)
            } else {
                onFetchFailed()
                result.addSource(dbSource
                ) { newData ->
                    result.setValue(
                            Resource.error(response?.toString(), newData))
                }
            }
        }

    }

    @MainThread
    private fun saveResultAndReInit(response: R?) {

        GlobalScope.launch {
            withContext(Dispatchers.Main)
            {
                saveCallResult(response)
                result.postValue(Resource.success(response))
            }
        }
    }

    // Called to save the result of the API response into the database
    // 当要把网络数据存储到数据库中时调用
    @WorkerThread
    protected open fun saveCallResult(item: R?) {
    }

    // Called with the data in the database to decide whether it should be
    // fetched from the network.
    // 决定是否去网络获取数据
    @MainThread
    protected open fun shouldFetch(data: R?): Boolean = true

    // Called to get the cached data from the database
    // 用于从数据库中获取缓存数据
    @MainThread
    protected fun loadFromDb(): LiveData<R> = MutableLiveData()

    // Called to create the API call.
    // 创建网络数据请求
    @MainThread
    protected abstract fun createCall(): LiveData<R>

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    // 网络数据获取失败时调用
    @MainThread
    protected open fun onFetchFailed() {
    }

}