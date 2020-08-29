package com.yyxnb.common.utils

import android.annotation.SuppressLint
import android.text.TextUtils

/**
 * 手机系统判断
 *
 * @author yyx
 */
object OSUtils {
    private const val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private const val KEY_EMUI_VERSION_NAME = "ro.build.version.emui"
    private const val KEY_DISPLAY = "ro.build.display.id"

    /**
     * 判断是否为miui
     * Is miui boolean.
     *
     * @return the boolean
     */
    val isMIUI: Boolean
        get() {
            val property = getSystemProperty(KEY_MIUI_VERSION_NAME, "")
            return !TextUtils.isEmpty(property)
        }

    /**
     * 判断是否为emui
     * Is emui boolean.
     *
     * @return the boolean
     */
    val isEMUI: Boolean
        get() {
            val property = getSystemProperty(KEY_EMUI_VERSION_NAME, "")
            return !TextUtils.isEmpty(property)
        }

    /**
     * 得到emui的版本
     * Gets emui version.
     *
     * @return the emui version
     */
    val eMUIVersion: String
        get() = if (isEMUI) getSystemProperty(KEY_EMUI_VERSION_NAME, "") else ""

    /**
     * 判断是否为emui3.1版本
     * Is emui 3 1 boolean.
     *
     * @return the boolean
     */
    val isEMUI3_1: Boolean
        get() {
            val property = eMUIVersion
            return if ("EmotionUI 3" == property || property.contains("EmotionUI_3.1")) {
                true
            } else false
        }

    /**
     * 判断是否为emui3.0版本
     * Is emui 3 1 boolean.
     *
     * @return the boolean
     */
    val isEMUI3_0: Boolean
        get() {
            val property = eMUIVersion
            return if (property.contains("EmotionUI_3.0")) {
                true
            } else false
        }

    /**
     * 判断是否为emui3.x版本
     * Is emui 3 x boolean.
     *
     * @return the boolean
     */
    val isEMUI3_x: Boolean
        get() = isEMUI3_0 || isEMUI3_1

    private fun getSystemProperty(key: String, defaultValue: String): String {
        try {
            @SuppressLint("PrivateApi") val clz = Class.forName("android.os.SystemProperties")
            val method = clz.getMethod("get", String::class.java, String::class.java)
            return method.invoke(clz, key, defaultValue) as String
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defaultValue
    }
}