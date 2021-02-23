package com.yyxnb.util_common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/02/22
 * 描    述：{@link Iterable} 和 {@link Iterator} 相关工具类
 * ================================================
 */
public class IterUtils {

    /**
     * 根据集合返回一个元素计数的 {@link Map}<br>
     * 所谓元素计数就是假如这个集合中某个元素出现了n次，那将这个元素做为key，n做为value<br>
     * 例如：[a,b,c,c,c] 得到：<br>
     * a: 1<br>
     * b: 1<br>
     * c: 3<br>
     *
     * @param <T>  集合元素类型
     * @param iter {@link Iterator}，如果为null返回一个空的Map
     * @return {@link Map}
     */
    public static <T> Map<T, Integer> countMap(Iterator<T> iter) {
        final HashMap<T, Integer> countMap = new HashMap<>();
        if (null != iter) {
            Integer count;
            T t;
            while (iter.hasNext()) {
                t = iter.next();
                count = countMap.get(t);
                if (null == count) {
                    countMap.put(t, 1);
                } else {
                    countMap.put(t, count + 1);
                }
            }
        }
        return countMap;
    }
}
