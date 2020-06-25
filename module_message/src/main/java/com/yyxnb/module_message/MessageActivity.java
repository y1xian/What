package com.yyxnb.module_message;

import android.support.v4.app.Fragment;

import com.yyxnb.module_base.base.ContainerActivity;
import com.yyxnb.module_message.fragments.MessageListFragment;

public class MessageActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new MessageListFragment();
    }

}