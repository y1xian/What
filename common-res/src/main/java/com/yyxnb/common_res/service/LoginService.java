package com.yyxnb.common_res.service;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/16
 * 描    述：登录模块对外提供的所有功能
 * ================================================
 */
public interface LoginService extends IARouterService {

    /**
     * 退出登录
     */
    void loginOut();

    /**
     * 是否登录
     */
    boolean isLogin();

}
