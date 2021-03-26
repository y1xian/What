package com.yyxnb.what.cache;

import android.content.Context;
import android.os.Parcelable;

import java.util.Set;

public interface ICache {

    void init(Context context);

    void save(String key, String value);

    void save(String key, int value);

    void save(String key, boolean value);

    void save(String key, byte[] value);

    void save(String key, Set<String> value);

    void save(String key, Parcelable value);

    String get(String key, String value);

    int get(String key, int value);

    boolean get(String key, boolean value);

    byte[] get(String key);

    Set<String> get(String key, Set<String> value);

    <T extends Parcelable> T get(String key, Class<T> clazz, T value);

    boolean containsKey(String key);

    void clearAll();

    void remove(String... keys);

}
