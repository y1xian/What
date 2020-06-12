package com.yyxnb.module_user.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.arch.annotations.BarStyle;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.common.log.LogUtils;
import com.yyxnb.module_base.arouter.ARouterUtils;
import com.yyxnb.module_user.R;
import com.yyxnb.module_user.databinding.FragmentUserBinding;

import static com.yyxnb.module_base.arouter.ARouterConstant.LOGIN_FRAGMENT;
import static com.yyxnb.module_base.arouter.ARouterConstant.USER_FRAGMENT;

/**
 * 我的 - 界面.
 */
@BindRes(statusBarStyle = BarStyle.LightContent)
@Route(path = USER_FRAGMENT)
public class UserFragment extends BaseFragment {

    private FragmentUserBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_user;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        binding = getBinding();

//        tvName.setText("yyyy更新 " + StatusBarUtils.INSTANCE.getStatusBarHeight(getMContext()));
//
//        tvShow.setOnClickListener(v -> {
//            ToastUtils.INSTANCE.normal(" tvShow user 13213123213213");
//        });

//        if (null != getMRootView()){
//            SwipeExtKt.wrap(Objects.requireNonNull(getMRootView()));
//        }

        binding.clHead.setOnClickListener(v -> {
//            ARouter.getInstance().build(LOGIN_ACTIVITY).navigation();
            startFragment(ARouterUtils.navFragment(LOGIN_FRAGMENT));
        });


    }

    @Override
    public void onInVisible() {
        super.onInVisible();
        LogUtils.d("---onInVisible---");
    }

    @Override
    public void onVisible() {
        super.onVisible();
        getBaseDelegate().setNeedsStatusBarAppearanceUpdate();
        LogUtils.d("---onVisible---");
    }
}
