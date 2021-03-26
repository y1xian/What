package com.yyxnb.what.image;

import android.content.Context;
import android.widget.ImageView;

public interface IImageLoder {

    /**
     * 初始化
     *
     * @param context 上下文
     */
    void init(Context context);

    /**
     * 图片展示
     *
     * @param url       图片地址
     * @param imageView
     */
    void displayImage(Object url, ImageView imageView);

    /**
     * 带占位，错误图
     *
     * @param url         图片地址
     * @param imageView
     * @param placeholder 占位图
     * @param error       错误图
     */
    void displayImage(Object url, ImageView imageView, int placeholder, int error);

    /**
     * gif
     * @param url
     * @param imageView
     */
    void displayGif(Object url, ImageView imageView);

}
