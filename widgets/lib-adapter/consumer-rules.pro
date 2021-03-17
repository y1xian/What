
-dontwarn com.yyxnb.lib_adapter.**
-keep class com.yyxnb.lib_adapter.** {*;}
-keep interface com.yyxnb.lib_adapter.** {*;}
-keep public class * extends com.yyxnb.lib_adapter.base.MultiItemTypeAdapter
-keep public class * extends com.yyxnb.lib_adapter.base.MultiItemTypePagedAdapter
-keepclassmembers class **$** extends com.yyxnb.lib_adapter.base.BaseViewHolder{*;}

-keep class java.util.** {*;}
-keep interface java.util.** {*;}


# paging
-dontwarn android.arch.paging.**
-keep class android.arch.paging.** {*;}

#-keep class com.chad.library.adapter.** {
#*;
#}
#-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
#-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
#-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
#     <init>(...);
#}