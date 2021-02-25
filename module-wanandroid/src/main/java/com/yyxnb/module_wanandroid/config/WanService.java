package com.yyxnb.module_wanandroid.config;

import com.yyxnb.module_wanandroid.bean.WanAriticleBean;
import com.yyxnb.module_wanandroid.bean.WanClassifyBean;
import com.yyxnb.common_res.bean.WanData;
import com.yyxnb.module_wanandroid.bean.WanNavigationBean;
import com.yyxnb.module_wanandroid.bean.WanStatus;
import com.yyxnb.module_wanandroid.bean.WanSystemBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.yyxnb.common_res.config.BaseAPI.HEADER_WAN;

public interface WanService {

    /**
     * banner
     *
     * @return
     */
    @Headers(HEADER_WAN)
    @GET("banner/json")
    Observable<WanData<List<WanAriticleBean>>> getBanner();

    /**
     * 首页数据
     *
     * @param page
     * @return
     */
    @Headers(HEADER_WAN)
    @GET("article/list/{page}/json")
    Observable<WanData<WanStatus<WanAriticleBean>>> getAritrilList(@Path("page") int page);

    /**
     * 置顶数据
     *
     * @return
     */
    @Headers(HEADER_WAN)
    @GET("article/top/json")
    Observable<WanData<List<WanAriticleBean>>> getTopAritrilList();

    /**
     * 项目分类
     *
     * @return
     */
    @Headers(HEADER_WAN)
    @GET("project/tree/json")
    Observable<WanData<List<WanClassifyBean>>> getProjecTypes();

    /**
     * 根据分类id获取项目数据
     *
     * @return
     */
    @Headers(HEADER_WAN)
    @GET("project/list/{page}/json")
    Observable<WanData<WanStatus<WanAriticleBean>>> getProjecDataByType(@Path("page") int page, @Query("cid") int cid);

    /**
     * 获取最新项目数据
     *
     * @return
     */
    @Headers(HEADER_WAN)
    @GET("article/listproject/{page}/json")
    Observable<WanData<List<WanAriticleBean>>> getProjecNewData(@Path("page") int page);

    /**
     * 获取热门搜索数据
     *
     * @return
     */
    @Headers(HEADER_WAN)
    @GET("hotkey/json")
    Observable<WanData<List<WanClassifyBean>>> getSearchData();

    /**
     * 根据关键词搜索数据
     *
     * @return
     */
    @Headers(HEADER_WAN)
    @POST("article/query/{page}/json")
    Observable<WanData<WanStatus<WanAriticleBean>>> getSearchDataByKey(@Path("page") int page, @Query("k") String searchKey);

    /**
     * 公众号分类
     *
     * @return
     */
    @Headers(HEADER_WAN)
    @GET("wxarticle/chapters/json")
    Observable<WanData<List<WanClassifyBean>>> getPublicTypes();

    /**
     * 获取公众号数据
     *
     * @return
     */
    @Headers(HEADER_WAN)
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<WanData<WanStatus<WanAriticleBean>>> getPublicData(@Path("page") int page, @Path("id") int id);

    /**
     * 获取体系数据
     *
     * @return
     */
    @Headers(HEADER_WAN)
    @GET("tree/json")
    Observable<WanData<List<WanSystemBean>>> getSystemData();

    /**
     * 知识体系下的文章数据
     *
     * @return
     */
    @Headers(HEADER_WAN)
    @GET("article/list/{page}/json")
    Observable<WanData<List<WanAriticleBean>>> getAritrilDataByTree(@Path("page") int page, @Query("cid") int cid);

    /**
     * 广场列表数据
     *
     * @return
     */
    @Headers(HEADER_WAN)
    @GET("user_article/list/{page}/json")
    Observable<WanData<WanStatus<WanAriticleBean>>> getSquareData(@Path("page") int page);

    /**
     * 获取导航数据
     *
     * @return
     */
    @Headers(HEADER_WAN)
    @GET("navi/json")
    Observable<WanData<List<WanNavigationBean>>> getNavigationData();


}
