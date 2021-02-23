package com.yyxnb.util_common;

import android.os.Build;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/02/22
 * 描    述：List工具类
 * ================================================
 */
public class ListUtils {

    /**
     * 获取一个空List
     *
     * @param <T> 元素类型
     * @return 空的List
     * @since 5.2.6
     */
    public static <T> List<T> empty() {
        return Collections.emptyList();
    }

    /**
     * 新建一个空List
     *
     * @param <T>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @return List对象
     * @since 4.1.2
     */
    public static <T> List<T> list(boolean isLinked) {
        return isLinked ? new LinkedList<>() : new ArrayList<>();
    }

    /**
     * 新建一个List
     *
     * @param <T>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @param values   数组
     * @return List对象
     * @since 4.1.2
     */
    @SafeVarargs
    public static <T> List<T> list(boolean isLinked, T... values) {
        if (ArrayUtils.isEmpty(values)) {
            return list(isLinked);
        }
        final List<T> arrayList = isLinked ? new LinkedList<>() : new ArrayList<>(values.length);
        Collections.addAll(arrayList, values);
        return arrayList;
    }

    /**
     * 新建一个List
     *
     * @param <T>        集合元素类型
     * @param isLinked   是否新建LinkedList
     * @param collection 集合
     * @return List对象
     * @since 4.1.2
     */
    public static <T> List<T> list(boolean isLinked, Collection<T> collection) {
        if (null == collection) {
            return list(isLinked);
        }
        return isLinked ? new LinkedList<>(collection) : new ArrayList<>(collection);
    }

    /**
     * 新建一个List<br>
     * 提供的参数为null时返回空{@link ArrayList}
     *
     * @param <T>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @param iterable {@link Iterable}
     * @return List对象
     * @since 4.1.2
     */
    public static <T> List<T> list(boolean isLinked, Iterable<T> iterable) {
        if (null == iterable) {
            return list(isLinked);
        }
        return list(isLinked, iterable.iterator());
    }

    /**
     * 新建一个ArrayList<br>
     * 提供的参数为null时返回空{@link ArrayList}
     *
     * @param <T>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @param iter     {@link Iterator}
     * @return ArrayList对象
     * @since 4.1.2
     */
    public static <T> List<T> list(boolean isLinked, Iterator<T> iter) {
        final List<T> list = list(isLinked);
        if (null != iter) {
            while (iter.hasNext()) {
                list.add(iter.next());
            }
        }
        return list;
    }

    /**
     * 新建一个List<br>
     * 提供的参数为null时返回空{@link ArrayList}
     *
     * @param <T>        集合元素类型
     * @param isLinked   是否新建LinkedList
     * @param enumration {@link Enumeration}
     * @return ArrayList对象
     * @since 3.0.8
     */
    public static <T> List<T> list(boolean isLinked, Enumeration<T> enumration) {
        final List<T> list = list(isLinked);
        if (null != enumration) {
            while (enumration.hasMoreElements()) {
                list.add(enumration.nextElement());
            }
        }
        return list;
    }

    /**
     * 新建一个ArrayList
     *
     * @param <T>    集合元素类型
     * @param values 数组
     * @return ArrayList对象
     */
    @SafeVarargs
    public static <T> ArrayList<T> toList(T... values) {
        return (ArrayList<T>) list(false, values);
    }

    /**
     * 新建LinkedList
     *
     * @param values 数组
     * @param <T>    类型
     * @return LinkedList
     * @since 4.1.2
     */
    @SafeVarargs
    public static <T> LinkedList<T> toLinkedList(T... values) {
        return (LinkedList<T>) list(true, values);
    }

    /**
     * 数组转为一个不可变List<br>
     * 类似于Java9中的List.of
     *
     * @param ts  对象
     * @param <T> 对象类型
     * @return 不可修改List
     * @since 5.4.3
     */
    @SafeVarargs
    public static <T> List<T> of(T... ts) {
        if (ArrayUtils.isEmpty(ts)) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(toList(ts));
    }

    /**
     * 针对List排序，排序会修改原List
     *
     * @param <T>  元素类型
     * @param list 被排序的List
     * @param c    {@link Comparator}
     * @return 原list
     * @see Collections#sort(List, Comparator)
     */
    public static <T> List<T> sort(List<T> list, Comparator<? super T> c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.sort(c);
        }
        return list;
    }

    /**
     * 反序给定List，会在原List基础上直接修改
     *
     * @param <T>  元素类型
     * @param list 被反转的List
     * @return 反转后的List
     * @since 4.0.6
     */
    public static <T> List<T> reverse(List<T> list) {
        Collections.reverse(list);
        return list;
    }

    /**
     * 截取集合的部分
     *
     * @param <T>   集合元素类型
     * @param list  被截取的数组
     * @param start 开始位置（包含）
     * @param end   结束位置（不包含）
     * @return 截取后的数组，当开始位置超过最大时，返回空的List
     */
    public static <T> List<T> sub(List<T> list, int start, int end) {
        return sub(list, start, end, 1);
    }

    /**
     * 截取集合的部分
     *
     * @param <T>   集合元素类型
     * @param list  被截取的数组
     * @param start 开始位置（包含）
     * @param end   结束位置（不包含）
     * @param step  步进
     * @return 截取后的数组，当开始位置超过最大时，返回空的List
     * @since 4.0.6
     */
    public static <T> List<T> sub(List<T> list, int start, int end, int step) {
        if (list == null) {
            return null;
        }

        if (list.isEmpty()) {
            return new ArrayList<>(0);
        }

        final int size = list.size();
        if (start < 0) {
            start += size;
        }
        if (end < 0) {
            end += size;
        }
        if (start == size) {
            return new ArrayList<>(0);
        }
        if (start > end) {
            int tmp = start;
            start = end;
            end = tmp;
        }
        if (end > size) {
            if (start >= size) {
                return new ArrayList<>(0);
            }
            end = size;
        }

        if (step <= 1) {
            return list.subList(start, end);
        }

        final List<T> result = new ArrayList<>();
        for (int i = start; i < end; i += step) {
            result.add(list.get(i));
        }
        return result;
    }

    /**
     * 对集合按照指定长度分段，每一个段为单独的集合，返回这个集合的列表
     *
     * <p>
     * 需要特别注意的是，此方法调用{@link List#subList(int, int)}切分List，
     * 此方法返回的是原List的视图，也就是说原List有变更，切分后的结果也会变更。
     * </p>
     *
     * @param <T>  集合元素类型
     * @param list 列表
     * @param size 每个段的长度
     * @return 分段列表
     * @since 5.4.5
     */
    public static <T> List<List<T>> split(List<T> list, int size) {
        if (CollUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        final int listSize = list.size();
        final List<List<T>> result = new ArrayList<>(listSize / size + 1);
        int offset = 0;
        for (int toIdx = size; toIdx <= listSize; offset = toIdx, toIdx += size) {
            result.add(list.subList(offset, toIdx));
        }
        if (offset < listSize) {
            result.add(list.subList(offset, listSize));
        }
        return result;
    }

}
