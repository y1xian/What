package com.yyxnb.arch.aop;

import android.app.Activity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

/**
 * Using AspectJ tools reach AOP. this class is used to inject
 * {@link com.yyxnb.arch.rigger.Rigger} to Activity's lifecycle and other methods.
 *
 * @author JingYeoh
 * <a href="mailto:yangjing9611@foxmail.com">Email me</a>
 * <a href="https://github.com/justkiddingbaby">Github</a>
 * <a href="http://blog.justkiddingbaby.com">Blog</a>
 * @since Nov 19,2017
 */
@Aspect
public class SwipeAspect {

    //****************PointCut***********************************

    /**
     * PointCut method.find all classes that is marked by
     * {@link Swiper} Annotation.
     */
    @Pointcut("@target(com.yyxnb.arch.annotations.Swiper)")
    public void annotatedWithSwiper() {
    }

    /**
     * PointCut method.find all classes that is marked by
     * {@link Puppet} Annotation.
     */
    @Pointcut("@target(com.yyxnb.arch.annotations.Puppet)")
    public void annotatedWithPuppet() {
    }

    //****************Helper************************************  && annotatedWithPuppet() && annotatedWithSwiper()

    @Pointcut("execution(* android.support.v4.app.FragmentActivity+.onCreate(..))")
    public void onCreate() {
    }

    @Pointcut("execution(* android.support.v4.app.FragmentActivity+.onDestroy(..))")
    public void onDestroy() {
    }

    //****************Process***********************************

    @Around("onCreate()")
    public Object onCreateProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        Object puppet = joinPoint.getTarget();
        //Only inject the class that marked by Puppet annotation.

        Method onCreate = invokeSwiperMethod("addToStack", Activity.class);
        onCreate.invoke(getSwiperInstance(), puppet);
        return result;
    }

    @Around("onDestroy()")
    public Object onDestroyProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        Object puppet = joinPoint.getTarget();
        //Only inject the class that marked by Puppet annotation.

        Method onDestroy = invokeSwiperMethod("removeFromStack", Activity.class);
        onDestroy.invoke(getSwiperInstance(), puppet);
        return result;
    }

    /**
     * Returns the instance of SwiperActivityManager class by reflect.
     */
    Object getSwiperInstance() throws Exception {
        Class<?> riggerClazz = Class.forName("com.yyxnb.arch.rigger.SwipeActivityManager");
        Method getInstance = riggerClazz.getDeclaredMethod("getInstance");
        getInstance.setAccessible(true);
        return getInstance.invoke(null);
    }

    /**
     * Returns the method object of SwiperActivityManager by reflect.
     */
    Method invokeSwiperMethod(String methodName, Class<?>... params) throws Exception {
        Object object = getSwiperInstance();
        Class<?> clazz = object.getClass();
        Method method = clazz.getDeclaredMethod(methodName, params);
        method.setAccessible(true);
        return method;
    }
}
