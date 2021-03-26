package com.yyxnb.what.network;

import com.yyxnb.what.okhttp.AbsOkHttp;
import com.yyxnb.what.okhttp.utils.GsonUtils;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/01/12
 * 描    述：AbstractHttp
 * ================================================
 */
public abstract class AbsRetrofit extends AbsOkHttp {

    /**
     * CallAdapter转换器
     */
    protected Iterable<CallAdapter.Factory> callAdapterFactories() {
        return new ArrayList<>();
    }

    /**
     * Converter转换器
     */
    protected Iterable<Converter.Factory> convertersFactories() {
        return new ArrayList<>();
    }

    /**
     * Retrofit
     */
    protected Retrofit retrofit() {
        return defaultRetrofit();
    }

    /**
     * OkHttpClient
     */
    @Override
    protected OkHttpClient okHttpClient() {
        return super.okHttpClient();
    }

    private Retrofit defaultRetrofit() {

        Retrofit.Builder builder = new Retrofit.Builder();

        for (CallAdapter.Factory it : callAdapterFactories()) {
            builder.addCallAdapterFactory(it);
        }
        for (Converter.Factory it : convertersFactories()) {
            builder.addConverterFactory(it);
        }

        builder.baseUrl(baseUrl())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson()))
                .client(okHttpClient());

        return builder.build();
    }

    public <T> T create(Class<T> clz) {
        return retrofit().create(clz);
    }

}
