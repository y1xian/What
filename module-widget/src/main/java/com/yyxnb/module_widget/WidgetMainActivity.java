package com.yyxnb.module_widget;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.ContainerActivity;
import com.yyxnb.module_widget.ui.WidgetMainFragment;

@Route(path = "/widget/main_activity")
public class WidgetMainActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new WidgetMainFragment();
    }

}