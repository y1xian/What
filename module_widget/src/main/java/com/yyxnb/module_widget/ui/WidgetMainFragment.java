package com.yyxnb.module_widget.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.yyxnb.common_base.core.BaseFragment;
import com.yyxnb.lib_arch.annotations.BindRes;
import com.yyxnb.util_permission.PermissionListener;
import com.yyxnb.util_permission.PermissionUtils;
import com.yyxnb.lib_view.tabbar.Tab;
import com.yyxnb.lib_view.tabbar.TabBarView;
import com.yyxnb.module_widget.R;
import com.yyxnb.module_widget.databinding.FragmentWidgetMainBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/12/03
 * 描    述：Widget 主页
 * ================================================
 */
@BindRes
public class WidgetMainFragment extends BaseFragment {

    private FragmentWidgetMainBinding binding;

    private ArrayList<Fragment> fragments;
    private List<Tab> tabs;

    private TabBarView mTabLayout;
    private int currentIndex;
    private boolean isAddeds;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_widget_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mTabLayout = binding.vTabLayout;

        PermissionUtils.with(getActivity())
                .addPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setPermissionsCheckListener(new PermissionListener() {
                    @Override
                    public void permissionRequestSuccess() {
                    }

                    @Override
                    public void permissionRequestFail(String[] grantedPermissions, String[] deniedPermissions, String[] forceDeniedPermissions) {
                    }
                })
                .createConfig()
                .setForceAllPermissionsGranted(true)
                .buildConfig()
                .startCheckPermission();
    }

    @Override
    public void initViewData() {

        if (fragments == null) {
            fragments = new ArrayList<>();
            fragments.add(new WidgetToolFragment());
            fragments.add(new WidgetFunctionFragment());
            fragments.add(new WidgetSystemFragment());

            tabs = new ArrayList<>();
            tabs.add(new Tab(getContext(), "控件", R.mipmap.ic_titlebar_progress));
            tabs.add(new Tab(getContext(), "功能", R.mipmap.ic_titlebar_progress));
            tabs.add(new Tab(getContext(), "系统", R.mipmap.ic_titlebar_progress));
        }

        mTabLayout.setTab(tabs);

        mTabLayout.setOnSelectListener((v, position, text) -> changeView(position));

        changeView(0);
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
            ft.add(R.id.fl_content, fragments.get(index)).show(fragments.get(index));
        } else {
            //显示新的Fragment
            ft.show(fragments.get(index));
        }
        ft.commitAllowingStateLoss();
        currentIndex = index;
    }

}