package com.yyxnb.what.arch.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 绑定 {@link android.arch.lifecycle.ViewModel}
 *
 * @author yyx
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindViewModel {
    boolean isActivity() default false;
}
