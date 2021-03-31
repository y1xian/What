package com.yyxnb.module_chat.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.constants.ChatRouterPath;
import com.yyxnb.module_chat.R;
import com.yyxnb.module_chat.databinding.FragmentChatMainBinding;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.view.tabbar.Tab;
import com.yyxnb.what.view.tabbar.TabBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/18
 * 历    史：
 * 描    述：环信 聊天 主界面
 * ================================================
 */
@BindRes(fitsSystemWindows = true)
@Route(path = ChatRouterPath.MAIN_FRAGMENT)
public class ChatMainFragment extends BaseFragment {

    private FragmentChatMainBinding binding;

    private ArrayList<Fragment> fragments;
    private List<Tab> tabs;

    private TabBarView mTabLayout;
    private int currentIndex;
    private boolean isAddeds;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_chat_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mTabLayout = binding.mTabLayout;
    }

    @Override
    public void initViewData() {

        if (fragments == null) {
            fragments = new ArrayList<>();
            fragments.add(new ChatConversationListFragment());
            fragments.add(new ChatContactListFragment());

            tabs = new ArrayList<>();
            tabs.add(new Tab(getContext(), "会话", R.mipmap.ic_titlebar_progress));
            tabs.add(new Tab(getContext(), "通讯录", R.mipmap.ic_titlebar_progress));
        }

        mTabLayout.setTab(tabs);

        mTabLayout.setOnSelectListener((v, position, text) -> changeView(position));

        changeView(0);


        EMClient.getInstance().login("user1", "user1", new EMCallBack() {
            //回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
            }
        });
    }

    //设置Fragment页面
    private void changeView(int index) {

        if (currentIndex == index && isAddeds) {
            //重复点击
            return;
        }
        isAddeds = true;
        //开启事务
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        //隐藏当前Fragment
        ft.hide(fragments.get(currentIndex));
        //判断Fragment是否已经添加
        if (!fragments.get(index).isAdded()) {
            ft.add(R.id.fragment_content_view, fragments.get(index)).show(fragments.get(index));
        } else {
            //显示新的Fragment
            ft.show(fragments.get(index));
        }
        ft.commitAllowingStateLoss();
        currentIndex = index;
    }
}