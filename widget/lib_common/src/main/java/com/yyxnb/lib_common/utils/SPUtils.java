package com.yyxnb.lib_common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yyxnb.util_app.AppUtils;

/**
 * 保存工具类
 *
 * @author yyx
 */
public class SPUtils {

    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_date";
    private static final Context CONTEXT = AppUtils.getApp();

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void setParam(String key, Object object) {

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = CONTEXT.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        switch (type) {
            case "String":
                editor.putString(key, (String) object);
                break;
            case "Integer":
                editor.putInt(key, (Integer) object);
                break;
            case "Boolean":
                editor.putBoolean(key, (Boolean) object);
                break;
            case "Float":
                editor.putFloat(key, (Float) object);
                break;
            case "Long":
                editor.putLong(key, (Long) object);
                break;
            default:
                break;
        }
        editor.apply();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = CONTEXT.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        switch (type) {
            case "String":
                return sp.getString(key, (String) defaultObject);
            case "Integer":
                return sp.getInt(key, (Integer) defaultObject);
            case "Boolean":
                return sp.getBoolean(key, (Boolean) defaultObject);
            case "Float":
                return sp.getFloat(key, (Float) defaultObject);
            case "Long":
                return sp.getLong(key, (Long) defaultObject);
            default:
                break;
        }
        return null;
    }

    /**
     * 清除所有数据
     */
    public static void clearAll() {
        SharedPreferences sp = CONTEXT.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().apply();
    }

    /**
     * 清除指定数据
     */
    public static void clear(String key) {
        SharedPreferences sp = CONTEXT.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }
}