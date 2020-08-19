package com.yyxnb.common_base.config


object BaseAPI {

    //====\key
    //免费开放接口API
    const val URL_APIOPEN = "https://www.apiopen.top/"
    const val URL_MOCKY = "http://www.mocky.io/"
    const val URL_WAN_ANDROID = "https://www.wanandroid.com/"


    // 切换请求url  @Headers()
    //apiopen
    const val HEADER_APIOPEN = UrlInterceptor.URL_PREFIX + URL_APIOPEN

    // 玩安卓
    const val HEADER_WAN = UrlInterceptor.URL_PREFIX + URL_WAN_ANDROID


}