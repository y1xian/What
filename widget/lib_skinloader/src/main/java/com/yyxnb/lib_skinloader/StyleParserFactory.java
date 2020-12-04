package com.yyxnb.lib_skinloader;

import android.util.AttributeSet;
import android.view.View;

import com.yyxnb.lib_skinloader.bean.SkinAttr;
import com.yyxnb.lib_skinloader.parser.ProgressBarIndeterminateDrawableStyleParser;
import com.yyxnb.lib_skinloader.parser.TextViewTextColorStyleParser;
import com.yyxnb.lib_skinloader.skinInterface.ISkinStyleParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class StyleParserFactory {
    private static List<ISkinStyleParser> sStyleParserArray = new ArrayList<>();

    static {
        addStyleParser(new TextViewTextColorStyleParser());
        addStyleParser(new ProgressBarIndeterminateDrawableStyleParser());
    }

    public static void addStyleParser(ISkinStyleParser parser) {
        if (!sStyleParserArray.contains(parser)) {
            sStyleParserArray.add(parser);
        }
    }

    public static void parseStyle(View view, AttributeSet attrs, Map<String, SkinAttr> viewAttrs, String[] specifiedAttrList) {
        for (ISkinStyleParser parser : sStyleParserArray) {
            parser.parseXmlStyle(view, attrs, viewAttrs, specifiedAttrList);
        }
    }

}
