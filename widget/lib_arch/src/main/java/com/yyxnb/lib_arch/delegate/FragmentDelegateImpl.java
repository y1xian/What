package com.yyxnb.lib_arch.delegate;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.yyxnb.lib_arch.annotations.BindViewModel;
import com.yyxnb.lib_arch.base.IFragment;
import com.yyxnb.lib_arch.livedata.ViewModelFactory;
import com.yyxnb.lib_arch.common.AppManager;
import com.yyxnb.lib_widget.action.HandlerAction;

import java.lang.reflect.Field;

/**
 * FragmentLifecycleCallbacks 监听 Fragment 生命周期
 * PS ：先走 Fragment 再走 FragmentLifecycleCallbacks
 *
 * @author yyx
 */
public class FragmentDelegateImpl implements IFragmentDelegate , HandlerAction {

    private Fragment mFragment = null;
    private FragmentManager mFragmentManager = null;

    private IFragment iFragment = null;

    public FragmentDelegateImpl(Fragment fragment, FragmentManager fragmentManager) {
        this.mFragment = fragment;
        this.mFragmentManager = fragmentManager;
        this.iFragment = (IFragment) fragment;
    }

    private FragmentDelegate delegate;

    @Override
    public void onAttached(Context context) {
        delegate = iFragment.getBaseDelegate();
        if (delegate != null) {
            delegate.onAttach(context);
            mFragment.getLifecycle().addObserver(this);
        }
    }

    @Override
    public void onCreated(Bundle savedInstanceState) {
        if (delegate != null) {
            delegate.onCreate(savedInstanceState);
            mFragment.getLifecycle().addObserver(iFragment);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initDeclaredFields();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (delegate != null) {
            delegate.onActivityCreated(savedInstanceState);
        }
    }

    @Override
    public void onStarted() {
    }

    @Override
    public void onResumed() {
        if (delegate != null) {
            delegate.onResume();
        }
    }

    @Override
    public void onPaused() {
        if (delegate != null) {
            delegate.onPause();
        }
    }

    @Override
    public void onStopped() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onViewDestroyed() {
        if (delegate != null) {
            delegate.onDestroyView();
        }
    }

    @Override
    public void onDestroyed() {
        if (delegate != null) {
            delegate.onDestroy();
            delegate = null;
        }
        removeCallbacks();
        AppManager.getInstance().getFragmentDelegates().remove(iFragment.hashCode());
        this.mFragmentManager = null;
        this.mFragment = null;
        this.iFragment = null;
    }

    @Override
    public void onDetached() {
    }

    @Override
    public boolean isAdd() {
        return mFragment.isAdded();
    }

    public void initDeclaredFields() {
        post(() -> {
            Field[] declaredFields = iFragment.getClass().getDeclaredFields();
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
                        field.set(iFragment, getViewModel(field, viewModel.isActivity()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

    }

    public ViewModel getViewModel(Field field, boolean isActivity) {
        if (isActivity) {
            return ViewModelFactory.createViewModel(mFragment.getActivity(), field);
        } else {
            return ViewModelFactory.createViewModel(mFragment, field);
        }
    }
}
