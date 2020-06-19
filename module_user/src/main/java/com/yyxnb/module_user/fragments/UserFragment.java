package com.yyxnb.module_user.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.arch.annotations.BarStyle;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.arch.common.Bus;
import com.yyxnb.arch.common.MsgEvent;
import com.yyxnb.common.log.LogUtils;
import com.yyxnb.lib_skin.SkinTheme;
import com.yyxnb.module_base.arouter.ARouterUtils;
import com.yyxnb.module_user.R;
import com.yyxnb.module_user.databinding.FragmentUserBinding;

import static com.yyxnb.module_base.arouter.ARouterConstant.LOGIN_FRAGMENT;
import static com.yyxnb.module_base.arouter.ARouterConstant.USER_FRAGMENT;
import static com.yyxnb.module_base.config.Constants.KEY_SKIN_SWITCH;

/**
 * 我的 - 界面.
 */
@BindRes(statusBarStyle = BarStyle.LightContent)
@Route(path = USER_FRAGMENT)
public class UserFragment extends BaseFragment {

    private FragmentUserBinding binding;

    private CheckBox mCheckBox;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_user;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        binding = getBinding();
        mCheckBox = binding.mCheckBox;

//        tvName.setText("yyyy更新 " + StatusBarUtils.INSTANCE.getStatusBarHeight(getMContext()));
//
//        tvShow.setOnClickListener(v -> {
//            ToastUtils.INSTANCE.normal(" tvShow user 13213123213213");
//        });

//        if (null != getMRootView()){
//            SwipeExtKt.wrap(Objects.requireNonNull(getMRootView()));
//        }

        binding.clHead.setOnClickListener(v -> {
//            ARouter.getInstance().build(LOGIN_ACTIVITY).navigation();
            startFragment(ARouterUtils.navFragment(LOGIN_FRAGMENT));
        });


        mCheckBox.setChecked(SkinTheme.getCurrentThemeId() == R.style.NightTheme);

        SkinTheme theme = new SkinTheme.Builder(getActivity())
                .backgroundColor(R.id.mLayout,R.attr.skinBackground)
                .backgroundColor(R.id.mLayoutMenu1,R.attr.skinBackground)
                .backgroundColor(R.id.mLayoutMenu,R.attr.skinBackground)
                .build();

        mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                theme.setTheme(R.style.DayTheme);
            } else {
                theme.setTheme(R.style.NightTheme);
            }
            Bus.post(new MsgEvent(KEY_SKIN_SWITCH, SkinTheme.getCurrentThemeId()));
        });

    }

    @Override
    public void onInVisible() {
        super.onInVisible();
        LogUtils.d("---onInVisible---");
    }

    @Override
    public void onVisible() {
        super.onVisible();
        getBaseDelegate().setNeedsStatusBarAppearanceUpdate();
        LogUtils.d("---onVisible---");
    }
}
