package com.yyxnb.what.skinloader.skinDeployer;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;

import com.yyxnb.what.skinloader.bean.SkinAttr;
import com.yyxnb.what.skinloader.bean.SkinConfig;
import com.yyxnb.what.skinloader.skinInterface.ISkinResDeployer;
import com.yyxnb.what.skinloader.skinInterface.ISkinResourceManager;

/***
 * ListView divider属性的换肤支持（android:divider）
 */
public class ListViewDividerResDeployer implements ISkinResDeployer {

    @Override
    public void deploy(View view, SkinAttr skinAttr, ISkinResourceManager resource) {
        if (!(view instanceof ListView)) {
            return;
        }
        ListView listView = (ListView) view;
        if (SkinConfig.RES_TYPE_NAME_COLOR.equals(skinAttr.attrValueTypeName)) {
            int color = resource.getColor(skinAttr.attrValueRefId);
            listView.setDivider(new ColorDrawable(color));
        } else if (SkinConfig.RES_TYPE_NAME_DRAWABLE.equals(skinAttr.attrValueTypeName)) {
            listView.setDivider(resource.getDrawable(skinAttr.attrValueRefId));
        }
    }
}
