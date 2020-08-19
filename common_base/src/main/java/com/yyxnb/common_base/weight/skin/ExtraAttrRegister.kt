package com.yyxnb.common_base.weight.skin

import com.yyxnb.skinloader.SkinResDeployerFactory

/**
 * 扩展换肤属性和style中的换肤属性
 */
object ExtraAttrRegister {

    const val CUSTIOM_VIEW_TEXT_COLOR = "bl_solid_color"

    fun init() {
        //增加自定义控件的自定义属性的换肤支持
        SkinResDeployerFactory.registerDeployer("tb_statusBarColor", TitleStatusColorResDeployer())
        SkinResDeployerFactory.registerDeployer("tb_titleBarColor", TitleColorResDeployer())
        SkinResDeployerFactory.registerDeployer("tb_centerTextColor", TitleTextColorResDeployer())
        SkinResDeployerFactory.registerDeployer("tb_bottomLineColor", TitleBottomLineColorResDeployer())

        //rv分割线
        SkinResDeployerFactory.registerDeployer("line", RecycleViewResDeployer())

//        SkinResDeployerFactory.registerDeployer(CUSTIOM_VIEW_TEXT_COLOR, new BLBackgroundColorResDeployer());

        //增加xml里的style中指定的View background属性换肤
//        StyleParserFactory.addStyleParser(new ViewBackgroundStyleParser());
    }
}