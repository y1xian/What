package com.yyxnb.common_res.utils;


import android.app.Activity;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yyxnb.common_base.constants.ArgumentKeys;
import com.yyxnb.common_base.base.ContainerActivity;
import com.yyxnb.what.arch.base.IFragment;

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
            intent.putExtra(ArgumentKeys.FRAGMENT, targetFragment.getClass().getCanonicalName());
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
