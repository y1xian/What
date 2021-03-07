package com.yyxnb.module_video.config;

import com.yyxnb.common_res.bean.BaseData;
import com.yyxnb.common_res.bean.StateData;
import com.yyxnb.module_video.bean.TikTokBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface VideoService {

//        @Headers(HEADER_APIOPEN)
    @GET("v2/5ecfd21e320000f1aee3d61a")
//    @GET("video/query")
    Observable<BaseData<StateData<TikTokBean>>> getVideoList4(@QueryMap Map<String, String> map);

}
