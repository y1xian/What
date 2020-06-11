package com.yyxnb.module_main.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.module_main.R;

import static com.yyxnb.module_base.arouter.ARouterConstant.VIDEO_VIDEO;

/**
 * 主页
 */
public class MainHomeFragment extends BaseFragment {

    private TextView tvTitle;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_main_home;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        tvTitle = findViewById(R.id.tvTitle);

        tvTitle.setOnClickListener(v -> {
//            startFragment(new MainClassificationFragment());
//            startFragment((BaseFragment) ARouter.getInstance().build("/login/LoginFragment").navigation());
            ARouter.getInstance().build(VIDEO_VIDEO).navigation();
        });
    }

    @Override
    public void initViewData() {
        super.initViewData();

    }
}
