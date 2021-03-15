package com.yyxnb.module_server;

import android.os.Bundle;

import com.yyxnb.common_base.core.BaseFragment;
import com.yyxnb.module_server.databinding.FragmentServerBinding;
import com.yyxnb.util_common.ClickUtils;
import com.yyxnb.util_core.ToastUtils;
import com.yyxnb.util_permission.PermissionUtils;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/02/26
 * 描    述：Http API
 * ================================================
 */
public class ServerFragment extends BaseFragment {

    private FragmentServerBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_server;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();

        ClickUtils.click(binding.btnStatus, v -> {
            ToastUtils.normal("点我没用啊");
        });

        if (!PermissionUtils.hasPermissions(getActivity(), PermissionUtils.FILE_REQUIRE_PERMISSIONS)) {
            PermissionUtils.with(getActivity())
                    .addPermissions(PermissionUtils.FILE_REQUIRE_PERMISSIONS)
                    .defaultConfig();
        }
    }

}