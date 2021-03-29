package com.yyxnb.module_user.ui;

import android.os.Bundle;

import com.yyxnb.common_res.service.impl.LoginImpl;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_user.R;
import com.yyxnb.module_user.databinding.FragmentUserSetUpBinding;
import com.yyxnb.what.popup.PopupManager;

/**
 * 设置.
 */
public class UserSetUpFragment extends BaseFragment {

    private FragmentUserSetUpBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_user_set_up;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        binding = getBinding();

        binding.iTitle.vTitle.getCenterTextView().setText(R.string.set_up);
        binding.iTitle.vTitle.setBackListener(v -> finish());

        binding.tvLoginOut.setOnClickListener(v -> {
            new PopupManager.Builder(getContext())
                    .asConfirm(null, "是否确认退出登录", () -> {
                        // 确定
                        LoginImpl.getInstance().loginOut();
                        finish();
                    }, () -> {

                    }).show();

        });
    }
}