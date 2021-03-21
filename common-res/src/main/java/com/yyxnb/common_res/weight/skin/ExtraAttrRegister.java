package com.yyxnb.common_res.weight.skin;


import com.yyxnb.what.skinloader.SkinResDeployerFactory;

/**
 * 扩展换肤属性和style中的换肤属性
 */
public class ExtraAttrRegister {

    public static final String CUSTIOM_VIEW_TEXT_COLOR = "bl_solid_color";

    public static void init() {
        //增加自定义控件的自定义属性的换肤支持
        SkinResDeployerFactory.registerDeployer("tb_statusBarColor", new TitleStatusColorResDeployer());
        SkinResDeployerFactory.registerDeployer("tb_titleBarColor", new TitleColorResDeployer());
        SkinResDeployerFactory.registerDeployer("tb_centerTextColor", new TitleTextColorResDeployer());
        SkinResDeployerFactory.registerDeployer("tb_bottomLineColor", new TitleBottomLineColorResDeployer());

        //rv分割线
        SkinResDeployerFactory.registerDeployer("line", new RecycleViewResDeployer());

//        SkinResDeployerFactory.registerDeployer(CUSTIOM_VIEW_TEXT_COLOR, new BLBackgroundColorResDeployer());

        //增加xml里的style中指定的View background属性换肤
//        StyleParserFactory.addStyleParser(new ViewBackgroundStyleParser());
    }

}
