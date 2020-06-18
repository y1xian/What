package com.yyxnb.module_base.utils.skin;

import android.content.res.Resources.Theme;
import android.view.View;

/**
 * View的背景色Setter
 */
public class ViewBackgroundColorSetter extends ViewSetter {

    public ViewBackgroundColorSetter(View target, int resId) {
        super(target, resId);
    }

    public ViewBackgroundColorSetter(int viewId, int resId) {
        super(viewId, resId);
    }

    @Override
    public void setValue(Theme newTheme, int themeId) {
        if (mView != null) {
            mView.setBackgroundColor(getColor(newTheme));
        }
    }

}