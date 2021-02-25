
-dontwarn com.yyxnb.common_res.**
-keep class com.yyxnb.common_res.** {*;}

# banner
-keep class com.youth.banner.** {*;}

 # magicindicator
 -keep class net.lucode.hackware.magicindicator.** {*;}

 # smartrefresh
 -dontwarn com.scwang.smart.**
 -keep class com.scwang.smart.**{ *;}

 # Canary
 -dontwarn com.squareup.haha.guava.**
 -dontwarn com.squareup.haha.perflib.**
 -dontwarn com.squareup.haha.trove.**
 -dontwarn com.squareup.leakcanary.**
 -keep class com.squareup.haha.** { *; }
 -keep class com.squareup.leakcanary.** { *; }