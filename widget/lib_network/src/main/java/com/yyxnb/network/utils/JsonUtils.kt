package com.yyxnb.network.utils

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.*

object JsonUtils {

    private val gson: Gson by lazy { GsonUtils.gson }

    /**
     * 格式化json字符串
     *
     * @param jsonStr 需要格式化的json串
     * @return string
     */
    fun formatJson(jsonStr: String?): String {
        if (null == jsonStr || "" == jsonStr) {
            return ""
        }
        val sb = StringBuilder()
        var last = '\u0000'
        var current = '\u0000'
        var indent = 0
        for (element in jsonStr) {
            last = current
            current = element
            //遇到{ [换行，且下一行缩进
            when (current) {
                '{', '[' -> {
                    sb.append(current)
                    sb.append('\n')
                    indent++
                    addIndentBlank(sb, indent)
                }
                //遇到} ]换行，当前行缩进
                '}', ']' -> {
                    sb.append('\n')
                    indent--
                    addIndentBlank(sb, indent)
                    sb.append(current)
                }
                //遇到,换行
                ',' -> {
                    sb.append(current)
                    if (last != '\\') {
                        sb.append('\n')
                        addIndentBlank(sb, indent)
                    }
                }
                else -> sb.append(current)
            }
        }
        return sb.toString()
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     */
    private fun addIndentBlank(sb: StringBuilder, indent: Int) {
        for (i in 0 until indent) {
            sb.append('\t')
        }
    }

    /**
     * http 请求数据返回 json 中中文字符为 unicode 编码转汉字转码
     *
     * @param dataStr
     * @return 转化后的结果.
     */
    fun decodeUnicode(dataStr: String): String {
        var start = 0
        var end = 0
        val buffer = StringBuffer()
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2)
            var charStr = ""
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length)
            } else {
                charStr = dataStr.substring(start + 2, end)
            }
            val letter = Integer.parseInt(charStr, 16).toChar() // 16进制parse整形字符串。
            buffer.append(letter.toString())
            start = end
        }
        return buffer.toString()
    }

    /**
     * 一个String字符串转换为json格式
     */
    fun stringToJson(s: String?): String {
        if (s == null) {
            return nullToJson()
        }
        val sb = StringBuilder()
        for (i in 0 until s.length) {
            val ch = s[i]
            when (ch) {
                '"' -> sb.append("\\\"")
                '\\' -> sb.append("\\\\")
                '\b' -> sb.append("\\b")
//                '\f'-> sb.append("\\f")
                '\n' -> sb.append("\\n")
                '\r' -> sb.append("\\r")
                '\t' -> sb.append("\\t")
                '/' -> sb.append("\\/")
                else -> if (ch >= '\u0000' && ch <= '\u001F') {
                    val ss = Integer.toHexString(ch.toInt())
                    sb.append("\\u")
                    var k = 0
                    while (k < 4 - ss.length) {
                        sb.append('0')
                        k++
                    }
                    sb.append(ss.toUpperCase(Locale.ROOT))
                } else {
                    sb.append(ch)
                }
            }
        }
        return sb.toString()
    }

    fun nullToJson(): String {
        return ""
    }

    /**
     * 一个obj对象转换为json格式
     */
    fun objectToJson(obj: Any?): String {
        val json = StringBuilder()
        if (obj == null) {
            json.append("\r\n")
        } else if (obj is Number) {
            json.append(numberToJson(obj))
        } else if (obj is Boolean) {
            json.append(booleanToJson(obj))
        } else if (obj is String) {
            json.append("\r\n").append(formatJson(obj.toString())).append("\r\n")
        } else if (obj is Array<*>) {
            @Suppress("UNCHECKED_CAST")
            json.append(arrayToJson(obj as Array<Any?>?))
        } else if (obj is List<*>) {
            json.append(listToJson(obj as List<*>?))
        } else if (obj is Map<*, *>) {
            json.append(mapToJson(obj as Map<*, *>?))
        } else if (obj is Set<*>) {
            json.append(setToJson(obj as Set<*>?))
        } else {
            json.append(beanToJson(obj))
        }
        return json.toString()
    }

    fun numberToJson(number: Number): String {
        return number.toString()
    }

    fun booleanToJson(bool: Boolean): String {
        return bool.toString()
    }

    /**
     * 一个bean对象转换为json格式
     */
    fun beanToJson(bean: Any?): String? {
        var gsonString: String? = null
        gsonString = gson.toJson(bean)
        return gsonString
    }

    /**
     * list转json
     */
    fun listToJson(list: List<*>?): String {
        val json = StringBuilder()
        json.append("[")
        if (list != null && list.size > 0) {
            for (obj in list) {
                json.append(objectToJson(obj))
                json.append(",")
            }
            json.setCharAt(json.length - 1, ']')
        } else {
            json.append("]")
        }
        return json.toString()
    }

    /**
     * 一个数组集合转换为json格式
     */
    fun arrayToJson(array: Array<Any?>?): String {
        val json = StringBuilder()
        json.append("[")
        if (array != null && array.size > 0) {
            for (obj in array) {
                json.append(objectToJson(obj))
                json.append(",")
            }
            json.setCharAt(json.length - 1, ']')
        } else {
            json.append("]")
        }
        return json.toString()
    }

    /**
     * 转成json
     */
    fun gsonString(`object`: Any?): String? {
        var gsonString: String? = null
        gsonString = gson.toJson(`object`)
        return gsonString
    }

    /**
     * 转成bean
     */
    fun <T> gsonToBean(gsonString: String?, cls: Class<T>?): T? {
        var t: T? = null
        t = gson.fromJson(gsonString, cls)
        return t
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     */
    fun <T> gsonToList(gsonString: String?, cls: Class<T>?): List<T>? {
        var list: List<T>? = null
        list = gson.fromJson(gsonString, object : TypeToken<List<T>?>() {}.type)
        return list
    }

    /**
     * 转成list
     * 解决泛型问题
     */
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
     * 一个Map集合转换为json格式
     */
    fun mapToJson(map: Map<*, *>?): String {
        val json = StringBuilder()
        json.append("{")
        if (map != null && map.size > 0) {
            for (key in map.keys) {
                json.append(objectToJson(key))
                json.append(":")
                json.append(objectToJson(map[key]))
                json.append(",")
            }
            json.setCharAt(json.length - 1, '}')
        } else {
            json.append("}")
        }
        return json.toString()
    }

    /**
     * 一个Set集合转换为json格式
     */
    fun setToJson(set: Set<*>?): String {
        val json = StringBuilder()
        json.append("[")
        if (set != null && set.size > 0) {
            for (obj in set) {
                json.append(objectToJson(obj))
                json.append(",")
            }
            json.setCharAt(json.length - 1, ']')
        } else {
            json.append("]")
        }
        return json.toString()
    }

    /**
     * json字符串转换为List中有Map
     */
    fun <T> parseJSONList(jsonStr: String?): List<Map<String, T>>? {
        var list: List<Map<String, T>>? = null
        list = gson.fromJson(jsonStr,
                object : TypeToken<List<Map<String?, T>?>?>() {}.type)
        return list
    }

    /**
     * json字符串转换为map
     */
    fun <T> parseJSONMap(jsonStr: String?): Map<String, T>? {
        var map: Map<String, T>? = null
        map = gson.fromJson(jsonStr, object : TypeToken<Map<String?, T>?>() {}.type)
        return map
    }

    /**
     * 根据一个url地址.获取json数据.转换为List
     */
    fun getListByUrl(url: String?): List<Map<String, Any>>? {
        try {
            //通过HTTP获取JSON数据
            val `in` = URL(url).openStream()
            val reader = BufferedReader(InputStreamReader(`in`))
            val sb = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            return parseJSONList(sb.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 根据一个url地址.获取json数据.转换为MAP
     */
    fun getMapByUrl(url: String?): Map<String, Any>? {
        try {
            //通过HTTP获取JSON数据
            val `in` = URL(url).openStream()
            val reader = BufferedReader(InputStreamReader(`in`))
            val sb = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            return parseJSONMap(sb.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}