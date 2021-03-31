package com.yyxnb.module_wanandroid.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.databinding.ActivityWanWebBinding;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.base.IActivity;
import com.yyxnb.what.view.titlebar.TitleBar;
import com.yyxnb.what.webview.WebActivity;

@BindRes
public class WanWebActivity extends WebActivity implements IActivity {

    private ActivityWanWebBinding binding;
    private TitleBar mTitle;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wan_web);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTitle = binding.iTitle.vTitle;

        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");

        mTitle.getCenterTextView().setText(title);
        mTitle.setBackListener(v -> finish());

        mLinearLayout = findViewById(R.id.ll_content);


        initAgent(url, mLinearLayout);
    }
}