package com.yyxnb.arch.action

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.Nullable
import java.io.Serializable
import java.util.*

/**
 * 参数意图
 *
 * @author yyx
 */
interface BundleAction {

    @Nullable
    fun getBundle(): Bundle?

    fun getInt(name: String): Int {
        return getInt(name, 0)
    }

    fun getInt(name: String, defaultValue: Int): Int {
        return if (getBundle() == null) defaultValue else getBundle()!!.getInt(name, defaultValue)
    }

    fun getLong(name: String): Long {
        return getLong(name, 0)
    }

    fun getLong(name: String, defaultValue: Int): Long {
        return if (getBundle() == null) defaultValue.toLong() else getBundle()!!.getLong(name, defaultValue.toLong())
    }

    fun getFloat(name: String): Float {
        return getFloat(name, 0)
    }

    fun getFloat(name: String, defaultValue: Int): Float {
        return if (getBundle() == null) defaultValue.toFloat() else getBundle()!!.getFloat(name, defaultValue.toFloat())
    }

    fun getDouble(name: String): Double {
        return getDouble(name, 0)
    }

    fun getDouble(name: String, defaultValue: Int): Double {
        return if (getBundle() == null) defaultValue.toDouble() else getBundle()!!.getDouble(name, defaultValue.toDouble())
    }

    fun getBoolean(name: String): Boolean {
        return getBoolean(name, false)
    }

    fun getBoolean(name: String, defaultValue: Boolean): Boolean {
        return if (getBundle() == null) defaultValue else getBundle()!!.getBoolean(name, defaultValue)
    }

    fun getString(name: String): String? {
        return if (getBundle() == null) null else getBundle()!!.getString(name)
    }

    fun <P : Parcelable> getParcelable(name: String): P? {
        return if (getBundle() == null) null else getBundle()!!.getParcelable(name)
    }

    fun <S : Serializable> getSerializable(name: String): S? {
        return (if (getBundle() == null) null else getBundle()!!.getSerializable(name)) as S?
    }

    fun getStringArrayList(name: String?): ArrayList<String>? {
        return if (getBundle() == null) null else getBundle()!!.getStringArrayList(name)
    }

    fun getIntegerArrayList(name: String?): ArrayList<Int>? {
        return if (getBundle() == null) null else getBundle()!!.getIntegerArrayList(name)
    }
}