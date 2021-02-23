package com.yyxnb.util_common;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/02/20
 * 描    述：字符串工具类
 * ================================================
 */
public class StrUtils {

    public static final String EMPTY = "";

    // ------------------------------------------------------------------------ Empty

    /**
     * 字符串是否为空，空的定义如下:<br>
     * 1、为null <br>
     * 2、为""<br>
     *
     * @param str 被检测的字符串
     * @return 是否为空
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * 字符串是否为非空白，非空白的定义如下： <br>
     * 1、不为null <br>
     * 2、不为""<br>
     *
     * @param str 被检测的字符串
     * @return 是否为非空
     */
    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    // ------------------------------------------------------------------------ Blank

    /**
     * 字符串是否为空白 空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""<br>
     *
     * @param str 被检测的字符串
     * @return 是否为空
     */
    public static boolean isBlank(CharSequence str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            // 只要有一个非空字符即为非空字符串
            if (!CharUtils.isBlankChar(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 如果对象是字符串是否为空白，空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""<br>
     *
     * @param obj 对象
     * @return 如果为字符串是否为空串
     * @since 3.3.0
     */
    public static boolean isBlankIfStr(Object obj) {
        if (null == obj) {
            return true;
        } else if (obj instanceof CharSequence) {
            return isBlank((CharSequence) obj);
        }
        return false;
    }

    /**
     * 字符串是否为非空白，非空白的定义如下： <br>
     * 1、不为null <br>
     * 2、不为不可见字符（如空格）<br>
     * 3、不为""<br>
     *
     * @param str 被检测的字符串
     * @return 是否为非空
     */
    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    /**
     * 是否包含空字符串
     *
     * @param strs 字符串列表
     * @return 是否包含空字符串
     */
    public static boolean hasBlank(CharSequence... strs) {
        if (ArrayUtils.isEmpty(strs)) {
            return true;
        }

        for (CharSequence str : strs) {
            if (isBlank(str)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 比较两个字符串（大小写不敏感）。
     *
     * <pre>
     * equalsIgnoreCase(null, null)   = true
     * equalsIgnoreCase(null, &quot;abc&quot;)  = false
     * equalsIgnoreCase(&quot;abc&quot;, null)  = false
     * equalsIgnoreCase(&quot;abc&quot;, &quot;abc&quot;) = true
     * equalsIgnoreCase(&quot;abc&quot;, &quot;ABC&quot;) = true
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, true);
    }

    /**
     * 比较两个字符串是否相等。
     *
     * @param str1       要比较的字符串1
     * @param str2       要比较的字符串2
     * @param ignoreCase 是否忽略大小写
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     * @since 3.2.0
     */
    public static boolean equals(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        if (null == str1) {
            // 只有两个都为null才判断相等
            return str2 == null;
        }
        if (null == str2) {
            // 字符串2空，字符串1非空，直接false
            return false;
        }

        if (ignoreCase) {
            return str1.toString().equalsIgnoreCase(str2.toString());
        } else {
            return str1.toString().contentEquals(str2);
        }
    }

    // ------------------------------------------------------------------------ Trim

    /**
     * {@link CharSequence} 转为字符串，null安全
     *
     * @param cs {@link CharSequence}
     * @return 字符串
     */
    public static String str(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }

    /**
     * 除去字符串头尾部的空白，如果字符串是<code>null</code>，依然返回<code>null</code>。
     *
     * <p>
     * 注意，和<code>String.trim</code>不同，此方法使用<code>NumberUtil.isBlankChar</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
     *
     * <pre>
     * trim(null)          = null
     * trim(&quot;&quot;)            = &quot;&quot;
     * trim(&quot;     &quot;)       = &quot;&quot;
     * trim(&quot;abc&quot;)         = &quot;abc&quot;
     * trim(&quot;    abc    &quot;) = &quot;abc&quot;
     * </pre>
     *
     * @param str 要处理的字符串
     * @return 除去头尾空白的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     */
    public static String trim(CharSequence str) {
        return (null == str) ? null : trim(str, 0);
    }

    /**
     * 除去字符串头尾部的空白符，如果字符串是<code>null</code>，依然返回<code>null</code>。
     *
     * @param str  要处理的字符串
     * @param mode <code>-1</code>表示trimStart，<code>0</code>表示trim全部， <code>1</code>表示trimEnd
     * @return 除去指定字符后的的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     */
    public static String trim(CharSequence str, int mode) {
        if (str == null) {
            return null;
        }

        int length = str.length();
        int start = 0;
        int end = length;

        // 扫描字符串头部
        if (mode <= 0) {
            while ((start < end) && (CharUtils.isBlankChar(str.charAt(start)))) {
                start++;
            }
        }

        // 扫描字符串尾部
        if (mode >= 0) {
            while ((start < end) && (CharUtils.isBlankChar(str.charAt(end - 1)))) {
                end--;
            }
        }

        if ((start > 0) || (end < length)) {
            return str.toString().substring(start, end);
        }

        return str.toString();
    }

    /**
     * 给定字符串数组全部做去首尾空格
     *
     * @param strs 字符串数组
     */
    public static void trim(String[] strs) {
        if (null == strs) {
            return;
        }
        String str;
        for (int i = 0; i < strs.length; i++) {
            str = strs[i];
            if (null != str) {
                strs[i] = str.trim();
            }
        }
    }

    // ------------------------------------------------------------------------------------------------- remove

    /**
     * 移除字符串中所有给定字符串<br>
     * 例：removeAll("aa-bb-cc-dd", "-") =》 aabbccdd
     *
     * @param str         字符串
     * @param strToRemove 被移除的字符串
     * @return 移除后的字符串
     */
    public static String removeAll(CharSequence str, CharSequence strToRemove) {
        if (isEmpty(str)) {
            return str(str);
        }
        return str.toString().replace(strToRemove, EMPTY);
    }

    /**
     * 去除字符串中指定的多个字符，如有多个则全部去除
     *
     * @param str   字符串
     * @param chars 字符列表
     * @return 去除后的字符
     * @since 4.2.2
     */
    public static String removeAll(CharSequence str, char... chars) {
        if (null == str || ArrayUtils.isEmpty(chars)) {
            return str(str);
        }
        final int len = str.length();
        if (0 == len) {
            return str(str);
        }
        final StringBuilder builder = builder(len);
        char c;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (!ArrayUtils.contains(chars, c)) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * 移除字符串中所有给定字符串，当某个字符串出现多次，则全部移除<br>
     * 例：removeAny("aa-bb-cc-dd", "a", "b") =》 --cc-dd
     *
     * @param str          字符串
     * @param strsToRemove 被移除的字符串
     * @return 移除后的字符串
     * @since 5.3.8
     */
    public static String removeAny(CharSequence str, CharSequence... strsToRemove) {
        String result = str(str);
        if (isNotEmpty(str)) {
            for (CharSequence strToRemove : strsToRemove) {
                result = removeAll(result, strToRemove);
            }
        }
        return result;
    }

    /**
     * 创建StringBuilder对象
     *
     * @param capacity 初始大小
     * @return StringBuilder对象
     */
    public static StringBuilder builder(int capacity) {
        return new StringBuilder(capacity);
    }
}
