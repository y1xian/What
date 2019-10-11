package com.yyxnb.http.network


import android.arch.lifecycle.LiveData
import com.yyxnb.http.exception.ApiException
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<R>(private val responseType: Type) :
        CallAdapter<R, LiveData<R>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<R> {
        return object : LiveData<R>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {//确保执行一次
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            if (response.isSuccessful) {
                                postValue(response.body())
                            } else {
                                postValue(null)
                            }
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            print("------onFailure ${ApiException.handleException(throwable).message}")
                            postValue(null)
                        }
                    })
                }
            }
        }
    }
}