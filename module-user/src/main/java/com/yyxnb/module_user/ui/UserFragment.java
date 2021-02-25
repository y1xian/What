package com.yyxnb.module_user.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.BaseFragment;
import com.yyxnb.common_res.arouter.ARouterUtils;
import com.yyxnb.common_res.arouter.service.impl.LoginImpl;
import com.yyxnb.lib_arch.annotations.BarStyle;
import com.yyxnb.lib_arch.annotations.BindRes;
import com.yyxnb.lib_arch.annotations.BindViewModel;
import com.yyxnb.module_user.R;
import com.yyxnb.module_user.databinding.FragmentUserBinding;
import com.yyxnb.module_user.ui.wallet.UserWalletFragment;
import com.yyxnb.module_user.viewmodel.UserViewModel;
import com.yyxnb.util_cache.KvUtils;

import static com.yyxnb.common_res.arouter.ARouterConstant.LOGIN_FRAGMENT;
import static com.yyxnb.common_res.arouter.ARouterConstant.USER_MAIN_FRAGMENT;
import static com.yyxnb.common_res.config.Constants.USER_ID;

/**
 * 我的 - 界面.
 */
@BindRes(statusBarStyle = BarStyle.LIGHT_CONTENT)
@Route(path = USER_MAIN_FRAGMENT)
public class UserFragment extends BaseFragment {

    private FragmentUserBinding binding;

    @BindViewModel
    UserViewModel mViewModel;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_user;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        binding = getBinding();

        binding.clHead.setOnClickListener(v -> {
            if (!LoginImpl.getInstance().isLogin()) {
                startFragment(ARouterUtils.navFragment(LOGIN_FRAGMENT));
            }else {
                startFragment(new UserPersonalDetailsFragment());
            }
        });
        binding.ivWallet.setOnClickListener(v -> {
            startFragment(new UserWalletFragment());
        });

        binding.rbSetUp.setOnClickListener(v -> {
            startFragment(new UserSetUpFragment());
        });

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initObservable() {

        mViewModel.getUser().observe(this, userBean -> {
            if (userBean != null) {
                LoginImpl.getInstance().updateUserInfo(userBean);
                binding.setData(userBean);
                log("u : " + userBean.toString() + " \n "+ LoginImpl.getInstance().getUserInfo().toString());
            }
            binding.setData(userBean);
        });
    }

    @Override
    public void onInVisible() {
        super.onInVisible();
        log("---onInVisible---");
    }

    @Override
    public void onVisible() {
        super.onVisible();
        getBaseDelegate().setNeedsStatusBarAppearanceUpdate();
        log("---onVisible---" + KvUtils.get(USER_ID,0));
        mViewModel.reqUserId.postValue(KvUtils.get(USER_ID,0));
    }
}
