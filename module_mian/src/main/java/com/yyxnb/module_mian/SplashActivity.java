package com.yyxnb.module_mian;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.yyxnb.arch.base.BaseActivity;



/**
 * 启动页
 *
 * @author yyx
 */
public class SplashActivity extends BaseActivity {

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        //将window的背景图设置为空
////        getWindow().setBackgroundDrawable(null);
//        super.onCreate(savedInstanceState);
////        setStatusBarTranslucent(true, true);
////        setStatusBarColor(Color.TRANSPARENT);
////        setNavigationBarHidden(true);
//    }

    @Override
    public int initLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        new Handler().postDelayed(this::inMain, 2 * 1000);
    }

    private void inMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
