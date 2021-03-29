package com.yyxnb.module_widget.config;

import com.yyxnb.module_widget.bean.MainBean;
import com.yyxnb.what.okhttp.utils.GsonUtils;
import com.yyxnb.what.file.FileUtils;
import com.yyxnb.what.app.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class DataConfig {

    public static final String SKIN_PATH = "SKIN_PATH";

    private volatile static List<MainBean> toolsBeans;
    private volatile static List<MainBean> functionBeans;
    private volatile static List<MainBean> systemBeans;
    private volatile static List<MainBean> popupBeans;
    private volatile static List<MainBean> dialogBeans;

    /**
     * 控件数据
     *
     * @return
     */
    public static List<MainBean> getToolsBeans() {
        if (toolsBeans == null) {
            String content = FileUtils.parseFile(AppUtils.getApp(), "widget_tool_data.json");
            toolsBeans = GsonUtils.jsonToList(content, MainBean.class);
        }
        return toolsBeans;
    }

    /**
     * 功能数据
     *
     * @return
     */
    public static List<MainBean> getFunctionBeans() {
        if (functionBeans == null) {
            String content = FileUtils.parseFile(AppUtils.getApp(), "widget_function_data.json");
            functionBeans = GsonUtils.jsonToList(content, MainBean.class);
        }
        return functionBeans;
    }

    /**
     * 系统数据
     *
     * @return
     */
    public static List<MainBean> getSystemBeans() {
        if (systemBeans == null) {
            String content = FileUtils.parseFile(AppUtils.getApp(), "widget_system_data.json");
            systemBeans = GsonUtils.jsonToList(content, MainBean.class);
        }
        return systemBeans;
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
