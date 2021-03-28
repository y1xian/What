package com.yyxnb.what.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<T> implements CallAdapter<T, LiveData<T>> {

    private final Type mResponseType;

    LiveDataCallAdapter(Type mResponseType) {
        this.mResponseType = mResponseType;
    }

    @NonNull
    @Override
    public Type responseType() {
        return mResponseType;
    }

    @NonNull
    @Override
    public LiveData<T> adapt(@NonNull final Call<T> call) {
        return new MyLiveData<>(call);
    }

    private static final class MyLiveData<T> extends LiveData<T> {

        private final AtomicBoolean stared = new AtomicBoolean(false);
        private final Call<T> call;

        MyLiveData(Call<T> call) {
            this.call = call;
        }

        @Override
        protected void onActive() {
            super.onActive();
            //确保执行一次
            if (stared.compareAndSet(false, true)) {
                call.enqueue(new Callback<T>() {
                    @Override
                    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                        postValue(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                        postValue(null);
                    }
                });
            }
        }
    }
}
