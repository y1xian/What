package com.yyxnb.common_base.weight.skin;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yyxnb.lib_adapter.ItemDecoration;
import com.yyxnb.skinloader.bean.SkinAttr;
import com.yyxnb.skinloader.bean.SkinConfig;
import com.yyxnb.skinloader.skinInterface.ISkinResDeployer;
import com.yyxnb.skinloader.skinInterface.ISkinResourceManager;


public class RecycleViewResDeployer implements ISkinResDeployer {

    private int divHeight;

    public RecycleViewResDeployer() {
    }

    public RecycleViewResDeployer(int divHeight) {
        this.divHeight = divHeight;
    }

    @Override
    public void deploy(View view, SkinAttr skinAttr, ISkinResourceManager resource) {
        if (!(view instanceof RecyclerView)) {
            return;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        if (SkinConfig.RES_TYPE_NAME_COLOR.equals(skinAttr.attrValueTypeName)) {
            ItemDecoration decoration = new ItemDecoration(recyclerView.getContext());
            if (divHeight > 0) {
                decoration.setDividerHeight(divHeight);
            }
            decoration.setDividerColor(resource.getColor(skinAttr.attrValueRefId));

            recyclerView.removeItemDecorationAt(0);
            recyclerView.addItemDecoration(decoration);

//            LogUtils.e("-----" + skinAttr.attrValueTypeName + " , " + skinAttr.attrValueRefId);
        }


    }
}
