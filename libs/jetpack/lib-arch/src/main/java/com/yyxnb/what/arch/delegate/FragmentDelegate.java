package com.yyxnb.what.arch.delegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.yyxnb.what.arch.annotations.BarStyle;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.base.IActivity;
import com.yyxnb.what.arch.base.IFragment;
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
 * 描    述：Fragment 代理
 * ================================================
 */
public class FragmentDelegate implements ILifecycle, HandlerAction {

    public FragmentDelegate(IFragment iFragment) {
        this.iFragment = iFragment;
        this.mFragment = (Fragment) iFragment;
        mLazyDelegate = new FragmentLazyDelegate(mFragment);
    }

    private IFragment iFragment;
    private IActivity iActivity;

    private View mRootView;

    private Fragment mFragment;
    private FragmentActivity mActivity;

    private FragmentLazyDelegate mLazyDelegate;

    private static final ArchConfig config = ArchManager.getInstance().getConfig();
    private int layoutRes = 0;
    private boolean statusBarTranslucent = config.isStatusBarTranslucent();
    private boolean fitsSystemWindows = config.isFitsSystemWindows();
    private int statusBarColor = config.getStatusBarColor();
    private int statusBarDarkTheme = config.getStatusBarStyle();
    private int swipeBack = config.getSwipeBack();
    private boolean subPage;
    private boolean needLogin = config.isNeedLogin();

    public FragmentActivity getActivity() {
        return mActivity;
    }

    public void onAttach(Context context) {
        mActivity = (FragmentActivity) context;
        if (!(mActivity instanceof IActivity)) {
            throw new IllegalArgumentException("Activity请实现IActivity接口");
        }
        iActivity = (IActivity) mActivity;
    }

    public void onCreate(Bundle savedInstanceState) {
        mLazyDelegate.onCreate(savedInstanceState);
        initAttributes();
    }

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mRootView) {
            if (layoutRes != 0 || iFragment.initLayoutResId() != 0) {
                mRootView = inflater.inflate(layoutRes == 0 ? iFragment.initLayoutResId()
                        : layoutRes, container, false);
            }
        } else {
            //  二次加载删除上一个子view
            ViewGroup viewGroup = (ViewGroup) mRootView;
            viewGroup.removeView(mRootView);
        }
        mRootView.setOnTouchListener((v, event) -> {
            mActivity.onTouchEvent(event);
            return false;
        });
        return mRootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        mLazyDelegate.onActivityCreated(savedInstanceState);
        setNeedsStatusBarAppearanceUpdate();
    }

    /**
     * viewpager调用 {@link BindRes} {@link FragmentDelegate#subPage 为 true}
     */
    public void setUserVisibleHint(boolean isVisibleToUser) {
        mLazyDelegate.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * show/hide调用
     */
    public void onHiddenChanged(boolean hidden) {
        mLazyDelegate.onHiddenChanged(hidden);
    }

    /**
     * 屏幕方向发生改变时
     */
    public void onConfigurationChanged(Configuration newConfig) {
        mLazyDelegate.onConfigurationChanged(newConfig);
    }

    public void onResume() {
        mLazyDelegate.onResume();
    }

    public void onPause() {
        mLazyDelegate.onPause();
    }

    public void onDestroy() {
        removeCallbacks();
        mLazyDelegate.onDestroy();
        iFragment = null;
        iActivity = null;
        mActivity = null;
        mFragment = null;
        mRootView = null;
    }

    public void onDestroyView() {

    }

    /**
     * 加载注解设置
     */
    public void initAttributes() {
        post(() -> {
            final BindRes bindRes = iFragment.getClass().getAnnotation(BindRes.class);
            if (bindRes != null) {
                layoutRes = bindRes.layoutRes();
                fitsSystemWindows = bindRes.fitsSystemWindows();
                statusBarTranslucent = bindRes.statusBarTranslucent();
                swipeBack = bindRes.swipeBack();
                subPage = bindRes.subPage();
                if (bindRes.statusBarStyle() != BarStyle.NONE) {
                    statusBarDarkTheme = bindRes.statusBarStyle();
                }
                if (bindRes.statusBarColor() != 0) {
                    statusBarColor = bindRes.statusBarColor();
                }
                needLogin = bindRes.needLogin();
                // 如果需要登录，并且处于未登录状态下，发送通知
                if (needLogin) {
                    BusHelper.post(new MsgEvent(ArgumentKeys.NEED_LOGIN_CODE));
                }
            }
        });
    }

    /**
     * 更新状态栏样式
     */
    public void setNeedsStatusBarAppearanceUpdate() {

        // 子页面不作做处理
        if (subPage) {
            return;
        }
        // 侧滑返回
        iActivity.setSwipeBack(swipeBack);

        // 文字颜色
        int statusBarStyle = statusBarDarkTheme;
        StatusBarUtils.setStatusBarStyle(getWindow(), statusBarStyle == BarStyle.DARK_CONTENT);

        // 隐藏 or 不留空间 则透明
        if (!fitsSystemWindows) {
            StatusBarUtils.setStatusBarColor(getWindow(), Color.TRANSPARENT);
        } else {
            int statusBarColor = this.statusBarColor;

            //不为深色
            boolean shouldAdjustForWhiteStatusBar = !StatusBarUtils.isBlackColor(statusBarColor, 176);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                shouldAdjustForWhiteStatusBar = shouldAdjustForWhiteStatusBar && statusBarStyle == BarStyle.LIGHT_CONTENT;
            }
            // 如果状态栏处于白色且状态栏文字也处于白色，避免看不见
            if (shouldAdjustForWhiteStatusBar) {
                statusBarColor = config.getShouldAdjustForWhiteStatusBar();
            }

            StatusBarUtils.setStatusBarColor(getWindow(), statusBarColor);
        }
        StatusBarUtils.setStatusBarTranslucent(getWindow(), statusBarTranslucent, fitsSystemWindows);
    }

    public Window getWindow() {
        return mActivity.getWindow();
    }

    public Bundle initArguments() {
        Bundle args = mFragment.getArguments();
        if (args == null) {
            args = new Bundle();
            mFragment.setArguments(args);
        }
        return args;
    }

    public void finish() {
        mActivity.onBackPressed();
    }

    public FragmentLazyDelegate getLazyDelegate() {
        return mLazyDelegate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FragmentDelegate that = (FragmentDelegate) o;
        return iFragment.equals(that.iFragment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iFragment);
    }

}
