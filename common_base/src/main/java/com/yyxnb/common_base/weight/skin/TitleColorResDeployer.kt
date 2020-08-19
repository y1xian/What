package com.yyxnb.common_base.weight.skin

import android.view.View
import com.yyxnb.common.utils.StatusBarUtils.isBlackColor
import com.yyxnb.skinloader.bean.SkinAttr
import com.yyxnb.skinloader.bean.SkinConfig
import com.yyxnb.skinloader.skinInterface.ISkinResDeployer
import com.yyxnb.skinloader.skinInterface.ISkinResourceManager
import com.yyxnb.view.titlebar.TitleBar

class TitleColorResDeployer : ISkinResDeployer {
    override fun deploy(view: View, skinAttr: SkinAttr, resource: ISkinResourceManager) {
        if (view !is TitleBar) {
            return
        }
        if (SkinConfig.RES_TYPE_NAME_COLOR == skinAttr.attrValueTypeName) {
            view.setBackgroundColor(resource.getColor(skinAttr.attrValueRefId))
            if (isBlackColor(resource.getColor(skinAttr.attrValueRefId), 176)) {
//                titleBar.getButtomLine().setBackgroundColor();
            }
        }
    }
}