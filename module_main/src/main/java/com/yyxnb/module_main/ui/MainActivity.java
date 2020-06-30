package com.yyxnb.module_main.ui;

import android.support.v4.app.Fragment;

import com.yyxnb.common_base.base.ContainerActivity;
import com.yyxnb.module_main.ui.MainHomeFragment;

/**
 * @author yyx
 */
public class MainActivity extends ContainerActivity {
    @Override
    public Fragment initBaseFragment() {
        return new MainHomeFragment();
    }

    //    private NoScrollViewPager mViewPager;
//    private ArrayList<Fragment> fragments;
//    private TabBarView mTabLayout;
//    private int currentIndex;
//    private boolean isAdded;
//
//
//    @Override
//    public int initLayoutResId() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    public void initView(@Nullable Bundle savedInstanceState) {
//        mViewPager = findViewById(R.id.mViewPager);
//        mTabLayout = findViewById(R.id.mTabLayout);
//
//        fragments = new ArrayList<>();
//        fragments.add(new MainHomeFragment());
//        fragments.add(new MainClassificationFragment());
//        fragments.add((Fragment) ARouterUtils.navFragment(USER_FRAGMENT));
//
//        List<Tab> tabs = new ArrayList<>();
//        tabs.add(new Tab(this, "推荐", R.mipmap.icon_main_sy));
//        tabs.add(new Tab(this, "分类", R.mipmap.icon_main_fl));
//        tabs.add(new Tab(this, "我的", R.mipmap.icon_main_wd));
//
//        mTabLayout.setTab(tabs);
////        mViewPager.setOffscreenPageLimit(fragments.size());
////        mViewPager.setAdapter(new BaseFragmentStatePagerAdapter(getSupportFragmentManager(), fragments));
////        mViewPager.setNoScroll(true);
//
//        mTabLayout.setOnTabChangeListener((v, index) -> {
////            mViewPager.setCurrentItem(index);
//            changeView(index);
//        });
//
//        mTabLayout.setNormalFocusIndex(0);
//        changeView(0);
////        mViewPager.setCurrentItem(0);
//    }
//
//    //设置Fragment页面
//    private void changeView(int index) {
//
//        if (currentIndex == index && isAdded) {
//            //重复点击
//            return;
//        }
//        isAdded = true;
//        //开启事务
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        //隐藏当前Fragment
//        ft.hide(fragments.get(currentIndex));
//        //判断Fragment是否已经添加
//        if (!fragments.get(index).isAdded()) {
//            ft.add(R.id.fragment_content_view, fragments.get(index)).show(fragments.get(index));
//        } else {
//            //显示新的Fragment
//            ft.show(fragments.get(index));
//        }
//        ft.commitAllowingStateLoss();
//        currentIndex = index;
//    }
}
