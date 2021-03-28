package com.yyxnb.what.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.yyxnb.what.app.AppUtils;

import java.io.Serializable;

/**
 * 自定义Toast
 *
 * @author yyx
 */
public class ToastUtils implements Serializable {
    private static final @ColorInt
    int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");

    private static final String TOAST_TYPEFACE = "sans-serif-condensed";

    private static Context context = AppUtils.getApp();

    /**
     * Toast对象
     */
    private static Toast mToast = null;

    private ToastUtils() {
    }

    public static Toast normal(@NonNull String message) {
        return normal(message, Toast.LENGTH_SHORT, null);
    }

    public static Toast normal(@NonNull String message, int duration,
                               Drawable icon) {
        return custom(message, icon, -1, duration);
    }

    public static Toast custom(@NonNull String message, Drawable icon, @DrawableRes int bgRes, int duration) {
        return custom(message, icon, DEFAULT_TEXT_COLOR, bgRes, duration);
    }

    public static Toast custom(@NonNull String message, @DrawableRes int iconRes,
                               @ColorInt int textColor, @DrawableRes int bgRes, int duration) {
        return custom(message, getDrawable(context, iconRes), textColor, bgRes, duration);
    }

    /**
     * 自定义toast方法
     *
     * @param message   提示消息文本
     * @param icon      提示消息的icon,传入null代表不显示
     * @param textColor 提示消息文本颜色
     * @param bgRes     提示背景颜色
     * @param duration  显示时长
     * @return
     */
    public static Toast custom(@NonNull String message, Drawable icon, @ColorInt int textColor, @DrawableRes int bgRes, int duration) {

        if (mToast == null) {
            mToast = new Toast(context);
        } else {
            mToast.cancel();
            mToast = null;
            mToast = new Toast(context);
        }

        @SuppressLint("InflateParams")
        View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout._toast_layout, null);
        ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
        TextView toastText = toastLayout.findViewById(R.id.toast_text);

        if (bgRes != -1) {
            setBackground(toastLayout, getDrawable(context, bgRes));
        }

        if (icon == null) {
            toastIcon.setVisibility(View.GONE);
        } else {
            setBackground(toastIcon, icon);
        }

        toastText.setTextColor(textColor);
        toastText.setText(message);
        toastText.setTypeface(Typeface.create(TOAST_TYPEFACE, Typeface.NORMAL));

        mToast.setView(toastLayout);
        mToast.setDuration(duration);
        mToast.show();
        return mToast;
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static Drawable getDrawable(Context context, @DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }
}