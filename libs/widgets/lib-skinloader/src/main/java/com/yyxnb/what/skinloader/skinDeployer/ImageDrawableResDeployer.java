package com.yyxnb.what.skinloader.skinDeployer;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.yyxnb.what.skinloader.bean.SkinAttr;
import com.yyxnb.what.skinloader.bean.SkinConfig;
import com.yyxnb.what.skinloader.skinInterface.ISkinResDeployer;
import com.yyxnb.what.skinloader.skinInterface.ISkinResourceManager;


public class ImageDrawableResDeployer implements ISkinResDeployer {
    @Override
    public void deploy(View view, SkinAttr skinAttr, ISkinResourceManager resource) {
        if (!(view instanceof ImageView)) {
            return;
        }
        Drawable drawable = null;
        if (SkinConfig.RES_TYPE_NAME_COLOR.equals(skinAttr.attrValueTypeName)) {
            drawable = new ColorDrawable(resource.getColor(skinAttr.attrValueRefId));
        } else if (SkinConfig.RES_TYPE_NAME_DRAWABLE.equals(skinAttr.attrValueTypeName)) {
            drawable = resource.getDrawable(skinAttr.attrValueRefId);
        } else if (SkinConfig.RES_TYPE_NAME_MIPMAP.equals(skinAttr.attrValueTypeName)) {
            drawable = resource.getDrawableForMapmip(skinAttr.attrValueRefId);
        }
        if (drawable != null) {
            ((ImageView) view).setImageDrawable(drawable);
        }
    }
}
