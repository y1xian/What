
#
-dontwarn com.yyxnb.what.cache.**
-keep class com.yyxnb.what.cache.** { *;}

-keep class cn.hutool.core.** { *; }
-keep class cn.hutool.cache.** { *; }

#MMKV
-keepclasseswithmembers,includedescriptorclasses class com.tencent.mmkv.** {
    native <methods>;
    long nativeHandle;
    private static *** onMMKVCRCCheckFail(***);
    private static *** onMMKVFileLengthError(***);
    private static *** mmkvLogImp(...);
    private static *** onContentChangedByOuterProcess(***);
}
