package com.yyxnb.common_base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.github.anzewei.parallaxbacklayout.ParallaxHelper
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import com.yyxnb.common_base.config.Constants
import com.yyxnb.common_base.module.ModuleLifecycleConfig.Companion.instance
import com.yyxnb.common_base.weight.skin.ExtraAttrRegister
import com.yyxnb.image_loader.ImageManager
import com.yyxnb.skinloader.SkinManager
import com.yyxnb.widget.WidgetManager
import me.jessyan.autosize.AutoSizeConfig

/**
 * debug包下的代码不参与编译，仅作为独立模块运行时初始化数据
 *
 * @author yyx
 */
open class DebugApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        //初始化阿里路由框架
        if (WidgetManager.isDebug) {
            ARouter.openLog() // 打印日志
            ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this)
        MMKV.initialize(this)

        ImageManager.getInstance().init(this.applicationContext)

        // 换肤
        ExtraAttrRegister.init()
        //        SkinConfig.DEBUG = true;
        SkinManager.get().init(applicationContext)
        SkinManager.get().loadSkin(MMKV.defaultMMKV().decodeString(Constants.SKIN_PATH, ""))

        // 布局
        AutoSizeConfig.getInstance() //按照宽度适配 默认true
                .setBaseOnWidth(true).isCustomFragment = true
        // 侧滑监听
        WidgetManager.getApp().registerActivityLifecycleCallbacks(ParallaxHelper.getInstance())

        //初始化组件
        instance.initModule(this)
    }

    companion object {
        //static 代码段可以防止内存泄露
        init {
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.transparent, R.color.colorText) //全局设置主题颜色
                //                layout.setEnablePureScrollMode(true);
                layout.setEnableOverScrollBounce(true)
                layout.setDragRate(0.4f)
                ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.transparent) //全局设置主题颜色
                //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter(context).setDrawableSize(20f)
            }
        }
    }
}