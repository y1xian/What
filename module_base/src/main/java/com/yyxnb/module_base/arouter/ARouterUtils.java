package com.yyxnb.module_base.arouter;


import com.alibaba.android.arouter.launcher.ARouter;
import com.yyxnb.arch.base.IFragment;

/**
 * ARouter路由跳转工具类
 */
public class ARouterUtils {

    /**
     *
     */
    public static IFragment navFragment(String path) {
        return (IFragment) ARouter.getInstance().build(path).navigation();
    }


}
