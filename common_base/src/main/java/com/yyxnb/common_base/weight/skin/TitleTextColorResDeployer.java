package com.yyxnb.common_base.weight.skin;

import android.view.View;

import com.yyxnb.skinloader.bean.SkinAttr;
import com.yyxnb.skinloader.bean.SkinConfig;
import com.yyxnb.skinloader.skinInterface.ISkinResDeployer;
import com.yyxnb.skinloader.skinInterface.ISkinResourceManager;
import com.yyxnb.lib_view.titlebar.TitleBar;


public class TitleTextColorResDeployer implements ISkinResDeployer {

    @Override
    public void deploy(View view, SkinAttr skinAttr, ISkinResourceManager resource) {
        if (!(view instanceof TitleBar)) {
            return;
        }
        TitleBar titleBar = (TitleBar) view;
        if (SkinConfig.RES_TYPE_NAME_COLOR.equals(skinAttr.attrValueTypeName)) {
            titleBar.getCenterTextView().setTextColor(resource.getColor(skinAttr.attrValueRefId));
        }


    }
}
