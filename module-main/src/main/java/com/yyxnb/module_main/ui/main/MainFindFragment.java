package com.yyxnb.module_main.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.yyxnb.module_main.R;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.base.IFragment;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/23
 * 历    史：
 * 描    述：发现
 * ================================================
 */
@BindRes(subPage = true)
public class MainFindFragment extends Fragment implements IFragment {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_main_find;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void onVisible() {
        getBaseDelegate().setNeedsStatusBarAppearanceUpdate();
    }
}