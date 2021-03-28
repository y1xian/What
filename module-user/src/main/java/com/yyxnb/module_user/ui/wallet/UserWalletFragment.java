package com.yyxnb.module_user.ui.wallet;

import android.os.Bundle;

import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_user.R;
import com.yyxnb.module_user.databinding.FragmentUserWalletBinding;

/**
 * 钱包.
 */
@BindRes
public class UserWalletFragment extends BaseFragment {

    private FragmentUserWalletBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_user_wallet;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();

        binding.iTitle.vTitle.getCenterTextView().setText(R.string.wallet);
        binding.iTitle.vTitle.setBackListener(v -> finish());

    }
}