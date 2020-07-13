package com.yyxnb.arch.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
@Target指定Annotation用于修饰哪些程序元素。
@Target也包含一个名为”value“的成员变量，该value成员变量类型为ElementType[ ]，ElementType为枚举类型，值有如下几个：

ElementType.TYPE：能修饰类、接口或枚举类型
ElementType.FIELD：能修饰成员变量
ElementType.METHOD：能修饰方法
ElementType.PARAMETER：能修饰参数
ElementType.CONSTRUCTOR：能修饰构造器
ElementType.LOCAL_VARIABLE：能修饰局部变量
ElementType.ANNOTATION_TYPE：能修饰注解
ElementType.PACKAGE：能修饰包


RetentionPolicy.SOURCE	注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃
RetentionPolicy.CLASS	注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期
RetentionPolicy.RUNTIME	注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在

 */

/**
 * 资源绑定
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BindRes {

    /**
     * 布局id
     */
    int layoutRes() default 0;

    /**
     * 作用于Fragment判定为子页面，不处理状态栏
     */
    boolean subPage() default false;

    /**
     * 给系统窗口留出空间，xx屏
     */
    boolean fitsSystemWindows() default false;

    /**
     * 状态栏透明
     */
    boolean statusBarTranslucent() default true;

    /**
     * 状态栏文字颜色
     */
    @BarStyle int statusBarStyle() default BarStyle.DarkContent;

    /**
     * 状态栏颜色
     */
    int statusBarColor() default 0;

    /**
     * 侧滑 Fragment
     */
    @SwipeStyle int swipeBack() default SwipeStyle.Edge;

    /**
     * 页面是否需要登录
     */
    boolean needLogin() default false;

    /**
     * Activity是否作为容器
     */
    boolean isContainer() default false;

}
