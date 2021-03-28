package com.yyxnb.common_res.service.impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yyxnb.common_res.service.UserService;
import com.yyxnb.common_res.bean.UserVo;
import com.yyxnb.common_res.constants.UserRouterPath;
import com.yyxnb.what.arch.base.IFragment;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/16
 * 描    述：对UserService包装，业务方直接调用，无需再自己初始化service类
 * ================================================
 */
public class UserImpl {

    @Autowired(name = UserRouterPath.SERVICE)
    protected UserService service;

    private static volatile UserImpl mInstance = null;

    private UserImpl() {
        //初始化
        ARouter.getInstance().inject(this);
    }

    public static UserImpl getInstance() {
        if (null == mInstance) {
            synchronized (UserImpl.class) {
                if (null == mInstance) {
                    mInstance = new UserImpl();
                }
            }
        }
        return mInstance;
    }

    public void start(Context context) {
        service.start(context);
    }

    public IFragment mainPage(Context context) {
        return service.mainPage(context);
    }

    public IFragment showPage(Context context) {
        return service.showPage(context);
    }

    /**
     * 获取用户信息
     */
    public UserVo getUserInfo() {
        return service.getUserInfo();
    }

    /**
     * 更新用户信息
     */
    public void updateUserInfo(UserVo vo) {
        service.updateUserInfo(vo);
    }

}
