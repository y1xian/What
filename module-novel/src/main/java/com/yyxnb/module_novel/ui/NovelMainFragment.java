package com.yyxnb.module_novel.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.BaseFragment;
import com.yyxnb.lib_view.tabbar.Tab;
import com.yyxnb.lib_view.tabbar.TabBarView;
import com.yyxnb.module_novel.R;
import com.yyxnb.module_novel.databinding.FragmentNovelMainBinding;

import java.util.ArrayList;
import java.util.List;

import static com.yyxnb.common_res.arouter.ARouterConstant.NOVEL_MAIN_FRAGMENT;

/**
 * 小说主页.
 */
@Route(path = NOVEL_MAIN_FRAGMENT)
public class NovelMainFragment extends BaseFragment {

    private FragmentNovelMainBinding binding;
    private ArrayList<Fragment> fragments;
    private List<Tab> tabs;

    private TabBarView mTabLayout;
    private int currentIndex = 1;
    private boolean isAddeds;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_novel_main;
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
            fragments.add(new BookShelfFragment());
            fragments.add(new BookHomeFragment());

            tabs = new ArrayList<>();
            tabs.add(new Tab(getContext(), "书架", R.mipmap.ic_titlebar_progress));
            tabs.add(new Tab(getContext(), "精选", R.mipmap.ic_titlebar_progress));
        }

        mTabLayout.setTab(tabs);

        mTabLayout.setOnSelectListener((v, position, text) -> changeView(position));

        changeView(currentIndex);
        mTabLayout.setNormalFocusIndex(currentIndex);
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