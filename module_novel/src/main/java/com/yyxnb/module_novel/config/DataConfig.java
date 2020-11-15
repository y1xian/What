package com.yyxnb.module_novel.config;

import com.yyxnb.module_novel.bean.BookInfoBean;
import com.yyxnb.lib_network.utils.GsonUtils;
import com.yyxnb.utils.FileUtils;
import com.yyxnb.lib_widget.AppUtils;

import java.util.List;

public class DataConfig {


    private volatile static List<BookInfoBean> mainBeans;

    /**
     * 首页数据
     *
     * @return
     */
    public static List<BookInfoBean> getMainBeans() {
        if (mainBeans == null) {
            String content = FileUtils.parseFile(AppUtils.getApp(), "novel_data.json");
            mainBeans = GsonUtils.jsonToList(content, BookInfoBean.class);
        }
        return mainBeans;
    }


}
