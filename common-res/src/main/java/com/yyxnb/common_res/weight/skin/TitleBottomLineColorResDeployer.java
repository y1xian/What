package com.yyxnb.common_res.weight.skin;

import android.view.View;

import com.yyxnb.what.skinloader.bean.SkinAttr;
import com.yyxnb.what.skinloader.bean.SkinConfig;
import com.yyxnb.what.skinloader.skinInterface.ISkinResDeployer;
import com.yyxnb.what.skinloader.skinInterface.ISkinResourceManager;
import com.yyxnb.what.view.titlebar.TitleBar;


public class TitleBottomLineColorResDeployer implements ISkinResDeployer {

    @Override
    public void deploy(View view, SkinAttr skinAttr, ISkinResourceManager resource) {
        if (!(view instanceof TitleBar)) {
            return;
        }
        TitleBar titleBar = (TitleBar) view;
        if (SkinConfig.RES_TYPE_NAME_COLOR.equals(skinAttr.attrValueTypeName)) {
            titleBar.getButtomLine().setBackgroundColor(resource.getColor(skinAttr.attrValueRefId));
        }


    }
}
