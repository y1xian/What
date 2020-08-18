package com.yyxnb.module_chat.config;

import com.yyxnb.common.CommonManager;
import com.yyxnb.module_chat.bean.MessageBean;
import com.yyxnb.network.utils.GsonUtils;
import com.yyxnb.utils.FileUtils;

import java.util.List;

public class DataConfig {

    private volatile static List<MessageBean> messageBeans;

    /**
     * 列表数据
     */
    public static List<MessageBean> getMessageBeans() {
        if (messageBeans == null) {
            String content = FileUtils.parseFile(CommonManager.INSTANCE.getContext(), "msg_data.json");
            messageBeans = GsonUtils.INSTANCE.jsonToList(content, MessageBean.class);
        }
        return messageBeans;
    }
}
