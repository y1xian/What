package com.yyxnb.module_login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.arch.base.BaseActivity;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.arch.base.RootActivity;
import com.yyxnb.arch.base.mvvm.BaseActivityVM;
import com.yyxnb.module_login.vm.TestViewModel;

import org.jetbrains.annotations.Nullable;

import static com.yyxnb.module_base.arouter.ARouterConstant.LOGIN_ACTIVITY;

@Route(path = LOGIN_ACTIVITY)
public class LoginActivity extends RootActivity {
    @Override
    public BaseFragment baseFragment() {
        return LoginFragment.newInstance();
    }

//    @Override
//    public int initLayoutResId() {
//        return R.layout.activity_login;
//    }
//
//    @Override
//    public void initView(@Nullable Bundle savedInstanceState) {
////        setStatusBarColor(R.color.green);
//
////        setRootFragment(LoginFragment.newInstance(),R.id.fragment_content);
//    }

}
