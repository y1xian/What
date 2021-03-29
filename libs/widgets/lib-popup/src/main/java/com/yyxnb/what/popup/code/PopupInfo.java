package com.yyxnb.what.popup.code;

import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;

import com.yyxnb.what.popup.animator.PopupAnimation;
import com.yyxnb.what.popup.animator.PopupAnimator;
import com.yyxnb.what.popup.enums.PopupPosition;
import com.yyxnb.what.popup.enums.PopupType;
import com.yyxnb.what.popup.interfaces.PopupCallback;


/**
 * Description: Popup的属性封装
 */
public class PopupInfo {
    public PopupType popupType = null; //窗体的类型
    public Boolean isDismissOnBackPressed = true;  //按返回键是否消失
    public Boolean isDismissOnTouchOutside = true; //点击外部消失
    public Boolean autoDismiss = true; //操作完毕后是否自动关闭
    public Boolean hasShadowBg = true; // 是否有半透明的背景
    public Boolean hasBlurBg = false; // 是否有高斯模糊背景
    public View atView = null; // 依附于那个View显示
    public View watchView = null; // 依附于那个View显示
    // 动画执行器，如果不指定，则会根据窗体类型popupType字段生成默认合适的动画执行器
    public PopupAnimation popupAnimation = null;
    public PopupAnimator customAnimator = null;
    public PointF touchPoint = null; // 触摸的点
    public int maxWidth; // 最大宽度
    public int maxHeight; // 最大高度
    public Boolean autoOpenSoftInput = false;//是否自动打开输入法
    public PopupCallback popupCallback;

    public ViewGroup decorView; //每个弹窗所属的DecorView
    public Boolean isMoveUpToKeyboard = true; //是否移动到软键盘上面，默认弹窗会移到软键盘上面
    public PopupPosition popupPosition = null; //弹窗出现在目标的什么位置
    public Boolean hasStatusBarShadow = false; //是否显示状态栏阴影
    public Boolean hasStatusBar = true; //是否显示状态栏
    public Boolean hasNavigationBar = true; //是否显示导航栏
    public int offsetX, offsetY;//x，y方向的偏移量
    public Boolean enableDrag = true;//是否启用拖拽
    public boolean isCenterHorizontal = false;//是否水平居中
    public boolean isRequestFocus = true; //弹窗是否强制抢占焦点
    public boolean autoFocusEditText = true; //是否让输入框自动获取焦点
    public boolean isClickThrough = false;//是否点击透传，默认弹背景点击是拦截的
    public boolean isDarkTheme = false; //是否是暗色调主题
    public boolean enableShowWhenAppBackground = false; //是否允许应用在后台的时候也能弹出弹窗
    public boolean isThreeDrag = false; //是否开启三阶拖拽
    public boolean isDestroyOnDismiss = false; //是否关闭后进行资源释放

    public View getAtView() {
        return atView;
    }

}
