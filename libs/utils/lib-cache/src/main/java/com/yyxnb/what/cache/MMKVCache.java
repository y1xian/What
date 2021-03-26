package com.yyxnb.what.cache;

import android.content.Context;
import android.os.Parcelable;

import com.tencent.mmkv.MMKV;

import java.util.Set;


/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/01/26
 * 描    述：mmkv实现
 * ================================================
 */
public class MMKVCache implements ICache {

    public MMKVCache() {
    }

    //============================================save

    @Override
    public void init(Context context) {
        MMKV.initialize(context);
    }

    @Override
    public void save(String key, String value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    @Override
    public void save(String key, int value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    @Override
    public void save(String key, boolean value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    @Override
    public void save(String key, byte[] value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    @Override
    public void save(String key, Set<String> value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    @Override
    public void save(String key, Parcelable value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    //============================================get

    @Override
    public String get(String key, String value) {
        return MMKV.defaultMMKV().decodeString(key, value);
    }

    @Override
    public int get(String key, int value) {
        return MMKV.defaultMMKV().decodeInt(key, value);
    }

    @Override
    public boolean get(String key, boolean value) {
        return MMKV.defaultMMKV().decodeBool(key, value);
    }

    @Override
    public byte[] get(String key) {
        return MMKV.defaultMMKV().decodeBytes(key);
    }

    @Override
    public Set<String> get(String key, Set<String> value) {
        return MMKV.defaultMMKV().decodeStringSet(key, value);
    }

    @Override
    public <T extends Parcelable> T get(String key, Class<T> clazz, T value) {
        return MMKV.defaultMMKV().decodeParcelable(key, clazz, value);
    }


    //===============================================

    /**
     * 查询
     *
     * @param key
     * @return
     */
    @Override
    public boolean containsKey(String key) {
        return MMKV.defaultMMKV().containsKey(key);
    }

    /**
     * 清除所有数据
     */
    @Override
    public void clearAll() {
        MMKV.defaultMMKV().clearAll();
    }

    /**
     * 清除指定数据
     */
    @Override
    public void remove(String... key) {
        MMKV.defaultMMKV().removeValuesForKeys(key);
    }
}