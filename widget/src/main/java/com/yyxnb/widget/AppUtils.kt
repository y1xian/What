package com.yyxnb.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.util.Log
import com.yyxnb.widget.AppGlobals.application
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType

/**
 * @author yyx
 */
object AppUtils {
    @SuppressLint("StaticFieldLeak")
    private var sApp: Application? = null

    fun init(app: Application?) {
        if (app == null) {
            Log.e("AppUtils", "app is null.")
            return
        }
        if (sApp == null) {
            sApp = app
            WidgetManager.init(sApp)
            return
        }
        if (sApp == app) {
            return
        }
        WidgetManager.unInit(sApp)
        sApp = app
        WidgetManager.init(sApp)
    }

    val app: Application
        get() {
            if (sApp != null) {
                return sApp!!
            }
            sApp = application
            if (sApp == null) {
                throw NullPointerException("application反射获取失败")
            }
            return sApp!!
        }

    fun isActivityAlive(activity: Activity): Boolean {
        return !activity.isFinishing && !activity.isDestroyed
    }

    /**
     * 判断当前应用是否是debug状态
     */
    val isDebug: Boolean
        get() = try {
            val info = app.applicationInfo
            info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        } catch (e: Exception) {
            false
        }


    /**
     * 返回实例的泛型类型
     */
    fun <T> getInstance(t: Any?, i: Int): T? {
        if (t != null) {
            try {
                return (t.javaClass.genericSuperclass as ParameterizedType)
                        .actualTypeArguments[i] as T
            } catch (e: ClassCastException) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun <T> getClass(t: Any): Class<T> {
        // 通过反射 获取当前类的父类的泛型 (T) 对应 Class类
        return (t.javaClass.genericSuperclass as ParameterizedType)
                .actualTypeArguments[0] as Class<T>
    }

    fun <T> getFiledClazz(field: Field): Class<T> {
        return field.genericType as Class<T>
    }


    open class ActivityLifecycleCallbacks {
        open fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) { /**/
        }

        open fun onActivityStarted(activity: Activity?) { /**/
        }

        open fun onActivityResumed(activity: Activity?) { /**/
        }

        open fun onActivityPaused(activity: Activity?) { /**/
        }

        open fun onActivityStopped(activity: Activity?) { /**/
        }

        open fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) { /**/
        }

        open fun onActivityDestroyed(activity: Activity?) { /**/
        }
    }
}