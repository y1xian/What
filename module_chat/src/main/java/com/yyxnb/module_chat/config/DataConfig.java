package com.yyxnb.module_chat.config;

import com.yyxnb.module_chat.bean.MessageBean;
import com.yyxnb.lib_network.utils.GsonUtils;
import com.yyxnb.lib_utils.FileUtils;
import com.yyxnb.lib_widget.AppUtils;

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
