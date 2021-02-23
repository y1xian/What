package com.yyxnb.util_common;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/02/20
 * 描    述：对象工具类，包括判空、克隆、序列化等操作
 * ================================================
 */
public class ObjUtils {

    /**
     * 比较两个对象是否相等。<br>
     * 相同的条件有两个，满足其一即可：<br>
     * <ol>
     * <li>obj1 == null &amp;&amp; obj2 == null</li>
     * <li>obj1.equals(obj2)</li>
     * </ol>
     * 1. obj1 == null &amp;&amp; obj2 == null 2. obj1.equals(obj2)
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return 是否相等
     * @see Objects#equals(Object, Object)
     */
    public static boolean equals(Object obj1, Object obj2) {
        // return (obj1 != null) ? (obj1.equals(obj2)) : (obj2 == null);
        return Objects.equals(obj1, obj2);
    }

    /**
     * 比较两个对象是否不相等。<br>
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return 是否不等
     * @since 3.0.7
     */
    public static boolean notEquals(Object obj1, Object obj2) {
        return !equals(obj1, obj2);
    }

    /**
     * 计算对象长度，如果是字符串调用其length函数，集合类调用其size函数，数组调用其length属性，其他可遍历对象遍历计算长度<br>
     * 支持的类型包括：
     * <ul>
     * <li>CharSequence</li>
     * <li>Map</li>
     * <li>Iterator</li>
     * <li>Enumeration</li>
     * <li>Array</li>
     * </ul>
     *
     * @param obj 被计算长度的对象
     * @return 长度
     */
    public static int length(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length();
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).size();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).size();
        }

        int count;
        if (obj instanceof Iterator) {
            Iterator<?> iter = (Iterator<?>) obj;
            count = 0;
            while (iter.hasNext()) {
                count++;
                iter.next();
            }
            return count;
        }
        if (obj instanceof Enumeration) {
            Enumeration<?> enumeration = (Enumeration<?>) obj;
            count = 0;
            while (enumeration.hasMoreElements()) {
                count++;
                enumeration.nextElement();
            }
            return count;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj);
        }
        return -1;
    }

    /**
     * 对象中是否包含元素<br>
     * 支持的对象类型包括：
     * <ul>
     * <li>String</li>
     * <li>Collection</li>
     * <li>Map</li>
     * <li>Iterator</li>
     * <li>Enumeration</li>
     * <li>Array</li>
     * </ul>
     *
     * @param obj     对象
     * @param element 元素
     * @return 是否包含
     */
    public static boolean contains(Object obj, Object element) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            if (element == null) {
                return false;
            }
            return ((String) obj).contains(element.toString());
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).contains(element);
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).containsValue(element);
        }

        if (obj instanceof Iterator) {
            Iterator<?> iter = (Iterator<?>) obj;
            while (iter.hasNext()) {
                Object o = iter.next();
                if (equals(o, element)) {
                    return true;
                }
            }
            return false;
        }
        if (obj instanceof Enumeration) {
            Enumeration<?> enumeration = (Enumeration<?>) obj;
            while (enumeration.hasMoreElements()) {
                Object o = enumeration.nextElement();
                if (equals(o, element)) {
                    return true;
                }
            }
            return false;
        }
        if (obj.getClass().isArray()) {
            int len = Array.getLength(obj);
            for (int i = 0; i < len; i++) {
                Object o = Array.get(obj, i);
                if (equals(o, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查对象是否为null<br>
     * 判断标准为：
     *
     * <pre>
     * 1. == null
     * 2. equals(null)
     * </pre>
     *
     * @param obj 对象
     * @return 是否为null
     */
    public static boolean isNull(Object obj) {
        //noinspection ConstantConditions
        return null == obj || obj.equals(null);
    }

    /**
     * 检查对象是否不为null
     *
     * @param obj 对象
     * @return 是否为null
     */
    public static boolean isNotNull(Object obj) {
        //noinspection ConstantConditions
        return null != obj && !obj.equals(null);
    }


}
