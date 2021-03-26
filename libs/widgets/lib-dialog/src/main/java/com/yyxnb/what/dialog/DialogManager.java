package com.yyxnb.what.dialog;

import android.content.Context;
import android.graphics.Color;

import com.yyxnb.what.dialog.core.DialogInfo;


/**
 * 全局弹窗的设置
 */
public class DialogManager {

    public DialogManager() {
    }

    /**
     * 全局弹窗的设置
     **/
    private static int primaryColor = Color.parseColor("#121212");
    private static int animationDuration = 350;
    public static int statusBarShadowColor = Color.parseColor("#55000000");
    private static int shadowBgColor = Color.parseColor("#9F000000");

    public static void setShadowBgColor(int color) {
        shadowBgColor = color;
    }

    public static int getShadowBgColor() {
        return shadowBgColor;
    }

    public static void setAnimationDuration(int duration) {
        if (duration >= 0) {
            animationDuration = duration;
        }
    }

    public static int getAnimationDuration() {
        return animationDuration;
    }

    public static class Builder {

        private final DialogInfo dialogInfo = new DialogInfo();
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置当操作完毕后是否自动关闭弹窗，默认为true。比如：点击Confirm弹窗的确认按钮默认是关闭弹窗，如果为false，则不关闭
         *
         * @param autoDismiss
         * @return
         */
        public Builder autoDismiss(Boolean autoDismiss) {
            this.dialogInfo.autoDismiss = autoDismiss;
            return this;
        }

        /**
         * 弹窗是否有半透明背景遮罩，默认是true
         *
         * @param hasShadowBg
         * @return
         */
        public Builder hasShadowBg(Boolean hasShadowBg) {
            this.dialogInfo.hasShadowBg = hasShadowBg;
            return this;
        }

    }

}
