package com.yyxnb.lib_skinloader.parser;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.yyxnb.lib_skinloader.SkinResDeployerFactory;
import com.yyxnb.lib_skinloader.bean.SkinAttr;
import com.yyxnb.lib_skinloader.bean.SkinConfig;
import com.yyxnb.lib_skinloader.skinInterface.ISkinStyleParser;
import com.yyxnb.lib_skinloader.util.ReflectUtils;

import java.util.Map;

/**
 * 解析Xml中的style属性，使支持style中定义的ProgressBar的indeterminateDrawable属性支持换肤
 */

public class ProgressBarIndeterminateDrawableStyleParser implements ISkinStyleParser {

    private static int[] sProgressBarStyleList;
    private static int sProgressBarIndeterminateDrawableIndex;

    @Override
    public void parseXmlStyle(View view, AttributeSet attrs, Map<String, SkinAttr> viewAttrs, String[] specifiedAttrList) {
        if (!ProgressBar.class.isAssignableFrom(view.getClass())) {
            return;
        }
        Context context = view.getContext();
        int[] progressBarStyleList = getProgressBarStyleable();
        int progressBarIndeterminateDrawableIndex = getProgressBarIndeterminateDrawableIndex();

        final TypedArray a = context.obtainStyledAttributes(attrs, progressBarStyleList, 0, 0);

        if (a != null) {
            int n = a.getIndexCount();
            for (int j = 0; j < n; j++) {
                int attr = a.getIndex(j);
                if (attr == progressBarIndeterminateDrawableIndex &&
                        SkinConfig.isCurrentAttrSpecified(SkinResDeployerFactory.PROGRESSBAR_INDETERMINATE_DRAWABLE, specifiedAttrList)) {
                    int drawableResId = a.getResourceId(attr, -1);
                    SkinAttr skinAttr = SkinAttributeParser.parseSkinAttr(context, SkinResDeployerFactory.PROGRESSBAR_INDETERMINATE_DRAWABLE, drawableResId);
                    if (skinAttr != null) {
                        viewAttrs.put(skinAttr.attrName, skinAttr);
                    }
                }
            }
            a.recycle();
        }
    }

    private static int[] getProgressBarStyleable() {
        if (sProgressBarStyleList == null || sProgressBarStyleList.length == 0) {
            sProgressBarStyleList = (int[]) ReflectUtils.getField("com.android.internal.R$styleable", "ProgressBar");
        }
        return sProgressBarStyleList;
    }

    private static int getProgressBarIndeterminateDrawableIndex() {
        if (sProgressBarIndeterminateDrawableIndex == 0) {
            Object o = ReflectUtils.getField("com.android.internal.R$styleable", "ProgressBar_indeterminateDrawable");
            if (o != null) {
                sProgressBarIndeterminateDrawableIndex = (int) o;
            }
        }
        return sProgressBarIndeterminateDrawableIndex;
    }
}
