package com.yyxnb.what.popup.enums;

/**
 * Description:
 */
public enum ImageType {
    GIF(true),
    JPEG(false),
    RAW(false),
    /** PNG type with alpha. */
    PNG_A(true),
    /** PNG type without alpha. */
    PNG(false),
    /** WebP type with alpha. */
    WEBP_A(true),
    /** WebP type without alpha. */
    WEBP(false),
    /** Unrecognized type. */
    UNKNOWN(false);

    private final boolean hasAlpha;

    ImageType(boolean hasAlpha) {
        this.hasAlpha = hasAlpha;
    }

    public boolean hasAlpha() {
        return hasAlpha;
    }
}