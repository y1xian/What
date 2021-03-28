package com.yyxnb.module_main.ui.main;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.constants.ChatRouterPath;
import com.yyxnb.common_res.service.impl.UserImpl;
import com.yyxnb.common_res.utils.ARouterUtils;
import com.yyxnb.module_main.R;
import com.yyxnb.module_main.databinding.FragmentMainBinding;
import com.yyxnb.module_main.ui.MainTestFragment;
import com.yyxnb.module_main.viewmodel.MainViewModel;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.annotations.BindViewModel;
import com.yyxnb.what.popup.PopupManager;
import com.yyxnb.what.view.tabbar.Tab;
import com.yyxnb.what.view.tabbar.TabBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/23
 * 历    史：
 * 描    述：页面主布局
 * ================================================
 */
@BindRes(subPage = true)
public class MainFragment extends BaseFragment {

    private FragmentMainBinding binding;

    @BindViewModel(isActivity = true)
    MainViewModel mViewModel;

    private ArrayList<Fragment> fragments;
    private List<Tab> tabs;

    private TabBarView mTabLayout;
    private int currentIndex;
    private boolean isAddeds;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mTabLayout = binding.tabLayout;
    }

    @Override
    public void initViewData() {

        // 隐私权限申请
        new PopupManager.Builder(getContext())
                .dismissOnTouchOutside(false)
                .dismissOnBackPressed(false)
                .asConfirm("温馨提示",
                        Html.fromHtml(getString(R.string.main_privacy_notice)), "我就不给", "给给给", () -> {
                            // 确定
                            toast("感想您的信任");
                        }, this::finish).show();

        if (fragments == null) {
            fragments = new ArrayList<>();
            fragments.add(new MainHomeFragment());
//            fragments.add(new MainFindFragment());
            fragments.add(new MainTestFragment());
            fragments.add((Fragment) ARouterUtils.navFragment(ChatRouterPath.MAIN_FRAGMENT));
            fragments.add((Fragment) UserImpl.getInstance().mainPage(getContext()));

            tabs = new ArrayList<>();
            tabs.add(new Tab(getContext(), "首页", R.mipmap.ic_titlebar_progress));
            tabs.add(new Tab(getContext(), "发现", R.mipmap.ic_titlebar_progress));
            tabs.add(new Tab(getContext(), "消息", R.mipmap.ic_titlebar_progress));
            tabs.add(new Tab(getContext(), "我的", R.mipmap.ic_titlebar_progress));
        }

        mTabLayout.setTab(tabs);

        mTabLayout.setOnSelectListener((v, position, text) -> changeView(position));

        changeView(0);
    }

    @Override
    public void initObservable() {
        mViewModel.isHideBottomBar.observe(this,data->{
            if (data){
                mTabLayout.setVisibility(View.GONE);
            }else {
                mTabLayout.setVisibility(View.VISIBLE);
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
            ft.add(R.id.flContent, fragments.get(index)).show(fragments.get(index));
        } else {
            //显示新的Fragment
            ft.show(fragments.get(index));
        }
        ft.commitAllowingStateLoss();
        currentIndex = index;
    }
}