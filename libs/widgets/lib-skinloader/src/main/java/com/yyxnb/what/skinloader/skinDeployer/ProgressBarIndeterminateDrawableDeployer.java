package com.yyxnb.what.skinloader.skinDeployer;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ProgressBar;

import com.yyxnb.what.skinloader.bean.SkinAttr;
import com.yyxnb.what.skinloader.bean.SkinConfig;
import com.yyxnb.what.skinloader.skinInterface.ISkinResDeployer;
import com.yyxnb.what.skinloader.skinInterface.ISkinResourceManager;


public class ProgressBarIndeterminateDrawableDeployer implements ISkinResDeployer {
    @Override
    public void deploy(View view, SkinAttr skinAttr, ISkinResourceManager resource) {
        if (!(view instanceof ProgressBar)) {
            return;
        }

        ProgressBar pb = (ProgressBar) view;
        Drawable drawable = null;
        if (SkinConfig.RES_TYPE_NAME_COLOR.equals(skinAttr.attrValueTypeName)) {
            drawable = new ColorDrawable(resource.getColor(skinAttr.attrValueRefId));
        } else if (SkinConfig.RES_TYPE_NAME_DRAWABLE.equals(skinAttr.attrValueTypeName)) {
            drawable = resource.getDrawable(skinAttr.attrValueRefId);
        }
        if (drawable != null) {
            pb.setIndeterminateDrawable(drawable);
        }
    }
}
