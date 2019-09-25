package com.yyxnb.http.gson

import com.google.gson.*
import com.yyxnb.http.utils.isDouble
import com.yyxnb.http.utils.toDoubleRegex
import java.lang.reflect.Type

/**
 * 定义为double类型,如果后台返回""或者null,则返回0.00
 */

class DoubleDefault0Adapter : JsonSerializer<Double>, JsonDeserializer<Double> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Double? {
        try {
            if ("" == json.asString || "null" == json.asString) {
                return 0.00
            }else if (!json.asString.isDouble()){
                return json.asString.toDoubleRegex()
            }
        } catch (ignore: Exception) {
        }

        try {
            return json.asDouble
        } catch (e: NumberFormatException) {
            throw JsonSyntaxException(e)
        }

    }

    override fun serialize(src: Double?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src!!)
    }

}