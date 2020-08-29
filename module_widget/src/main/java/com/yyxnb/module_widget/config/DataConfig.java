package com.yyxnb.module_widget.config;

import com.yyxnb.module_widget.bean.MainBean;
import com.yyxnb.network.utils.GsonUtils;
import com.yyxnb.utils.FileUtils;
import com.yyxnb.widget.WidgetManager;

import java.util.ArrayList;
import java.util.List;

public class DataConfig {

    public static final String SKIN_PATH = "SKIN_PATH";

    private volatile static List<MainBean> mainBeans;
    private volatile static List<MainBean> popupBeans;

    /**
     * 首页数据
     *
     * @return
     */
    public static List<MainBean> getMainBeans() {
        if (mainBeans == null) {
            String content = FileUtils.parseFile(WidgetManager.INSTANCE.getContext(), "main_data.json");
            mainBeans = GsonUtils.INSTANCE.jsonToList(content, MainBean.class);
        }
        return mainBeans;
    }

    /**
     * popup数据
     *
     * @return
     */
    public static List<MainBean> getPopupBeans() {
        if (popupBeans == null) {
            String content = FileUtils.parseFile(WidgetManager.INSTANCE.getContext(), "popup_data.json");
            popupBeans = GsonUtils.INSTANCE.jsonToList(content, MainBean.class);
        }
        return popupBeans;
    }

    public static List<String> getDialogList() {
        List<String> list = new ArrayList<>();
        list.add("loading");
        list.add("提示");
        list.add("输入框");
        list.add("中间列表");
        list.add("中间列表 带选中");
        list.add("底部列表");
        list.add("底部列表 带选中");
        list.add("全屏");
        list.add("底部弹框 注册");
        list.add("评论列表");
        list.add("底部 + vp");
        return list;
    }
}
