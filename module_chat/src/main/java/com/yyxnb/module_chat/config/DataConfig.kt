package com.yyxnb.module_chat.config

import com.yyxnb.module_chat.bean.MessageBean
import com.yyxnb.network.utils.GsonUtils.jsonToList
import com.yyxnb.utils.FileUtils
import com.yyxnb.widget.WidgetManager.getContext

object DataConfig {
    /**
     * 列表数据
     */
    @JvmStatic
    @Volatile
    var messageBeans: List<MessageBean>? = null
        get() {
            if (field == null) {
                val content = FileUtils.parseFile(getContext(), "msg_data.json")
                field = jsonToList(content, MessageBean::class.java)
            }
            return field
        }
        private set
}