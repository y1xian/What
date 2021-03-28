package com.yyxnb.what.arch.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;

import com.yyxnb.what.arch.annotations.BindDataBinding;
import com.yyxnb.what.arch.annotations.BindViewModel;
import com.yyxnb.what.arch.base.IActivity;
import com.yyxnb.what.arch.config.AppManager;
import com.yyxnb.what.arch.viewmodel.ViewModelFactory;
import com.yyxnb.what.core.action.HandlerAction;

import java.lang.reflect.Field;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/21
 * 历    史：
 * 描    述：ActivityLifecycleCallbacks 监听 Activity 生命周期
 * PS ：先走 ActivityLifecycleCallbacks 再走 Activity
 * ================================================
 */
public class ActivityDelegateImpl implements IActivityDelegate, HandlerAction {

    private FragmentActivity mActivity = null;
    private IActivity iActivity = null;

    public ActivityDelegateImpl(FragmentActivity activity) {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }

    private ActivityDelegate delegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (iActivity != null) {
            // 在界面未初始化之前调用的初始化窗口
            iActivity.initWindows();
            delegate = iActivity.getBaseDelegate();
            if (delegate != null) {
                delegate.onCreate(savedInstanceState);
                mActivity.getLifecycle().addObserver(this);
            }
            mActivity.getLifecycle().addObserver(iActivity);
            initDeclaredFields();
            iActivity.initView(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        View view = ((ViewGroup) mActivity.getWindow().getDecorView()).getChildAt(0);
        view.getViewTreeObserver().addOnWindowFocusChangeListener(hasFocus -> {
            if (delegate != null) {
                delegate.onWindowFocusChanged(hasFocus);
            }
        });
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onSaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onDestroy() {
        if (delegate != null) {
            delegate.onDestroy();
        }
        removeCallbacks();
        AppManager.getInstance().getActivityDelegates().remove(iActivity.hashCode());
        this.mActivity = null;
        this.iActivity = null;
        this.delegate = null;
    }

    public void initDeclaredFields() {
        post(() -> {
            Field[] declaredFields = iActivity.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                // 允许修改反射属性
                field.setAccessible(true);

                /*
                 *  根据 {@link BindViewModel } 注解, 查找注解标示的变量（ViewModel）
                 *  并且 创建 ViewModel 实例, 注入到变量中
                 */
                final BindViewModel viewModel = field.getAnnotation(BindViewModel.class);
                final BindDataBinding dataBinding = field.getAnnotation(BindDataBinding.class);
                if (viewModel != null) {
                    try {
                        field.set(iActivity, ViewModelFactory.createViewModel(mActivity, field));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
//                if (dataBinding != null) {
//                    try {
//                        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(mActivity, iActivity.initLayoutResId());
//                        viewDataBinding.setLifecycleOwner(mActivity);
//                        field.set(mActivity, viewDataBinding);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }else {
//
//                }
            }
        });
    }
}

