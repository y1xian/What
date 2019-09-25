package com.yyxnb.module_base;


import com.alibaba.android.arouter.launcher.ARouter;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.mmkv.MMKV;
import com.yyxnb.arch.BuildConfig;
import com.yyxnb.arch.base.BaseApplication;
import com.yyxnb.http.RetrofitManager;
import com.yyxnb.http.config.OkHttpConfig;
import com.yyxnb.module_base.module.ModuleLifecycleConfig;

import okhttp3.OkHttpClient;

import static com.yyxnb.module_base.BaseAPI.BASE_URL;

/**
 * debug包下的代码不参与编译，仅作为独立模块运行时初始化数据
 *
 * @author yyx
 */

public class DebugApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化组件
        ModuleLifecycleConfig.getInstance().initModule(this);

        MMKV.initialize(getApplicationContext());

        initRxHttp();

//        SmartSwipeBack.activitySlidingBack(this, null);

        //开启打印日志
        //初始化阿里路由框架
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    //网络
    private void initRxHttp() {

        RetrofitManager.INSTANCE
                .init(this)
                .setBaseUrl(BASE_URL)
//                .setHeaderPriorityEnable(true)
//                .putHeaderBaseUrl("", "")
                .setOkClient(mClient);

    }

    private OkHttpClient mClient = new OkHttpConfig.Builder()
            //全局的请求头信息
//            .setHeaders(new HashMap<>())
            //开启缓存策略(默认false)
            //1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
            //2、在没有网络的时候，去读缓存中的数据。
            .setCache(true)
            //全局持久话cookie,保存本地每次都会携带在header中（默认false）
            .setSaveCookie(true)
            //可以添加自己的拦截器(比如使用自己熟悉三方的缓存库等等)
//            .setAddInterceptor(new RetryInterceptor.Builder()
//                    .executionCount(1).retryInterval(888)
//                    .build())
            //全局ssl证书认证
            //1、信任所有证书,不安全有风险（默认信任所有证书）
            .setSslSocketFactory()
            //2、使用预埋证书，校验服务端证书（自签名证书）
//            .setSslSocketFactory(cerInputStream)
            //3、使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
            //.setSslSocketFactory(bksInputStream,"123456",cerInputStream)
            //全局超时配置
            .setTimeout(8)
            //全局是否打开请求log日志
            .setLogEnable(true)
            .build();
}
