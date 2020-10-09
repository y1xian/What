package com.yyxnb.module_widget.config;

import com.yyxnb.module_widget.bean.MainBean;
import com.yyxnb.network.utils.GsonUtils;
import com.yyxnb.utils.FileUtils;
import com.yyxnb.widget.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class DataConfig {

    public static final String SKIN_PATH = "SKIN_PATH";

    private volatile static List<MainBean> mainBeans;
    private volatile static List<MainBean> popupBeans;
    private volatile static List<MainBean> dialogBeans;

    /**
     * 首页数据
     *
     * @return
     */
    public static List<MainBean> getMainBeans() {
        if (mainBeans == null) {
            String content = FileUtils.parseFile(AppUtils.getApp(), "widget_main_data.json");
            mainBeans = GsonUtils.jsonToList(content, MainBean.class);
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
            String content = FileUtils.parseFile(AppUtils.getApp(), "popup_data.json");
            popupBeans = GsonUtils.jsonToList(content, MainBean.class);
        }
        return popupBeans;
    }

    /**
     * dialog数据
     *
     * @return
     */
    public static List<MainBean> getDialogBeans() {
        if (dialogBeans == null) {
            String content = FileUtils.parseFile(AppUtils.getApp(), "dialog_data.json");
            dialogBeans = GsonUtils.jsonToList(content, MainBean.class);
        }
        return dialogBeans;
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
