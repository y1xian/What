package com.yyxnb.lib_skinloader.skinDeployer;

import android.view.View;
import android.widget.TextView;

import com.yyxnb.lib_skinloader.bean.SkinAttr;
import com.yyxnb.lib_skinloader.bean.SkinConfig;
import com.yyxnb.lib_skinloader.skinInterface.ISkinResDeployer;
import com.yyxnb.lib_skinloader.skinInterface.ISkinResourceManager;


public class TextColorResDeployer implements ISkinResDeployer {
    @Override
    public void deploy(View view, SkinAttr skinAttr, ISkinResourceManager resource) {
        if (view instanceof TextView && SkinConfig.RES_TYPE_NAME_COLOR.equals(skinAttr.attrValueTypeName)) {
            TextView tv = (TextView) view;
            tv.setTextColor(resource.getColorStateList(skinAttr.attrValueRefId));
        }
    }
}
