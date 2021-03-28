package com.yyxnb.module_main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yyxnb.module_main.R;
import com.yyxnb.what.arch.base.IActivity;

/**
 * 启动页
 *
 * @author yyx
 */
public class SplashActivity extends AppCompatActivity implements IActivity {

    @Override
    public int initLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        // 白屏时间 + 2秒进入首页 ≈ 3~5秒
        handler.sendEmptyMessageDelayed(1, 2 * 1000);
    }

    private final Handler handler = new Handler(message -> {
        if (message.what == 1) {
            inMain();
        }
        return false;
    });

    private void inMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 避免内存泄漏
        handler.removeCallbacksAndMessages(null);
    }
}
