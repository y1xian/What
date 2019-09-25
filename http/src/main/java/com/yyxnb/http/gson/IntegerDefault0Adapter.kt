package com.yyxnb.http.gson

import com.google.gson.*
import com.yyxnb.http.utils.isInteger
import com.yyxnb.http.utils.toIntegerRegex
import java.lang.reflect.Type

/**
 * 定义为int类型,如果后台返回""或者null,则返回0
 */

class IntegerDefault0Adapter : JsonSerializer<Int>, JsonDeserializer<Int> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Int? {
        try {
            if ("" == json.asString || "null" == json.asString) {
                return 0
            }else if (!json.asString.isInteger()){
                return json.asString.toIntegerRegex()
            }
        } catch (ignore: Exception) {
        }

        try {
            return json.asInt
        } catch (e: NumberFormatException) {
            throw JsonSyntaxException(e)
        }

    }

    override fun serialize(src: Int?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src!!)
    }
}