package com.yyxnb.module_wanandroid.ui.provide;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.lib_arch.annotations.BindRes;
import com.yyxnb.lib_arch.viewmodel.ViewModelFactory;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.ui.home.WanHomeFragment;
import com.yyxnb.module_wanandroid.viewmodel.WanHomeViewModel;

import static com.yyxnb.common_res.arouter.ARouterConstant.WAN_HOME_PROVIDE_FRAGMENT;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：11/26/20
 * 历    史：
 * 描    述：对外提供的玩安卓首页
 * ================================================
 */
@BindRes(subPage = true)
@Route(path = WAN_HOME_PROVIDE_FRAGMENT)
public class WanHomeProvideFragment extends WanHomeFragment {


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_wan_home_provide, container, false);
//    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mViewModel = ViewModelFactory.createViewModel(this, WanHomeViewModel.class);
        findViewById(R.id.cl_head).setVisibility(View.GONE);
    }
}