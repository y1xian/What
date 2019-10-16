/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yyxnb.http.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <R>
</RequestType></R> */
abstract class NetworkBoundResource<R>
@MainThread constructor() {

    private val result = MediatorLiveData<Resource<R>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        if (loadFromDb().value == null) {
            val apiResponse = createCall()
            result.addSource(apiResponse) { response ->
                result.removeSource(apiResponse)
                GlobalScope.launch {
                    when (response) {
                        is ApiSuccessResponse -> {
                            withContext(Dispatchers.IO) {
                                saveCallResult(processResponse(response))
                            }
                            withContext(Dispatchers.Main) {
                                // we specially request a new live data,
                                setValue(Resource.success(processResponse(response)))
                            }
                        }
                        is ApiEmptyResponse -> {
                            onFetchEmpty()
                            withContext(Dispatchers.Main) {
                                // reload from disk whatever we had
                                result.addSource(loadFromDb()) { newData ->
                                    setValue(Resource.success(newData))
                                }
                            }
                        }
                        is ApiErrorResponse -> {
                            onFetchFailed()
                            setValue(Resource.error(response.errorMessage, null))
                        }
                    }
                }
            }
        } else {
            val dbSource = loadFromDb()
            result.addSource(dbSource) { data ->
                result.removeSource(dbSource)
                if (shouldFetch(data)) {
                    fetchFromNetwork(dbSource)
                } else {
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.success(newData))
                    }
                }
            }
        }

    }

    @MainThread
    private fun setValue(newValue: Resource<R>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<R>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            GlobalScope.launch {
                when (response) {
                    is ApiSuccessResponse -> {
                        withContext(Dispatchers.IO) {
                            saveCallResult(processResponse(response))
                        }

                        withContext(Dispatchers.Main) {
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                    is ApiEmptyResponse -> {
                        onFetchEmpty()
                        withContext(Dispatchers.Main) {
                            // reload from disk whatever we had
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                    is ApiErrorResponse -> {
                        onFetchFailed()
                        result.addSource(dbSource) { newData ->
                            setValue(Resource.error(response.errorMessage, newData))
                        }
                    }
                }
            }
        }
    }

    // 网络数据获取空数据时调用
    protected open fun onFetchEmpty() {
        Log.e("network", "onFetchEmpty");
    }

    // 网络数据获取失败时调用
    protected open fun onFetchFailed() {
        Log.e("network", "onFetchFailed");
    }

    fun asLiveData() = result as LiveData<Resource<R>>

    //成功数据
    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<R>) = response.body

    // 当要把网络数据存储到数据库中时调用
    @WorkerThread
    protected open fun saveCallResult(item: R) {
    }

    // 决定是否去网络获取数据
    @MainThread
    protected open fun shouldFetch(data: R?): Boolean = true

    // 用于从数据库中获取缓存数据
    @MainThread
    protected open fun loadFromDb(): LiveData<R> = MutableLiveData()

    // 创建网络数据请求
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<R>>
}
