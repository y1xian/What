package com.yyxnb.what.network;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    private static final String TAG = LiveDataCallAdapterFactory.class.getSimpleName();

    @SuppressWarnings("ClassGetClass")
    @Nullable
    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        if (getRawType(returnType) != LiveData.class) {
            return null;
        }
        //获取第一个泛型类型
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawType = getRawType(observableType);
        Log.d(TAG, "rawType = " + rawType.getClass().getSimpleName());
        if (!(observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        return new LiveDataCallAdapter<>(observableType);
    }
}