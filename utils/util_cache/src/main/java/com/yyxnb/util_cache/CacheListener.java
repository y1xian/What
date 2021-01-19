package com.yyxnb.util_cache;

/**
 * 缓存监听，用于实现缓存操作时的回调监听，例如缓存对象的移除事件等
 *
 * @param <K> 缓存键
 * @param <V> 缓存值
 */
public interface CacheListener<K, V> {

    /**
     * 对象移除回调
     *
     * @param key          键
     * @param cachedObject 被缓存的对象
     */
    void onRemove(K key, V cachedObject);
}
