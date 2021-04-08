package com.yyxnb.common_res.helper;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yyxnb.common_res.bean.UserVo;
import com.yyxnb.common_res.constants.UserRouterPath;
import com.yyxnb.common_res.service.UserService;
import com.yyxnb.what.arch.base.IFragment;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/16
 * 描    述：对UserService包装，业务方直接调用，无需再自己初始化service类
 * ================================================
 */
public class UserHelper {

    private static final UserService SERVICE =
            (UserService) ARouter.getInstance().build(UserRouterPath.SERVICE).navigation();

    public static void start(Context context) {
        SERVICE.start(context);
    }

    public static IFragment mainPage(Context context) {
        return SERVICE.mainPage(context);
    }

    public static IFragment showPage(Context context) {
        return SERVICE.showPage(context);
    }

    /**
     * 获取用户信息
     */
    public static UserVo getUserInfo() {
        return SERVICE.getUserInfo();
    }

    /**
     * 更新用户信息
     */
    public static void updateUserInfo(UserVo vo) {
        SERVICE.updateUserInfo(vo);
    }

}
