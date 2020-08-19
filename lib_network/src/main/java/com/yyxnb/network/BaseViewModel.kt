package com.yyxnb.network

import android.view.View
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yyxnb.common.interfaces.IData
import com.yyxnb.common.utils.log.LogUtils
import com.yyxnb.http.exception.ResponseThrowable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseViewModel : ViewModel() {

    val status = MutableLiveData<Status>()

    //重试的监听
    var listener: View.OnClickListener? = null

    val mScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }

    /**
     * 所有网络请求都在 viewModelScope 域中启动，当页面销毁时会自动
     * 调用ViewModel的  #onCleared 方法取消所有协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = mScope.launch { block() }

    /**
     * 用流的方式进行网络请求
     */
    fun <T> launchFlow(block: suspend () -> T): Flow<T> {
        return flow {
            emit(block())
        }
    }

    /**
     * 过滤请求结果，其他全抛异常
     * @param block 请求体
     * @param success 成功回调
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param view
     *
     **/
    fun <T> launchOnlyResult(
            block: suspend CoroutineScope.() -> IData<T>,
            //成功
            success: (T) -> Unit = {},
            //错误 根据错误进行不同分类
            error: (Throwable) -> Unit = {
                //UnknownHostException 1：服务器地址错误；2：网络未连接
                reTry()
            },
            //完成
            complete: () -> Unit = {},
            //重试
            reTry: () -> Unit = {}
    ) {
        status.postValue(Status.LOADING)
        //正式请求接口
        launchUI {
            //异常处理
            handleException(
                    //调用接口
                    { withContext(Dispatchers.IO) { block() } },
                    { res ->
                        //接口成功返回
                        executeResponse(res) {
                            success(it)
                        }
                    },
                    {
                        status.postValue(Status.ERROR)
                        //接口失败返回
                        error(it)
                    },
                    {
                        status.postValue(Status.COMPLETE)
                        //接口完成
                        complete()
                    }
            )
        }
    }


    /**
     * 请求结果过滤
     */
    private suspend fun <T> executeResponse(
            response: IData<T>,
            success: suspend CoroutineScope.(T) -> Unit
    ) {
        coroutineScope {
            //接口成功返回后判断是否是增删改查成功，不满足的话，返回异常
            if (response.isSuccess) {
                status.postValue(Status.SUCCESS)
                success(response.result!!)
            } else {
                status.postValue(Status.ERROR)
                //状态码错误
                throw ResponseThrowable(
                        response.code!!,
                        response.msg!!
                )
            }
        }
    }

    /**
     * 异常统一处理
     */
    private suspend fun <T> handleException(
            block: suspend CoroutineScope.() -> IData<T>,
            success: suspend CoroutineScope.(IData<T>) -> Unit,
            error: suspend CoroutineScope.(Throwable) -> Unit,
            complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                success(block())
            } catch (e: Throwable) {
                error(e)
                e.printStackTrace()
            } finally {
                complete()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mScope.cancel()
        listener = null
    }
}