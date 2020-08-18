package com.yyxnb.common.interfaces

import androidx.lifecycle.DefaultLifecycleObserver

/**
 * 观察到对应的生命周期事件
 * 避免代码臃肿
 * 1.实现DefaultLifecycleObserver接口，然后重写里面生命周期方法；(Google官方推荐)
 * 2.直接实现LifecycleObserver接口，然后通过注解的方式来接收生命周期的变化；
 *
 *
 * LifecycleObserver接口（ Lifecycle观察者）：实现该接口的类，通过注解的方式，可以通过被 [androidx.lifecycle.Lifecycle.addObserver] 方法注册,
 * 被注册后，LifecycleObserver便可以观察到LifecycleOwner的生命周期事件。
 *
 *
 * LifecycleOwner接口（Lifecycle持有者）：实现该接口的类持有生命周期(Lifecycle对象)，该接口的生命周期(Lifecycle对象)的改变会被其注册的观察者LifecycleObserver观察到并触发其对应的事件。
 * [androidx.lifecycle.OnLifecycleEvent] 如：@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
 * [androidx.lifecycle.Lifecycle.Event]
 *
 *
 * Lifecycle(生命周期)：和LifecycleOwner不同的是，LifecycleOwner本身持有Lifecycle对象，LifecycleOwner通过其Lifecycle getLifecycle()的接口获取内部Lifecycle对象。
 *
 *
 * State(当前生命周期所处状态)。[androidx.lifecycle.Lifecycle.getCurrentState]
 *
 *
 * Event(当前生命周期改变对应的事件)：当Lifecycle发生改变，如进入onCreate,会自动发出ON_CREATE事件， @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
 *
 *
 * 注册 [androidx.lifecycle.Lifecycle.addObserver]
 * 移除 [androidx.lifecycle.Lifecycle.removeObserver]
 * 当前状态 [androidx.lifecycle.Lifecycle.getCurrentState]
 *
 * @author yyx
 */
interface ILifecycle : DefaultLifecycleObserver