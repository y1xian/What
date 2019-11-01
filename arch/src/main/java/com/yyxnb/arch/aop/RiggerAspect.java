package com.yyxnb.arch.aop;

import com.yyxnb.arch.rigger.Rigger;

import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

/**
 * Using AspectJ tools reach AOP. this class is used to define common method.
 *
 * @author JingYeoh
 * <a href="mailto:yj@viroyal.cn">Email me</a>
 * <a href="https://github.com/justkiddingbaby">Github</a>
 * <a href="http://blog.justkiddingbaby.com">Blog</a>
 * @since Jan 10,2018.
 */
class RiggerAspect {

    /**
     * PointCut method.find all classes that is marked by
     * {@link com.yyxnb.arch.annotations.Puppet} Annotation.
     */
    @Pointcut("@target(com.yyxnb.arch.annotations.Puppet)")
    public void annotatedWithPuppet() {
    }

    //****************Helper************************************

    /**
     * Returns the instance of Rigger class by reflect.
     */
    Rigger getRiggerInstance() throws Exception {
        Class<?> riggerClazz = Class.forName(Rigger.class.getName());
        Method getInstance = riggerClazz.getDeclaredMethod("getInstance");
        getInstance.setAccessible(true);
        return (Rigger) getInstance.invoke(null);
    }

    /**
     * Returns the method object of Rigger by reflect.
     */
    Method getRiggerMethod(String methodName, Class<?>... params) throws Exception {
        Rigger rigger = getRiggerInstance();
        Class<? extends Rigger> clazz = rigger.getClass();
        Method method = clazz.getDeclaredMethod(methodName, params);
        method.setAccessible(true);
        return method;
    }
}
