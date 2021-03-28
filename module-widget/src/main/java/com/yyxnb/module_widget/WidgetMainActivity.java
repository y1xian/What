package com.yyxnb.module_widget;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.ContainerActivity;
import com.yyxnb.module_widget.ui.WidgetMainFragment;

@Route(path = "/widget/main_activity")
public class WidgetMainActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new WidgetMainFragment();
    }

}