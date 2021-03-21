package com.yyxnb.common_res.config;


import com.yyxnb.common_res.utils.UrlInterceptor;
import com.yyxnb.what.core.NetworkUtils;

public final class BaseAPI {


    // 切换请求url  @Headers()

    // 本地
    public static final String HEADER_LOCAL = UrlInterceptor.URL_PREFIX + BaseAPI.URL_LOCAL;
    //apiopen
    public static final String HEADER_APIOPEN = UrlInterceptor.URL_PREFIX + BaseAPI.URL_APIOPEN;
    // 玩安卓
    public static final String HEADER_WAN = UrlInterceptor.URL_PREFIX + BaseAPI.URL_WAN_ANDROID;
    // 极速
    public static final String HEADER_JISU = UrlInterceptor.URL_PREFIX + BaseAPI.URL_JISU;


    //====\key

    // 本地ip
    public final static String URL_LOCAL = String.format("http:/%s:8080/", NetworkUtils.getLocalIPAddress());

    //免费开放接口API
    public final static String URL_APIOPEN = "https://api.apiopen.top/";
    public final static String URL_MOCKY = "https://run.mocky.io/";
    public final static String URL_WAN_ANDROID = "https://www.wanandroid.com/";
    public final static String URL_JISU = "https://api.jisuapi.com/";

}
