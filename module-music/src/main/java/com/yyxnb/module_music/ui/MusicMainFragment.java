package com.yyxnb.module_music.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.constants.MusicRouterPath;
import com.yyxnb.module_music.R;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/16
 * 描    述：MusicMainFragment
 * ================================================
 */
@Route(path = MusicRouterPath.MAIN_FRAGMENT)
public class MusicMainFragment extends BaseFragment {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_music_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initViewData() {

    }
}