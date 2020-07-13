package com.yyxnb.module_message.config;

import com.yyxnb.common.AppConfig;
import com.yyxnb.network.utils.GsonUtils;
import com.yyxnb.module_message.bean.MessageBean;
import com.yyxnb.utils.FileUtils;

import java.util.List;

public class DataConfig {

    private volatile static List<MessageBean> messageBeans;

    /**
     * 列表数据
     */
    public static List<MessageBean> getMessageBeans() {
        if (messageBeans == null) {
            String content = FileUtils.parseFile(AppConfig.getInstance().getContext(), "msg_data.json");
            messageBeans = GsonUtils.jsonToList(content, MessageBean.class);
        }
        return messageBeans;
    }
}
