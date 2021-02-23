package com.yyxnb.util_common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/02/22
 * 描    述：集合相关工具类
 * ================================================
 */
public class CollUtils {

    /**
     * 如果提供的集合为{@code null}，返回一个不可变的默认空集合，否则返回原集合<br>
     * 空集合使用{@link Collections#emptySet()}
     *
     * @param <T> 集合元素类型
     * @param set 提供的集合，可能为null
     * @return 原集合，若为null返回空集合
     * @since 4.6.3
     */
    public static <T> Set<T> emptyIfNull(Set<T> set) {
        return (null == set) ? Collections.emptySet() : set;
    }

    /**
     * 如果提供的集合为{@code null}，返回一个不可变的默认空集合，否则返回原集合<br>
     * 空集合使用{@link Collections#emptyList()}
     *
     * @param <T> 集合元素类型
     * @param set 提供的集合，可能为null
     * @return 原集合，若为null返回空集合
     * @since 4.6.3
     */
    public static <T> List<T> emptyIfNull(List<T> set) {
        return (null == set) ? Collections.emptyList() : set;
    }

    /**
     * 两个集合的并集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留最多的个数<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c, c, c]，此结果中只保留了三个c
     *
     * @param <T>   集合元素类型
     * @param coll1 集合1
     * @param coll2 集合2
     * @return 并集的集合，返回 {@link ArrayList}
     */
    public static <T> Collection<T> union(Collection<T> coll1, Collection<T> coll2) {
        if (isEmpty(coll1)) {
            return new ArrayList<>(coll2);
        } else if (isEmpty(coll2)) {
            return new ArrayList<>(coll1);
        }

        final ArrayList<T> list = new ArrayList<>(Math.max(coll1.size(), coll2.size()));
        final Map<T, Integer> map1 = countMap(coll1);
        final Map<T, Integer> map2 = countMap(coll2);
        final Set<T> elts = newHashSet(coll2);
        elts.addAll(coll1);
        int m;
        for (T t : elts) {
//            m = Math.max(Convert.toInt(map1.get(t), 0), Convert.toInt(map2.get(t), 0));
            m = Math.max(map1.get(t), map2.get(t));
            for (int i = 0; i < m; i++) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * 多个集合的并集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留最多的个数<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c, c, c]，此结果中只保留了三个c
     *
     * @param <T>        集合元素类型
     * @param coll1      集合1
     * @param coll2      集合2
     * @param otherColls 其它集合
     * @return 并集的集合，返回 {@link ArrayList}
     */
    @SafeVarargs
    public static <T> Collection<T> union(Collection<T> coll1, Collection<T> coll2, Collection<T>... otherColls) {
        Collection<T> union = union(coll1, coll2);
        for (Collection<T> coll : otherColls) {
            union = union(union, coll);
        }
        return union;
    }


    /**
     * 多个集合的交集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留最少的个数<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c, c]，此结果中只保留了两个c
     *
     * @param <T>        集合元素类型
     * @param coll1      集合1
     * @param coll2      集合2
     * @param otherColls 其它集合
     * @return 并集的集合，返回 {@link ArrayList}
     */
    @SafeVarargs
    public static <T> Collection<T> intersection(Collection<T> coll1, Collection<T> coll2, Collection<T>... otherColls) {
        Collection<T> intersection = intersection(coll1, coll2);
        if (isEmpty(intersection)) {
            return intersection;
        }
        for (Collection<T> coll : otherColls) {
            intersection = intersection(intersection, coll);
            if (isEmpty(intersection)) {
                return intersection;
            }
        }
        return intersection;
    }

    /**
     * 两个集合的差集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留两个集合中此元素个数差的个数<br>
     * 例如：
     *
     * <pre>
     *     disjunction([a, b, c, c, c], [a, b, c, c]) -》 [c]
     *     disjunction([a, b], [])                    -》 [a, b]
     *     disjunction([a, b, c], [b, c, d])          -》 [a, d]
     * </pre>
     * 任意一个集合为空，返回另一个集合<br>
     * 两个集合无差集则返回空集合
     *
     * @param <T>   集合元素类型
     * @param coll1 集合1
     * @param coll2 集合2
     * @return 差集的集合，返回 {@link ArrayList}
     */
    public static <T> Collection<T> disjunction(Collection<T> coll1, Collection<T> coll2) {
        if (isEmpty(coll1)) {
            return coll2;
        }
        if (isEmpty(coll2)) {
            return coll1;
        }

        final List<T> result = new ArrayList<>();
        final Map<T, Integer> map1 = countMap(coll1);
        final Map<T, Integer> map2 = countMap(coll2);
        final Set<T> elts = newHashSet(coll2);
        elts.addAll(coll1);
        int m;
        for (T t : elts) {
            m = Math.abs(map1.get(t) - map2.get(t));
            for (int i = 0; i < m; i++) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * 计算集合的单差集，即只返回【集合1】中有，但是【集合2】中没有的元素，例如：
     *
     * <pre>
     *     subtract([1,2,3,4],[2,3,4,5]) -》 [1]
     * </pre>
     *
     * @param coll1 集合1
     * @param coll2 集合2
     * @param <T>   元素类型
     * @return 单差集
     */
    public static <T> Collection<T> subtract(Collection<T> coll1, Collection<T> coll2) {
        final Collection<T> result = coll1;
        result.removeAll(coll2);
        return result;
    }

    /**
     * 计算集合的单差集，即只返回【集合1】中有，但是【集合2】中没有的元素，例如：
     *
     * <pre>
     *     subtractToList([1,2,3,4],[2,3,4,5]) -》 [1]
     * </pre>
     *
     * @param coll1 集合1
     * @param coll2 集合2
     * @param <T>   元素类型
     * @return 单差集
     * @since 5.3.5
     */
    public static <T> List<T> subtractToList(Collection<T> coll1, Collection<T> coll2) {

        if (isEmpty(coll1)) {
            return ListUtils.empty();
        }
        if (isEmpty(coll2)) {
            return ListUtils.list(true, coll1);
        }

        //将被交数用链表储存，防止因为频繁扩容影响性能
        final List<T> result = new LinkedList<>();
        Set<T> set = new HashSet<>(coll2);
        for (T t : coll1) {
            if (false == set.contains(t)) {
                result.add(t);
            }
        }
        return result;
    }

    // ----------------------------------------------------------------------------------------------- new HashSet

    /**
     * 新建一个HashSet
     *
     * @param <T> 集合元素类型
     * @param ts  元素数组
     * @return HashSet对象
     */
    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... ts) {
        return set(false, ts);
    }

    /**
     * 新建一个LinkedHashSet
     *
     * @param <T> 集合元素类型
     * @param ts  元素数组
     * @return HashSet对象
     * @since 4.1.10
     */
    @SafeVarargs
    public static <T> LinkedHashSet<T> newLinkedHashSet(T... ts) {
        return (LinkedHashSet<T>) set(true, ts);
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>      集合元素类型
     * @param isSorted 是否有序，有序返回 {@link LinkedHashSet}，否则返回 {@link HashSet}
     * @param ts       元素数组
     * @return HashSet对象
     */
    @SafeVarargs
    public static <T> HashSet<T> set(boolean isSorted, T... ts) {
        if (null == ts) {
            return isSorted ? new LinkedHashSet<>() : new HashSet<>();
        }
        int initialCapacity = Math.max((int) (ts.length / .75f) + 1, 16);
        final HashSet<T> set = isSorted ? new LinkedHashSet<>(initialCapacity) : new HashSet<>(initialCapacity);
        Collections.addAll(set, ts);
        return set;
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @return HashSet对象
     */
    public static <T> HashSet<T> newHashSet(Collection<T> collection) {
        return newHashSet(false, collection);
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>        集合元素类型
     * @param isSorted   是否有序，有序返回 {@link LinkedHashSet}，否则返回{@link HashSet}
     * @param collection 集合，用于初始化Set
     * @return HashSet对象
     */
    public static <T> HashSet<T> newHashSet(boolean isSorted, Collection<T> collection) {
        return isSorted ? new LinkedHashSet<>(collection) : new HashSet<>(collection);
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>      集合元素类型
     * @param isSorted 是否有序，有序返回 {@link LinkedHashSet}，否则返回{@link HashSet}
     * @param iter     {@link Iterator}
     * @return HashSet对象
     * @since 3.0.8
     */
    public static <T> HashSet<T> newHashSet(boolean isSorted, Iterator<T> iter) {
        if (null == iter) {
            return set(isSorted, (T[]) null);
        }
        final HashSet<T> set = isSorted ? new LinkedHashSet<>() : new HashSet<>();
        while (iter.hasNext()) {
            set.add(iter.next());
        }
        return set;
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>         集合元素类型
     * @param isSorted    是否有序，有序返回 {@link LinkedHashSet}，否则返回{@link HashSet}
     * @param enumeration {@link Enumeration}
     * @return HashSet对象
     * @since 3.0.8
     */
    public static <T> HashSet<T> newHashSet(boolean isSorted, Enumeration<T> enumeration) {
        if (null == enumeration) {
            return set(isSorted, (T[]) null);
        }
        final HashSet<T> set = isSorted ? new LinkedHashSet<>() : new HashSet<>();
        while (enumeration.hasMoreElements()) {
            set.add(enumeration.nextElement());
        }
        return set;
    }


    // ---------------------------------------------------------------------- isEmpty

    /**
     * 集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }


    /**
     * 根据集合返回一个元素计数的 {@link Map}<br>
     * 所谓元素计数就是假如这个集合中某个元素出现了n次，那将这个元素做为key，n做为value<br>
     * 例如：[a,b,c,c,c] 得到：<br>
     * a: 1<br>
     * b: 1<br>
     * c: 3<br>
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @return {@link Map}
     */
    public static <T> Map<T, Integer> countMap(Iterable<T> collection) {
        return IterUtils.countMap(null == collection ? null : collection.iterator());
    }
}
