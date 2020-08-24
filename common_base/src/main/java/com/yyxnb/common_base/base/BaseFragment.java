package com.yyxnb.common_base.base;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyxnb.arch.action.ArchAction;
import com.yyxnb.arch.action.BundleAction;
import com.yyxnb.arch.base.IFragment;
import com.yyxnb.arch.base.Java8Observer;
import com.yyxnb.arch.common.ArchConfig;
import com.yyxnb.arch.delegate.FragmentDelegate;
import com.yyxnb.widget.action.HandlerAction;

import java.lang.ref.WeakReference;
import java.util.UUID;

/**
 * 懒加载
 *
 * @author yyx
 */
public abstract class BaseFragment extends Fragment
        implements IFragment, ArchAction, BundleAction, HandlerAction {

    protected final String TAG = getClass().getCanonicalName();
    private FragmentDelegate mFragmentDelegate = getBaseDelegate();

    protected WeakReference<Context> mContext;
    protected WeakReference<AppCompatActivity> mActivity;
    protected View mRootView;

    private Java8Observer java8Observer;

    @Nullable
    @Override
    public Context getContext() {
        return mContext.get();
    }


    public BaseFragment() {
        java8Observer = new Java8Observer(TAG);
        getLifecycle().addObserver(java8Observer);
        getLifecycle().addObserver(mFragmentDelegate);
    }

    public <B extends ViewDataBinding> B getBinding() {
        DataBindingUtil.bind(mRootView);
        return DataBindingUtil.getBinding(mRootView);
    }

    @Override
    public String sceneId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Bundle initArguments() {
        return mFragmentDelegate.initArguments();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        requireActivity().getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                mContext = new WeakReference<>(context);
                mActivity = new WeakReference<>((AppCompatActivity) getContext());
                owner.getLifecycle().removeObserver(this);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootView = mFragmentDelegate.onCreateView(inflater, container, savedInstanceState);
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mFragmentDelegate.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mFragmentDelegate.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mFragmentDelegate.onConfigurationChanged(newConfig);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragmentDelegate = null;
        mContext.clear();
        mContext = null;
        mRootView = null;
        mActivity.clear();
        mActivity = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getLifecycle().removeObserver(java8Observer);
    }

    @Nullable
    @Override
    public Bundle getBundle() {
        return getArguments();
    }

    @SuppressWarnings("unchecked")
    public <T> T findViewById(@IdRes int resId) {
        return (T) mRootView.findViewById(resId);
    }

    /**
     * 返回.
     */
    public void finish() {
        mFragmentDelegate.finish();
    }

    public <T extends IFragment> void startFragment(T targetFragment) {
        startFragment(targetFragment, 0);
    }

    public <T extends IFragment> void startFragment(T targetFragment, int requestCode) {
        try {
            Bundle bundle = initArguments();
            Intent intent = new Intent(mActivity.get(), ContainerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(ArchConfig.FRAGMENT, targetFragment.getClass().getCanonicalName());
            bundle.putInt(ArchConfig.REQUEST_CODE, requestCode);
            intent.putExtra(ArchConfig.BUNDLE, bundle);
            mActivity.get().startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
