package com.yyxnb.what.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;

import java.io.File;

public interface IImageLoader {

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
     *
     * @param url       图片地址
     * @param imageView
     */
    void displayGif(Object url, ImageView imageView);

    /**
     * 加载圆形图片
     *
     * @param url
     * @param imageView
     */
    void displayCircleImage(String url, ImageView imageView);

    /**
     * 加载圆角图片
     *
     * @param url
     * @param imageView
     * @param radius    圆角大小
     */
    void displayRoundImage(String url, ImageView imageView, int radius);

    /**
     * 加载图片指定大小
     *
     * @param context
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    void displaySizeImage(Context context, String url, ImageView imageView, int width, int height);

    /**
     * 加载本地图片文件
     *
     * @param context
     * @param file
     * @param imageView
     */
    void displayFileImage(Context context, File file, ImageView imageView);

    /**
     * 为非view加载图片
     */
    void displayImageForTarget(Context context, Target target, String url);

}
