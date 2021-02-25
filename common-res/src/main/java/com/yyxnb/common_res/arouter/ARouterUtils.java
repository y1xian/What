package com.yyxnb.common_res.arouter;


import android.app.Activity;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yyxnb.common_base.core.ContainerActivity;
import com.yyxnb.lib_arch.base.IFragment;
import com.yyxnb.lib_arch.common.ArchConfig;

/**
 * ARouter路由跳转工具类
 */
public class ARouterUtils {

    /**
     * 获取fragment
     */
    public static IFragment navFragment(String path) {
        return (IFragment) ARouter.getInstance().build(path).navigation();
    }

    public static void navActivity(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    public <T extends IFragment> void startFragment(Activity activity, T targetFragment) {
        try {
            Intent intent = new Intent(activity, ContainerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(ArchConfig.FRAGMENT, targetFragment.getClass().getCanonicalName());
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
