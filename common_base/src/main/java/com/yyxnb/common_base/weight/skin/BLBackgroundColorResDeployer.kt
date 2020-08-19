package com.yyxnb.common_base.weight.skin

import android.view.View
import com.noober.background.drawable.DrawableCreator
import com.yyxnb.skinloader.bean.SkinAttr
import com.yyxnb.skinloader.bean.SkinConfig
import com.yyxnb.skinloader.skinInterface.ISkinResDeployer
import com.yyxnb.skinloader.skinInterface.ISkinResourceManager

class BLBackgroundColorResDeployer : ISkinResDeployer {
    private val radius = 8f
    override fun deploy(view: View, skinAttr: SkinAttr, resource: ISkinResourceManager) {
//        if (!(view instanceof CustomTitleView)) {
//            return;
//        }
        if (SkinConfig.RES_TYPE_NAME_COLOR == skinAttr.attrValueTypeName) {
            val drawable = DrawableCreator.Builder()
                    .setCornersRadius(radius)
                    .setSolidColor(resource.getColor(skinAttr.attrValueRefId))
                    .build()
            view.background = drawable
        }
    }
}