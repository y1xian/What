package com.yyxnb.module_video.config;

import com.yyxnb.common.AppConfig;
import com.yyxnb.http.utils.GsonUtils;
import com.yyxnb.module_video.bean.TikTokBean;
import com.yyxnb.utils.FileUtils;

import java.util.List;

public class DataConfig {

    private volatile static List<TikTokBean> tikTokBeans;

    /**
     * 首页数据
     * @return
     */
    public static List<TikTokBean> getTikTokBeans() {
        if (tikTokBeans == null) {
            String content = FileUtils.parseFile(AppConfig.getInstance().getContext(), "tiktok_data.json");
            tikTokBeans = GsonUtils.jsonToList(content, TikTokBean.class);
        }
        return tikTokBeans;
    }

}
