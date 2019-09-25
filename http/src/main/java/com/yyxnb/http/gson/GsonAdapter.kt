package com.yyxnb.http.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder


object GsonAdapter {

    /**
     * 后台返回""和"null"的处理
     */
    fun buildGson(): Gson {
        var gson: Gson? = null
        if (gson == null) {
            gson = GsonBuilder()
                    .registerTypeAdapter(Int::class.java, IntegerDefault0Adapter())
                    .registerTypeAdapter(Int::class.javaPrimitiveType, IntegerDefault0Adapter())
                    .registerTypeAdapter(Double::class.java, DoubleDefault0Adapter())
                    .registerTypeAdapter(Double::class.javaPrimitiveType, DoubleDefault0Adapter())
                    .registerTypeAdapter(Long::class.java, LongDefault0Adapter())
                    .registerTypeAdapter(Long::class.javaPrimitiveType, LongDefault0Adapter())
                    .registerTypeAdapter(String::class.java, StringNullAdapter())
                    .create()
        }
        return gson!!
    }

}
