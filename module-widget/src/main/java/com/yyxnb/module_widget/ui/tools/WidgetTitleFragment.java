package com.yyxnb.module_widget.ui.tools;

import android.os.Bundle;

import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.module_widget.R;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/12/03
 * 描    述：标题栏
 * ================================================
 */
@BindRes
public class WidgetTitleFragment extends BaseFragment {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_widget_title;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}