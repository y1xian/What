
-dontwarn com.yyxnb.what.adapter.**
-keep class com.yyxnb.what.adapter.** {*;}
-keep interface com.yyxnb.what.adapter.** {*;}
-keep public class * extends com.yyxnb.what.adapter.base.MultiItemTypeAdapter
-keepclassmembers class **$** extends com.yyxnb.what.adapter.base.BaseViewHolder{*;}

-keep class java.util.** {*;}
-keep interface java.util.** {*;}




#-keep class com.chad.library.adapter.** {
#*;
#}
#-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
#-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
#-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
#     <init>(...);
#}