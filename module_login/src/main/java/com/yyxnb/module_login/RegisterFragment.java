package com.yyxnb.module_login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.arch.utils.log.LogUtils;

import org.jetbrains.annotations.Nullable;


/**
 * A simple {@link Fragment} subclass.
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
    public void initView(@Nullable Bundle savedInstanceState) {
        LogUtils.INSTANCE.d("-RegisterFragment--initView--");

        tvShow = findViewById(R.id.tvShow);
        tvShow.setOnClickListener(v -> {
            startFragment(LoginFragment.newInstance());
        });
    }


    @Override
    public void initViewData() {
        super.initViewData();
        LogUtils.INSTANCE.d("-RegisterFragment--initViewData--");

    }
}
