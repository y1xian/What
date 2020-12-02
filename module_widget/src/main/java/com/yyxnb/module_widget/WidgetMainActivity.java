package com.yyxnb.module_widget;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.ContainerActivity;
import com.yyxnb.module_widget.ui.WidgetMainFragment;

import static com.yyxnb.common_res.arouter.ARouterConstant.WIDGET_MAIN;

@Route(path = WIDGET_MAIN)
public class WidgetMainActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new WidgetMainFragment();
    }

}