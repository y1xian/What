package com.yyxnb.module_user.fragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.arch.interfaces.BarStyle;
import com.yyxnb.arch.interfaces.StatusBarDarkTheme;
import com.yyxnb.arch.utils.log.LogUtils;
import com.yyxnb.module_user.R;

import org.jetbrains.annotations.Nullable;

import static com.yyxnb.module_base.arouter.ARouterConstant.USER_FRAGMENT;

/**
 * A simple {@link Fragment} subclass.
 */
@StatusBarDarkTheme(value = BarStyle.LightContent)
@Route(path = USER_FRAGMENT)
public class UserFragment extends BaseFragment {

    private TextView tvName;
    private CoordinatorLayout mCoordinatorLayout;
    private ConstraintLayout clHead;

    public static UserFragment newInstance() {

        Bundle args = new Bundle();

        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_user;
    }

    @Override
    public int initStatusBarColor() {
        return getResources().getColor(R.color.user_head);
    }


    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

        mCoordinatorLayout = findViewById(R.id.mCoordinatorLayout);
        clHead = findViewById(R.id.clHead);

        tvName = findViewById(R.id.tvName);

//        tvName.setText("yyyy更新 " + StatusBarUtils.INSTANCE.getStatusBarHeight(getMContext()));
//
//        tvShow.setOnClickListener(v -> {
//            ToastUtils.INSTANCE.normal(" tvShow user 13213123213213");
//        });

//        if (null != getMRootView()){
//            SwipeExtKt.wrap(Objects.requireNonNull(getMRootView()));
//        }

        clHead.setOnClickListener(v -> {
//            ARouter.getInstance().build(LOGIN_ACTIVITY).navigation();
            startFragment((BaseFragment)ARouter.getInstance().build("/login/LoginFragment").navigation());
        });


    }

    @Override
    public void onInVisible() {
        super.onInVisible();
        LogUtils.INSTANCE.d("---onInVisible---");
    }

    @Override
    public void onVisible() {
        super.onVisible();
        LogUtils.INSTANCE.d("---onVisible---");
    }
}
