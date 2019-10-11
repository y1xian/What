package com.yyxnb.http.utils

import okhttp3.HttpUrl
import okhttp3.Request

/**
 * Description: 多BaseUrl解析器
 */
interface IUrlParser {

    /**
     * 将 [RetrofitMultiUrl.mBaseUrlMap] 中映射的 Url 解析成完整的[HttpUrl]
     * 用来替换 @[Request.url] 里的BaseUrl以达到动态切换 Url的目的
     *
     * @param domainUrl 目标请求(base url)
     * @param url       需要替换的请求(原始url)
     * @return
     */
    fun parseUrl(domainUrl: HttpUrl, url: HttpUrl): HttpUrl
}
