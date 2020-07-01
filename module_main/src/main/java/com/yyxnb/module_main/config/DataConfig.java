package com.yyxnb.module_main.config;

import com.yyxnb.common.AppConfig;
import com.yyxnb.common.log.LogUtils;
import com.yyxnb.http.utils.GsonUtils;
import com.yyxnb.module_main.bean.MainHomeBean;
import com.yyxnb.utils.FileUtils;

import java.util.List;

public class DataConfig {

    private volatile static List<MainHomeBean> mainBeans;

    /**
     * 首页数据
     * @return
     */
    public static List<MainHomeBean> getMainBeans() {
        if (mainBeans == null) {
            String content = FileUtils.parseFile(AppConfig.getInstance().getContext(), "main_data.json");
            mainBeans = GsonUtils.jsonToList(content, MainHomeBean.class);
        }
        LogUtils.list(mainBeans);
        return mainBeans;
    }
}