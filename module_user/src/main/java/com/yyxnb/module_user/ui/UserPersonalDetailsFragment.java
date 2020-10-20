package com.yyxnb.module_user.ui;

import android.os.Bundle;

import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.common_base.arouter.service.impl.LoginImpl;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_user.R;
import com.yyxnb.module_user.databinding.FragmentUserPersonalDetailsBinding;
import com.yyxnb.module_user.viewmodel.UserViewModel;

/**
 * 个人详情页.
 */
public class UserPersonalDetailsFragment extends BaseFragment {

    private FragmentUserPersonalDetailsBinding binding;

    @BindViewModel
    UserViewModel mViewModel;


    @Override
    public int initLayoutResId() {
        return R.layout.fragment_user_personal_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        binding.iTitle.vTitle.getCenterTextView().setText(R.string.personal_details);
        binding.iTitle.vTitle.setBackListener(v -> finish());

    }

    @Override
    public void initViewData() {
        mViewModel.reqUserId.postValue(LoginImpl.getInstance().getUserInfo().userId);
        mViewModel.getUser().observe(this, userBean -> {
            if (userBean != null) {
                LoginImpl.getInstance().updateUserInfo(userBean);
                binding.setData(userBean);
            }
            binding.setData(userBean);
        });
    }
}