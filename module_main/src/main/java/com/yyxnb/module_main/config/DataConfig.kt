package com.yyxnb.module_main.config

import com.yyxnb.common.CommonManager
import com.yyxnb.common.utils.log.LogUtils.list
import com.yyxnb.module_main.bean.MainHomeBean
import com.yyxnb.network.utils.GsonUtils.jsonToList
import com.yyxnb.utils.FileUtils

object DataConfig {
    /**
     * 首页数据
     * @return
     */
    @JvmStatic
    @Volatile
    var mainBeans: List<MainHomeBean>? = null
        get() {
            if (field == null) {
                val content = FileUtils.parseFile(CommonManager.getContext(), "main_data.json")
                field = jsonToList(content, MainHomeBean::class.java)
            }
            list(field)
            return field
        }
        private set
}