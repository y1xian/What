package com.yyxnb.dialog.core;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.yyxnb.common.utils.DpUtils;
import com.yyxnb.dialog.R;

import java.util.Objects;


/**
 * Description: BaseSheetDialog
 *
 * @author : yyx
 * @date ：2018/11/18
 */
public abstract class BaseSheetDialog extends BaseDialog {

    private static final int DEFAULT_WH = ViewGroup.LayoutParams.MATCH_PARENT;

    /**
     * 顶部向下偏移量
     */
    private int mTopOffset = 0;
    /**
     * 折叠的高度
     */
    private int mPeekHeight = 0;
    /**
     * 默认折叠高度 屏幕60%
     */
    @FloatRange(from = 0f, to = 1.0f)
    private float mDefaultHeight = 0.6f;

    private boolean mIsTransparent = true;

    private boolean mIsBehavior = true;

    /**
     * 初始为展开状态
     */
    private int mState = ViewPagerBottomSheetBehavior.STATE_EXPANDED;

    private ViewPagerBottomSheetBehavior<FrameLayout> mBehavior;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //解决ViewPager + Fragment 无法滑动问题
        return new ViewPagerBottomSheetDialog(Objects.requireNonNull(getContext()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutRes(initLayoutId());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(mLayoutRes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        //这样设置高度才会正确展示
        view.setLayoutParams(new ViewGroup.LayoutParams(DEFAULT_WH, mHeight));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null || getDialog().getWindow() == null) {
            return;
        }
        AppCompatDialog dialog = (AppCompatDialog) getDialog();
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(R.id.design_bottom_sheet);
        if (bottomSheet != null && mIsBehavior) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            mBehavior = ViewPagerBottomSheetBehavior.from(bottomSheet);
            if (mHeight <= 0) {
                //高度 = 屏幕高度 - 顶部向下偏移量
                layoutParams.height = DpUtils.getScreenHeight(mActivity) - mTopOffset;
                //如果顶部向下偏移量为0
                if (mTopOffset == 0) {
                    //如果默认高度为0
                    if (mPeekHeight == 0) {
                        //则 默认高度60% 且为折叠状态
                        mPeekHeight = (int) (DpUtils.getScreenHeight(mActivity) * mDefaultHeight);
                        //折叠
                        mState = BottomSheetBehavior.STATE_COLLAPSED;
                    }
                } else {
                    //如果顶部向下偏移量不为0 ，则为高度 = 屏幕高度 - 顶部向下偏移量
                    mPeekHeight = layoutParams.height;
                }
            }
            mBehavior.setPeekHeight(mHeight > 0 ? mHeight : mPeekHeight);
            // 初始为展开
            mBehavior.setState(mState);
        }
        if (mIsTransparent) {
            assert bottomSheet != null;
            bottomSheet.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        getDialog().setCanceledOnTouchOutside(mIsCancelOnTouchOutside);
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        initWindowLayoutParams(window, layoutParams);
        window.setAttributes(layoutParams);
    }

    /**
     * 初始化 Window 和 LayoutParams 参数
     *
     * @param window
     * @param layoutParams
     */
    @Override
    @SuppressLint("ResourceType")
    protected void initWindowLayoutParams(Window window, WindowManager.LayoutParams layoutParams) {

        if (mIsKeyboardEnable) {
            window.setSoftInputMode(mSoftInputMode);
            Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(mSoftInputMode);
        }

        if (mAnimationStyle > 0) {
            window.setWindowAnimations(mAnimationStyle);
        }

        //这样设置高度会发生布局错误
//        if (mHeight > 0 || mHeight == ViewGroup.LayoutParams.MATCH_PARENT || mHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
//            layoutParams.height = mHeight;
//        }

//        if (mWidth > 0 || mWidth == ViewGroup.LayoutParams.MATCH_PARENT || mWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
//            layoutParams.width = mWidth;
//        }
        if (mWidth > 0) {
            layoutParams.width = mWidth;
        }

        layoutParams.dimAmount = mDimAmount;
    }

    /**
     * 设置背景是否透明
     *
     * @param mIsTransparent
     * @return
     */
    public BaseSheetDialog setIsTransparent(boolean mIsTransparent) {
        this.mIsTransparent = mIsTransparent;
        return this;
    }

    /**
     * 设置是否使用Behavior
     *
     * @param mIsBehavior
     * @return
     */
    public BaseSheetDialog setIsBehavior(boolean mIsBehavior) {
        this.mIsBehavior = mIsBehavior;
        return this;
    }

    /**
     * 设置顶部向下偏移量
     *
     * @param mTopOffset
     * @return
     */
    public BaseSheetDialog setTopOffset(int mTopOffset) {
        this.mTopOffset = mTopOffset;
        return this;
    }

    /**
     * 设置折叠高度
     *
     * @param mPeekHeight
     * @return
     */
    public BaseSheetDialog setPeekHeight(int mPeekHeight) {
        this.mPeekHeight = mPeekHeight;
        return this;
    }

    /**
     * 设置默认高度
     *
     * @param mDefaultHeight
     * @return
     */
    public BaseSheetDialog setDefaultHeight(@FloatRange(from = 0f, to = 1.0f) float mDefaultHeight) {
        this.mDefaultHeight = mDefaultHeight;
        return this;
    }

    /**
     * 设置状态
     *
     * @param mState
     * @return
     */
    public BaseSheetDialog setState(int mState) {
        this.mState = mState;
        return this;
    }

    public ViewPagerBottomSheetBehavior<FrameLayout> getBehavior() {
        return mBehavior;
    }

}