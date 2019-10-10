package com.yyxnb.arch.base.mvvm

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
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

    val msg: MutableLiveData<String> = MutableLiveData()

    open val mScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }

    fun launchUI(block: suspend CoroutineScope.() -> Unit) {
        mScope.launch {
            tryCatch({
                block()
            }, {
                throw it
            })
        }
    }

    suspend fun <T> launchOnIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
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
