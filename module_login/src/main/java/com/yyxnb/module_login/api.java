package com.yyxnb.module_login;

import android.arch.lifecycle.LiveData;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;

import static com.yyxnb.module_base.BaseAPI.HEAD_MOCKY;

public interface api {

    @Headers(HEAD_MOCKY)
    @GET("v2/5cc2a63e3300000d007e5330")
    LiveData<BaseDatas<List<TestData>>> getTest();
}
