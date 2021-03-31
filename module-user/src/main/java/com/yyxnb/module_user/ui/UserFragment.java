package com.yyxnb.module_user.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_base.event.MessageEvent;
import com.yyxnb.common_base.event.StatusEvent;
import com.yyxnb.common_res.constants.UserRouterPath;
import com.yyxnb.common_res.service.impl.LoginImpl;
import com.yyxnb.common_res.service.impl.UserImpl;
import com.yyxnb.module_user.R;
import com.yyxnb.module_user.databinding.FragmentUserBinding;
import com.yyxnb.module_user.ui.wallet.UserWalletFragment;
import com.yyxnb.module_user.viewmodel.UserViewModel;
import com.yyxnb.what.arch.annotations.BarStyle;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.annotations.BindViewModel;

/**
 * 我的 - 界面.
 */
@BindRes(statusBarStyle = BarStyle.LIGHT_CONTENT)
@Route(path = UserRouterPath.MAIN_FRAGMENT)
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

        setOnClickListener(binding.clHead, binding.ivWallet, binding.rbSetUp);

    }

    @Override
    public void onClickEvent(View v) {
        int id = v.getId();
        if (id == R.id.clHead) {
            if (!LoginImpl.getInstance().isLogin()) {
                startFragment(LoginImpl.getInstance().mainPage(getContext()));
            } else {
                startFragment(new UserPersonalDetailsFragment());
            }
        } else if (id == R.id.ivWallet) {
            startFragment(new UserWalletFragment());
        } else if (id == R.id.rbSetUp) {
            startFragment(new UserSetUpFragment());
        }
    }

    @Override
    public void initViewData() {
        mViewModel.userLiveData.reqUser();
    }

    @Override
    public void initObservable() {

        mViewModel.userLiveData.getUser().observe(this, vo -> {
            if (vo != null) {
                UserImpl.getInstance().updateUserInfo(vo);
                binding.setData(vo);
            }
            binding.setData(vo);
        });

        mViewModel.getMessageEvent().observe(this, (MessageEvent.MessageObserver) this::log);

        mViewModel.getStatusEvent().observe(this, (StatusEvent.StatusObserver) status -> {

            log("user " + status);
            switch (status) {
                case LOADING:
                    break;
                case SUCCESS:
                    break;
                case FAILURE:
                    break;
                case ERROR:
                    break;
            }
        });

    }

    @Override
    public void onInVisible() {
        super.onInVisible();
    }

    @Override
    public void onVisible() {
        super.onVisible();
        getBaseDelegate().setNeedsStatusBarAppearanceUpdate();
    }
}
