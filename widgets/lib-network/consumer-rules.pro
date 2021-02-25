
#
-dontwarn com.yyxnb.lib_network.**
-keep class com.yyxnb.lib_network.** {*;}

# Retrofit
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions
-dontwarn javax.annotation.**

# paging
-dontwarn android.arch.paging.**
-keep class android.arch.paging.** {*;}