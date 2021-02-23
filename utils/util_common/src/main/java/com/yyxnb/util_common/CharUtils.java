package com.yyxnb.util_common;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/02/20
 * 描    述：字符工具类
 * ================================================
 */
public class CharUtils {

    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     * @since 4.0.10
     */
    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }

    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     * @since 4.0.10
     */
    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c)
                || Character.isSpaceChar(c)
                || c == '\ufeff'
                || c == '\u202a';
    }

    /**
     * 判断是否为emoji表情符<br>
     *
     * @param c 字符
     * @return 是否为emoji
     * @since 4.0.8
     */
    public static boolean isEmoji(char c) {
        //noinspection ConstantConditions
        return false == ((c == 0x0) || //
                (c == 0x9) || //
                (c == 0xA) || //
                (c == 0xD) || //
                ((c >= 0x20) && (c <= 0xD7FF)) || //
                ((c >= 0xE000) && (c <= 0xFFFD)) || //
                ((c >= 0x100000) && (c <= 0x10FFFF)));
    }
}
