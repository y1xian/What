
-dontwarn com.yyxnb.what.webview.**
-keep class com.yyxnb.what.webview.** {*;}

# AgentWeb
-keep class com.just.agentweb.** {*;}
-dontwarn com.just.agentweb.**

#--------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}


#---------------------------------js交互-------------------------------------
-keepattributes *JavascriptInterface*