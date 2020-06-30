package com.yyxnb.module_login;


import android.os.Bundle;
import android.widget.TextView;

import com.yyxnb.common.log.LogUtils;
import com.yyxnb.common_base.base.BaseFragment;


/**
 * 注册
 */
public class RegisterFragment extends BaseFragment {

    private TextView tvShow;

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_register;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        tvShow = findViewById(R.id.tvShow);
        tvShow.setOnClickListener(v -> {
            startFragment(LoginFragment.newInstance());
        });
    }


    @Override
    public void initViewData() {
        super.initViewData();
        LogUtils.d("-RegisterFragment--initViewData--");

    }
}
