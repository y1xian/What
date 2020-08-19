package com.yyxnb.common_base.weight.skin

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yyxnb.adapter.ItemDecoration
import com.yyxnb.skinloader.bean.SkinAttr
import com.yyxnb.skinloader.bean.SkinConfig
import com.yyxnb.skinloader.skinInterface.ISkinResDeployer
import com.yyxnb.skinloader.skinInterface.ISkinResourceManager

class RecycleViewResDeployer : ISkinResDeployer {
    private var divHeight = 0

    constructor() {}
    constructor(divHeight: Int) {
        this.divHeight = divHeight
    }

    override fun deploy(view: View, skinAttr: SkinAttr, resource: ISkinResourceManager) {
        if (view !is RecyclerView) {
            return
        }
        val recyclerView = view
        if (SkinConfig.RES_TYPE_NAME_COLOR == skinAttr.attrValueTypeName) {
            val decoration = ItemDecoration(recyclerView.context)
            if (divHeight > 0) {
                decoration.setDividerHeight(divHeight)
            }
            decoration.setDividerColor(resource.getColor(skinAttr.attrValueRefId))
            recyclerView.removeItemDecorationAt(0)
            recyclerView.addItemDecoration(decoration)

//            LogUtils.e("-----" + skinAttr.attrValueTypeName + " , " + skinAttr.attrValueRefId);
        }
    }
}