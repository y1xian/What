package com.yyxnb.what.dialog.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.FloatRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.OnLifecycleEvent;

import com.yyxnb.what.core.action.ClickAction;
import com.yyxnb.what.core.action.CoreAction;
import com.yyxnb.what.core.action.HandlerAction;
import com.yyxnb.what.core.action.ResourcesAction;
import com.yyxnb.what.dialog.R;

import java.util.Objects;

/**
 * Description: BaseDialog
 *
 * @author : yyx
 * @date ：2018/11/18
 */
public class BaseDialogFragment extends AppCompatDialogFragment implements LifecycleOwner, HandlerAction {

    private final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);

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

    public Window getWindow() {
        if (mActivity == null) {
            return null;
        }
        return mActivity.getWindow();
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
//        setLayoutRes(initLayoutId());
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
//        initViews(view);
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
//    public abstract int initLayoutId();
//
//    /**
//     * 初始化 Views
//     *
//     * @param view
//     */
//    public abstract void initViews(View view);

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

//    /**
//     * 设置 Dialog 高度
//     *
//     * @param height
//     * @return
//     */
//    public BaseDialogFragment setHeight(int height) {
//        this.mHeight = height;
//        return this;
//    }
//
//    /**
//     * 设置 Dialog 宽度
//     *
//     * @param width
//     * @return
//     */
//    public BaseDialogFragment setWidth(int width) {
//        this.mWidth = width;
//        return this;
//    }

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
//    public BaseDialogFragment setGravity(int gravity) {
//        this.mGravity = gravity;
//        return this;
//    }

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

    //////////////////////


    @Override
    public Dialog getDialog() {
        return new BaseDialog(mActivity);
    }

    /**
     * 获取 Dialog 的根布局
     */
    public View getContentView() {
        return mActivity.findViewById(Window.ID_ANDROID_CONTENT);
    }

    /**
     * 获取当前设置重心
     */
    public int getGravity() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            return params.gravity;
        }
        return Gravity.NO_GRAVITY;
    }

    /**
     * 设置宽度
     */
    public void setWidth(int width) {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = width;
            window.setAttributes(params);
        }
    }

    /**
     * 设置高度
     */
    public void setHeight(int height) {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.height = height;
            window.setAttributes(params);
        }
    }

    /**
     * 设置 Dialog 重心
     */
    public void setGravity(int gravity) {
        Window window = getWindow();
        if (window != null) {
            window.setGravity(gravity);
        }
    }

    /**
     * 设置 Dialog 的动画
     */
    public void setWindowAnimations(@StyleRes int id) {
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(id);
        }
    }

    /**
     * 获取 Dialog 的动画
     */
    public int getWindowAnimations() {
        Window window = getWindow();
        if (window != null) {
            return window.getAttributes().windowAnimations;
        }
        return BaseDialog.ANIM_DEFAULT;
    }

    /**
     * 设置背景遮盖层开关
     */
    public void setBackgroundDimEnabled(boolean enabled) {
        Window window = getWindow();
        if (window != null) {
            if (enabled) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        }
    }

    /**
     * 设置背景遮盖层的透明度（前提条件是背景遮盖层开关必须是为开启状态）
     */
    public void setBackgroundDimAmount(@FloatRange(from = 0.0, to = 1.0) float dimAmount) {
        Window window = getWindow();
        if (window != null) {
            window.setDimAmount(dimAmount);
        }
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_DESTROY)
    @Override
    public void dismiss() {
        removeCallbacks();
//        View focusView = getCurrentFocus();
//        if (focusView != null) {
//            getSystemService(InputMethodManager.class).hideSoftInputFromWindow(focusView.getWindowToken(), 0);
//        }
        super.dismiss();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycle;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static class Builder<B extends Builder> implements LifecycleOwner, CoreAction, ResourcesAction, ClickAction {

        /**
         * 上下文对象
         */
        private final Context mContext;
        /**
         * Dialog 对象
         */
        private BaseDialogFragment mDialog;
        /**
         * Dialog 布局
         */
        private View mContentView;

        /**
         * 主题样式
         */
        private int mThemeId = R.style.BaseDialogStyle;
        /**
         * 动画样式
         */
        private int mAnimStyle = BaseDialog.ANIM_DEFAULT;
        /**
         * 重心位置
         */
        private int mGravity = Gravity.NO_GRAVITY;

        /**
         * 水平偏移
         */
        private int mXOffset;
        /**
         * 垂直偏移
         */
        private int mYOffset;

        /**
         * 宽度和高度
         */
        private int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        /**
         * 背景遮盖层开关
         */
        private boolean mBackgroundDimEnabled = true;
        /**
         * 背景遮盖层透明度
         */
        private float mBackgroundDimAmount = 0.5f;

        /**
         * 是否能够被取消
         */
        private boolean mCancelable = true;
        /**
         * 点击空白是否能够取消  前提是这个对话框可以被取消
         */
        private boolean mCanceledOnTouchOutside = true;

        public Builder(Activity activity) {
            this((Context) activity);
        }

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        /**
         * 设置主题 id
         */
        public B setThemeStyle(@StyleRes int id) {
            if (isCreated()) {
                // Dialog 创建之后不能再设置主题 id
                throw new IllegalStateException("are you ok?");
            }
            mThemeId = id;
            return (B) this;
        }

        /**
         * 设置重心位置
         */
        public B setGravity(int gravity) {
            // 适配 Android 4.2 新特性，布局反方向（开发者选项 - 强制使用从右到左的布局方向）
            mGravity = gravity;
            if (isCreated()) {
                mDialog.setGravity(gravity);
            }
            return (B) this;
        }

        /**
         * 设置水平偏移
         */
        public B setXOffset(int offset) {
            mXOffset = offset;
            return (B) this;
        }

        /**
         * 设置垂直偏移
         */
        public B setYOffset(int offset) {
            mYOffset = offset;
            return (B) this;
        }

        /**
         * 设置宽度
         */
        public B setWidth(int width) {
            mWidth = width;
            if (isCreated()) {
                mDialog.setWidth(width);
            } else {
                ViewGroup.LayoutParams params = mContentView != null ? mContentView.getLayoutParams() : null;
                if (params != null) {
                    params.width = width;
                    mContentView.setLayoutParams(params);
                }
            }
            return (B) this;
        }

        /**
         * 设置高度
         */
        public B setHeight(int height) {
            mHeight = height;
            if (isCreated()) {
                mDialog.setHeight(height);
            } else {
                // 这里解释一下为什么要重新设置 LayoutParams
                // 因为如果不这样设置的话，第一次显示的时候会按照 Dialog 宽高显示
                // 但是 Layout 内容变更之后就不会按照之前的设置宽高来显示
                // 所以这里我们需要对 View 的 LayoutParams 也进行设置
                ViewGroup.LayoutParams params = mContentView != null ? mContentView.getLayoutParams() : null;
                if (params != null) {
                    params.height = height;
                    mContentView.setLayoutParams(params);
                }
            }
            return (B) this;
        }

        /**
         * 是否可以取消
         */
        public B setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            if (isCreated()) {
                mDialog.setCancelable(cancelable);
            }
            return (B) this;
        }

        /**
         * 是否可以通过点击空白区域取消
         */
        public B setCanceledOnTouchOutside(boolean cancel) {
            mCanceledOnTouchOutside = cancel;
            if (isCreated() && mCancelable) {
//                mDialog.setCanceledOnTouchOutside(cancel);
                mDialog.setCancelOnTouchOutside(cancel);
            }
            return (B) this;
        }

        /**
         * 设置动画，已经封装好几种样式，具体可见{@link com.yyxnb.what.core.action.AnimAction}类
         */
        public B setAnimStyle(@StyleRes int id) {
            mAnimStyle = id;
            if (isCreated()) {
                mDialog.setWindowAnimations(id);
            }
            return (B) this;
        }

        /**
         * 设置背景遮盖层开关
         */
        public B setBackgroundDimEnabled(boolean enabled) {
            mBackgroundDimEnabled = enabled;
            if (isCreated()) {
                mDialog.setBackgroundDimEnabled(enabled);
            }
            return (B) this;
        }

        /**
         * 设置背景遮盖层的透明度（前提条件是背景遮盖层开关必须是为开启状态）
         */
        public B setBackgroundDimAmount(@FloatRange(from = 0.0, to = 1.0) float dimAmount) {
            mBackgroundDimAmount = dimAmount;
            if (isCreated()) {
                mDialog.setBackgroundDimAmount(dimAmount);
            }
            return (B) this;
        }

        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            return null;
        }

        @Override
        public <V extends View> V findViewById(int id) {
            if (mContentView == null) {
                // 没有 setContentView 就想 findViewById ?
                throw new IllegalStateException("are you ok?");
            }
            return mContentView.findViewById(id);
        }

        @Override
        public Context getContext() {
            return mContext;
        }

        /**
         * 当前 Dialog 是否创建了
         */
        public boolean isCreated() {
            return mDialog != null;
        }

        /**
         * 当前 Dialog 是否显示了
         */
        public boolean isShowing() {
            return mDialog != null && mDialog.isShowing();
        }

    }
} 