package com.yyxnb.module_chat.ui.provide;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.BaseFragment;
import com.yyxnb.lib_arch.annotations.BindRes;
import com.yyxnb.module_chat.R;
import com.yyxnb.module_chat.databinding.FragmentChatHomeProvideBinding;

import static com.yyxnb.common_res.arouter.ARouterConstant.CHAT_HOME_PROVIDE_FRAGMENT;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：对外提供的消息首页
 * ================================================
 */
@BindRes(subPage = true)
@Route(path = CHAT_HOME_PROVIDE_FRAGMENT)
public class ChatHomeProvideFragment extends BaseFragment {

    private FragmentChatHomeProvideBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_chat_home_provide;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();

        binding.iRv.vStatus.showEmptyView();
    }
}