package com.yyxnb.module_chat.config;

import com.yyxnb.module_chat.bean.MessageBean;
import com.yyxnb.what.okhttp.utils.GsonUtils;
import com.yyxnb.what.file.FileUtils;
import com.yyxnb.what.app.AppUtils;

import java.util.List;

public class DataConfig {

    private volatile static List<MessageBean> messageBeans;

    /**
     * 列表数据
     */
    public static List<MessageBean> getMessageBeans() {
        if (messageBeans == null) {
            String content = FileUtils.parseFile(AppUtils.getApp(), "msg_data.json");
            messageBeans = GsonUtils.jsonToList(content, MessageBean.class);
        }
        return messageBeans;
    }
}
