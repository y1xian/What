package com.yyxnb.common_base.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.yyxnb.common_base.constants.ArgumentKeys;
import com.yyxnb.what.arch.action.ArchAction;
import com.yyxnb.what.arch.action.BundleAction;
import com.yyxnb.what.arch.base.IFragment;
import com.yyxnb.what.arch.base.Java8Observer;
import com.yyxnb.what.arch.delegate.FragmentDelegate;
import com.yyxnb.what.core.action.AnimAction;
import com.yyxnb.what.core.action.ClickAction;
import com.yyxnb.what.core.action.HandlerAction;

import java.lang.ref.WeakReference;

/**
 * 懒加载
 *
 * @author yyx
 */
public abstract class BaseFragment extends Fragment
        implements IFragment, ArchAction, BundleAction, HandlerAction, ClickAction, AnimAction {

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
        removeCallbacks();
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
        return initArguments();
    }

    @Override
    public <V extends View> V findViewById(int id) {
        return mRootView.findViewById(id);
    }

    /**
     * 返回.
     */
    public void finish() {
        mFragmentDelegate.finish();
    }

    public <T extends IFragment> void startFragment(T targetFragment) {
        try {
            Bundle bundle = initArguments();
            Intent intent = new Intent(mActivity.get(), ContainerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(ArgumentKeys.FRAGMENT, targetFragment.getClass().getCanonicalName());
            intent.putExtra(ArgumentKeys.BUNDLE, bundle);
            mActivity.get().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int resultCode;
    private Intent result;

    public void setResultCode(int resultCode) {
        setResultCode(resultCode, null);
    }

    public void setResultCode(int resultCode, Intent result) {
        this.resultCode = resultCode;
        this.result = result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public Intent getResult() {
        return result;
    }
}
