package com.yyxnb.ktx

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.text.TextUtils
import android.view.View
import com.yyxnb.common.AppConfig
import java.text.SimpleDateFormat
import java.util.*

inline fun tryCatch(tryBlock: () -> Unit, catchBlock: (Exception) -> Unit) {
    try {
        tryBlock()
    } catch (t: Exception) {
        catchBlock(t)
    }
}

fun Any.toast(msg: String) {
    AppConfig.getInstance().toast(msg)
}

fun Any.log(msg: String) {
    AppConfig.getInstance().log(msg)
}

/**
 * 非空
 */
fun <T> Any?.notNull(f: () -> T, t: () -> T): T {
    return if (this != null) f() else t()
}

fun Context.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun Context.px2dp(px: Int): Int {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

fun View.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun View.px2dp(px: Int): Int {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

/**
 * 获取当前时间的字符串，格式为yyyyMMddHHmmss。
 * @return 当前时间的字符串。
 */
val currentDateString: String
    get() {
        val sdf = SimpleDateFormat("yyyyMMddHHmmss", Locale.US)
        return sdf.format(Date())
    }

/**
 * 将当前线程睡眠指定毫秒数。
 *
 * @param millis
 * 睡眠的时长，单位毫秒。
 */
fun sleep(millis: Long) {
    try {
        Thread.sleep(millis)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }

}

/**
 * 获取资源文件中定义的字符串。
 *
 * @param resId
 * 字符串资源id
 * @return 字符串资源id对应的字符串内容。
 */
fun getString(resId: Int): String {
    return AppConfig.getInstance().context.resources.getString(resId)
}

fun getColor(resId: Int): Int {
    return AppConfig.getInstance().context.resources.getColor(resId)
}

/**
 * 获取请求结果的线索，即将状态码和简单描述组合成一段调试信息。
 *
 * @param status
 * 请求结果的状态码
 * @param msg
 * 请求结果的简单描述
 * @return 请求结果的调试线索。
 */
fun getResponseClue(status: Int, msg: String): String {
    return "code: $status , msg: $msg"
}

/**
 * 获取传入通用资源标识符的后缀名。
 *
 * @param uri
 * 通用资源标识符
 * @return 通用资源标识符的后缀名。
 */
fun getUriSuffix(uri: String): String {
    if (!TextUtils.isEmpty(uri)) {
        val doubleSlashIndex = uri.indexOf("//")
        val slashIndex = uri.lastIndexOf("/")
        if (doubleSlashIndex != -1 && slashIndex != -1) {
            if (doubleSlashIndex + 1 == slashIndex) {
                return ""
            }
        }
        val dotIndex = uri.lastIndexOf(".")
        if (dotIndex != -1 && dotIndex > slashIndex) {
            return uri.substring(dotIndex + 1)
        }
    }
    return ""
}

/**
 * 生成资源对应的key。
 *
 * @param uri
 * 通用资源标识符
 * @param dir
 * 生成带有指定目录结构的key
 * @return 资源对应的key。
 */
@SuppressLint("DefaultLocale")
fun generateKey(uri: String, dir: String): String {
    val suffix = getUriSuffix(uri)
    val uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase()
    return if (TextUtils.isEmpty(dir)) {
        var key = "$currentDateString-$uuid"
        if (!TextUtils.isEmpty(suffix)) {
            key = "$key.$suffix"
        }
        key
    } else {
        var key = "$dir/$currentDateString-$uuid"
        if (!TextUtils.isEmpty(suffix)) {
            key = "$key.$suffix"
        }
        key.replace("//", "/")
    }
}

/**
 * 获取转换之后的数字显示，如123456会被转换成12.3w。
 * @param number
 * 原始数字
 * @return 转换之后的数字。
 */
fun getConvertedNumber(number: Int) = when {
    number < 10000 -> number.toString()
    number < 1000000 -> {
        var converted = String.format(Locale.ENGLISH, "%.1f", number / 10000.0)
        if (converted.endsWith(".0")) {
            converted = converted.replace(".0", "")
        }
        converted + "w"
    }
    number < 100100100 -> {
        val converted = number / 10000
        converted.toString() + "w"
    }
    else -> {
        var converted = String.format(Locale.ENGLISH, "%.1f", number / 100_000_000.0)
        if (converted.endsWith(".00")) {
            converted = converted.replace(".00", "")
        }
        converted + "e"
    }
}

/**
 * 判断某个应用是否安装。
 * @param packageName
 * 要检查是否安装的应用包名
 * @return 安装返回true，否则返回false。
 */
fun isInstalled(packageName: String): Boolean {
    val packageInfo: PackageInfo? = try {
        AppConfig.getInstance().context.packageManager.getPackageInfo(packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }
    return packageInfo != null
}

/**
 * 判断手机是否安装了QQ。
 */
fun isQQInstalled() = isInstalled("com.tencent.mobileqq")

/**
 * 判断手机是否安装了微信。
 */
fun isWechatInstalled() = isInstalled("com.tencent.mm")

/**
 * 判断手机是否安装了微博。
 * */
fun isWeiboInstalled() = isInstalled("com.sina.weibo")