package com.yyxnb.common_res.service;

import com.yyxnb.common_res.bean.UserVo;

/**
 * 用户模块对外提供的所有功能
 */
public interface UserService extends IARouterService{

    /**
     * 用户信息
     */
    UserVo getUserInfo();

    /**
     * 更新信息
     */
    void updateUserInfo(UserVo vo);

}
