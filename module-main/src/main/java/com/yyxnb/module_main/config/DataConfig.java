package com.yyxnb.module_main.config;

import com.yyxnb.what.app.AppUtils;
import com.yyxnb.what.core.log.LogUtils;
import com.yyxnb.what.okhttp.utils.GsonUtils;
import com.yyxnb.module_main.bean.MainHomeBean;
import com.yyxnb.what.file.FileUtils;

import java.util.List;

public class DataConfig {

    private volatile static List<MainHomeBean> mainHomeBeans;
    private volatile static List<MainHomeBean> mainBeans;

    /**
     * 首页列表数据
     *
     * @return
     */
    public static List<MainHomeBean> getHomeListBeans() {
        if (mainHomeBeans == null) {
            String content = FileUtils.parseFile(AppUtils.getApp(), "main_home_data.json");
            mainHomeBeans = GsonUtils.jsonToList(content, MainHomeBean.class);
        }
        LogUtils.list(mainHomeBeans);
        return mainHomeBeans;
    }

    /**
     * 首页数据 test
     *
     * @return
     */
    public static List<MainHomeBean> getMainBeans() {
        if (mainBeans == null) {
            String content = FileUtils.parseFile(AppUtils.getApp(), "main_data.json");
            mainBeans = GsonUtils.jsonToList(content, MainHomeBean.class);
        }
        LogUtils.list(mainBeans);
        return mainBeans;
    }
}
