package com.yyxnb.module_base.weight;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.noober.background.drawable.DrawableCreator;
import com.yyxnb.skinloader.bean.SkinAttr;
import com.yyxnb.skinloader.bean.SkinConfig;
import com.yyxnb.skinloader.skinInterface.ISkinResDeployer;
import com.yyxnb.skinloader.skinInterface.ISkinResourceManager;


public class BLBackgroundColorResDeployer implements ISkinResDeployer {

    private float radius = 8f;

    @Override
    public void deploy(View view, SkinAttr skinAttr, ISkinResourceManager resource) {
//        if (!(view instanceof CustomTitleView)) {
//            return;
//        }
        if (SkinConfig.RES_TYPE_NAME_COLOR.equals(skinAttr.attrValueTypeName)) {
            Drawable drawable = new DrawableCreator.Builder()
                    .setCornersRadius(radius)
                    .setSolidColor(resource.getColor(skinAttr.attrValueRefId))
                    .build();
            view.setBackground(drawable);
        }


    }
}
