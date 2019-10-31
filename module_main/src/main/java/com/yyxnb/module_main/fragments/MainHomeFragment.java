package com.yyxnb.module_main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.arch.annotations.SwipeBack;
import com.yyxnb.module_main.R;

import org.jetbrains.annotations.Nullable;

import static com.yyxnb.module_base.arouter.ARouterConstant.VIDEO_VIDEO;

/**
 * A simple {@link Fragment} subclass.
 */
//@StatusBarDarkTheme(value = BarStyle.LightContent)
@SwipeBack(value = -1)
public class MainHomeFragment extends BaseFragment {

    private TextView tvShow;

    public static MainHomeFragment newInstance() {

        Bundle args = new Bundle();

        MainHomeFragment fragment = new MainHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_main_home;
    }

    @Override
    public int initStatusBarColor() {
        return getResources().getColor(R.color.purple);
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        tvShow = findViewById(R.id.tvShow);

        tvShow.setOnClickListener(v -> {
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
