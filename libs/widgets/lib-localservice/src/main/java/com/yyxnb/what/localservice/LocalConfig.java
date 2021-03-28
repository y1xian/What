package com.yyxnb.what.localservice;

import java.io.Serializable;

public class LocalConfig implements Serializable {

    /**
     * 最大文件大小，int类型，默认180m
     */
    public static final String MAX_SELECT_SIZE = "max_select_size";

    public static final long DEFAULT_SELECTED_MAX_SIZE = 188743680L;

    /**
     * 图片选择模式，默认选视频和图片
     */
    public static final String SELECT_MODE = "select_mode";

    /**
     * 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合
     */
    public static final String EXTRA_RESULT = "select_result";
    /**
     * 默认选择集
     */
    public static final String DEFAULT_SELECTED_LIST = "default_list";
    /**
     * 预览集
     */
    public static final String PRE_RAW_LIST = "pre_raw_List";
    public static final int RESULT_CODE = 0x201;
    public static final int RESULT_UPDATE_CODE = 0x200;
    public static final int IMAGE = 0x100;
    public static final int VIDEO = 0x102;
    public static final int AUDIO = 0x103;
    public static final int IMAGE_VIDEO = 0x101;

    private int mMaxCount = 9; //最大选择数量
    private boolean mIsSelectSingle = false; //是否是单选 默认false

    public boolean isIsSelectSingle() {
        return mIsSelectSingle;
    }

    public LocalConfig setIsSelectSingle(boolean mIsSelectSingle) {
        this.mIsSelectSingle = mIsSelectSingle;
        return this;
    }

    public int getMaxCount() {
        return mMaxCount;
    }

    public LocalConfig setMaxCount(int maxCount) {
        this.mMaxCount = maxCount;
        return this;
    }

}
