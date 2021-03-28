package com.yyxnb.common_res;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.yyxnb.common_base.BaseApplication;
import com.yyxnb.common_res.weight.skin.ExtraAttrRegister;
import com.yyxnb.what.app.AppUtils;
import com.yyxnb.what.cache.KvUtils;
import com.yyxnb.what.skinloader.SkinManager;

import static com.yyxnb.common_res.constants.Constants.SKIN_PATH;


/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/12/02
 * 描    述：debug包下的代码不参与编译，仅作为独立模块运行时初始化数据
 * ================================================
 */
public class ModuleApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化阿里路由框架
        if (AppUtils.isDebug()) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);

        KvUtils.initialize(this.getApplicationContext());
//        UITask.run(() -> {
        // 换肤
        ExtraAttrRegister.init();
//        SkinConfig.DEBUG = true;
        SkinManager.get().init(getApplicationContext());
        SkinManager.get().loadSkin(KvUtils.get(SKIN_PATH, ""));
//        });

//        ImageView imageView = new ImageView(getApplicationContext());
//        imageView.setImageResource(R.drawable.ic_launcher_background);
//
//        // 悬浮窗
//        FloatWindow
//                .with(getApplicationContext())
//                .setView(imageView)
//                .setWidth(200)                               //设置控件宽高
//                .setHeight(200)
//                .setX(20)                                   //设置控件初始位置
//                .setY(Screen.HEIGHT, 0.3f)
//                .setDesktopShow(false)                        //桌面显示
//                .setFilter(true, BaseActivity.class)         // 指定界面显示
//                .setMoveType(MoveType.SLIDE, 20, 20)
//                .setMoveStyle(500, new AccelerateInterpolator())  //贴边动画时长为500ms，加速插值器
////                .setViewStateListener(mViewStateListener)    //监听悬浮控件状态改变
////                .setPermissionListener(mPermissionListener)  //监听权限申请结果
//                .build();

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
