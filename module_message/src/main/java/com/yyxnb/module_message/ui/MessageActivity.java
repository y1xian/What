package com.yyxnb.module_message.ui;

import android.support.v4.app.Fragment;

import com.yyxnb.common_base.base.ContainerActivity;

public class MessageActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new MessageListFragment();
    }

}