package com.yyxnb.arch.jetpack

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer

class NonNullLiveData<T>(liveData: LiveData<T>) : LiveData<T>() {
    private val mediatorLiveData = MediatorLiveData<T>()
    private val mediatorObserver = Observer<T> {
        it?.let {
            this@NonNullLiveData.value = it
        }
    }

    init {
        mediatorLiveData.observeForever(mediatorObserver)
        mediatorLiveData.addSource(liveData, mediatorObserver)
    }

    override fun onInactive() {
        super.onInactive()
        mediatorLiveData.removeObserver(mediatorObserver)
    }

}