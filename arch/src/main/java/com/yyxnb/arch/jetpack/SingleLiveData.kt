package com.yyxnb.arch.jetpack

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer

class SingleLiveData<T>(liveData: LiveData<T>) : MediatorLiveData<T>() {
    private var hasSetValue = false
    private val mediatorObserver = Observer<T> {
        if (!hasSetValue) {
            hasSetValue = true
            this@SingleLiveData.value = it
        }
    }

    init {
        if (liveData.value != null) {
            hasSetValue = true
            this.value = liveData.value
        } else {
            addSource(liveData, mediatorObserver)
        }
    }


}