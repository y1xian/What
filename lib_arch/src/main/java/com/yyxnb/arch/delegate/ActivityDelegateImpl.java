package com.yyxnb.arch.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.arch.base.IActivity;
import com.yyxnb.arch.livedata.ViewModelFactory;
import com.yyxnb.arch.utils.AppManager;
import com.yyxnb.common.MainThreadUtils;

import java.lang.reflect.Field;

/**
 * ActivityLifecycleCallbacks 监听 Activity 生命周期
 * PS ：先走 ActivityLifecycleCallbacks 再走 Activity
 */
public class ActivityDelegateImpl implements IActivityDelegate {

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
            }
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
        AppManager.getInstance().getActivityDelegates().remove(iActivity.hashCode());
        this.mActivity = null;
        this.iActivity = null;
    }

    public void initDeclaredFields() {
        MainThreadUtils.post(() -> {
            Field[] declaredFields = iActivity.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                // 允许修改反射属性
                field.setAccessible(true);

                /*
                 *  根据 {@link BindViewModel } 注解, 查找注解标示的变量（ViewModel）
                 *  并且 创建 ViewModel 实例, 注入到变量中
                 */
                final BindViewModel viewModel = field.getAnnotation(BindViewModel.class);
                if (viewModel != null) {
                    try {
                        field.set(iActivity, ViewModelFactory.createViewModel(mActivity, field));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

