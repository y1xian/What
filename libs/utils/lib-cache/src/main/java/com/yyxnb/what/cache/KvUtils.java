package com.yyxnb.what.cache;

import android.content.Context;
import android.os.Parcelable;

import java.util.Set;


/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/01/26
 * 描    述：轻量级保存工具类
 * ================================================
 */
public class KvUtils {

    private static final ICache cache = new MMKVCache();

    private KvUtils() {
    }

    public static void initialize(Context context) {
        cache.init(context);
    }

    // ----------------------------------------------------------------------- save

    public static void save(String key, String value) {
        cache.save(key, value);
    }

    public static void save(String key, int value) {
        cache.save(key, value);
    }

    public static void save(String key, boolean value) {
        cache.save(key, value);
    }

    public static void save(String key, byte[] value) {
        cache.save(key, value);
    }

    public static void save(String key, Set<String> value) {
        cache.save(key, value);
    }

    public static void save(String key, Parcelable value) {
        cache.save(key, value);
    }

    // ----------------------------------------------------------------------- get
    public static String get(String key, String value) {
        return cache.get(key, value);
    }

    public static int get(String key, int value) {
        return cache.get(key, value);
    }

    public static boolean get(String key, boolean value) {
        return cache.get(key, value);
    }

    public static byte[] get(String key) {
        return cache.get(key);
    }

    public static Set<String> get(String key, Set<String> value) {
        return cache.get(key, value);
    }

    public static <T extends Parcelable> T get(String key, Class<T> clazz, T value) {
        return cache.get(key, clazz, value);
    }


    // ----------------------------------------------------------------------- 查询

    /**
     * 查询
     *
     * @param key
     * @return
     */
    public static boolean containsKey(String key) {
        return cache.containsKey(key);
    }

    /**
     * 清除所有数据
     */
    public static void clearAll() {
        cache.clearAll();
    }

    /**
     * 清除指定数据
     */
    public static void remove(String... keys) {
        cache.remove(keys);
    }
}