package com.yyxnb.what.arch.delegate;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.yyxnb.what.arch.annotations.BarStyle;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.base.IActivity;
import com.yyxnb.what.arch.bean.MsgEvent;
import com.yyxnb.what.arch.config.ArchConfig;
import com.yyxnb.what.arch.config.ArchManager;
import com.yyxnb.what.arch.constants.ArgumentKeys;
import com.yyxnb.what.arch.helper.BusHelper;
import com.yyxnb.what.core.action.HandlerAction;
import com.yyxnb.what.core.interfaces.ILifecycle;
import com.yyxnb.what.core.StatusBarUtils;

import java.util.Objects;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/21
 * 历    史：
 * 描    述：Activity 代理
 * ================================================
 */
public class ActivityDelegate implements ILifecycle, HandlerAction {

    public ActivityDelegate(IActivity iActivity) {
        this.iActivity = iActivity;
        this.mActivity = (FragmentActivity) iActivity;
    }

    private IActivity iActivity;
    private FragmentActivity mActivity;

    private static final ArchConfig config = ArchManager.getInstance().getConfig();
    private int layoutRes = 0;
    private boolean statusBarTranslucent = config.isStatusBarTranslucent();
    private boolean fitsSystemWindows = config.isFitsSystemWindows();
    private int statusBarColor = config.getStatusBarColor();
    private int statusBarDarkTheme = config.getStatusBarStyle();
    private boolean needLogin = config.isNeedLogin();
    private boolean isContainer;

    /**
     * 是否第一次加载
     */
    private boolean mIsFirstVisible = true;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        initAttributes();
        if (layoutRes != 0 || iActivity.initLayoutResId() != 0) {
            mActivity.setContentView(layoutRes == 0 ? iActivity.initLayoutResId() : layoutRes);
        }
        initView();
    }

    private void initView() {
        if (!isContainer) {
            // 不留空间 则透明
            if (!fitsSystemWindows) {
                StatusBarUtils.setStatusBarColor(getWindow(), Color.TRANSPARENT);
            } else {
                StatusBarUtils.setStatusBarColor(getWindow(), statusBarColor);
            }
            StatusBarUtils.setStatusBarStyle(getWindow(), statusBarDarkTheme == BarStyle.DARK_CONTENT);
            StatusBarUtils.setStatusBarTranslucent(getWindow(), statusBarTranslucent, fitsSystemWindows);
        }

    }

    private Window getWindow() {
        return mActivity.getWindow();
    }

    /**
     * 当前窗体得到或失去焦点的时候的时候调用
     *
     * @param hasFocus true 得到
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        if (mIsFirstVisible && hasFocus) {
            mIsFirstVisible = false;
            iActivity.initViewData();
            iActivity.initObservable();
        }
    }

    public void onDestroy() {
        removeCallbacks();
        mIsFirstVisible = true;
        iActivity = null;
        mActivity = null;
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    @SuppressWarnings("AlibabaAvoidNegationOperator")
    public boolean isShouldHideKeyboard(View v, MotionEvent event) {

        if ((v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 加载注解设置
     */
    public void initAttributes() {
        post(() -> {
            final BindRes bindRes = iActivity.getClass().getAnnotation(BindRes.class);
            if (bindRes != null) {
                layoutRes = bindRes.layoutRes();
                fitsSystemWindows = bindRes.fitsSystemWindows();
                statusBarTranslucent = bindRes.statusBarTranslucent();
                if (bindRes.statusBarStyle() != BarStyle.NONE) {
                    statusBarDarkTheme = bindRes.statusBarStyle();
                }
                if (bindRes.statusBarColor() != 0) {
                    statusBarColor = bindRes.statusBarColor();
                }
                needLogin = bindRes.needLogin();
                isContainer = bindRes.isContainer();
                // 如果需要登录，并且处于未登录状态下，发送通知
                if (needLogin) {
                    BusHelper.post(new MsgEvent(ArgumentKeys.NEED_LOGIN_CODE));
                }
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActivityDelegate that = (ActivityDelegate) o;
        return iActivity.equals(that.iActivity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iActivity);
    }
}
