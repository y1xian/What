package com.yyxnb.module_login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.arch.base.mvvm.BaseFragmentVM;
import com.yyxnb.arch.base.nav.StackModeManager;
import com.yyxnb.arch.utils.ToastUtils;
import com.yyxnb.arch.utils.log.LogUtils;
import com.yyxnb.module_login.vm.TestViewModel;

import org.jetbrains.annotations.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = "/login/LoginFragment")
public class LoginFragment extends BaseFragmentVM<TestViewModel> {

    private TextView tvTitle;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        tvTitle = findViewById(R.id.tvTitle);

        tvTitle.setOnClickListener(v -> {

//            if (mViewModel != null) {
//
//                mViewModel.reqTest();
//            }
            ToastUtils.INSTANCE.debug("onclick");
            startFragment(RegisterFragment.newInstance());
//            addFragment(this, RegisterFragment.newInstance(), null, null, StackModeManager.STANDARD);
        });

        LogUtils.INSTANCE.d("---initView--");

    }

    @Override
    public void initViewData() {
        super.initViewData();
        LogUtils.INSTANCE.d("---initViewData--");

    }

    @Override
    public void onVisible() {
        super.onVisible();
        LogUtils.INSTANCE.d("---onVisible--");

    }

    @Override
    public void onInVisible() {
        super.onInVisible();
        LogUtils.INSTANCE.d("---onInVisible--");

    }

    @Override
    public int initStatusBarColor() {
        return getResources().getColor(R.color.green);
    }

    @Override
    public void initObservable() {
        super.initObservable();

        LogUtils.INSTANCE.d("---initObservable--");

        mViewModel.reqTest();

        mViewModel.getTest().observe(this, baseData -> {

            if (baseData != null) {
                TestData data = baseData.getResult().get(0);
//                    tvShow.setText(baseDataLcee.getData().getResult().get(0).toString());
                tvTitle.setText(data.getTestInt() + " \n"
                        + data.getTestInt2() + " \n"
                        + data.getTestInt3() + " \n"
                        + data.getTestDouble() + " \n"
                        + data.getTestDouble2() + " \n"
                        + data.getTestDouble3() + " \n"
                        + data.getTestString() + " \n"
                        + data.getTestString2() + " \n"
                        + data.getTestString3() + " \n\n\n\n\n\n" + data.toString());
            }

        });
    }

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
