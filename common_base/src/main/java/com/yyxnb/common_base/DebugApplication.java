package com.yyxnb.common_base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.mmkv.MMKV;
import com.yyxnb.common.CommonManager;
import com.yyxnb.common_base.module.ModuleLifecycleConfig;
import com.yyxnb.common_base.weight.skin.ExtraAttrRegister;
import com.yyxnb.image_loader.ImageManager;
import com.yyxnb.skinloader.SkinManager;

import me.jessyan.autosize.AutoSizeConfig;

import static com.yyxnb.common_base.config.Constants.SKIN_PATH;

/**
 * debug包下的代码不参与编译，仅作为独立模块运行时初始化数据
 *
 * @author yyx
 */

public class DebugApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化阿里路由框架
        if (CommonManager.getInstance().isDebug()) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);

        MMKV.initialize(this);

        ImageManager.getInstance().init(this.getApplicationContext());

        // 换肤
        ExtraAttrRegister.init();
//        SkinConfig.DEBUG = true;
        SkinManager.get().init(getApplicationContext());
        SkinManager.get().loadSkin(MMKV.defaultMMKV().decodeString(SKIN_PATH, ""));

        // 布局
        AutoSizeConfig.getInstance()
                //按照宽度适配 默认true
                .setBaseOnWidth(true)
                //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
                //如果没有这个需求建议不开启
                .setCustomFragment(true);
        // 侧滑监听
        CommonManager.getInstance().getApp().registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());

        //初始化组件
        ModuleLifecycleConfig.getInstance().initModule(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.transparent, R.color.colorText);//全局设置主题颜色
//                layout.setEnablePureScrollMode(true);
                layout.setEnableOverScrollBounce(true);
                layout.setDragRate(0.4f);
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.transparent);//全局设置主题颜色
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

}
