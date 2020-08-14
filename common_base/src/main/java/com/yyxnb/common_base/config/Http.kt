package com.yyxnb.common_base.config

import com.yyxnb.network.AbstractHttp
import okhttp3.Interceptor
import java.util.*

object Http : AbstractHttp() {
    override fun baseUrl(): String {
        return BaseAPI.URL_MOCKY
    }

    override fun interceptors(): Iterable<Interceptor> {
        val urlBucket: MutableList<String> = ArrayList()
        urlBucket.add(BaseAPI.URL_WAN_ANDROID)
        urlBucket.add(BaseAPI.URL_APIOPEN)
        val interceptorList: MutableList<Interceptor> = ArrayList()
        interceptorList.add(UrlInterceptor(urlBucket))
        //        interceptorList.add(new BaseUrlInterceptor());
        return interceptorList
    }
//
//    companion object {
//        @Volatile
//        private var http: Http? = null
//
//        @JvmStatic
//        val instance: Http?
//            get() {
//                if (http == null) {
//                    synchronized(Http::class.java) {
//                        if (http == null) {
//                            http = Http()
//                        }
//                    }
//                }
//                return http
//            }
//    }
}