package com.yyxnb.common_base.module;

import android.app.Application;

import androidx.annotation.Nullable;


/**
 * 作为组件生命周期初始化的配置类，通过反射机制，动态调用每个组件初始化逻辑
 *
 * @author yyx
 */
public class ModuleLifecycleConfig {
    //内部类，在装载该内部类时才会去创建单例对象
    private static class SingletonHolder {
        public static ModuleLifecycleConfig instance = new ModuleLifecycleConfig();
    }

    public static ModuleLifecycleConfig getInstance() {
        return SingletonHolder.instance;
    }

    private ModuleLifecycleConfig() {
    }

    //初始化组件-靠前
    public void initModule(@Nullable Application application) {
        for (String moduleInitName : ModuleLifecycleReflexs.initModuleNames) {
            try {
                Class<?> clazz = Class.forName(moduleInitName);
                IModuleInit init = (IModuleInit) clazz.newInstance();
                //调用初始化方法
                init.onCreate(application);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
