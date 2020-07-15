package com.yyxnb.module_chat.ui;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.ContainerActivity;

import static com.yyxnb.common_base.arouter.ARouterConstant.MESSAGE_MAIN;

@Route(path = MESSAGE_MAIN)
public class MessageActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new MessageListFragment();
    }

}