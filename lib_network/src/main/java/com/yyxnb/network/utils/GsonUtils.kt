package com.yyxnb.network.utils

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.lang.reflect.Type
import java.util.*

@Suppress("UNREACHABLE_CODE")
object GsonUtils {

    val gson: Gson by lazy {
        GsonBuilder()
                .registerTypeAdapter(Long::class.java, LongDefault0Adapter())
                .registerTypeAdapter(Long::class.javaPrimitiveType, LongDefault0Adapter())
                .registerTypeAdapter(Double::class.java, DoubleDefault0Adapter())
                .registerTypeAdapter(Double::class.javaPrimitiveType, DoubleDefault0Adapter())
                .registerTypeAdapter(Int::class.javaPrimitiveType, IntegerDefault0Adapter())
                .registerTypeAdapter(Int::class.java, IntegerDefault0Adapter())
                .registerTypeAdapter(String::class.java, StringNullAdapter())
//                .registerTypeAdapterFactory(NullStringToEmptyAdapterFactory<Any?>())
                .disableHtmlEscaping()
                .create()
    }

    //忽略字段id
    fun getSkipIdGson(id: String): Gson {
        return GsonBuilder().setExclusionStrategies(
                object : ExclusionStrategy {
                    override fun shouldSkipField(f: FieldAttributes): Boolean {
                        //过滤掉字段名包含"id","address"的字段
                        return f.name == id
                    }

                    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                        // 过滤掉 类名包含 Bean的类
                        return false
                    }
                }).create()
    }

    //过滤掉字段名包含"id","address"的字段
    val skipIdAndGroupIdGson: Gson
        get() = GsonBuilder().setExclusionStrategies(
                object : ExclusionStrategy {
                    override fun shouldSkipField(f: FieldAttributes): Boolean {
                        //过滤掉字段名包含"id","address"的字段
                        return (f.name == "id") or (f.name == "groupGuid")
                    }

                    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                        // 过滤掉 类名包含 Bean的类
                        return false
                    }
                }).create()

    /**
     * 将object对象转成json字符串
     *
     * @param object
     * @return
     */
    fun gsonString(`object`: Any?): String? {
        var gsonString: String? = null
        gsonString = gson.toJson(`object`)
        return gsonString
    }

    /**
     * 将gsonString转成泛型bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    fun <T> gsonToBean(gsonString: String?, cls: Class<T>?): T? {
        var t: T? = null
        t = gson.fromJson(gsonString, cls)
        return t
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     *
     * @param gsonString
     * @param cls
     * @return
     */
    fun <T> gsonToList(gsonString: String?, cls: Class<T>?): List<T>? {
        var list: List<T>? = null
        list = gson.fromJson(gsonString, object : TypeToken<List<T>?>() {}.type)
        return list
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
    </T> */
    fun <T> jsonToList(json: String?, cls: Class<T>?): List<T> {
        val gson = Gson()
        val list: MutableList<T> = ArrayList()
        val array = JsonParser().parse(json).asJsonArray
        for (elem in array) {
            list.add(gson.fromJson(elem, cls))
        }
        return list
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    fun <T> GsonToListMaps(gsonString: String?): List<Map<String, T>>? {
        var list: List<Map<String, T>>? = null
        list = gson.fromJson(gsonString,
                object : TypeToken<List<Map<String?, T>?>?>() {}.type)
        return list
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    fun <T> gsonToMaps(gsonString: String?): Map<String, T>? {
        var map: Map<String, T>? = null
        map = gson.fromJson(gsonString, object : TypeToken<Map<String?, T>?>() {}.type)
        return map
    }

    class IntegerDefault0Adapter : JsonSerializer<Int?>, JsonDeserializer<Int> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Int {
            try {
                if ("" == json.asString || "null" == json.asString) {
                    // /定义为int类型,如果后台返回""或者null,则返回0
                    return 0
                }
            } catch (ignore: Exception) {
            } finally {
                return try {
                    json.asInt
                } catch (e: NumberFormatException) {
                    throw JsonSyntaxException(e)
                }
            }
        }

        override fun serialize(src: Int?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(src)
        }
    }

    class DoubleDefault0Adapter : JsonSerializer<Double?>, JsonDeserializer<Double> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Double {
            try {
                if ("" == json.asString || "null" == json.asString) {
                    //定义为double类型,如果后台返回""或者null,则返回0.00
                    //
                    return 0.00
                }
            } catch (ignore: Exception) {
            } finally {
                return try {
                    json.asDouble
                } catch (e: NumberFormatException) {
                    throw JsonSyntaxException(e)
                }
            }
        }

        override fun serialize(src: Double?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(src)
        }
    }

    class LongDefault0Adapter : JsonSerializer<Long?>, JsonDeserializer<Long> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Long {
            try {
                if ("" == json.asString || "null" == json.asString) {
                    //定义为long类型,如果后台返回""或者null,则返回0
                    return 0L
                }
            } catch (ignore: Exception) {
            } finally {
                return try {
                    json.asLong
                } catch (e: NumberFormatException) {
                    throw JsonSyntaxException(e)
                }
            }
        }

        override fun serialize(src: Long?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(src)
        }
    }

    class NullStringToEmptyAdapterFactory<T> : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T> {
            val rawType = type.rawType as Class<T>
            return (if (rawType != String::class.java) {
                null
            } else StringNullAdapter() as TypeAdapter<T>) as TypeAdapter<T>
        }
    }

    class StringNullAdapter : TypeAdapter<String?>() {
        @Throws(IOException::class)
        override fun read(reader: JsonReader): String? {
            // TODO Auto-generated method stub
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull()
                return ""
            }
            return reader.nextString()
        }

        @Throws(IOException::class)
        override fun write(writer: JsonWriter, value: String?) {
            // TODO Auto-generated method stub
            if (value == null) {
                writer.nullValue()
                return
            }
            writer.value(value)
        }
    }

}