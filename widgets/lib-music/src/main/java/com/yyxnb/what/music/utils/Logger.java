package com.yyxnb.what.music.utils;

import android.util.Log;

public class Logger {

    public static boolean IS_DEBUG=true;

    public static void d(String TAG, String message) {
        if(IS_DEBUG){
            Log.d(TAG,message);
        }
    }

    public static void e(String TAG, String message) {
        if(IS_DEBUG){
            Log.e(TAG,message);
        }
    }

    public static void v(String TAG, String message) {
        if(IS_DEBUG){
            Log.e(TAG,message);
        }
    }

    public static void w(String TAG, String message) {
        if(IS_DEBUG){
            Log.w(TAG,message);
        }
    }

    public static void i(String TAG, String message) {
        if(IS_DEBUG){
            Log.i(TAG,message);
        }
    }
}
