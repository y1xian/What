package com.yyxnb.common_base.module;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;


/**
 * 作为组件生命周期初始化的配置类，通过反射机制，动态调用每个组件初始化逻辑
 *
 * @author yyx
 */
public class ModuleLifecycleConfig {

    private static volatile ModuleLifecycleConfig mInstance = null;
    private List<IModuleInit> moduleInits = new ArrayList<>();

    private ModuleLifecycleConfig() {
    }

    public static ModuleLifecycleConfig getInstance() {
        if (null == mInstance) {
            synchronized (ModuleLifecycleConfig.class) {
                if (null == mInstance) {
                    mInstance = new ModuleLifecycleConfig();
                }
            }
        }
        return mInstance;
    }

    //初始化组件
    public void initModule(Context base) {
        for (String moduleInitName : ModuleLifecycleReflexs.initModuleNames) {
            try {
                Class<?> clazz = Class.forName(moduleInitName);
                IModuleInit init = (IModuleInit) clazz.newInstance();
                //调用初始化方法
                init.attachBaseContext(base);
                moduleInits.add(init);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void onCreate() {
        for (IModuleInit init : moduleInits) {
            init.onCreate();
        }
    }

    public void onTerminate() {
        for (IModuleInit init : moduleInits) {
            init.onTerminate();
        }

    }

    public void onLowMemory() {
        for (IModuleInit init : moduleInits) {
            init.onLowMemory();
        }
    }

    public void onTrimMemory(int level) {
        for (IModuleInit init : moduleInits) {
            init.onTrimMemory(level);
        }
    }

    public void onDestroy() {
        for (IModuleInit init : moduleInits) {
            init.onDestroy();
        }
        moduleInits.clear();
        moduleInits = null;
    }


}
