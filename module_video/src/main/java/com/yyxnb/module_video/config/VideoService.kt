package com.yyxnb.module_video.config

import com.yyxnb.common_base.base.BaseData
import com.yyxnb.common_base.base.StateData
import com.yyxnb.module_video.bean.TikTokBean
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface VideoService {
    //        @Headers(HEADER_APIOPEN)
    @GET("v2/5ecfd21e320000f1aee3d61a")
    //    @GET("video/query")
    suspend fun getVideoList4(@QueryMap map: Map<String, String>): BaseData<StateData<TikTokBean>>
}