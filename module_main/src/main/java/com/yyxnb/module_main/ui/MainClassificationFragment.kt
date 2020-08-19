package com.yyxnb.module_main.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.yyxnb.arch.base.IFragment;
import com.yyxnb.module_main.R;

/**
 * 分类
 */
public class MainClassificationFragment extends Fragment implements IFragment {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_main_classification;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void onVisible() {
        getBaseDelegate().setNeedsStatusBarAppearanceUpdate();
    }
}