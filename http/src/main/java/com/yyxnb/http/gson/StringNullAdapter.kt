package com.yyxnb.http.gson

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

import java.io.IOException

/**
 * 定义为String类型,如果后台返回"null"或者null,则返回""
 */
class StringNullAdapter : TypeAdapter<String>() {
    @Throws(IOException::class)
    override fun read(reader: JsonReader): String {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return ""//原先是返回Null，这里改为返回空字符串
        }

        val jsonStr = reader.nextString()
        return if ("null" == jsonStr) {
            ""
        } else {
            jsonStr
        }
    }

    @Throws(IOException::class)
    override fun write(writer: JsonWriter, value: String?) {
        if (value == null) {
            writer.nullValue()
            return
        }
        writer.value(value)
    }
}