
#
-dontwarn com.yyxnb.what.network.**
-keep class com.yyxnb.what.network.** {*;}

# Retrofit
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions
-dontwarn javax.annotation.**

# paging
-dontwarn android.arch.paging.**
-keep class android.arch.paging.** {*;}