package com.yyxnb.arch

import android.app.Application
import android.arch.lifecycle.ProcessLifecycleOwner
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.support.multidex.MultiDex
import com.github.anzewei.parallaxbacklayout.ParallaxHelper
import com.yyxnb.arch.base.ManagerActivityLifecycleCallbacksImplI
import com.yyxnb.arch.utils.log.LogUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.jessyan.autosize.AutoSizeConfig

/**
 * 使用ContentProvider初始化三方库
 */
class LibraryInitializer : ContentProvider() {

    /**
     * ContentProvider 的 onCreate() 是介于 Application 的 attachBaseContext(Context) 和 onCreate() 之间所调用的，
     * Application 的 attachBaseContext(Context) 方法被调用这就意味着 Application 的 Context 被初始化了，
     * 而 ContentProvider 拿到的 Context 也正就是 Application，所以可以在 ContentProvider 的 onCreate() 方法中完成相应的初始化操作 。
     */
    override fun onCreate(): Boolean {
        // 可以拿到ApplicationContext进行有些初始化操作
        GlobalScope.launch {
            (context?.applicationContext as Application).let {

                //突破65535的限制
                MultiDex.install(it)

                Arch.init(it)

                AutoSizeConfig.getInstance().isCustomFragment = true

                //系统会在每个 Activity 执行完对应的生命周期后都调用这个实现类中对应的方法
                it.registerActivityLifecycleCallbacks(ParallaxHelper.getInstance())
                it.registerActivityLifecycleCallbacks(ManagerActivityLifecycleCallbacksImplI())
                ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifeObserver())

                LogUtils.init()
                        .setTag("Arch")//设置全局tag
                        .setShowThreadInfo(true).setDebug(Arch.isDebug) //是否显示日志，默认true，发布时最好关闭
            }
        }

        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
            uri: Uri,
            projection: Array<String>?,
            selection: String?,
            selectionArgs: Array<String>?,
            sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

}