package com.yyxnb.what.app;

import android.annotation.SuppressLint;
import android.app.Application;

import java.lang.reflect.InvocationTargetException;

/**
 * 这种方式获取全局的Application 是一种拓展思路。
 * <p>
 * 对于组件化项目,不可能把项目实际的Application下沉到Base,而且各个module也不需要知道Application真实名字
 * <p>
 * 这种一次反射就能获取全局Application对象的方式相比于在Application#OnCreate保存一份的方式显示更加通用了
 */
public class AppGlobals {
    private static Application sApplication;

    @SuppressLint("PrivateApi")
    public static Application getApplication() {
        if (sApplication == null) {
            try {
                sApplication = (Application) Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication")
                        .invoke(null, (Object[]) null);
                if (sApplication == null) {
                    return null;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return sApplication;
    }
}
