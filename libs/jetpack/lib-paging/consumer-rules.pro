
-dontwarn com.yyxnb.what.lib_paging.**
-keep class com.yyxnb.what.lib_paging.** {*;}
-keep public class * extends com.yyxnb.what.lib_paging.base.MultiItemTypePagedAdapter

# paging
-dontwarn android.arch.paging.**
-keep class android.arch.paging.** {*;}