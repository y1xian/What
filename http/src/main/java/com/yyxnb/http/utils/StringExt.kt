package com.yyxnb.http.utils

import android.os.Build
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Html.fromHtml
import android.text.Spanned
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.util.regex.Pattern

/**
 * Description: 字符串处理相关
 */

private const val HTTPS_PREFIX = "https://"
private const val HTTP_REGEX = "^http?://"

@Suppress("DEPRECATION")
fun String.fromHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        fromHtml(this, FROM_HTML_MODE_LEGACY)
    } else {
        fromHtml(this)
    }
}

fun String.addHttpsPrefix(): String {
    return if (startsWith(HTTPS_PREFIX)) {
        this
    } else {
        replace(HTTP_REGEX.toRegex(), HTTPS_PREFIX)
    }
}

/**
 * 是否是手机号
 */
fun String.isPhone() = "(\\+\\d+)?1[34589]\\d{9}$".toRegex().matches(this)

/**
 * 是否是邮箱地址
 */
fun String.isEmail() = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?".toRegex().matches(this)

/**
 * 是否是身份证号码
 */
fun String.isIDCard() = "[1-9]\\d{16}[a-zA-Z0-9]".toRegex().matches(this)

/**
 * 是否是中文字符
 */
fun String.isChinese() = "^[\u4E00-\u9FA5]+$".toRegex().matches(this)

/**
 * 判断两字符串是否相等
 */
fun Boolean.equalsIgnoreCase(a: String, b: String) = a === b || b != null && a.length == b.length && a.regionMatches(0, b, 0, b.length, ignoreCase = true)

/**
 * 判断字符串是否为null或全为空格
 */
fun Boolean.isSpace(s: String) = s == null || s.trim { it <= ' ' }.isEmpty()

/**
 * 首字母大写
 */
fun String.upperFirstLetter() = let {
    if (it.isEmpty() || !Character.isLowerCase(it[0])) {
        it
    } else (it[0].toInt() - 32).toChar().toString() + it.substring(1)
}

/**
 * 首字母小写
 */
fun String.lowerFirstLetter() = let {
    if (it.isEmpty() || !Character.isUpperCase(it[0])) {
        it
    } else (it[0].toInt() + 32).toChar().toString() + it.substring(1)
}

/**
 * 反转字符串
 */
fun String.reverse(): String {
    let {
        val len = it.length
        if (len <= 1) {
            return it
        }
        val mid = len shr 1
        val chars = it.toCharArray()
        var c: Char
        for (i in 0 until mid) {
            c = chars[i]
            chars[i] = chars[len - i - 1]
            chars[len - i - 1] = c
        }
        return String(chars)
    }

}

//判断整数（int）
fun String.isInteger(): Boolean {
    let {
        if (null == it || "" == it) {
            return false
        }
        val pattern = Pattern.compile("^[-\\+]?[\\d]*$")
        return pattern.matcher(it).matches()
    }
}

//判断浮点数（double和float）
fun String.isDouble(): Boolean {
    let {
        if (null == it || "" == it) {
            return false
        }
        val pattern = Pattern.compile("^[-\\+]?[.\\d]*$")
        return pattern.matcher(it).matches()
    }
}

//转换整数（int）
fun String.toIntegerRegex(): Int {
    let {
        if (null == it || "" == it) {
            return 0
        }
        return Integer.valueOf(it.replace("[a-zA-Z\u4e00-\u9fa5`~!@#$%^&*()+=|{}':;',\\\\[\\\\]<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：\\\"_”“’。，、？-]".toRegex(), ""))
    }

}

//转换浮点数（double和float）
fun String.toDoubleRegex(): Double {
    let {
        if (null == it || "" == it) {
            return 0.00
        }
        return java.lang.Double.valueOf(it.replace("[a-zA-Z\u4e00-\u9fa5`~!@#$%^&*()+=|{}':;',\\\\[\\\\]<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：\\\"_”“’。，、？-]".toRegex(), ""))
    }
}


fun getRequestBody(hashMap: HashMap<String, String>?): RequestBody {
    val data = StringBuffer()
    if (hashMap != null && hashMap.size > 0) {
        val iter = hashMap.iterator()
        while (iter.hasNext()) {
            val entry = iter.next()
            val key = entry.key
            val value = entry.value
            data.append(key).append("=").append(value).append("&")
        }
    }
    val jso = data.substring(0, data.length - 1)

    return RequestBody.create("application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull(), jso)
}