package com.yyxnb.what.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * 图处加载类，外界唯一调用类,直持为view,notifaication,appwidget加载图片
 */
public class ImageHelper {

    private static final IImageLoader HELPER = new GlideImageLoader();

    /**
     * 图片展示
     *
     * @param url       图片地址
     * @param imageView
     */
    public static void displayImage(Object url, ImageView imageView) {
        HELPER.displayImage(url, imageView);
    }

    /**
     * 带占位，错误图
     *
     * @param url         图片地址
     * @param imageView
     * @param placeholder 占位图
     * @param error       错误图
     */
    public static void displayImage(Object url, ImageView imageView, int placeholder, int error) {
        HELPER.displayImage(url, imageView, placeholder, error);
    }

    /**
     * gif
     *
     * @param url       图片地址
     * @param imageView
     */
    public static void displayGif(Object url, ImageView imageView) {
        HELPER.displayGif(url, imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param url
     * @param imageView
     */
    public static void displayCircleImage(String url, ImageView imageView) {
        HELPER.displayCircleImage(url, imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param url
     * @param imageView
     * @param radius    圆角大小
     */
    public static void displayRoundImage(String url, ImageView imageView, int radius) {
        HELPER.displayRoundImage(url, imageView, radius);
    }

    /**
     * 加载图片指定大小
     *
     * @param context
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public static void displaySizeImage(Context context, String url, ImageView imageView, int width, int height) {
        HELPER.displaySizeImage(context, url, imageView, width, height);
    }

    /**
     * 加载本地图片文件
     *
     * @param context
     * @param file
     * @param imageView
     */
    public static void displayFileImage(Context context, File file, ImageView imageView) {
        HELPER.displayFileImage(context, file, imageView);
    }

    /**
     * 为非view加载图片
     */
    public static void displayImageForTarget(Context context, Target target, String url) {
        HELPER.displayImageForTarget(context, target, url);
    }

}
