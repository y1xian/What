package com.yyxnb.common_base.weight.skin

import android.view.View
import com.yyxnb.skinloader.bean.SkinAttr
import com.yyxnb.skinloader.bean.SkinConfig
import com.yyxnb.skinloader.skinInterface.ISkinResDeployer
import com.yyxnb.skinloader.skinInterface.ISkinResourceManager
import com.yyxnb.view.titlebar.TitleBar

class TitleStatusColorResDeployer : ISkinResDeployer {
    override fun deploy(view: View, skinAttr: SkinAttr, resource: ISkinResourceManager) {
        if (view !is TitleBar) {
            return
        }
        if (SkinConfig.RES_TYPE_NAME_COLOR == skinAttr.attrValueTypeName) {
            view.setStatusBarColor(resource.getColor(skinAttr.attrValueRefId))
        }
    }
}