package com.yyxnb.http.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object JsonUtils {

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
     * 将Json数据转化为对象;
     *
     * @param jsonString Json数据;
     * @param cls        转换后的类;
     * @return
     */
    fun <T> getObject(jsonString: String, cls: Class<T>): T? {
        var t: T? = null
        try {
            val gson = Gson()
            t = gson.fromJson(jsonString, cls)
        } catch (e: Exception) {
        }

        return t
    }

    /**
     * 将Json数据转化成List<Object>集合;
     *
     * @param jsonString Json数据;
     * @param cls        将要转化成集合的类;
     * @return
     */
    fun <T> getArray(jsonString: String, cls: Class<T>): List<T>? {
        var list: List<T> = ArrayList()
        try {
            val gson = Gson()
            list = gson.fromJson(
                    jsonString,
                    object : TypeToken<List<T>>() {

                    }.type
            )
        } catch (e: Exception) {
        }

        return list
    }


    /**
     * 将Json数据转化成List<Map></Map><String></String>, Object>>对象;
     *
     * @param jsonString Json数据;
     * @return
     */
    fun listKeyMaps(jsonString: String): List<Map<String, Any>>? {
        var list: List<Map<String, Any>> = ArrayList()
        try {
            val gson = Gson()
            list = gson.fromJson(
                    jsonString,
                    object : TypeToken<List<Map<String, Any>>>() {

                    }.type
            )
        } catch (e: Exception) {
        }

        return list
    }

    /**
     * 将Json数据转化成Map<String></String>, Object>对象;
     *
     * @param jsonString Json数据;
     * @return
     */
    fun objKeyMaps(jsonString: String): Map<String, Any>? {
        var map: Map<String, Any> = HashMap()
        try {
            val gson = Gson()
            map = gson.fromJson(
                    jsonString,
                    object : TypeToken<Map<String, Any>>() {

                    }.type
            )
        } catch (e: Exception) {
        }

        return map
    }

}