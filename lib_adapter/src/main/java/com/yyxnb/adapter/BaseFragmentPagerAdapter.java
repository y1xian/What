package com.yyxnb.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mTitles = new ArrayList<>();

    public BaseFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments,
                                    List<String> titles) {
        super(fragmentManager);
        if (fragments != null) {
            this.mFragments.addAll(fragments);
        }
        if (titles != null) {
            this.mTitles.addAll(titles);
        }
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return position < mTitles.size() ? mTitles.get(position) : null;
    }
}