package com.yyxnb.common_res.weight.skin;

import android.view.View;

import com.yyxnb.what.core.StatusBarUtils;
import com.yyxnb.what.skinloader.bean.SkinAttr;
import com.yyxnb.what.skinloader.bean.SkinConfig;
import com.yyxnb.what.skinloader.skinInterface.ISkinResDeployer;
import com.yyxnb.what.skinloader.skinInterface.ISkinResourceManager;
import com.yyxnb.what.view.titlebar.TitleBar;


public class TitleColorResDeployer implements ISkinResDeployer {

    @Override
    public void deploy(View view, SkinAttr skinAttr, ISkinResourceManager resource) {
        if (!(view instanceof TitleBar)) {
            return;
        }
        TitleBar titleBar = (TitleBar) view;
        if (SkinConfig.RES_TYPE_NAME_COLOR.equals(skinAttr.attrValueTypeName)) {

            titleBar.setBackgroundColor(resource.getColor(skinAttr.attrValueRefId));

            if (StatusBarUtils.isBlackColor(resource.getColor(skinAttr.attrValueRefId),176)){
//                titleBar.getButtomLine().setBackgroundColor();
            }

        }


    }
}
