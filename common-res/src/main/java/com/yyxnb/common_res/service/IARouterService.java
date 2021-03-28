package com.yyxnb.common_res.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.yyxnb.what.arch.base.IFragment;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/16
 * 描    述：IARouterService
 * ================================================
 */
public interface IARouterService extends IProvider {

    /**
     * 跳转页面
     *
     * @param context
     */
    void start(Context context);

    /**
     * 主页面
     *
     * @param context
     * @return
     */
    IFragment mainPage(Context context);

    /**
     * 对外展示页面
     *
     * @param context
     * @return
     */
    IFragment showPage(Context context);
}
