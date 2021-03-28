package com.yyxnb.module_user.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_res.utils.ARouterUtils;
import com.yyxnb.common_res.service.UserService;
import com.yyxnb.common_res.bean.UserVo;
import com.yyxnb.common_res.constants.UserRouterPath;
import com.yyxnb.what.arch.base.IFragment;
import com.yyxnb.module_user.config.UserManager;

@Route(path = UserRouterPath.SERVICE)
public class UserServiceImpl implements UserService {

    @Override
    public UserVo getUserInfo() {
        return UserManager.getInstance().getUserInfo();
    }

    @Override
    public void updateUserInfo(UserVo vo) {
        UserManager.getInstance().setUserBean(vo);
    }

    @Override
    public void start(Context context) {
        ARouterUtils.navActivity(UserRouterPath.MAIN_ACTIVITY);
    }

    @Override
    public IFragment mainPage(Context context) {
        return ARouterUtils.navFragment(UserRouterPath.MAIN_FRAGMENT);
    }

    @Override
    public IFragment showPage(Context context) {
        return ARouterUtils.navFragment(UserRouterPath.SHOW_FRAGMENT);
    }

    @Override
    public void init(Context context) {

    }
}
