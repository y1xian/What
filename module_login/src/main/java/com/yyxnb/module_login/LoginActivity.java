package com.yyxnb.module_login;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.arch.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

import static com.yyxnb.module_base.arouter.ARouterConstant.LOGIN_ACTIVITY;

@Route(path = LOGIN_ACTIVITY)
public class LoginActivity extends BaseActivity {

    @Override
    public int initLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        setStatusBarColor(R.color.gray);

        findViewById(R.id.tvTitle).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }

}
