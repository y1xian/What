package com.yyxnb.module_chat.ui;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.ContainerActivity;
import com.yyxnb.common_res.constants.ChatRouterPath;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：聊天 主界面
 * ================================================
 */
@Route(path = ChatRouterPath.MAIN_ACTIVITY)
public class ChatActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new ChatMainFragment();
    }

}