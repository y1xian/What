package com.yyxnb.dialog.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.yyxnb.dialog.R;

import java.util.Objects;

/**
 * Description: BaseDialog
 *
 * @author : yyx
 * @date ：2018/11/18
 */
public abstract class BaseDialogFragment extends AppCompatDialogFragment {

    private static final float DEFAULT_DIM_AMOUNT = 0.5f;
    private static final int DEFAULT_WH = ViewGroup.LayoutParams.WRAP_CONTENT;

    private static final String KEY_IS_CANCEL_ON_TOUCH_OUTSIDE = "keyIsCancelOnTouchOutside";
    private static final String KEY_DIM_AMOUNT = "keyDimAmount";
    private static final String KEY_HEIGHT = "keyHeight";
    private static final String KEY_WIDTH = "keyWidth";
    private static final String KEY_ANIMATION_STYLE = "keyAnimationStyle";
    private static final String KEY_IS_KEYBOARD_ENABLE = "keyIsKeyboardEnable";
    private static final String KEY_SOFT_INPUT_MODE = "keySoftInputMode";
    private static final String KEY_GRAVITY = "keyGravity";

    @LayoutRes
    protected int mLayoutRes;

    /**
     * 点击外部是否可取消
     */
    protected boolean mIsCancelOnTouchOutside = true;

    protected String mTag = "BaseDialog";

    /**
     * 阴影透明度 默认0.5f
     */
    @FloatRange(from = 0f, to = 1.0f)
    protected float mDimAmount = DEFAULT_DIM_AMOUNT;

    protected int mHeight = DEFAULT_WH;

    protected int mWidth = DEFAULT_WH;

    @StyleRes
    protected int mAnimationStyle;

    protected boolean mIsKeyboardEnable = true;

    protected int mSoftInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING;

    private int mGravity = Gravity.CENTER;

    private DialogInterface.OnCancelListener mOnCancelListener;
    private DialogInterface.OnDismissListener mOnDismissListener;

    protected FragmentActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity) {
            mActivity = (FragmentActivity) context;
        }
    }

    @Override
    public void onDestroy() {
        mActivity = null;
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置Style 透明背景，No Title
        setStyle(AppCompatDialogFragment.STYLE_NORMAL, R.style.BaseDialog);
        if (savedInstanceState != null) {
            mIsCancelOnTouchOutside = savedInstanceState.getBoolean(KEY_IS_CANCEL_ON_TOUCH_OUTSIDE, true);
            mDimAmount = savedInstanceState.getFloat(KEY_DIM_AMOUNT, DEFAULT_DIM_AMOUNT);
            mHeight = savedInstanceState.getInt(KEY_HEIGHT, 0);
            mWidth = savedInstanceState.getInt(KEY_WIDTH, 0);
            mAnimationStyle = savedInstanceState.getInt(KEY_ANIMATION_STYLE, 0);
            mIsKeyboardEnable = savedInstanceState.getBoolean(KEY_IS_KEYBOARD_ENABLE, true);
            mSoftInputMode = savedInstanceState.getInt(KEY_SOFT_INPUT_MODE, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            mGravity = savedInstanceState.getInt(KEY_GRAVITY, Gravity.CENTER);
        }
        initArguments(getArguments());
        setLayoutRes(initLayoutId());
    }

    /**
     * 在此方法中获取参数
     *
     * @param arguments
     */
    protected void initArguments(@Nullable Bundle arguments) {
        //getArguments
    }

    /**
     * 屏幕旋转时回调
     * <p>
     * 修复屏幕旋转时各种回调等数据为null
     * http://www.yrom.net/blog/2014/11/02/dialogfragment-retaining-listener-after-screen-rotation/
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_IS_CANCEL_ON_TOUCH_OUTSIDE, mIsCancelOnTouchOutside);
        outState.putFloat(KEY_DIM_AMOUNT, mDimAmount);
        outState.putInt(KEY_HEIGHT, mHeight);
        outState.putInt(KEY_WIDTH, mWidth);
        outState.putInt(KEY_ANIMATION_STYLE, mAnimationStyle);
        outState.putBoolean(KEY_IS_KEYBOARD_ENABLE, mIsKeyboardEnable);
        outState.putInt(KEY_SOFT_INPUT_MODE, mSoftInputMode);
        outState.putInt(KEY_GRAVITY, mGravity);
        super.onSaveInstanceState(outState);
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
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() == null || getDialog().getWindow() == null) {
            return;
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
    @SuppressLint("ResourceType")
    protected void initWindowLayoutParams(Window window, WindowManager.LayoutParams layoutParams) {
        if (mIsKeyboardEnable) {
            window.setSoftInputMode(mSoftInputMode);
            Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(mSoftInputMode);
        }

        if (mAnimationStyle > 0) {
            window.setWindowAnimations(mAnimationStyle);
        }

        if (mHeight > 0 || mHeight == ViewGroup.LayoutParams.MATCH_PARENT || mHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            layoutParams.height = mHeight;
        }
        if (mWidth > 0 || mWidth == ViewGroup.LayoutParams.MATCH_PARENT || mWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
            layoutParams.width = mWidth;
        }

        layoutParams.dimAmount = mDimAmount;
        layoutParams.gravity = mGravity;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mOnCancelListener != null) {
            mOnCancelListener.onCancel(dialog);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }

    /**
     * 设置dialog布局
     *
     * @return
     */
    public abstract int initLayoutId();

    /**
     * 初始化 Views
     *
     * @param view
     */
    public abstract void initViews(View view);

    /**
     * 设置布局id
     *
     * @param layoutRes
     * @return
     */
    public void setLayoutRes(@LayoutRes int layoutRes) {
        this.mLayoutRes = layoutRes;
    }

    /**
     * 判断是否显示
     *
     * @return
     */
    public boolean isShowing() {
        return getDialog() != null && getDialog().isShowing();
    }

    /**
     * 显示Dialog
     *
     * @param fragmentManager
     */
    public void show(FragmentManager fragmentManager) {
        if (!isAdded()) {
            show(fragmentManager, mTag);
        }
    }

    /**
     * 设置点击 Dialog 之外的地方是否消失
     *
     * @param isCancelOnTouchOutside
     * @return
     */
    public BaseDialogFragment setCancelOnTouchOutside(boolean isCancelOnTouchOutside) {
        this.mIsCancelOnTouchOutside = isCancelOnTouchOutside;
        return this;
    }

    /**
     * 设置显示时的 Fragment Tag
     *
     * @param tag
     * @return
     */
    public BaseDialogFragment setFragmentTag(String tag) {
        this.mTag = tag;
        return this;
    }

    public String getFragmentTag() {
        return mTag;
    }

    /**
     * 设置阴影透明度
     *
     * @param dimAmount
     * @return
     */
    public BaseDialogFragment setDimAmount(@FloatRange(from = 0f, to = 1.0f) float dimAmount) {
        this.mDimAmount = dimAmount;
        return this;
    }

    /**
     * 设置 Dialog 高度
     *
     * @param height
     * @return
     */
    public BaseDialogFragment setHeight(int height) {
        this.mHeight = height;
        return this;
    }

    /**
     * 设置 Dialog 宽度
     *
     * @param width
     * @return
     */
    public BaseDialogFragment setWidth(int width) {
        this.mWidth = width;
        return this;
    }

    /**
     * 设置 Dialog 显示动画
     *
     * @param animationStyle
     * @return
     */
    public BaseDialogFragment setAnimationStyle(@StyleRes int animationStyle) {
        this.mAnimationStyle = animationStyle;
        return this;
    }

    /**
     * 设置是否支持弹出键盘调整位置
     *
     * @param keyboardEnable
     * @return
     */
    public BaseDialogFragment setKeyboardEnable(boolean keyboardEnable) {
        this.mIsKeyboardEnable = keyboardEnable;
        return this;
    }

    /**
     * 设置弹出键盘时调整方式
     *
     * @param inputMode
     * @return
     */
    public BaseDialogFragment setSoftInputMode(int inputMode) {
        this.mSoftInputMode = inputMode;
        return this;
    }

    /**
     * 设置 Dialog 对齐方式
     *
     * @param gravity
     * @return
     */
    public BaseDialogFragment setGravity(int gravity) {
        this.mGravity = gravity;
        return this;
    }

    /**
     * 设置OnCancelListener
     *
     * @param listener
     * @return
     */
    public BaseDialogFragment setOnCancelListener(DialogInterface.OnCancelListener listener) {
        this.mOnCancelListener = listener;
        return this;
    }

    /**
     * 设置OnDismissListener
     *
     * @param listener
     * @return
     */
    public BaseDialogFragment setOnDismissListener(DialogInterface.OnDismissListener listener) {
        this.mOnDismissListener = listener;
        return this;
    }

    /**
     * 设置是否可以取消
     * （单纯的是为了链式调用）
     *
     * @param cancelable
     * @return
     */
    public BaseDialogFragment setDialogCancelable(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    /**
     * 清除匿名内部类 callback 对外部类的引用，避免可能导致的内存泄漏
     */
    public void clearRefOnDestroy() {
        //清除 onDismissListener 引用
        setOnDismissListener(null);
        //清除 onCancelListener 引用
        setOnCancelListener(null);
    }
} 