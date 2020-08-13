package com.yyxnb.module_user.ui;


import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tencent.mmkv.MMKV;
import com.yyxnb.arch.annotations.BarStyle;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.common.utils.log.LogUtils;
import com.yyxnb.common_base.arouter.ARouterUtils;
import com.yyxnb.common_base.arouter.service.impl.LoginImpl;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_base.config.BaseConfig;
import com.yyxnb.module_user.R;
import com.yyxnb.module_user.databinding.FragmentUserBinding;
import com.yyxnb.module_user.ui.wallet.UserWalletFragment;
import com.yyxnb.module_user.viewmodel.UserViewModel;

import static com.yyxnb.common_base.arouter.ARouterConstant.LOGIN_FRAGMENT;
import static com.yyxnb.common_base.arouter.ARouterConstant.USER_FRAGMENT;
import static com.yyxnb.common_base.config.Constants.USER_ID;

/**
 * 我的 - 界面.
 */
@BindRes(statusBarStyle = BarStyle.LIGHT_CONTENT)
@Route(path = USER_FRAGMENT)
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
                LogUtils.e("u : " + userBean.toString() + " \n "+ LoginImpl.getInstance().getUserInfo().toString());
            }
            binding.setData(userBean);
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
        LogUtils.d("---onVisible---" + MMKV.defaultMMKV().decodeInt(USER_ID,0));
        mViewModel.reqUserId.postValue(BaseConfig.getInstance().kv.decodeInt(USER_ID,0));
    }
}
