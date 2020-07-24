package com.yyxnb.dialog;

import android.graphics.drawable.Drawable;

import com.yyxnb.dialog.core.InputInfo;
import com.yyxnb.dialog.core.TextInfo;
import com.yyxnb.dialog.interfaces.DialogLifeCycleListener;


/**
 *
 */
public class DialogManager {

    public enum THEME {
        LIGHT, DARK
    }

    //是否开启模糊效果
    public static boolean isUseBlur = false;

    //开启模态化队列启动方式
    public static boolean modalDialog = false;

    //全局对话框明暗风格
    public static THEME theme = THEME.LIGHT;

    //全局提示框明暗风格
    public static THEME tipTheme = THEME.DARK;

    //全局标题文字样式
    public static TextInfo titleTextInfo;

    //全局正文文字样式
    public static TextInfo contentTextInfo;

    //全局提示文字样式
    public static TextInfo tipTextInfo;

    //全局默认按钮文字样式
    public static TextInfo buttonTextInfo;

    //全局焦点按钮文字样式
    public static TextInfo buttonPositiveTextInfo;

    //全局输入框文本样式
    public static InputInfo inputInfo;

    //全局菜单标题样式
    public static TextInfo menuTitleInfo;

    //全局菜单文字样式
    public static TextInfo menuTextInfo;

    //全局对话框背景颜色，值0时不生效
    public static int backgroundColor = 0;

    //全局对话框默认是否可以点击外围遮罩区域或返回键关闭，此开关不影响提示框（TipDialog）以及等待框（TipDialog）
    public static boolean cancelable = true;

    //全局提示框及等待框（WaitDialog、TipDialog）默认是否可以关闭
    public static boolean cancelableTipDialog = false;

    //是否允许显示日志
    public static boolean DEBUGMODE = false;

    //模糊透明度(0~255)
    public static int blurAlpha = 210;

    //允许自定义系统对话框style，注意设置此功能会导致原对话框风格和动画失效
    public static int systemDialogStyle;

    //默认取消按钮文本文字，影响BottomDialog、ShareDialog
    public static String defaultCancelButtonText;

    //全局Dialog生命周期监听器
    public static DialogLifeCycleListener dialogLifeCycleListener;

    //全局提示框背景资源，值0时不生效
    public static int tipBackgroundResId = 0;

    //对话框，全局按钮资源
    public static Drawable okButtonDrawable;
    public static Drawable cancelButtonDrawable;
    public static Drawable otherButtonDrawable;

    //输入对话框，是否自动弹出输入键盘
    public static boolean autoShowInputKeyboard = false;

    public static void init() {

    }
}
