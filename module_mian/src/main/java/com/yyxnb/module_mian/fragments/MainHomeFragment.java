package com.yyxnb.module_mian.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.arch.interfaces.SwipeBack;
import com.yyxnb.module_mian.R;

import org.jetbrains.annotations.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
@SwipeBack(value = false)
//@StatusBarDarkTheme(value = BarStyle.LightContent)
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
        return getResources().getColor(R.color.green);
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
//        onResume();
        tvShow = findViewById(R.id.tvShow);

        tvShow.setOnClickListener(v -> {
//            startFragment(new MainHomeFragment());
        });

    }

    @Override
    public void initViewData() {
        super.initViewData();

//        scheduleTaskAtStarted(() -> {
//            setNeedsStatusBarAppearanceUpdate();
//            LogUtils.INSTANCE.d(" " + getStatusBarDarkTheme());
//        },0);


    }

    @Override
    public void onVisible() {
        super.onVisible();
//        setNeedsStatusBarAppearanceUpdate();
    }
}
