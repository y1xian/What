package com.yyxnb.arch.base

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.util.Log
import java.io.Serializable

class Java8Observer(var tag: String) : DefaultLifecycleObserver, Serializable {

    override fun onCreate(owner: LifecycleOwner) {
        Log.w("---onCreate $tag", owner.lifecycle.currentState.name)
    }

    override fun onStart(owner: LifecycleOwner) {
        Log.i("---onStart $tag", owner.lifecycle.currentState.name)
    }

    override fun onResume(owner: LifecycleOwner) {
        Log.i("---onResume $tag", owner.lifecycle.currentState.name)
    }

    override fun onPause(owner: LifecycleOwner) {
        Log.i("---onPause $tag", owner.lifecycle.currentState.name)
    }

    override fun onStop(owner: LifecycleOwner) {
        Log.i("---onStop $tag", owner.lifecycle.currentState.name)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.e("---onDestroy $tag", owner.lifecycle.currentState.name)
    }

}