package com.yyxnb.module_user.ui;

import android.os.Bundle;

import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_base.config.UserManager;
import com.yyxnb.module_user.R;
import com.yyxnb.module_user.databinding.FragmentUserSetUpBinding;
import com.yyxnb.view.popup.Popup;

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

        binding.iTitle.mTitle.getCenterTextView().setText(R.string.set_up);
        binding.iTitle.mTitle.setBackListener(v -> finish());

        binding.tvLoginOut.setOnClickListener(v -> {
            new Popup.Builder(getContext())
                    .asConfirm(null, "是否确认退出登录", () -> {
                        // 确定
                        UserManager.getInstance().loginOut();
                        finish();
                    }, () -> {

                    }).show();

        });
    }
}