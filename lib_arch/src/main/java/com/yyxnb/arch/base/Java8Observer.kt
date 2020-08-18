package com.yyxnb.arch.base

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.yyxnb.common.interfaces.ILifecycle
import java.io.Serializable

/**
 * 打印页面生命周期
 *
 * @author yyx
 */
class Java8Observer(private val tag: String) : ILifecycle, Serializable {

    override fun onCreate(owner: LifecycleOwner) {
        Log.e(tag, owner.lifecycle.currentState.name)
    }

    override fun onStart(owner: LifecycleOwner) {
        Log.w(tag, owner.lifecycle.currentState.name)
    }

    override fun onResume(owner: LifecycleOwner) {
        Log.w(tag, owner.lifecycle.currentState.name)
    }

    override fun onPause(owner: LifecycleOwner) {
        Log.w(tag, owner.lifecycle.currentState.name)
    }

    override fun onStop(owner: LifecycleOwner) {
        Log.w(tag, owner.lifecycle.currentState.name)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.e(tag, owner.lifecycle.currentState.name)
    }

}