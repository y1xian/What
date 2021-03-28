package com.yyxnb.what.core.interfaces;


import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 观察到对应的生命周期事件
 * 避免代码臃肿
 * 1.实现DefaultLifecycleObserver接口，然后重写里面生命周期方法；(Google官方推荐)
 * 2.直接实现LifecycleObserver接口，然后通过注解的方式来接收生命周期的变化；
 * <p>
 * LifecycleObserver接口（ Lifecycle观察者）：实现该接口的类，通过注解的方式，可以通过被 {@link Lifecycle#addObserver(LifecycleObserver)} 方法注册,
 * 被注册后，LifecycleObserver便可以观察到LifecycleOwner的生命周期事件。
 * <p>
 * LifecycleOwner接口（Lifecycle持有者）：实现该接口的类持有生命周期(Lifecycle对象)，该接口的生命周期(Lifecycle对象)的改变会被其注册的观察者LifecycleObserver观察到并触发其对应的事件。
 * {@link OnLifecycleEvent} 如：@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
 * {@link Lifecycle.Event}
 * <p>
 * Lifecycle(生命周期)：和LifecycleOwner不同的是，LifecycleOwner本身持有Lifecycle对象，LifecycleOwner通过其Lifecycle getLifecycle()的接口获取内部Lifecycle对象。
 * <p>
 * State(当前生命周期所处状态)。{@link Lifecycle#getCurrentState()}
 * <p>
 * Event(当前生命周期改变对应的事件)：当Lifecycle发生改变，如进入onCreate,会自动发出ON_CREATE事件， @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
 * <p>
 * 注册 {@link Lifecycle#addObserver(LifecycleObserver)}
 * 移除 {@link Lifecycle#removeObserver(LifecycleObserver)}
 * 当前状态 {@link Lifecycle#getCurrentState()}
 *
 * @author yyx
 */
public interface ILifecycle extends DefaultLifecycleObserver {
}
