package com.yyxnb.common_base.core;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.yyxnb.lib_arch.annotations.BindRes;
import com.yyxnb.lib_arch.base.IFragment;
import com.yyxnb.lib_arch.common.ArchConfig;

import java.lang.ref.WeakReference;

/**
 * Description: 盛装Fragment的一个容器(代理)Activity
 * 普通界面只需要编写Fragment,使用此Activity盛装,这样就不需要每个界面都在AndroidManifest中注册一遍
 *
 * @author : yyx
 * @date ：2018/6/9
 */
@BindRes(isContainer = true)
public class ContainerActivity extends BaseActivity {

    private WeakReference<Fragment> mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FrameLayout mFrameLayout = new FrameLayout(this);
        mFrameLayout.setId(android.R.id.content);
        setContentView(mFrameLayout);
    }

    public Fragment initBaseFragment() {
        return null;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void initView(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            Intent intent = getIntent();

            if (intent == null) {
                throw new RuntimeException("you must provide a page info to display");
            }

            mFragment = new WeakReference<>(initBaseFragment());

            if (mFragment.get() != null) {
                if (intent.getBundleExtra(ArchConfig.BUNDLE) != null) {
                    mFragment.get().setArguments(intent.getBundleExtra(ArchConfig.BUNDLE));
                }
                setRootFragment((IFragment) mFragment.get(), android.R.id.content);
                return;
            }

            String fragmentName = intent.getStringExtra(ArchConfig.FRAGMENT);
            if (fragmentName.isEmpty()) {
                throw new IllegalArgumentException("can not find page fragmentName");
            }

            Class<?> fragmentClass = Class.forName(fragmentName);

            Fragment fragment = (Fragment) fragmentClass.newInstance();

            if (intent.getBundleExtra(ArchConfig.BUNDLE) != null) {
                fragment.setArguments(intent.getBundleExtra(ArchConfig.BUNDLE));
            }

            setRootFragment((IFragment) fragment, android.R.id.content);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mFragment.clear();
    }
}
