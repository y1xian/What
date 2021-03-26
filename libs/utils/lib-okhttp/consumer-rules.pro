
# Okhttp3
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okhttp3.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Okio
-dontwarn okio.**
-keep class okio.**{*;}
-keep interface okio.**{*;}
-dontwarn com.squareup.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *;}
-keep class com.google.gson.stream.** { *;}
-keepattributes EnclosingMethod
-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.gson.** { *;}

#
-dontwarn com.yyxnb.what.okhttp.**
-keep class com.yyxnb.what.okhttp.** { *;}